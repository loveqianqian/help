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
public class MnisClient {

    private static JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();

    private static Map<String, Client> cxfClients = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Client client = factory.createClient("http://localhost:8082/mnisWebService?wsdl");
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(10000L);
        httpClientPolicy.setAllowChunking(false);
        httpClientPolicy.setReceiveTimeout(10000L);
        http.setClient(httpClientPolicy);

        Object[] objects = client.invoke("getTurnDeptTurnBed", "<payload>" +
                "    <request>" +
                "        <items>" +
                "            <admission_id>297659_1</admission_id>" +
                "            <admission_id>297658_1</admission_id>" +
                "        </items>" +
                "        <user_id>0020</user_id>" +
                "    </request>" +
                "</payload>");

        System.out.println(objects[0].toString());
    }
}
