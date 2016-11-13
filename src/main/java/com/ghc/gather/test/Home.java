package com.ghc.gather.test;

import java.util.List;

/**
 * com.ghc.gather.test
 *
 * @author zhiwei
 * @create 2016-10-30 11:53.
 */
public class Home {

    public static void main(String[] args) throws Exception {
        String fileName = "C:\\logs";
        ReadFile readFile = new ReadFile();
        WsTest wsTest = new WsTest();
        List<String> resultList = readFile.getFile("", fileName);
        for (String s : resultList) {
            wsTest.startWs("", s);
        }
    }

}
