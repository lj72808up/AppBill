package com.tv.sohu.util;

import cn.hutool.db.Db;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

public class Remark {
    private static final Logger log = LoggerFactory.getLogger(Remark.class);
    public static final AtomicLong maxFinishTime = new AtomicLong(0);
    static {
        maxFinishTime.compareAndSet(maxFinishTime.get(),Remark.initRemark());
    }

    private static long initRemark() {
        long res = 0;
        try {
            res = Db.use().queryOne("select * from appbill limit 1").getLong("max_finish_time");
        } catch (SQLException e) {
            log.error("get max finish time from mysql fail !!!", e);
        }
        log.info("get max finish time from mysql:" + res);
        return res;
    }

    public static void setRemark(long remark) throws SQLException {
        //todo 这里判断，比他大才能插入
        maxFinishTime.compareAndSet(maxFinishTime.get(),remark);
        Db.use().execute("update appbill set max_finish_time = ? where id>0", remark);
        log.info("update max finish time to mysql:" + remark);
    }

    public static void main(String[] args) throws SQLException {
        setRemark(1711002341233L);
        System.out.println(initRemark());
    }
}
