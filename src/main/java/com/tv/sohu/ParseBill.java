package com.tv.sohu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tv.sohu.util.App;
import com.tv.sohu.util.HttpUtil;
import com.tv.sohu.util.Remark;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ParseBill {

    public static List<App> genApp(String resp) throws Exception {
        LinkedList<App> apps = new LinkedList<>();
        JSONArray arr = JSONObject.parseObject(resp).getJSONObject("apps").getJSONArray("app");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject responseObj = (JSONObject) arr.get(i);
            App app = new App();
            app.setId(responseObj.getString("id"));
            app.setUser(responseObj.getString("user"));
            app.setName(responseObj.getString("name"));
            app.setQueue(responseObj.getString("queue"));
            app.setState(responseObj.getString("state"));
            app.setFinalState(responseObj.getString("finalStatus"));
            app.setProgress(responseObj.getString("progress"));
            app.setApplicationType(responseObj.getString("applicationType"));
            app.setPriority(responseObj.getString("priority"));
            app.setStartedTime(responseObj.getString("startedTime"));
            app.setLaunchTime(responseObj.getString("launchTime"));
            long finishedTime = responseObj.getLongValue("finishedTime");
            if (finishedTime == 0L)
                throw new Exception("parse error:" + responseObj.toJSONString());
            app.setFinishedTime(finishedTime);
            app.setElapsedTime(responseObj.getString("elapsedTime"));
            app.setMemorySeconds(responseObj.getString("memorySeconds"));
            app.setVcoreSeconds(responseObj.getString("vcoreSeconds"));
            app.setLogAggregationStatus(responseObj.getString("logAggregationStatus"));
            if (finishedTime > Remark.maxFinishTime.get()){
                apps.add(app);
                Remark.setRemark(finishedTime);
            }
        }
        return apps;
    }

    public static void main(String[] args) throws Exception {
        String resp = HttpUtil.get();
        List<App> apps = genApp(resp);
        for (App app : apps) {
            System.out.println(app.toHiveString());
        }
        System.out.println(apps.size());
    }
}
