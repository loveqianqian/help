package com.ghc.gather.test;

import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BatTest {
    private static String strShutDown = "E:/apache-tomcat-7.0.0/bin/shutdown.bat";
    private static String strStartUp = "E:/apache-tomcat-7.0.0/bin/startup.bat";
    private static String url = "http://localhost:8090/manager/status?XML=true";

    public static void main(String[] args) throws Exception {
        dirOpt(strStartUp);
    }

    public static void dirOpt(String str) {
        try {
            Process process = Runtime.getRuntime().exec(str);
            InputStream fis = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            String htmlContext = getHtmlContext(url, "admin", "tomcat");
            System.out.println(htmlContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getHtmlContext(String urlPath, String username, String password) throws Exception {
        URL url = new URL(urlPath);
        String nameAndPassword = username + ":" + password;
        String encoding = new BASE64Encoder().encode(nameAndPassword.getBytes());
        StringBuffer sb = new StringBuffer();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Basic " + encoding);
        InputStream in = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}