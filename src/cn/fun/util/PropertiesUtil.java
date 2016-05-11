package cn.fun.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
   public static String getValue(String propertiesFile,String key){
      String value="";
      try{
    	  Properties properties=new Properties();
    	  File file=new File(propertiesFile);
    	  InputStream fis=new FileInputStream(file); 
    	  properties.load(fis);
    	  value=properties.getProperty(key);
    	  
      }catch(FileNotFoundException e){
    	  e.printStackTrace();
      }catch(IOException e1){
    	  e1.printStackTrace();
      }
	   return value;
   }
}
