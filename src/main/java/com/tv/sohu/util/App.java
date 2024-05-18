package com.tv.sohu.util;

import lombok.Data;

@Data
public class App {
    private String id;
    private String user;
    private String name;
    private String queue;
    private String state;
    private String finalState;
    private String progress;
    private String applicationType;
    private String priority;
    private String startedTime;
    private String launchTime;
    private Long finishedTime;
    private String elapsedTime;
    private String memorySeconds;
    private String vcoreSeconds;
    private String logAggregationStatus;

    public String toHiveString() {
        return id + '\t' +
               user + '\t' +
               name.replaceAll("[\r\n\t\\s]+"," ")+ '\t' +
               queue + '\t' +
               state + '\t' +
               finalState + '\t' +
               progress + '\t' +
               applicationType + '\t' +
               priority + '\t' +
               startedTime + '\t' +
               launchTime + '\t' +
               finishedTime + '\t' +
               elapsedTime + '\t' +
               memorySeconds + '\t' +
               vcoreSeconds + '\t' +
               logAggregationStatus ;
    }

    public static void main(String[] args) {
        String s = "abc   bc";
        String replaced = s.replaceAll("[a\\s]+", "_");
        System.out.println(replaced);

    }
}
