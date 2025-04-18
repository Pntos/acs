package com.acs.web.system.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("SystemDao")
public class SystemDaoImpl implements SystemDao{
	@Autowired 
	@Qualifier("mariaSqlSession")
	private SqlSession mariaSqlSession;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
    public int selectSystemCnt(Map<String, Object> paramMap) {
        return mariaSqlSession.selectOne("SystemDao.selectSystemCnt", paramMap);
    }
	
	@Override
    public Map<String, Object> selectSystemInfo(Map<String, Object> paramMap) throws Exception{
        return mariaSqlSession.selectOne("SystemDao.selectSystemInfo", paramMap);
    }
	
	@Override
    public int systemInsert(Map<String, Object> paramMap) {
        return mariaSqlSession.insert("SystemDao.systemInsert", paramMap);
    }
	
	@Override
    public int systemUpdate(Map<String, Object> paramMap) {
        return mariaSqlSession.update("SystemDao.systemUpdate", paramMap);
    }
}
