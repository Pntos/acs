package com.acs.hermes.wms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WmsDataDao {
	public List<HashMap<String,Object>> selectHermesTimeCheckList(Map<String, Object> paramMap) throws Exception;
}
