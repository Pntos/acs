package com.acs.schedule;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.acs.hermes.wms.service.WmsDataService;
import com.acs.schedule.service.TaskSchedulerService;

@Service
public class WmsScheduler {
	@Autowired
	WmsDataService wmsDataService;
	
	@Autowired
	TaskSchedulerService taskSchedulerService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	* WMS 전문처리
	* 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
	 * @throws InterruptedException 
	*/
	@Scheduled(cron = "0 0/5 * * * *")
	@Async
	public void scheduleTaskUsingCronExpression() throws InterruptedException {
		getWmsData();
		Thread.sleep(2000);
	}

	public void getWmsData() {
		System.out.println("schedule tasks using cron jobs --->   " +  LocalDateTime.now().toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        logger.info("작업 날짜 시간 fixedRate::" + strDate);
        logger.info("현재 쓰레드 : "+ Thread.currentThread().getName());
        
		try { 
			Map<String, Object> params = new HashMap<String, Object>();
			//params.put("SEND_YN", "Y");	// 수신여부
			List<HashMap<String, Object>> rList = wmsDataService.selectHermesTimeCheckList(params); 
			
			if(rList.size() > 0) {
				int actCnt = 0;
				for( int i=0; i<rList.size(); i++ ) {
					Map<String, Object> map = rList.get(i);
					String INTERFACE_ID = String.valueOf(map.get("INTERFACE_ID")).trim();
					String ORDER_TYPE = String.valueOf(map.get("ORDER_TYPE")).trim();
					String ORDER_NO = String.valueOf(map.get("ORDER_NO")).trim();
					String ALF_PACK_NO = String.valueOf(map.get("ALF_PACK_NO")).trim();
					String START_DATE = String.valueOf(map.get("START_DATE")).trim();
					String END_DATE = String.valueOf(map.get("END_DATE")).trim();
					String REMOVE_YN = String.valueOf(map.get("REMOVE_YN")).trim();
					String SEND_YN = String.valueOf(map.get("SEND_YN")).trim();
					String ADDWHO = String.valueOf(map.get("ADDWHO")).trim();
					String ADDDATE = String.valueOf(map.get("ADDDATE")).trim();
					String EDITWHO = String.valueOf(map.get("ADDDATE")).trim();
					String EDITDATE = String.valueOf(map.get("EDITDATE")).trim();
					
					Map<String, Object> paramMap = new HashMap<String, Object>();
					
					// 캔슬처리
					if("Y".equals(REMOVE_YN)) {
						paramMap.put("VO_STATE", "C");
					}else {
						paramMap.put("VO_STATE", "S");
					}
					
					paramMap.put("INTERFACE_ID", INTERFACE_ID);
					paramMap.put("ORDER_TYPE", ORDER_TYPE);
					paramMap.put("ORDER_NO", ORDER_NO);
					paramMap.put("ALF_PACK_NO", ALF_PACK_NO);
					
					if(START_DATE.trim().length()==0 || "null".equals(START_DATE)) {
						paramMap.put("START_DATE", "0000-00-00 00:00:00");
					}else{
						paramMap.put("START_DATE", START_DATE);
					}
					if(END_DATE.trim().length()==0 || "null".equals(END_DATE)) {
						paramMap.put("END_DATE", "0000-00-00 00:00:00");
					}else{
						paramMap.put("END_DATE", END_DATE);
					}
					
					paramMap.put("REMOVE_YN", REMOVE_YN);
					paramMap.put("SEND_YN", SEND_YN);
					paramMap.put("ISRT_ID", ADDWHO);
					paramMap.put("ISRT_DTM", ADDDATE);
					paramMap.put("UPDT_ID", EDITWHO);
					paramMap.put("UPDT_DTM", EDITDATE);
					System.out.println("paramMap --->   " +  paramMap);
					
					int dataCnt = taskSchedulerService.selectWmsIfNoCnt(paramMap);
					System.out.println("dataCnt --->   " +  dataCnt);
					
					if(dataCnt==0) {
						taskSchedulerService.wmsInsert(paramMap);
						actCnt++;
					}else{
						Map<String, Object> paramUpMap = new HashMap<String, Object>();
						if("Y".equals(REMOVE_YN)) {
							paramUpMap.put("VO_STATE", "C");
						}else{
							paramUpMap.put("VO_STATE", "S");
						}
						paramUpMap.put("INTERFACE_ID", INTERFACE_ID);
						paramUpMap.put("REMOVE_YN", REMOVE_YN);
						taskSchedulerService.wmsUpdate(paramUpMap);
					}
				}
				
				if(actCnt > 0) {
					Map<String, Object> logMap = new HashMap<String, Object>();
					logMap.put("VS_STATE", "S");
					logMap.put("VS_CNT", actCnt);
					logMap.put("VS_TYPE", "01");
					logMap.put("ERR_MSG", "");
					taskSchedulerService.wmsLogInsert(logMap);
				}
				
				
			}
			
			
		
		} catch (Exception e) { 
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put("VS_STATE", "f");
			logMap.put("VS_CNT", "0");
			logMap.put("VS_TYPE", "01");
			logMap.put("ERR_MSG", e);
			try {
				taskSchedulerService.wmsLogInsert(logMap);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace(); 
		}
		
	}
	
	
	
	
}
