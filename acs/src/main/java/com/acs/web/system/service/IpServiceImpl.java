package com.acs.web.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.acs.web.system.dao.IpDao;

@Service("IpService")
public class IpServiceImpl implements IpService{
	@Resource(name="IpDao")
	private IpDao IpDao;
	
	@Override
	public int selectIpListCnt(Map<String, Object> paramMap) throws Exception{
		int totalCnt = IpDao.selectIpListCnt(paramMap);
		return totalCnt;	
	}
	
	@Override
	public List<HashMap<String,Object>> selectIpList(Map<String, Object> paramMap) throws Exception{
		List<HashMap<String,Object>> resultList = IpDao.selectIpList(paramMap);
		return resultList;
	}
	
	@Override
	public List<HashMap<String,Object>> selectCheckIpList(Map<String, Object> paramMap) throws Exception{
		List<HashMap<String,Object>> resultList = IpDao.selectCheckIpList(paramMap);
		return resultList;
	}
	
	@Override
	public int IpInsert(Map<String, Object> paramMap) throws Exception{
		int IpInsert = IpDao.IpInsert(paramMap);
		return IpInsert;	
	}
	
	@Override
	public int IpUpdate(Map<String, Object> paramMap) throws Exception{
		int IpUpdate = IpDao.IpUpdate(paramMap);
		return IpUpdate;	
	}
}
