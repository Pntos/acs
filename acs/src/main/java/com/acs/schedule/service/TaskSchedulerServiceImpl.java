package com.acs.schedule.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.acs.schedule.dao.TaskSchedulerDao;

@Service("TaskSchedulerService")
public class TaskSchedulerServiceImpl implements TaskSchedulerService{
	@Resource(name="TaskSchedulerDao")
	private TaskSchedulerDao TaskSchedulerDao;
	
	@Override
	public int selectWmsIfNoCnt(Map<String, Object> paramMap) throws Exception{
		int intefaceNo = TaskSchedulerDao.selectWmsIfNoCnt(paramMap);
		return intefaceNo;	
	}
	
	@Override
	public int wmsInsert(Map<String, Object> paramMap) throws Exception{
		int wmsInsert = TaskSchedulerDao.wmsInsert(paramMap);
		return wmsInsert;	
	}
	
	@Override
	public int wmsUpdate(Map<String, Object> paramMap) throws Exception{
		int wmsUpdate = TaskSchedulerDao.wmsUpdate(paramMap);
		return wmsUpdate;	
	}
	
	@Override
	public int wmsLogInsert(Map<String, Object> paramMap) throws Exception{
		int wmsLogInsert = TaskSchedulerDao.wmsLogInsert(paramMap);
		return wmsLogInsert;	
	}
	
	@Override
	public int orgFileInsert(Map<String, Object> paramMap) throws Exception{
		int orgFileInsert = TaskSchedulerDao.orgFileInsert(paramMap);
		return orgFileInsert;	
	}
	
	@Override
	public int wmvFileInsert(Map<String, Object> paramMap) throws Exception{
		int wmvFileInsert = TaskSchedulerDao.wmvFileInsert(paramMap);
		return wmvFileInsert;	
	}
	
	
	
	@Override
	public Map<String, Object> selectOrgFileInfo(Map<String, Object> paramMap) throws Exception{	
		HashMap<String, Object> resultMap = (HashMap<String, Object>)TaskSchedulerDao.selectOrgFileInfo(paramMap);
		return resultMap;	
	}
	
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> paramMap) throws Exception{	
		HashMap<String, Object> resultMap = (HashMap<String, Object>)TaskSchedulerDao.selectFileInfo(paramMap);
		return resultMap;	
	}
	
	@Override
	public List<HashMap<String,Object>> selectFileOrderList(Map<String, Object> paramMap) throws Exception{
		List<HashMap<String,Object>> resultList = TaskSchedulerDao.selectFileOrderList(paramMap);
		return resultList;
	}
	
}
