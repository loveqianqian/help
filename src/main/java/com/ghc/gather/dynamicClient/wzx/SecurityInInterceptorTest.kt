/*
 *
 *  *****************************************************************************
 *  * Copyright ( c ) 2016 Heren Tianjin Inc. All Rights Reserved.
 *  *
 *  * This software is the confidential and proprietary information of Heren Tianjin Inc
 *  * ("Confidential Information").  You shall not disclose such Confidential Information
 *  *  and shall use it only in accordance with the terms of the license agreement
 *  *  you entered into with Heren Tianjin or a Heren Tianjin authorized
 *  *  reseller (the "License Agreement").
 *  ****************************************************************************
 *  *
 */

package com.ghc.gather.dynamicClient.wzx

import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor
import org.apache.cxf.endpoint.Client
import org.apache.cxf.interceptor.Interceptor
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory
import org.apache.cxf.message.Message
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor
import org.apache.ws.security.WSConstants
import org.apache.ws.security.handler.WSHandlerConstants

import java.util.HashMap

/**
 * com.heren.turtle.entrance.ws

 * @author zhiwei
 * *
 * @create 2017-03-10 16:31.
 */
object SecurityInInterceptorTest {

    @Throws(Exception::class)
    @JvmStatic fun main(args: Array<String>) {
        val outProps = HashMap<String, Any>()
        outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN)
        outProps.put(WSHandlerConstants.USER, "3000123")
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT)
        outProps.put(WSHandlerConstants.PW_CALLBACK_REF, ClientCallBackHandler())

        val factory = JaxWsDynamicClientFactory.newInstance()
        val client = factory.createClient("http://168.192.1254.155:10020/mnisWebService?wsdl")
        val outInterceptors = client.outInterceptors
        outInterceptors.add(SAAJOutInterceptor())
        outInterceptors.add(WSS4JOutInterceptor(outProps))
        val invoke = client.invoke("send", "test")
        println(invoke[0])
    }

}