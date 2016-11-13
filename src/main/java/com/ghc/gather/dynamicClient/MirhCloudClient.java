package com.ghc.gather.dynamicClient;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * com.ghc.gather.dynamicClient
 *
 * @author zhiwei
 * @create 2016-11-13 15:27.
 */
public class MirhCloudClient {

    private static JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();

    private static Map<String, Client> cxfClients = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Client client = factory.createClient("http://192.168.200.145:9988/BITCService.asmx?wsdl");

        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(10000L);
        httpClientPolicy.setAllowChunking(false);
        httpClientPolicy.setReceiveTimeout(10000L);
        http.setClient(httpClientPolicy);
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><BITCRoot><CommitData><Data><DataRow INSUR_NO=\"6214670060010424798\" INSUR_PWD=\"\"/></Data></CommitData><ReturnData /><ClientInfo><Info METHOD=\"GetInsuranceInfo\" TOKEN=\"\" OPT_ID=\"AB1482\" OPT_NAME=\"\" OPT_IP=\"\" OPT_DATE=\"\" OPT_GUID=\"\" OPT_ACCT=\"\"/></ClientInfo><ServerInfo><Info DEV_ID=\"000015\" DEV_IP=\"\" DEV_DATE=\"\" DEV_GUID=\"\" DEV_ACCT=\"\"/></ServerInfo><Result><Info EXECUTE_STATUS=\"0\" EXECUTE_MESSAGE=\"\" /></Result></BITCRoot>\n";
        System.out.println("start:" + System.currentTimeMillis());
        long s = System.currentTimeMillis();
        Object[] objects = client.invoke("GetInsuranceInfo", str);
        System.out.println("end:" + System.currentTimeMillis());
        long e = System.currentTimeMillis();
        System.out.println("waste:" + (e - s));
        System.out.println(objects[0].toString());
    }
}
