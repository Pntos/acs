package com.acs.hermes.wms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WmsDataService {
	public List<HashMap<String,Object>> selectHermesTimeCheckList(Map<String, Object> paramMap) throws Exception;
}
