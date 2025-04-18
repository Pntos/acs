package com.acs.hermes.wms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("WmsDataDao")
public class WmsDataDaoImpl implements WmsDataDao{
	@Autowired 
	@Qualifier("oracleSqlSession")
	private SqlSession oracleSqlSession;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
    public List<HashMap<String,Object>> selectHermesTimeCheckList(Map<String, Object> paramMap) throws Exception{
    	return oracleSqlSession.selectList("WmsDataDao.selectHermesTimeCheckList", paramMap);
    }
	
	
	
	
}
