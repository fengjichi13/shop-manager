//package com.taotao.controller;
//
//import java.io.File;
//import java.io.FileInputStream;
//
//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;
//import org.junit.Test;
//
//import com.taotao.common.utils.FtpUtil;
//
//public class FTPTest {
//
//	@Test
//	public void testFtpClient() throws Exception {
//		//创建一个FtpClient对象
//		FTPClient ftpClient = new FTPClient();
//		//创建ftp连接。默认是21端口
//		ftpClient.connect("192.168.18.211", 21);
//		//登录ftp服务器，使用用户名和密码
//		ftpClient.login("ftpuser", "ftpuser");
//		//上传文件。
//		//读取本地文件
//		FileInputStream inputStream = new FileInputStream(new File("D:\\test\\Pictures\\1.jpg"));
//		//设置上传的路径,根据当前ftp主路径的相对路径，如当前图片上传到路径为/home/ftpuser/www/images，ftp-server的主路径为/home/ftpuser，则相对路径则设为www/images
//		ftpClient.changeWorkingDirectory("www/images");
//		//设置为被动模式,如果上传文件为空则加入下面一行
//		ftpClient.enterLocalPassiveMode();
//		//修改上传文件的格式
//		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//		//第一个参数：服务器端文档名
//		//第二个参数：上传文档的inputStream
//		ftpClient.storeFile("hello1.jpg", inputStream);
//		//关闭连接
//		ftpClient.logout();
//		
//	}
//	
//	@Test
//	public void testFtpUtil() throws Exception {
//		FileInputStream inputStream = new FileInputStream(new File("D:\\test\\Pictures\\1.jpg"));
//		FtpUtil.uploadFile("192.168.18.211", 21, "ftpuser", "ftpuser", "www/images", "2015/09/04", "hello.jpg", inputStream);
//		
//	}
//}
