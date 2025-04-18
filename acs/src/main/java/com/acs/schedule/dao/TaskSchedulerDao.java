package com.acs.schedule.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TaskSchedulerDao {
	public int selectWmsIfNoCnt(Map<String, Object> paramMap) throws Exception;
	public int wmsInsert(Map<String, Object> paramMap) throws Exception;
	public int wmsUpdate(Map<String, Object> paramMap) throws Exception;
	
	public int wmsLogInsert(Map<String, Object> paramMap) throws Exception;
	public int orgFileInsert(Map<String, Object> paramMap) throws Exception;
	public int wmvFileInsert(Map<String, Object> paramMap) throws Exception;
	public Map<String, Object> selectOrgFileInfo(Map<String, Object> paramMap) throws Exception;
	public Map<String, Object> selectFileInfo(Map<String, Object> paramMap) throws Exception;	
	public List<HashMap<String,Object>> selectFileOrderList(Map<String, Object> paramMap) throws Exception;
}
