package com.acs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SftpUtil {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    // Set the prompt when logging in for the first time. Optional value: (ask | yes | no)
    private static final String SESSION_CONFIG_STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";

    String host ="";
    String username ="";
    String password = "";
    String root = "/home/";
    
    int port = 22;
    int timeout = 15000;
    
    public SftpUtil(String host , String username , String password){
    	this.host = host;
    	this.username = username;
    	this.password = password;
    }
    
    private ChannelSftp createSftp() throws Exception {
		JSch jsch = new JSch();
		Session session = createSession(jsch, host, username, port);
		session.setPassword(password);
		session.connect(timeout);

		Channel channel = session.openChannel("sftp");
		channel.connect(timeout);

		return (ChannelSftp) channel;
	}
    
    private Session createSession(JSch jsch, String host, String username, Integer port) throws Exception {
    	 Session session = null;
         if (port <= 0) {
             session = jsch.getSession(username, host);
         } else {
             session = jsch.getSession(username, host, port);
         }
         if (session == null) {
             throw new Exception(host + " session is null");
         }
         session.setConfig(SESSION_CONFIG_STRICT_HOST_KEY_CHECKING, "no");
         return session;
    }
    
    private void disconnect(ChannelSftp sftp) {
        try {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                } else if (sftp.isClosed()) {
                }
                if (null != sftp.getSession()) {
                    sftp.getSession().disconnect();
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
    
    public boolean uploadFile(String targetPath, File file) throws Exception {
        return this.uploadFile(targetPath, new FileInputStream(file));
    }
    
    private boolean uploadFile(String targetPath, InputStream inputStream) throws Exception {
        ChannelSftp sftp = this.createSftp();
        try {
            sftp.cd(root);
            int index = targetPath.lastIndexOf("/");
            String fileName = targetPath.substring(index + 1);
            sftp.put(inputStream, fileName);
            return true;
        } catch (Exception e) {
            throw new Exception("Upload File failure");
        } finally {
            this.disconnect(sftp);
        }
    }

    public boolean uploadFile2(String targetPath, InputStream inputStream) throws Exception {
        ChannelSftp sftp = this.createSftp();
        try {
            sftp.cd(root);
            logger.info("Change path to {}", root);
     
            int index = targetPath.lastIndexOf("/");
            String fileDir = targetPath.substring(0, index);
            String fileName = targetPath.substring(index + 1);
            boolean dirs = this.createDirs(fileDir, sftp);
            if (!dirs) {
            	logger.error("Remote path error. path:{}", targetPath);
                throw new Exception("Upload File failure");
            }
            sftp.put(inputStream, fileName);
            return true;
        } catch (Exception e) {
        	logger.error("Upload file failure. TargetPath: {}", targetPath, e);
            throw new Exception("Upload File failure");
        } finally {
            this.disconnect(sftp);
        }
    }
    
    private boolean createDirs(String dirPath, ChannelSftp sftp) {
        if (dirPath != null && !dirPath.isEmpty()
                    && sftp != null) {
            String[] dirs = Arrays.stream(dirPath.split("/"))
            		//.filter(StringUtils::isNotBlank)
                   .filter(String::isEmpty)
                   .toArray(String[]::new);
     
            for (String dir : dirs) {
                try {
                    sftp.cd(dir);
                    logger.info("Change directory {}", dir);
                } catch (Exception e) {
                    try {
                        sftp.mkdir(dir);
                        logger.info("Create directory {}", dir);
                    } catch (SftpException e1) {
                    	logger.error("Create directory failure, directory:{}", dir, e1);
                        e1.printStackTrace();
                    }
                    try {
                        sftp.cd(dir);
                        logger.info("Change directory {}", dir);
                    } catch (SftpException e1) {
                    	logger.error("Change directory failure, directory:{}", dir, e1);
                        e1.printStackTrace();
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public File downloadFile(String targetPath) throws Exception {
        ChannelSftp sftp = this.createSftp();
        OutputStream outputStream = null;
        try {
            sftp.cd(root);
            logger.info("Change path to {}", root);
     
            File file = new File(targetPath.substring(targetPath.lastIndexOf("/") + 1));
     
            outputStream = new FileOutputStream(file);
            sftp.get(targetPath, outputStream);
            logger.info("Download file success. TargetPath: {}", targetPath);
            return file;
        } catch (Exception e) {
        	logger.error("Download file failure. TargetPath: {}", targetPath, e);
            throw new Exception("Download File failure");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            this.disconnect(sftp);
        }
    }
    
}
