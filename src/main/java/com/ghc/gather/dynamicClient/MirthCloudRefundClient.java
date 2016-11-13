package com.ghc.gather.dynamicClient;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class MirthCloudRefundClient {
    private static JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();

    private static Map<String, Client> cxfClients = new HashMap<>();

    public static void main(String[] args)
            throws Exception {
        Client client = factory.createClient("http://192.168.100.232:8077/services/Mirth?wsdl");

        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(10000L);
        httpClientPolicy.setAllowChunking(false);
        httpClientPolicy.setReceiveTimeout(10000L);
        http.setClient(httpClientPolicy);
        Map<String, Object> test = new HashMap<>();
        test.put("visitId", "");
        test.put("hisContractId", "33333");
        test.put("returnType", "1");
        test.put("operateType", "1");
        Document document = DocumentHelper.createDocument();
        Element parameter = DocumentHelper.createElement("Parameter");
        document.setRootElement(parameter);
        Element visitId = parameter.addElement("visitId");
        Element hisContractId = parameter.addElement("hisContractId");
        hisContractId.setText("33333");
        Element operateType = parameter.addElement("operateType");
        operateType.setText("1");
        Element returnType = parameter.addElement("returnType");
        returnType.setText("1");
        Object[] objects = client.invoke("acceptMessage", document.asXML());
        System.out.println(objects[0].toString());
    }
}