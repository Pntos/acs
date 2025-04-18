package com.acs.hermes.wms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.acs.hermes.wms.dao.WmsDataDao;

@Service("WmsDataService")
public class WmsDataServiceImpl implements WmsDataService{
	@Resource(name="WmsDataDao")
	private WmsDataDao WmsDataDao;
	
	@Override
	public List<HashMap<String,Object>> selectHermesTimeCheckList(Map<String, Object> paramMap) throws Exception{
		List<HashMap<String,Object>> resultList = WmsDataDao.selectHermesTimeCheckList(paramMap);
		return resultList;
	}
}
