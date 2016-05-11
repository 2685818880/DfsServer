package cn.fun.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
		//文件连接最多同时1024个
    	Properties properties=new Properties();
		File file=new File("project.properties");
		try {
			FileInputStream fis=new FileInputStream(file);
		    properties.load(fis);
		    String property=properties.getProperty("name");
		    System.out.println(property);
		    fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e1){
			e1.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
