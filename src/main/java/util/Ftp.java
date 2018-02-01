package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;



public class Ftp {
	private static Logger logger = Logger.getLogger(Ftp.class);
	private FTPClient ftp;

	public boolean connect(String path, String addr, int port, String username, String password) throws Exception {
		boolean result = false;
		ftp = new FTPClient();
		int reply;
		ftp.connect(addr, port);
		ftp.login(username, password);
		//ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		//ftp.enterLocalPassiveMode(); 
		//ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE); 
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			logger.error("FTP服务器连接失败. reply ： " + reply);
			logger.error("username ： " + username);
			logger.error("password ： " + password);
			logger.error("addr ： " + addr);
			logger.error("port ： " + port);
			return result;
		}
		
		ftp.changeWorkingDirectory(path);
		result = true;
		return result;
	}
	


	/**
	 * 
	 * @param file
	 *            上传的文件或文件夹
	 * @throws Exception
	 */
	public void upload(File file) throws Exception {
		if (file.isDirectory()) {
			ftp.makeDirectory(file.getName());
			ftp.changeWorkingDirectory(file.getName());
			String[] files = file.list();
			for (int i = 0; i < files.length; i++) {
				File file1 = new File(file.getPath() + "\\" + files[i]);
				if (file1.isDirectory()) {
					upload(file1);
					ftp.changeToParentDirectory();
				} else {
					File file2 = new File(file.getPath() + "\\" + files[i]);
					FileInputStream input = new FileInputStream(file2);
					ftp.storeFile(file2.getName(), input);
					input.close();
				}
			}
		} else {
			File file2 = new File(file.getPath());
			FileInputStream input = new FileInputStream(file2);
			ftp.storeFile(file2.getName(), input);
			input.close();
		}
	}
	
	public boolean upload(String filename) throws Exception {
		
		logger.info("-------------upload------------");
		logger.debug("filename : " + filename);
		File file = new File(filename);
		if (file.isDirectory()) {
			ftp.makeDirectory(file.getName());
			ftp.changeWorkingDirectory(file.getName());
			String[] files = file.list();
			for (int i = 0; i < files.length; i++) {
				File file1 = new File(file.getPath() + "\\" + files[i]);
				if (file1.isDirectory()) {
					upload(file1);
					ftp.changeToParentDirectory();
				} else {
					File file2 = new File(file.getPath() + "\\" + files[i]);
					FileInputStream input = new FileInputStream(file2);
					logger.info("update filename : " + file2.getName());
					if(!ftp.storeFile(file2.getName(), input))
					{
						logger.error("ftp store file error .");
						return false;
					}
					input.close();
					return true;
				}
			}
		} else {
			File file2 = new File(file.getPath());
			FileInputStream input = new FileInputStream(file2);
			logger.info("update filename : " + file2.getName());
			if(!ftp.storeFile(file2.getName(), input))
			{
				logger.error("ftp store file error .");
				return false;
			}
			input.close();
			
			return true;
		}
		return false;
	}

	public boolean download(String sPathName, String sFileName, String sLocalName) {
		OutputStream is;
		try {
			ftp.changeWorkingDirectory(sPathName);
			is = new FileOutputStream(sLocalName);
			ftp.retrieveFile(sFileName, is);
			is.close();
		} catch (Exception e) {
			logger.error("ftp oper error!", e);
			return false;
		}

		return true;
	}
	public boolean move(String sPathName, String sFileName, String sMovePath)
	{
		try {
			ftp.changeWorkingDirectory(sPathName);
			ftp.rename(sFileName, "../" + sMovePath + sFileName);
			return true;
		} catch (Exception e) {
			logger.error("ftp oper error!", e);
			return false;
		}
	}
	public boolean moveSingleFile(String sPathName, String sFileName, String sMovePath) {
		try {
			ftp.changeWorkingDirectory(sPathName);
			ftp.rename(sFileName, sMovePath + sFileName);
			return true;
		} catch (Exception e) {
			logger.error("ftp oper error!", e);
			return false;
		}
	}
	public boolean renameSingleFile(String sPathName, String sFileName, String newFileName) {
	    try {
	        ftp.changeWorkingDirectory(sPathName);
	        ftp.rename(sFileName, newFileName);
	        return true;
	    } catch (Exception e) {
	        logger.error("ftp oper error!", e);
	        return false;
	    }
	}
	public boolean move(String sPathName, List<String> sFileNames, String sMovePath)
	{
	    try {
	        ftp.changeWorkingDirectory(sPathName);
	        for(String sFileName : sFileNames)
	            ftp.rename(sFileName, sMovePath + sFileName);
	        return true;
	    } catch (Exception e) {
	        logger.error("ftp oper error!", e);
	        return false;
	    }
	}
	public List<String> getFiles(String sPathName)
	{
		String prefix = PropertiesUtil.getUtf8Value("ftp_file_prefix");
		logger.info("-------------getFiles------------");
		logger.debug("sPathName    : " + sPathName);
		logger.debug("prefix       : " + prefix);
		try {
			ftp.changeWorkingDirectory(sPathName);
			
			FTPFile[] fs = ftp.listFiles();
			List<String> fileNames = new ArrayList<String>();
			for(FTPFile f : fs)
			{
				if(f.isFile()) {
					if (!f.getName().startsWith(prefix)) {
						continue;
					}
					Date now = new Date();
					logger.debug("filename       : " + f.getName());
					logger.debug("now            : " + now.getTime());
					logger.debug("filetime       : " + f.getTimestamp().getTime().getTime());
					logger.debug("filetime       : " + new Date(f.getTimestamp().getTime().getTime()));
					logger.debug("open_log_time  :" + PropertiesUtil.getValue("open_log_time"));
					logger.debug("是否满足时间差" + (now.getTime() - f.getTimestamp().getTime().getTime()>= 180000));
					if(PropertiesUtil.getValue("open_log_time").equals("no") || now.getTime() - f.getTimestamp().getTime().getTime() >= 180000)
					{
						logger.debug("待处理文件：" + f.getName());
						fileNames.add(f.getName());
					}
				}
			}
			return fileNames;
		} catch (Exception e) {
			logger.error("ftp oper error!", e);
			return null;
		}

	}
	public boolean downloadEx(String sPathName, String sFileName, String sLocalName) {
		OutputStream is;
		try {
			ftp.changeWorkingDirectory(sPathName);
			is = new FileOutputStream(sLocalName);
			FTPFile[] fs = ftp.listFiles();
			for(FTPFile f : fs)
			{
				if(f.getName().indexOf(sFileName) != -1)
				{
					ftp.retrieveFile(sFileName, is);
					
					is.close();
					return true;
				}
			}
			is.close();
		} catch (Exception e) {
			logger.error("ftp oper error!", e);
			return false;
		}

		return false;
	}

	public void close() {
		try {
			ftp.logout();
		} catch (IOException e) {
			logger.error("ftp oper error!", e);
		}
	}
}
