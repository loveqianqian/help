package com.ghc.gather.xiongkeHelp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ghc.gather.utils.BeanUtils;
import com.ghc.gather.utils.DateUtils;
import com.ghc.gather.utils.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LisHelp {
    private static String sql = "SELECT t.id,t.send_date,t.msg FROM   t_ex_msg_log t WHERE  t.msg_code = 'HIS30011'        AND t.send_type = 'response'        AND       t.send_date>'2016-07-14 00:00:00'        AND        t.send_date<'2016-07-15 08:00:00' ORDER  BY send_date ";

    private static String[] Header = {"id号", "发送成功状态", "平台发送费用", "申请单号", "时间"};

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection("jdbc:oracle:thin:@192.168.15.197:1521/oracle", "exchanger", "exchanger");
    }

    public static Connection getConnection2() throws Exception {
        return DriverManager.getConnection("jdbc:oracle:thin:@192.168.15.221/oracle", "mid", "mid");
    }

    public static void main(String[] args) throws Exception {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List result = new ArrayList();
        while (resultSet.next()) {
            Map subMap = new HashMap();
            String id = resultSet.getString("ID");
            String msg = resultSet.getString("MSG");
            subMap.put("id", id);
            JSONObject jsonObject = JSON.parseObject(msg);
            JSONObject payload = jsonObject.getJSONObject("payload");
            JSONObject headers = jsonObject.getJSONObject("headers");
            JSONObject response = payload.getJSONObject("response");
            String sendDate = headers.getString("sendTime");
            String status = response.getString("status");
            String charges = response.getString("charges");
            String testNo = response.getString("test_no");
            subMap.put("sendDate", sendDate);
            subMap.put("status", status);
            subMap.put("charges", charges);
            subMap.put("testNo", testNo);
            result.add(subMap);
        }
        List list = BeanUtils.putIntoList(result);
        HSSFSheet sheet = ExcelUtils.createExcelFile(0, DateUtils.getCurrentTime(), true);
        ExcelUtils.addHSSFTableHead(sheet, Header);
        ExcelUtils.addHSSFTableBody(sheet, list.size(), ((List) list.get(0)).size(), list);
        ExcelUtils.writeIntoFile("D:\\20160714lis计不上费平台返回情况.xls");
        System.out.println("success");
    }

    private static String getSql(String testNo, String myTime) {
        return "select sum(ibd.charges) as charges,ibd.billing_date_time as time from inp_bill_detail ibd,lab_test_master ltm where ltm.test_no=\"" + testNo + "\" " + "and ibd.patient_id=ltm.patient_id " + "and ibd.item_class='C' " + "and ibd.billing_date_time>=to_date(+" + "'" + myTime + "'" + ",'yyyy-MM-dd hh24:mi:ss') " + "group by ibd.billing_date_time" + "union" + "select sum(cbi.charges) as charges,cbi.bill_date_time as time " + "from clinic_bill_items cbi,lab_test_master ltm " + "where ltm.test_no=\"" + testNo + "\" " + "and cbi.patient_id=ltm.patient_id " + "and cbi.item_class='C' " + "and cbi.bill_date_time>=to_date(+" + "'" + myTime + "'" + ",'yyyy-MM-dd hh24:mi:ss') " + "group by cbi.bill_date_time;";
    }
}