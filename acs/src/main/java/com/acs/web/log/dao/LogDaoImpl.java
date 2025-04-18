package com.acs.web.log.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("LogDao")
public class LogDaoImpl implements LogDao{
	@Autowired 
	@Qualifier("mariaSqlSession")
	private SqlSession mariaSqlSession;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
    public int selectLogListCnt(Map<String, Object> paramMap) {
        return mariaSqlSession.selectOne("LogDao.selectLogListCnt", paramMap);
    }
	
	@Override
    public List<HashMap<String,Object>> selectLogList(Map<String, Object> paramMap) throws Exception{
    	return mariaSqlSession.selectList("LogDao.selectLogList", paramMap);
    }
	
	@Override
    public int logInsert(Map<String, Object> paramMap) {
        return mariaSqlSession.insert("LogDao.logInsert", paramMap);
    }
	
	
	
}
