package com.acs.schedule.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("TaskSchedulerDao")
public class TaskSchedulerDaoImpl implements TaskSchedulerDao{
	@Autowired 
	@Qualifier("mariaSqlSession")
	private SqlSession mariaSqlSession;
	
	@Override
    public int selectWmsIfNoCnt(Map<String, Object> paramMap) {
        return mariaSqlSession.selectOne("TaskSchedulerDao.selectWmsIfNoCnt", paramMap);
    }
	
	@Override
    public int wmsInsert(Map<String, Object> paramMap) {
        return mariaSqlSession.insert("TaskSchedulerDao.wmsInsert", paramMap);
    }
	
	@Override
    public int wmsUpdate(Map<String, Object> paramMap) {
        return mariaSqlSession.update("TaskSchedulerDao.wmsUpdate", paramMap);
    }
	
	@Override
    public int wmsLogInsert(Map<String, Object> paramMap) {
        return mariaSqlSession.insert("TaskSchedulerDao.wmsLogInsert", paramMap);
    }
	
	@Override
    public int orgFileInsert(Map<String, Object> paramMap) {
        return mariaSqlSession.insert("TaskSchedulerDao.orgFileInsert", paramMap);
    }
	
	@Override
    public int wmvFileInsert(Map<String, Object> paramMap) {
        return mariaSqlSession.insert("TaskSchedulerDao.wmvFileInsert", paramMap);
    }
	
	@Override
    public Map<String, Object> selectFileInfo(Map<String, Object> paramMap) throws Exception{
        return mariaSqlSession.selectOne("TaskSchedulerDao.selectFileInfo", paramMap);
    }
	
	@Override
    public Map<String, Object> selectOrgFileInfo(Map<String, Object> paramMap) throws Exception{
        return mariaSqlSession.selectOne("TaskSchedulerDao.selectOrgFileInfo", paramMap);
    }
	
	@Override
    public List<HashMap<String,Object>> selectFileOrderList(Map<String, Object> paramMap) throws Exception{
    	return mariaSqlSession.selectList("TaskSchedulerDao.selectFileOrderList", paramMap);
    }
}
