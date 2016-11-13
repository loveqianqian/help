package com.ghc.gather.test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * com.ghc.gather.test
 *
 * @author zhiwei
 * @create 2016-10-30 11:53.
 */
public class ReadFile {

    List<String> resultList = new ArrayList<>();

    public List<String> getFile(String methodName, String fileName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(methodName)) {
                    resultList.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }
}
