package com.ghc.gather.test;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * com.ghc.gather.test
 *
 * @author zhiwei
 * @create 2016-08-10 15:04.
 */
public class WsTest {

    private static JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();

    private static final String url = "http://192.168.200.145:9988/BITCService.asmx?wsdl";

    public void startWs(String methodName, String xml) throws Exception {
        Client client = factory.createClient(url);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(10000);
        httpClientPolicy.setAllowChunking(false);
        httpClientPolicy.setReceiveTimeout(10000);
        http.setClient(httpClientPolicy);
        Object[] objects = client.invoke(methodName, xml);
        System.out.println(objects);
    }
}
