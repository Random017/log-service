// package com.skycong.log;
//
// import com.gudsen.moza.util.valid.Assert;
// import com.gudsen.moza.webutil.vo.ResponseBaseVo;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import java.util.Arrays;
//
// /**
//  * @author ruanmingcong 2020.8.3 13:50
//  */
// @RestController
// @RequestMapping("api/log/nginx")
// public class NginxLogController {
//
//     @Autowired
//     NginxLogService nginxLogService;
//
//     /**
//      * 统计一段时间内 的流量
//      */
//     @GetMapping("countBytes")
//     ResponseBaseVo countBytes(NginxLogReqVo reqVo) {
//         if (!Assert.isEmpty(reqVo.getField())) {
//             boolean b = Arrays.stream(fields).anyMatch(a -> a.equals(reqVo.getField()));
//             if (!b) {
//                 return ResponseBaseVo.error("field error");
//             }
//         }
//         return nginxLogService.countBytes(reqVo);
//     }
//
//
//     /**
//      * 查询一段时间内的 请求数据量
//      */
//     @GetMapping("queryCount")
//     ResponseBaseVo queryCount(NginxLogReqVo reqVo) {
//         return nginxLogService.queryCount(reqVo);
//     }
//
//     /**
//      * 根据一个字段，分组统计数量，从多到少逆序，limit限制结果数量
//      */
//     @GetMapping("queryCountGroupBy")
//     ResponseBaseVo queryCountGroupBy(NginxLogReqVo reqVo) {
//         Assert.notNull(reqVo.getField(), "字段不能为空");
//         boolean b = Arrays.stream(fields).anyMatch(a -> a.equals(reqVo.getField()));
//         if (!b) {
//             return ResponseBaseVo.error("field error");
//         }
//         return nginxLogService.queryCountGroupBy(reqVo);
//     }
//
//
//     private static final String[] fields = {"ip", "domain", "method", "uri", "protocol", "status", "referer", "language", "userAgent"};
//
// }
