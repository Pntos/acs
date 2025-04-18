package com.acs.web.system.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.acs.web.system.dao.SystemDao;

@Service("SystemService")
public class SystemServiceImpl implements SystemService{
	@Resource(name="SystemDao")
	private SystemDao SystemDao;
	
	@Override
	public int selectSystemCnt(Map<String, Object> paramMap) throws Exception{
		int totalCnt = SystemDao.selectSystemCnt(paramMap);
		return totalCnt;	
	}
	
	@Override
	public Map<String, Object> selectSystemInfo(Map<String, Object> paramMap) throws Exception{	
		HashMap<String, Object> resultMap = (HashMap<String, Object>)SystemDao.selectSystemInfo(paramMap);
		return resultMap;	
	}
	
	@Override
	public int systemInsert(Map<String, Object> paramMap) throws Exception{
		int systemInsert = SystemDao.systemInsert(paramMap);
		return systemInsert;	
	}
	
	@Override
	public int systemUpdate(Map<String, Object> paramMap) throws Exception{
		int systemUpdate = SystemDao.systemUpdate(paramMap);
		return systemUpdate;	
	}
}
