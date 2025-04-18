package com.acs.util;

import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.modelmapper.internal.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.acs.Constants;

import javax.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class VideoFileUtils {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private String ffmpegPath = Constants.FFMPEG_PATH;
    private String ffprobePath = Constants.FFPROBE_PATH;

    private static FFmpeg ffmpeg;
    private static FFprobe ffprobe;
    
    @PostConstruct
    public void init(){
        try {
            ffmpeg = new FFmpeg(ffmpegPath);
            Assert.isTrue(ffmpeg.isFFmpeg());

            ffprobe = new FFprobe(ffprobePath);
            Assert.isTrue(ffprobe.isFFprobe());

            logger.debug("VideoFileUtils init complete.");
        } catch (Exception e) {
        	logger.error("VideoFileUtils init fail.", e);
        }
    }
    
    public void getMediaInfo(String filePath) throws IOException {
        FFmpegProbeResult probeResult = ffprobe.probe(filePath);

        if(logger.isDebugEnabled()){
        	logger.debug("========== VideoFileUtils.getMediaInfo() ==========");
        	logger.debug("filename : {}", probeResult.getFormat().filename);
        	logger.debug("format_name : {}", probeResult.getFormat().format_name);
        	logger.debug("format_long_name : {}", probeResult.getFormat().format_long_name);
        	logger.debug("tags : {}", probeResult.getFormat().tags.toString());
        	logger.debug("duration : {} second", probeResult.getFormat().duration);
        	logger.debug("size : {} byte", probeResult.getFormat().size);

        	logger.debug("width : {} px", probeResult.getStreams().get(0).width);
        	logger.debug("height : {} px", probeResult.getStreams().get(0).height);
        	logger.debug("===================================================");
        }
    }
    
    public static Map<String, Object> convertWmv(Map<String, Object> params){
    	Map<String, Object> resultMap = new HashMap<String, Object>();	
		
		String  orgFileNm = Function.nvl(params.get("orgFileNm")).trim();
		resultMap.put("orgFileNm", orgFileNm);
		String  fileId = Function.nvl(params.get("fileId")).trim();
		
		long beforeTime = System.currentTimeMillis();
		resultMap.put("beforeTime", beforeTime);
		resultMap.put("startTime", String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance()));
		
		String folder = Function.getCurrentDate();
		String downPath = Constants.ORG_FILE_PATH + "/wmv/" + folder;
		
		File dir = new File(downPath);
		if(!dir.exists())
			dir.mkdirs();
		
		String fileFolder = "/wmv/" + folder + "/";
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

		long afterTime = System.currentTimeMillis();
		long secDiffTime = (afterTime - beforeTime) / 1000;

		System.out.println("파일 변환 시간 => " + secDiffTime);
		
		resultMap.put("endTime", String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance()));
		resultMap.put("afterTime", afterTime);
		resultMap.put("secDiffTime", secDiffTime);
		resultMap.put("result", "sucess");
		
		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		executor.createJob(builder).run();
		
		return resultMap;
        
        
        
    }
    
    
}
