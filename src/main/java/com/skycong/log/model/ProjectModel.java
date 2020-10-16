package com.skycong.log.model;

/**
 * @author ruanmingcong 2020.10.15 11:50
 */
public class ProjectModel {

    public static final String COLLECTION_NAME = "ProjectModel";

    /**
     * 项目名称
     */
    private String name;
    /**
     * 日志所在目录
     */
    private String logDirectory;
    /**
     * 日志文件名称
     */
    private String logFilename;
    /**
     * 日志类型 （Nginx 或 Spring）
     */
    private String logType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogDirectory() {
        return logDirectory;
    }

    public void setLogDirectory(String logDirectory) {
        this.logDirectory = logDirectory;
    }

    public String getLogFilename() {
        return logFilename;
    }

    public void setLogFilename(String logFilename) {
        this.logFilename = logFilename;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProjectModel{");
        sb.append("name='").append(name).append('\'');
        sb.append(", logDirectory='").append(logDirectory).append('\'');
        sb.append(", logFilename='").append(logFilename).append('\'');
        sb.append(", logType='").append(logType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
