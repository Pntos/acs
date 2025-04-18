package com.acs.web.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {
	public Map<String, Object> getUser(Map<String, Object> paramMap) throws Exception;	
	
	public int selectUserListCnt(Map<String, Object> paramMap) throws Exception;
	public List<HashMap<String,Object>> selectUserList(Map<String, Object> paramMap) throws Exception;
	
	public int selectUserId(Map<String, Object> paramMap) throws Exception;
	
	public int userInsert(Map<String, Object> paramMap) throws Exception;
	public int userUpdate(Map<String, Object> paramMap) throws Exception;
}
