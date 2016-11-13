package com.ghc.gather.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BeanUtils {
    public static List<List<?>> putIntoList(List<Map<String, Object>> argArray) {
        List<List<?>> list = new ArrayList<>();
        for (int i = 0; i < argArray.size(); i++) {
            List<Object> subList = new ArrayList<>();
            Object id = argArray.get(i).get("id");
            Object status = argArray.get(i).get("status");
            Object charges = argArray.get(i).get("charges");
            Object testNo = argArray.get(i).get("testNo");
            Object sendDate = argArray.get(i).get("sendDate");
            subList.add(id);
            subList.add(status);
            subList.add(charges);
            subList.add(testNo);
            subList.add(sendDate);
            list.add(subList);
        }
        return list;
    }
}