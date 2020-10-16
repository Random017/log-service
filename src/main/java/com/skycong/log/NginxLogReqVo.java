// package com.skycong.log;
//
// import com.gudsen.moza.util.time.TimeUtil;
// import lombok.Getter;
// import lombok.Setter;
// import lombok.ToString;
//
// import java.io.Serializable;
// import java.time.LocalDateTime;
// import java.util.Date;
//
// /**
//  * @author ruanmingcong 2020.8.3 13:56
//  */
// @Getter
// @Setter
// @ToString
// public class NginxLogReqVo implements Serializable {
//
//     String ip;
//     LocalDateTime time;
//     String domain;
//     String method;
//     String uri;
//     String protocol;
//     Integer status;
//     Long bytes;
//     String referer;
//     String language;
//     String userAgent;
//
//
//     String field;
//     String value;
//     Date startTime = TimeUtil.getTodayBegin();
//     Date endTime = new Date();
//     Integer limit = Integer.MAX_VALUE;
//
//
// }
