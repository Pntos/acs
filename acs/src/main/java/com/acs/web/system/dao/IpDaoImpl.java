package com.acs.web.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("IpDao")
public class IpDaoImpl implements IpDao{
	@Autowired 
	@Qualifier("mariaSqlSession")
	private SqlSession mariaSqlSession;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
    public int selectIpListCnt(Map<String, Object> paramMap) {
        return mariaSqlSession.selectOne("IpDao.selectIpListCnt", paramMap);
    }
	
	@Override
    public List<HashMap<String,Object>> selectIpList(Map<String, Object> paramMap) throws Exception{
    	return mariaSqlSession.selectList("IpDao.selectIpList", paramMap);
    }
	
	@Override
    public List<HashMap<String,Object>> selectCheckIpList(Map<String, Object> paramMap) throws Exception{
    	return mariaSqlSession.selectList("IpDao.selectCheckIpList", paramMap);
    }
	
	@Override
    public int IpInsert(Map<String, Object> paramMap) {
        return mariaSqlSession.insert("IpDao.IpInsert", paramMap);
    }
	
	@Override
    public int IpUpdate(Map<String, Object> paramMap) {
        return mariaSqlSession.update("IpDao.IpUpdate", paramMap);
    }
}
