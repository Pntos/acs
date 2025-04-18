package com.acs.web.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.acs.web.order.dao.OrderDao;

@Service("OrderService")
public class OrderServiceImpl implements OrderService{
	@Resource(name="OrderDao")
	private OrderDao OrderDao;
	
	@Override
	public Map<String, Object> selectOrderTopCnt(Map<String, Object> paramMap) throws Exception{	
		HashMap<String, Object> resultMap = (HashMap<String, Object>)OrderDao.selectOrderTopCnt(paramMap);
		return resultMap;	
	}
	
	@Override
	public int selectOrderListCnt(Map<String, Object> paramMap) throws Exception{
		int totalCnt = OrderDao.selectOrderListCnt(paramMap);
		return totalCnt;	
	}
	
	@Override
	public List<HashMap<String,Object>> selectOrderList(Map<String, Object> paramMap) throws Exception{
		List<HashMap<String,Object>> resultList = OrderDao.selectOrderList(paramMap);
		return resultList;
	}
	
	@Override
	public int orderUpdate(Map<String, Object> paramMap) throws Exception{
		int orderUpdate = OrderDao.orderUpdate(paramMap);
		return orderUpdate;	
	}
}
