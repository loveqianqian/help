package com.ghc.gather.dynamicClient.szx

import ca.uhn.hl7v2.DefaultHapiContext
import ca.uhn.hl7v2.model.v26.message.ACK
import org.apache.cxf.common.i18n.Exception
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory
import java.util.*

/**
 * com.ghc.gather.dynamicClient.szx
 * @author zhiwei
 * @create 2017-03-22 17:01.
 */
object Hl7Sender {

    @Throws(Exception::class)
    @JvmStatic fun main(args: Array<String>) {
        val ack = ACK()
        ack.initQuickstart("ACK", "ACK", "P")
        val msh = ack.msh
        msh.sendingApplication.namespaceID.value = "HIS"
        msh.receivingApplication.namespaceID.value = "LIS"
        msh.dateTimeOfMessage.setValue(Date())

        val msa = ack.msa
        msa.msa1_AcknowledgmentCode.value = "AR"
        msa.msa2_MessageControlID.value = "1"
        msa.msa3_TextMessage.value = "msg test"
        msa.msa4_ExpectedSequenceNumber.value = "1"

        val context = DefaultHapiContext()
        val pipeParser = context.pipeParser
        val encode = pipeParser.encode(ack)

        val factory = JaxWsDynamicClientFactory.newInstance()
        val client = factory.createClient("http://localhost:8082/oaWebService?wsdl")
        val invoke = client.invoke("testHL7", encode)
        println(invoke[0])
    }
}