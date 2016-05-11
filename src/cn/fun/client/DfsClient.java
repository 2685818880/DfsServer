package cn.fun.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class DfsClient {
	public static void main(String[] args) {
		String result = "";
		try {
			// 发送socket连接
			Socket socket = new Socket("127.0.0.1", 6666);
			// 向服务器发送数据
			OutputStream outputStream = socket.getOutputStream();
			InputStream inputStream = socket.getInputStream();

			// 一定要封装DataOutputStream才可以，服务器与客户端通信
			DataOutputStream dataOutputStream = new DataOutputStream(
					outputStream);
			// dataOutputStream.writeUTF("are you ready");
			dataOutputStream.writeUTF("UPLOAD");

			// 读取本地文件

			String fileName = "E:/usr/local/apache-maven-3.2.3/README.txt";
			// 获取文件类型
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			dataOutputStream.writeUTF(suffix);

			File file = new File(fileName);
			FileInputStream fileInputStream = new FileInputStream(file);
			//获取文件的大小
			int fileSize = fileInputStream.available();
			dataOutputStream.writeInt(fileSize);
			
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = fileInputStream.read(bytes)) != -1) {
				dataOutputStream.write(bytes, 0, read);
			}

			DataInputStream dataInputStream = new DataInputStream(inputStream);
			result = dataInputStream.readUTF();
			/*
			 * System.out.println(result); if(result.equals("I am fine")){
			 * dataOutputStream.writeUTF("I know"); }
			 */
			if (result.equals("SUCCESS")) {
				System.out.println("client upload success");
			}

			if (dataOutputStream != null) {
				dataOutputStream.close();
			}
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    public static String upload(InputStream inputStream){
    	return "";
    }
}
