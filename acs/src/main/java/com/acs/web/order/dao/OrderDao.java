package com.acs.web.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderDao {
	public Map<String, Object> selectOrderTopCnt(Map<String, Object> paramMap) throws Exception;
	public int selectOrderListCnt(Map<String, Object> paramMap) throws Exception;
	public List<HashMap<String,Object>> selectOrderList(Map<String, Object> paramMap) throws Exception;
	
	public List<HashMap<String,Object>> selectFileOrderList(Map<String, Object> paramMap) throws Exception;
	public int orderUpdate(Map<String, Object> paramMap) throws Exception;
}
