package com.ghc.gather.xiongkeHelp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class XkHelp1 {
    public static Connection getConnection()
            throws Exception {
        return DriverManager.getConnection("jdbc:oracle:thin:@192.168.200.1:1521/hisoracle", "mid", "manager");
    }

    public static void main(String[] args) throws Exception {
        String sql = "SELECT patient_id FROM pat_visit  t WHERE t.discharge_date_time>sysdate-7 ";
        ResultSet patientIdRs = null;
        Connection conn = getConnection();
        patientIdRs = conn.createStatement().executeQuery(sql);
        List<String> ids = new ArrayList<>();
        while (patientIdRs.next()) {
            ids.add(patientIdRs.getString(1));
        }
        String sql_1 = "UPDATE pat_visit SET DEPT_DISCHARGE_FROM = DEPT_DISCHARGE_FROM WHERE patient_id=?";
        String sql_2 = "UPDATE MID_PAT_VISIT SET IDUPDATE='INSERT' WHERE PATIENT_ID=? AND UPDATE_DATE=(SELECT max(UPDATE_DATE) FROM MID_PAT_VISIT WHERE PATIENT_ID=?)";
        PreparedStatement ps_1;
        PreparedStatement ps_2;
        for (int i = 0; i < ids.size(); i++) {
            ps_1 = conn.prepareStatement(sql_1);
            ps_1.setString(1, ids.get(i));
            ps_1.executeUpdate();
            ps_2 = conn.prepareStatement(sql_2);
            ps_2.setString(1, ids.get(i));
            ps_2.setString(2, ids.get(i));
            ps_2.executeUpdate();
            Thread.sleep(5000L);
            ps_1.executeUpdate();
            System.out.println(ids.get(i));
        }
    }
}