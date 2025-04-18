package com.acs.web.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IpDao {
	public int selectIpListCnt(Map<String, Object> paramMap) throws Exception;
	public List<HashMap<String,Object>> selectIpList(Map<String, Object> paramMap) throws Exception;
	public List<HashMap<String,Object>> selectCheckIpList(Map<String, Object> paramMap) throws Exception;
	public int IpInsert(Map<String, Object> paramMap) throws Exception;
	public int IpUpdate(Map<String, Object> paramMap) throws Exception;
}
