package com.acs.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

import com.acs.Constants;

public class FFMpegUtil {
	
	/*
	 * // ffmpeg.exe 경로 properties private static String FFMPEG_PATH;
	 * 
	 * @Value("${ffmpeg.path}") public void setFfmpeg(String value) {
	 * FFMpegUtil.FFMPEG_PATH = value;
	 * System.out.println("Value of num in the demo method "+
	 * FFMpegUtil.FFMPEG_PATH); }
	 * 
	 * // ffprobe.exe 경로 properties private static String FFPROBE_PATH;
	 * 
	 * @Value("${ffprobe.path}") public void setFfprobe(String value) {
	 * FFMpegUtil.FFPROBE_PATH = value; }
	 * 
	 * //동영상 파일 경로 private static String ORG_FILE_PATH;
	 * 
	 * @Value("${ffmpeg.file}") public void setPath(String value) {
	 * FFMpegUtil.ORG_FILE_PATH = value; }
	 */
	
	/*
	 * 동영상 파일 Get Media Information
	 */
	public static Map<String, Object> ffmpegInformations(Map<String, Object> params){
		Map<String, Object> resultMap = new HashMap<String, Object>();	
		System.out.println(Constants.FFMPEG_PATH);
		String  orgFileNm = Function.nvl(params.get("orgFileNm")).trim();
		
		try {
			FFmpeg ffmpeg = new FFmpeg(Constants.FFMPEG_PATH);
			FFprobe ffprobe = new FFprobe(Constants.FFPROBE_PATH);
			
			FFmpegProbeResult probeResult = ffprobe.probe(Constants.ORG_FILE_PATH + orgFileNm);
			FFmpegFormat format = probeResult.getFormat();
			System.out.format("%nFile: '%s' ; Format: '%s' ; Duration: %.3fs", 
				format.filename, 
				format.format_long_name,
				format.duration
			);

			FFmpegStream stream = probeResult.getStreams().get(0);
			System.out.format("%nCodec: '%s' ; Width: %dpx ; Height: %dpx",
				stream.codec_long_name,
				stream.width,
				stream.height
			);
			resultMap.put("filename", format.filename);
			resultMap.put("format_long_name", format.format_long_name);
			resultMap.put("duration", format.duration);
			
			resultMap.put("codec_long_name", stream.codec_long_name);
			resultMap.put("width", stream.width);
			resultMap.put("height", stream.height);
			
		} catch (IOException e) {
			System.out.print("e :: " + e);
			resultMap.put("result", "0001");
			return resultMap;
		}
		
		resultMap.put("result", "0000");
		return resultMap;
	}
	
	/*
	 * 동영상 파일 WMV파일 변환
	 */
	public static Map<String, Object> convertWmv(Map<String, Object> params){
		Map<String, Object> resultMap = new HashMap<String, Object>();	
		
		String  orgFileNm = Function.nvl(params.get("orgFileNm")).trim();
		resultMap.put("orgFileNm", orgFileNm);
		String  fileId = Function.nvl(params.get("fileId")).trim();
		
		try {
			FFmpeg ffmpeg = new FFmpeg(Constants.FFMPEG_PATH);
			FFprobe ffprobe = new FFprobe(Constants.FFPROBE_PATH);
			
			long beforeTime = System.currentTimeMillis();
			resultMap.put("beforeTime", beforeTime);
			resultMap.put("startTime", String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance()));
			
			String folder = Function.getCurrentDate();
			String downPath = Constants.ORG_FILE_PATH + "/wmv/" + folder;
			
			File dir = new File(downPath);
			if(!dir.exists())
				dir.mkdirs();
			
			String fileFolder = "/wmv/" + folder + "/";
			String fileStr = "wmv_";
			/*
			 * String fileId = fileStr+String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS",
			 * Calendar.getInstance()); fileId += System.nanoTime() +
			 * (Math.round(java.lang.Math.random()*1000000));
			 */	
			String saveFileName = fileId + ".wmv";
			System.out.println(saveFileName);
			
			resultMap.put("filePath", fileFolder);
			resultMap.put("saveFileName", saveFileName);
			
			FFmpegBuilder builder = new FFmpegBuilder()
					.setInput(Constants.ORG_FILE_PATH + "/" + orgFileNm) // 파일 경로
	                .overrideOutputFiles(true) 					 // 파일 덮어씌기
	                .addOutput(Constants.ORG_FILE_PATH + fileFolder + saveFileName) 		 // 생성되는 파일
	                //.setVideoWidth(1920)
	                //.setVideoHeight(1080)
	                
	                .disableSubtitle()       // No subtiles

	                .setAudioChannels(1)         // Mono audio
	                .setAudioCodec("aac")        // using the aac codec
	                .setAudioSampleRate(48_000)  // at 48KHz
	                .setAudioBitRate(32768)      // at 32 kbit/s

	                // .setVideoCodec("libx264")     // Video using x264
	                .setVideoCodec("msmpeg4") 
	                .setVideoFrameRate(24, 1)     // at 24 frames per second
	                .setVideoResolution(1920, 1080) 
	                //.setVideoResolution(640, 480) // at 640x480 resolution

	                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
	                .done();

			FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
			long afterTime = System.currentTimeMillis();
			long secDiffTime = (afterTime - beforeTime) / 1000;

			System.out.println("파일 변환 시간 => " + secDiffTime);
			
			resultMap.put("endTime", String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance()));
			resultMap.put("afterTime", afterTime);
			resultMap.put("secDiffTime", secDiffTime);
			resultMap.put("result", "sucess");
			
	    	executor.createJob(builder).run();
		
	    	
		} catch (IOException e) {
			System.out.print("e :: " + e);
			resultMap.put("result", "0001");
			return resultMap;
		}
		
		return resultMap;
		
	}
}
