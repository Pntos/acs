package com.acs.ffmpeg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.acs.view.BaseAjaxView;
import com.google.gson.Gson;

import com.acs.hermes.wms.service.WmsDataService;
import com.acs.config.RestTemplateConfig;

@RestController
@EnableAutoConfiguration
public class FFmpegController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	WmsDataService wmsDataService;

	@Autowired
	RestTemplateConfig RestTemplateConfig;
	
	
	@RequestMapping(value = "/api/ffmpeg/selectMpegTest.json", produces = "application/json; charset=utf8")
	public @ResponseBody String selectMpegTest(HttpServletRequest request , HttpSession session, @RequestParam Map<String, Object> params) throws IOException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, ParseException{	
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();        
				
		String cameraId = "14123_10872797-d29b-44dc-a363-bc81528e7c3c";
		String StartTime = "2022-11-01 01:08:20";
		String StopTime = "2022-11-01 01:10:55";
		String StartIndex = "0";
		String NumberOfElements = "20";

		String paramStr = "{\"cameraIds\": [{\"Id\": \""+cameraId+"\"}],"
			 + "\"interval\": {\"StartTime\": \""+StartTime+"\",\"StopTime\": \""+StopTime+"\"},"
			 + "\"range\": {\"StartIndex\": "+StartIndex+",\"NumberOfElements\": "+NumberOfElements
			 + "}}";
		
        final String API_URL = "https://10.85.203.94:55756/Acs/Api/RecordingFacade/GetRecordings"; //GetRecordedMedia
        HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                
        RestTemplate restTemplate = RestTemplateConfig.restTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        restTemplate.getMessageConverters().add(converter);
        
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(paramStr, headers);
		ResponseEntity<String> result = restTemplate.exchange(API_URL ,HttpMethod.POST, requestEntity, String.class);
		logger.info("<<<< getBody     : "  + result.getBody()); 
		logger.info("<<<< getHeaders     : "  + result.getHeaders());
		logger.info("<<<< getStatusCodeValue     : "  + result.getStatusCodeValue());
		logger.info("<<<< getStatusCode     : "  + result.getStatusCode());
       
		JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result.getBody());
        JSONArray jsonArray = (JSONArray)jsonObject.get("RecordedMedia");
        
        logger.info("<<<< jsonArray     : "  + jsonArray);
        
        JSONObject local = (JSONObject)jsonArray.get(0);
        logger.info("<<<< local     : "  + local);
    	//JSONObject RecordingId = (JSONObject)local.get("RecordingId");
    	//JSONObject CameraId = (JSONObject)local.get("CameraId");
    	//JSONObject QualityLevel = (JSONObject)local.get("QualityLevel");
    	//JSONObject VideoTrack = (JSONObject)local.get("VideoTrack");
    	
    	//logger.info("<<<< RecordingId     : "  + RecordingId);
    	//logger.info("<<<< CameraId     : "  + CameraId);
    	//logger.info("<<<< QualityLevel     : "  + QualityLevel);
    	//logger.info("<<<< VideoTrack     : "  + VideoTrack);
		
		return gs.toJson(brv);	
	}	
	
	@RequestMapping(value = "/api/ffmpeg/selectMpegTest2.json", produces = "application/json; charset=utf8")
	public @ResponseBody String selectMpegTest2(HttpSession session, @RequestParam Map<String, Object> params) throws IOException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException{	
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();   
		
		final String API_URL = "https://10.85.203.94:55756/Acs/Streaming/Video/Export/MP4/?camera=14123_10872797-d29b-44dc-a363-bc81528e7c3c&quality=high&start=2022-11-01-010820-9914500Z&end=2022-11-01-011055-9934500Z&audio=0";
		//final String API_URL = "https://10.85.203.94:55756/Acs/Streaming/Video/Export/MP4/?camera=14123_10872797-d29b-44dc-a363-bc81528e7c3c&quality=medium&start=2022-11-01-024623-9914500Z&end=2022-11-01-025529-9934500Z&audio=0";
		//final String API_URL = "https://10.85.203.94:55756/Acs/Streaming/Video/Export/MP4/?camera=14123_10872797-d29b-44dc-a363-bc81528e7c3c&quality=low&start=2022-11-01-032215-9914500Z&end=2022-11-01-035525-9934500Z&audio=0";
		
		String outputDir = "D:/4k/upload";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM)); 
		HttpEntity<String> entity = new	HttpEntity<>(headers);
		
		RestTemplate restTemplate = RestTemplateConfig.restTemplate();
		ResponseEntity<byte[]> result = restTemplate.exchange(API_URL, HttpMethod.GET, entity, byte[].class);
		String filename = result.getHeaders().getContentDisposition().getFilename();
	    Path path = Paths.get(outputDir + "/" + filename);
	    Files.write(path, result.getBody());
		
		return gs.toJson(brv);	
	}	
	
	
}
