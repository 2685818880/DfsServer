package cn.fun.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.fun.util.PropertiesUtil;

public class CoreServer {
	private final static String propertiesFile = "project.properties";
	private final static String port = PropertiesUtil.getValue(propertiesFile,
			"DfsServer.server.port");
	private final static String num1 = PropertiesUtil.getValue(propertiesFile,
			"DfsServer.server.num1");

	private final static String basePath=PropertiesUtil.getValue(propertiesFile, "DfsServer.server.basepath");
	public static void main(String[] args) {
		// 创建一个可重用固定线程数的线程池
		ExecutorService executorService = Executors.newFixedThreadPool(Integer
				.parseInt(num1));
		try {
			//socket每次最多只能传输512KB的数据
			//在socket编程中把socket的inputstream流关闭了，就会把outputs流关闭
			final ServerSocket serverSocket = new ServerSocket(
					Integer.parseInt(port));
			/*
			 * 绑定localhost，不允许外网访问 SocketAddress endpoint=new
			 * InetSocketAddress("127.0.0.1", Integer.parseInt(port));
			 * bind方法将ServerSocket 绑定到特定地址（IP 地址和端口号）serverSocket.bind(endpoint);
			 */
			// 阻塞方法 侦听并接受到此套接字的连接,增强并发力
			final Socket socket = serverSocket.accept();
			System.out.println("connect success:" + socket.getPort());
			// 创建线程池
			executorService.execute(new Runnable() {

				public void run() {
					try {
						
						//获得输入流
						InputStream inputStream = socket.getInputStream();
						DataInputStream dataInputStream = new DataInputStream(inputStream);
						//获取输出流
						OutputStream outputStream = socket.getOutputStream();
						//一定要用dataoutputstream封装outputstream
						DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
						//接收客户端输入的数据
						String rev = dataInputStream.readUTF();
						System.out.println("cmd:"+rev);
						//读取简单数据，可以用readUTF()
						String stuffix=dataInputStream.readUTF();
						int fileSize=dataInputStream.readInt();
						
						/*
						if (rev.equals("are you ready")) {
							
							dataOutputStream.writeUTF("I am fine");
						}*/
						
						//System.out.println(rev);
						//是否上传
						
		 				if(rev.equals("UPLOAD")){
		 				   FileUpload upload=new FileUpload(basePath,stuffix,fileSize); 
						   String result=upload.upload(inputStream);
						   dataOutputStream.writeUTF(result);
						}
		 			   socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			// System.out.println(scoket.getLocalPort());
			/*if (executorService.isShutdown()) {
				serverSocket.close();
			}*/
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  
}
