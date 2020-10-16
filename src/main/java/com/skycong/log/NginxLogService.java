// package com.skycong.log;
//
// import com.skycong.log.model.ConfigMapModel;
// import com.skycong.log.model.NginxLogModel;
// import com.gudsen.moza.util.valid.Assert;
// import com.gudsen.moza.webutil.vo.ResponseBaseVo;
// import com.mongodb.client.result.UpdateResult;
// import lombok.extern.slf4j.Slf4j;
// import org.bson.Document;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.data.domain.Sort;
// import org.springframework.data.mongodb.core.MongoTemplate;
// import org.springframework.data.mongodb.core.aggregation.Aggregation;
// import org.springframework.data.mongodb.core.aggregation.AggregationResults;
// import org.springframework.data.mongodb.core.query.Criteria;
// import org.springframework.data.mongodb.core.query.Query;
// import org.springframework.data.mongodb.core.query.Update;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Service;
//
// import java.io.File;
// import java.io.RandomAccessFile;
// import java.time.LocalDateTime;
// import java.time.ZoneId;
// import java.util.Arrays;
// import java.util.Date;
// import java.util.List;
// import java.util.stream.Collectors;
//
// /**
//  * 1,后台创建Project 包含：项目名称，日志存储目录，日志格式
//  * 2，依据日志格式，解析日志文件入库
//  * 3，统计查询
//  *
//  *
//  *
//  *
//  *
//  *
//  *
//  * @author ruanmingcong 2020.8.3 10:28
//  */
// @Service
// @Slf4j
// public class NginxLogService {
//
//     @Value("${file-disk-save-path}")
//     private String diskPath;
//
//     @Autowired
//     private MongoTemplate mongoTemplate;
//
//
//     @Scheduled(fixedRate = 60_000L)
//     public void job() {
//         this.lastFilePosition = getLastFilePosition();
//         log.info("开始读取日志：lastFilePosition = {}", this.lastFilePosition);
//         List<String> strings = readLog(getLogFilePath());
//         if (strings == null || strings.isEmpty()) {
//             log.info("无最新的日志读取，提前终止job");
//             return;
//         }
//         log.info("解析的日志行数：{}", strings.size());
//         strings.forEach(s -> {
//             try {
//                 NginxLogModel resolve = NginxLogModel.resolve(s);
//                 mongoTemplate.save(resolve);
//             } catch (Exception e) {
//                 log.error("{}", e.getMessage());
//                 log.error("{}", s);
//             }
//         });
//         log.info("日志解析完成，保存位置：lastFilePosition = {}", lastFilePosition);
//         setFilePosition(lastFilePosition);
//     }
//
//
//     /**
//      * 统计流量
//      */
//     public ResponseBaseVo countBytes(NginxLogReqVo reqVo) {
//         log.info("{}", reqVo);
//         String field = reqVo.field;
//         String value = reqVo.value;
//
//         Criteria criteria = Criteria.where("time").gte(convert(reqVo.startTime)).lt(convert(reqVo.endTime));
//         if (!Assert.isEmpty(field)) {
//             criteria.and(field).is(value);
//         }
//         Aggregation aggregation = Aggregation.newAggregation(
//                 Aggregation.match(criteria),
//                 Aggregation.group().sum("bytes").as("totalBytes")
//         );
//         AggregationResults<Document> aggregate = mongoTemplate.aggregate(aggregation, NginxLogModel.class, Document.class);
//         List<Document> mappedResults = aggregate.getMappedResults();
//         return ResponseBaseVo.success(mappedResults);
//     }
//
//
//     public ResponseBaseVo queryCount(NginxLogReqVo reqVo) {
//         log.info("{}", reqVo);
//         Criteria criteria = Criteria.where("time").gte(convert(reqVo.startTime)).lt(convert(reqVo.endTime));
//         Aggregation aggregation = Aggregation.newAggregation(
//                 Aggregation.match(criteria),
//                 Aggregation.count().as("count")
//         );
//         AggregationResults<Document> aggregate = mongoTemplate.aggregate(aggregation, NginxLogModel.class, Document.class);
//         List<Document> mappedResults = aggregate.getMappedResults();
//         return ResponseBaseVo.success(mappedResults);
//     }
//
//     /**
//      * 通过字段 group 分组统计数量
//      */
//     public ResponseBaseVo queryCountGroupBy(NginxLogReqVo reqVo) {
//         log.info("{}", reqVo);
//         String field = reqVo.field;
//         String asCount = "count_" + field;
//         Criteria criteria = Criteria.where("time").gte(convert(reqVo.startTime)).lt(convert(reqVo.endTime));
//         Aggregation aggregation = Aggregation.newAggregation(
//                 Aggregation.match(criteria),
//                 Aggregation.group(field).count().as(asCount),
//                 Aggregation.sort(Sort.by(asCount).descending()),
//                 Aggregation.limit(reqVo.limit)
//         );
//         AggregationResults<Document> aggregate = mongoTemplate.aggregate(aggregation, NginxLogModel.class, Document.class);
//         List<Document> mappedResults = aggregate.getMappedResults();
//         return ResponseBaseVo.success(mappedResults);
//     }
//
//
//     /**
//      * 上次读取的位置
//      */
//     private long lastFilePosition = 0L;
//
//     /**
//      * 从指定位置读取日志
//      *
//      * @param path 日志路径
//      */
//     private List<String> readLog(String path) {
//         log.info("日志文件path： {}", path);
//
//         try {
//             RandomAccessFile file = new RandomAccessFile(path, "r");
//             long length = file.length();
//             if (lastFilePosition >= length) lastFilePosition = 0L;
//             file.seek(lastFilePosition);
//             byte[] bytes = new byte[(int) (length - lastFilePosition)];
//             file.read(bytes);
//             lastFilePosition = length;
//             String string = new String(bytes);
//             return Arrays.stream(string.split("\n")).filter(s1 -> !s1.isEmpty()).collect(Collectors.toList());
//         } catch (Exception e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
//
//     private static LocalDateTime convert(Date date) {
//         return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
//     }
//
//
//     /**
//      * 获取上一次文件读取的位置
//      */
//     private long getLastFilePosition() {
//         Query query = new Query();
//         query.addCriteria(Criteria.where("key").is("NginxLastFilePosition"));
//         ConfigMapModel configMapModel = mongoTemplate.findOne(query, ConfigMapModel.class);
//         if (configMapModel == null) return 0L;
//         return Long.parseLong(configMapModel.value);
//     }
//
//     /**
//      * 获取上一次文件读取的位置
//      */
//     private void setFilePosition(long lastFilePosition) {
//         Query query = new Query();
//         query.addCriteria(Criteria.where("key").is("NginxLastFilePosition"));
//
//         Update update = new Update();
//         update.set("key", "NginxLastFilePosition");
//         update.set("value", String.valueOf(lastFilePosition));
//
//         UpdateResult upsert = mongoTemplate.upsert(query, update, ConfigMapModel.class);
//
//         log.info("{}", upsert);
//     }
//
//     /**
//      * 获取今天的日志文件路径
//      */
//     private String getLogFilePath() {
//         return diskPath + File.separator + "access.log";
//     }
//
//
//     // private List<String> getLogFilePaths() {
//     //     System.out.println(diskPath);
//     //
//     //     File[] files = new File(diskPath).listFiles();
//     //     List<String> collect = Arrays.stream(files)
//     //             .filter(file -> file.isFile())
//     //             .filter(file -> file.getName().endsWith("-access.log"))
//     //             .filter(file -> !file.getName().contains("0621"))
//     //             .sorted((o1, o2) -> (int) (o1.lastModified() - o2.lastModified()))
//     //             .map(file -> file.getAbsolutePath()).collect(Collectors.toList());
//     //
//     //     log.info("{}", collect);
//     //     return collect;
//     // }
//
//
// }
