package com.acs.web.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderService {
	public Map<String, Object> selectOrderTopCnt(Map<String, Object> paramMap) throws Exception;
	public int selectOrderListCnt(Map<String, Object> paramMap) throws Exception;
	public List<HashMap<String,Object>> selectOrderList(Map<String, Object> paramMap) throws Exception;
	public int orderUpdate(Map<String, Object> paramMap) throws Exception;
}
