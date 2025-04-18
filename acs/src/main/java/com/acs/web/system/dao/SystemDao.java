package com.acs.web.system.dao;

import java.util.Map;

public interface SystemDao {
	public int selectSystemCnt(Map<String, Object> paramMap) throws Exception;
	public  Map<String, Object> selectSystemInfo(Map<String, Object> paramMap) throws Exception;	
	public int systemInsert(Map<String, Object> paramMap) throws Exception;
	public int systemUpdate(Map<String, Object> paramMap) throws Exception;
}
