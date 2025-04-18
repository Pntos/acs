package com.acs.schedule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.acs.Constants;
import com.acs.config.RestTemplateConfig;
import com.acs.schedule.service.TaskSchedulerService;
import com.acs.util.Function;
import com.acs.util.VideoFileUtils;
import com.acs.web.order.service.OrderService;

@Service("TaskScheduler")
public class TaskScheduler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TaskSchedulerService taskSchedulerService;
	
	@Autowired
	RestTemplateConfig RestTemplateConfig;
	
	@Autowired
	OrderService orderService;
	
	private static final Random random = new Random();
	private final int POOL_SIZE = 10;
	private ThreadPoolTaskScheduler scheduler;
	
	private String cron = "0 0/2 * * * *";
	
	@Async
	public void startScheduler() {
		scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(POOL_SIZE);
		//scheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
		scheduler.setThreadNamePrefix("MY-FILE-TASK-POOL"); 
		scheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		scheduler.initialize();
		// scheduler setting 
		scheduler.schedule(getRunnable(), getTrigger());
	}

	public void changeCronSet(String cron) {
		logger.info("sys cron ::     " + cron);
		this.cron = cron;
	}

	public void stopScheduler() {
		scheduler.shutdown();
	}
	
	private Runnable getRunnable() {
		// do something
		return () -> {
			try {
				String threadName = Thread.currentThread().getName();
		        logger.info("- " + threadName + " has been started");
		        
		        Thread thread = new Thread(() -> {
		            System.out.println(Thread.currentThread().getName()); // [결과]: Thread-0
		            try {
		              Thread.sleep(3000L); // 3초간 sleep
		            } catch (InterruptedException e) {
		              System.out.println("Interrupted!");
		              return; // Exit
		            }
		        });
		        thread.start();

		        logger.info(Thread.currentThread().getName()); // [결과]: main
		        getOrderFile();
		       
		        try {
	              thread.join();
		        } catch (InterruptedException e) {
	              // TODO Auto-generated catch blockP@ntos!@
	              e.printStackTrace();
		        } // thread가 끝날 때까지 기다립니다.
		        logger.info("Finished!");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

	private Trigger getTrigger() {
		// cronSetting
		return new CronTrigger(cron);
	}

	@PostConstruct
	public void init() {
		startScheduler();
	}

	@PreDestroy
	public void destroy() {
		stopScheduler();
	}
	
	public void getOrderFile() {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			List<HashMap<String, Object>> rList = taskSchedulerService.selectFileOrderList(params);
			
			if(rList.size() > 0) {
				//int actCnt = 0;
				for( int i=0; i<rList.size(); i++ ) {
					Map<String, Object> map = rList.get(i);
					
					String cmId = Function.nvl((String)map.get("CM_ID"));	// 카메라 아이디
					if(cmId.trim().length() > 0) {
						AxisFileDownload(map);
						//Thread.sleep(5000);
						
					}
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 * MP4 파일
	 */
	public String AxisFileDownload(Map<String, Object> map) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, IOException, ParseException{
		String state = "true";
		
		String voWmsNo = String.valueOf(map.get("VO_WMS_NO")).trim();
		String sdate = String.valueOf(map.get("START_DATE")).trim();
		String stime = String.valueOf(map.get("START_TIME")).trim();
		String edate = String.valueOf(map.get("END_DATE")).trim();
		String etime = String.valueOf(map.get("END_TIME")).trim();
		String cmNm = String.valueOf(map.get("CM_USER")).trim();
		String cmId = String.valueOf(map.get("CM_ID")).trim();
		String osDate = String.valueOf(map.get("ORG_START_DATE")).trim();
		String oeDate = String.valueOf(map.get("ORG_END_DATE")).trim();
		
		String time1 = Function.getDateTime9(osDate);
		String time2 = Function.getDateTime9(oeDate);
		System.out.println(time1);
		// 2022-11-01-010820
		String fir_url = "https://10.85.203.94:55756/Acs/Streaming/Video/Export/MP4/?";
		String sec_url = "camera=" + cmId + "&quality=high";
		String thi_url = "&start="+sdate+"-"+time1+"-9914500Z&end="+edate+"-"+time2+"-9934500Z&audio=0";
		String ApiUrl = fir_url+sec_url+thi_url;
		map.put("ApiUrl", ApiUrl);
		
		long beforeTime = System.currentTimeMillis();
		
		// 파일 저장 맵
		Map<String, Object> fileMap = new HashMap<String, Object>();
		fileMap.put("VO_WMS_NO", voWmsNo);
		fileMap.put("VO_CAMERA", cmId);
		fileMap.put("VO_STARTTIME", beforeTime);
		
		//HttpHeaders headers = new HttpHeaders();
		//headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
		
		//HttpHeaders headers = TaskScheduler.getHeaders();
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = RestTemplateConfig.restTemplate();
	    // Optional Accept header
	    //RequestCallback requestCallback = request -> request
	    //        .getHeaders()
	    //        .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
	    
		//MultiValueMap<String, Object> parts;
		
	    RequestCallback requestCallback = request -> {
	        request.getHeaders().addAll(headers);
	        request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
	        //FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
	        //formHttpMessageConverter.setCharset(Charset.forName("EUC-KR"));
	        //formHttpMessageConverter.write(parts, MediaType.APPLICATION_FORM_URLENCODED, request);
	   };
	      
	    // Streams the response instead of loading it all in memory
	    ResponseExtractor<Void> responseExtractor = response -> {
	        // Here you can write the inputstream to a file or any other place
	    	String folder = Function.getCurrentDate();
			String downPath = Constants.ORG_FILE_PATH + "/" + folder;
			
			File dir = new File(downPath);
			if(!dir.exists())
				dir.mkdirs();
			
			//System.out.println(response.getBody());
			
			if (response.getBody() != null) {
				String fileId = voWmsNo+"_"+String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		        fileId += System.nanoTime() + (Math.round(java.lang.Math.random()*1000000));
		        Path path = Paths.get(downPath + "/" + fileId + "_ACS_video.mp4");
		        Files.copy(response.getBody(), path);
		        
		        fileMap.put("VO_FILEPATH", downPath);
		        fileMap.put("VO_FILENAME", fileId + "_ACS_video.mp4");
		        
		        try {
					int fileInsert = taskSchedulerService.orgFileInsert(fileMap);
					Map<String, Object> upMap = new HashMap<String, Object>();
					upMap.put("VO_WMS_NO", voWmsNo);
					upMap.put("FILE_YN", "Y");
					upMap.put("ISRT_ID", "master");
					int orderUpdate = orderService.orderUpdate(upMap);
					//WMS 파일변환
					convertWmvFile(voWmsNo, folder, fileId, fileId + "_ACS_video.mp4");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	        
	        return null;
	    };
	    restTemplate.execute(ApiUrl, HttpMethod.GET, requestCallback, responseExtractor);
	    return state;
	}
	
	/*
	 * 
	 * WMV 파일변환
	 */
	public void convertWmvFile(String voWmsNo, String folder, String fileId, String orgFileName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgFileNm", folder+"/"+orgFileName);
		paramMap.put("fileId", fileId);
		
		//Map<String, Object> resultMap = FFMpegUtil.convertWmv(paramMap);
		Map<String, Object> resultMap = VideoFileUtils.convertWmv(paramMap);
		//System.out.println(" resultMap >> " + resultMap);
		
		String result = String.valueOf(resultMap.get("result"));
		String filePath = String.valueOf(resultMap.get("filePath"));
		String saveFileName = String.valueOf(resultMap.get("saveFileName"));
		String startTime = String.valueOf(resultMap.get("startTime"));
		String endTime = String.valueOf(resultMap.get("endTime"));
		String beforeTime = String.valueOf(resultMap.get("beforeTime"));
		String afterTime = String.valueOf(resultMap.get("afterTime"));
		String secDiffTime = String.valueOf(resultMap.get("secDiffTime"));
		
		if("sucess".equals(result)) {
			// WMV 파일정보
			Map<String, Object> wmvMap = new HashMap<String, Object>();
			wmvMap.put("VW_ORDERNO", voWmsNo);
			wmvMap.put("VW_FILEPATH", filePath);
			wmvMap.put("VW_FILENAME", saveFileName);
			wmvMap.put("VW_STARTTIME", startTime);
			wmvMap.put("VW_ENDTIME", endTime);
			//wmvMap.put("B_TIME", beforeTime);
			//wmvMap.put("A_TIME", afterTime);
			wmvMap.put("VW_DIFFTIME", secDiffTime);
			
			try {
				int wmsInsert = taskSchedulerService.wmvFileInsert(wmvMap);
				
				Map<String, Object> logMap = new HashMap<String, Object>();
				logMap.put("VS_STATE", "S");
				logMap.put("VS_CNT", voWmsNo);
				logMap.put("VS_TYPE", "03");
				logMap.put("ERR_MSG", "");
				taskSchedulerService.wmsLogInsert(logMap);
				
			} catch (Exception e) {
				
				Map<String, Object> logMap = new HashMap<String, Object>();
				logMap.put("VS_STATE", "f");
				logMap.put("VS_CNT", "0");
				logMap.put("VS_TYPE", "03");
				logMap.put("ERR_MSG", e);
				try {
					taskSchedulerService.wmsLogInsert(logMap);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				
			}
		}else{
			
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put("VS_STATE", "f");
			logMap.put("VS_CNT", "0");
			logMap.put("VS_TYPE", "03");
			logMap.put("ERR_MSG", "WMV 변환실패!");
			try {
				taskSchedulerService.wmsLogInsert(logMap);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
		
		
		
		
		
	}
	
	
	public static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept-Encoding", "gzip, deflate, sdch");
		return headers;
	}
	
	
	
	
	
	
	
	
	
}
