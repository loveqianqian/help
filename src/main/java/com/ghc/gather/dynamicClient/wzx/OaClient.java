package com.ghc.gather.dynamicClient.wzx;


import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.util.HashMap;
import java.util.Map;

/**
 * com.ghc.gather.dynamicClient
 *
 * @author zhiwei
 * @create 2016-11-13 15:25.
 */
public class OaClient {

    private static JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();

    private static Map<String, Client> cxfClients = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Client client = factory.createClient("http://localhost:8082/oaWebService?wsdl");
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(10000L);
        httpClientPolicy.setAllowChunking(false);
        httpClientPolicy.setReceiveTimeout(10000L);
        http.setClient(httpClientPolicy);

        Object[] objects = client.invoke("setDept", "<payload><request><serialNo>211</serialNo><supDeptCode>A001</supDeptCode><deptCode>B001</deptCode><deptName>消化内科</deptName><deptProfile>xh</deptProfile><isHomePage>0</isHomePage><describe>消化内科</describe><domainId>0</domainId><actionType>modify</actionType></request></payload>");

        System.out.println(objects[0].toString());
    }
}
