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

@Repository("UserDao")
public class UserDaoImpl implements UserDao{
	@Autowired 
	@Qualifier("mariaSqlSession")
	private SqlSession mariaSqlSession;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
    public Map<String, Object> getUser(Map<String, Object> paramMap) throws Exception{
        return mariaSqlSession.selectOne("UserDao.getUser", paramMap);
    }
	
	@Override
    public int selectUserListCnt(Map<String, Object> paramMap) {
        return mariaSqlSession.selectOne("UserDao.selectUserListCnt", paramMap);
    }
	
	@Override
    public List<HashMap<String,Object>> selectUserList(Map<String, Object> paramMap) throws Exception{
    	return mariaSqlSession.selectList("UserDao.selectUserList", paramMap);
    }
	
	@Override
    public int selectUserId(Map<String, Object> paramMap) {
        return mariaSqlSession.selectOne("UserDao.selectUserId", paramMap);
    }
	
	@Override
    public int userInsert(Map<String, Object> paramMap) {
        return mariaSqlSession.insert("UserDao.userInsert", paramMap);
    }
	
	@Override
    public int userUpdate(Map<String, Object> paramMap) {
        return mariaSqlSession.update("UserDao.userUpdate", paramMap);
    }
	
}
