package com.acs.web.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.acs.web.system.dao.UserDao;

@Service("UserService")
public class UserServiceImpl implements UserService{
	@Resource(name="UserDao")
	private UserDao UserDao;
	
	@Override
	public Map<String, Object> getUser(Map<String, Object> paramMap) throws Exception{	
		HashMap<String, Object> resultMap = (HashMap<String, Object>)UserDao.getUser(paramMap);
		return resultMap;	
	}
	
	@Override
	public int selectUserListCnt(Map<String, Object> paramMap) throws Exception{
		int totalCnt = UserDao.selectUserListCnt(paramMap);
		return totalCnt;	
	}
	
	@Override
	public List<HashMap<String,Object>> selectUserList(Map<String, Object> paramMap) throws Exception{
		List<HashMap<String,Object>> resultList = UserDao.selectUserList(paramMap);
		return resultList;
	}
	
	@Override
	public int selectUserId(Map<String, Object> paramMap) throws Exception{
		int checkIdCnt = UserDao.selectUserId(paramMap);
		return checkIdCnt;	
	}
	
	@Override
	public int userInsert(Map<String, Object> paramMap) throws Exception{
		int userInsert = UserDao.userInsert(paramMap);
		return userInsert;	
	}
	
	@Override
	public int userUpdate(Map<String, Object> paramMap) throws Exception{
		int userUpdate = UserDao.userUpdate(paramMap);
		return userUpdate;	
	}
	
}
