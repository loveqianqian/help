package com.ghc.gather.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.misc.BASE64Encoder;

public class BatDemo {
    private static String url = "http://localhost:8090/manager/status?XML=true";

    public static void main(String[] args) throws Exception {
        String htmlContext = getHtmlContext(url, "admin", "tomcat");
        System.out.println(htmlContext);
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