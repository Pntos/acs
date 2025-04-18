package com.acs.web.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("OrderDao")
public class OrderDaoImpl implements OrderDao{
	@Autowired 
	@Qualifier("mariaSqlSession")
	private SqlSession mariaSqlSession;
	
	@Override
    public Map<String, Object> selectOrderTopCnt(Map<String, Object> paramMap) throws Exception{
        return mariaSqlSession.selectOne("OrderDao.selectOrderTopCnt", paramMap);
    }
	
	@Override
    public int selectOrderListCnt(Map<String, Object> paramMap) {
        return mariaSqlSession.selectOne("OrderDao.selectOrderListCnt", paramMap);
    }
	
	@Override
    public List<HashMap<String,Object>> selectOrderList(Map<String, Object> paramMap) throws Exception{
    	return mariaSqlSession.selectList("OrderDao.selectOrderList", paramMap);
    }
	
	@Override
    public List<HashMap<String,Object>> selectFileOrderList(Map<String, Object> paramMap) throws Exception{
    	return mariaSqlSession.selectList("OrderDao.selectFileOrderList", paramMap);
    }
	
	@Override
    public int orderUpdate(Map<String, Object> paramMap) {
        return mariaSqlSession.update("OrderDao.orderUpdate", paramMap);
    }
	
}
