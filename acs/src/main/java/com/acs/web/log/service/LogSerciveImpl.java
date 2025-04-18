package com.acs.web.log.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.acs.web.log.dao.LogDao;

@Service("LogService")
public class LogSerciveImpl implements LogSercive{
	@Resource(name="LogDao")
	private LogDao LogDao;
	
	@Override
	public int selectLogListCnt(Map<String, Object> paramMap) throws Exception{
		int totalCnt = LogDao.selectLogListCnt(paramMap);
		return totalCnt;	
	}
	
	@Override
	public List<HashMap<String,Object>> selectLogList(Map<String, Object> paramMap) throws Exception{
		List<HashMap<String,Object>> resultList = LogDao.selectLogList(paramMap);
		return resultList;
	}
	
	@Override
	public int logInsert(Map<String, Object> paramMap) throws Exception{
		int logInsert = LogDao.logInsert(paramMap);
		return logInsert;	
	}
	
	
}
