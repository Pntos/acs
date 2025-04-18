package com.acs.web.log.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LogSercive {
	public int selectLogListCnt(Map<String, Object> paramMap) throws Exception;
	public List<HashMap<String,Object>> selectLogList(Map<String, Object> paramMap) throws Exception;
	public int logInsert(Map<String, Object> paramMap) throws Exception;
}
