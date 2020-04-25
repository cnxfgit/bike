package com.hlx.vbbike.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private Properties properties = new Properties();

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("app\\src\\main\\assets\\user.properties"));
            properties.load(in);
            properties.setProperty("loginname", "def");
            properties.setProperty("loginpass","123");

            FileOutputStream fos = new FileOutputStream("app\\src\\main\\assets\\user.properties");
            properties.store(fos,null);
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void changePro(String loginname,String loginpass){
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("app\\src\\main\\assets\\user.properties"));
            properties.load(in);
            properties.setProperty("loginname", loginname);
            properties.setProperty("loginpass",loginpass);

            FileOutputStream fos = new FileOutputStream("app\\src\\main\\assets\\user.properties");
            properties.store(fos,null);
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
