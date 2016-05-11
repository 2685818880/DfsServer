package cn.fun.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class FileUpload {
	private String basepath;
    private String stuffix;
    private int fileSize;
    
	public FileUpload(String basepath) {
		super();
		this.basepath = basepath;
		
	}
    
	
	
	public FileUpload(String basepath, String stuffix,int fileSize) {
		super();
		this.basepath = basepath;
		this.stuffix = stuffix;
		this.fileSize=fileSize;
	}



	public String getPath() {
		return basepath;
	}



	public String getStuffix() {
		return stuffix;
	}



	public void setPath(String basepaht) {
		this.basepath = basepaht;
	}

	public void setStuffix(String stuffix) {
		this.stuffix = stuffix;
	}

	public String upload(InputStream inputStream) {
		String result = "FAIL";
		File file = new File(basepath);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileOutputStream fileOutputStream = null;
		System.out.println("upload path:" + basepath);
		String upLoadFileName = "";
		try {
			// 准备路径
			UUID uuid = UUID.randomUUID();
			String path = uuid.toString().replaceAll("-", "");
			String folder1 = path.substring(0, 2).toUpperCase();
			String folder2 = path.substring(2, 4).toUpperCase();
			String fileName = path.substring(4).toUpperCase();
			// 斜杠/file.separator
			upLoadFileName = basepath + "/" + folder1 + "/" + folder2 + "/"
					+ fileName+"."+stuffix;
			System.out.println("file save path:" + upLoadFileName);
			result = "SUCCESS";
			System.out.println(upLoadFileName + " :" + "file save succeed");
            
			File file2=new File(basepath+"/"+folder1+"/"+folder2+"/");
			if(!file2.exists()){
				file2.mkdirs();
				System.out.println(basepath+"/"+folder1+"/"+folder2+"/"+"create success");
			}
			
			// 开始写文件
			fileOutputStream = new FileOutputStream(upLoadFileName);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				fileOutputStream.write(bytes, 0, read);
				fileOutputStream.flush();
			}

		} catch (Exception e) {
			result = "FAIL";
			System.out.println(upLoadFileName + "file save failed");
			e.printStackTrace();

		} finally {

			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
            /*
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
		}

		return result;
	}
}
