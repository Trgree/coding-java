package org.ace.coding.common.utils;
import java.io.*;
import java.util.Properties;

/**
 * 配置工具类
 * 读取properties文件，并缓存key value
 * @author Liangsj
 *
 */
public class PropsTool {
	
	private Properties prop = new Properties();

	/**
	 * 通过resources文件构建PropsTool
	 * @param resourceFile
	 */
	public PropsTool(String resourceFile){
        InputStream in = null;
        try {
            in = PropsTool.class.getClassLoader().getResourceAsStream(resourceFile);
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	/**
	 * 通过File文件构建PropsTool
	 * @param file
	 */
	public PropsTool(File file) {
		InputStreamReader in = null;
		try {
			in = new InputStreamReader(new FileInputStream(file), "utf-8");
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace(); 
			throw new RuntimeException("读取配置异常", e);
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public  String getString(String key) {
		return prop.getProperty(key);
	}
	
	public String getString(String key, String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}
	
	public int getInt(String key, int defaultVal) {
		int result = defaultVal;
		try {
			result = Integer.parseInt(getString(key));
		} catch (Exception e) {
		}
		return result;
	}
	
	public float getFloat(String key, float defaultVal) {
		float result = defaultVal;
		try {
			result = Float.parseFloat(getString(key));
		} catch (Exception e) {
		}
		return result;
	}
	
	public double getDouble(String key, double defaultVal) {
		double result = defaultVal;
		try {
			result = Double.parseDouble(getString(key));
		} catch (Exception e) {
		}
		return result;
	}
	
	public double getLong(String key, long defaultVal) {
		long result = defaultVal;
		try {
			result = Long.parseLong(getString(key));
		} catch (Exception e) {
		}
		return result;
	}
	
	public void setProperty(String key, String value){
		prop.setProperty(key, value);
	}
	
	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public static void main(String[] args) {
		PropsTool pro = new PropsTool("config.properties");
		System.out.println(pro.getString("KEY1"));
	}
}
