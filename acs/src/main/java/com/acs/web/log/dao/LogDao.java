package com.acs.web.log.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LogDao {
	public int selectLogListCnt(Map<String, Object> paramMap) throws Exception;
	public List<HashMap<String,Object>> selectLogList(Map<String, Object> paramMap) throws Exception;
	public int logInsert(Map<String, Object> paramMap) throws Exception;
}
