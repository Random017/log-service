package com.skycong.log.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ruanmingcong 2020.8.3 10:28
 */
public class NginxLogModel implements Serializable {

    private static final long serialVersionUID = -4641598705610301638L;

    String ip;
    LocalDateTime time;
    String domain;
    String method;
    String uri;
    String protocol;
    Integer status;
    Long bytes;
    String referer;
    String language;
    String userAgent;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NginxLogModel{");
        sb.append("ip='").append(ip).append('\'');
        sb.append(", time=").append(time);
        sb.append(", domain='").append(domain).append('\'');
        sb.append(", method='").append(method).append('\'');
        sb.append(", uri='").append(uri).append('\'');
        sb.append(", protocol='").append(protocol).append('\'');
        sb.append(", status=").append(status);
        sb.append(", bytes=").append(bytes);
        sb.append(", referer='").append(referer).append('\'');
        sb.append(", language='").append(language).append('\'');
        sb.append(", userAgent='").append(userAgent).append('\'');
        sb.append('}');
        return sb.toString();
    }

    private static final char SPA = ' ';
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    public static NginxLogModel resolve(String s) {
        if (s.isEmpty()) return null;
        NginxLogModel model = new NginxLogModel();
        int from = 0, to;
        to = s.indexOf(SPA);
        model.ip = s.substring(from, to);
        from = s.indexOf('[');
        to = s.indexOf(']');
        model.time = LocalDateTime.parse(s.substring(from + 1, to), dateTimeFormatter);
        from = s.indexOf(SPA, to);
        to = s.indexOf(SPA, from + 2);
        model.domain = s.substring(from + 1, to);
        from = s.indexOf('\"', to);
        to = s.indexOf(SPA, from);
        model.method = s.substring(from + 1, to);
        from = to;

        // to = s.indexOf('?', from + 1);
        to = s.indexOf(SPA, from + 1);
        String uri = s.substring(from + 1, to);
        int i = uri.indexOf('?');
        if (i > 0) {
            uri = uri.substring(0, i);
        }
        model.uri = uri;
        from = s.indexOf(SPA, to);
        to = s.indexOf('\"', from);
        model.protocol = s.substring(from + 1, to);
        from = to;
        to = s.indexOf(SPA, from + 2);
        model.status = Integer.parseInt(s.substring(to - 3, to));
        from = to;
        to = s.indexOf(SPA, from + 1);
        model.bytes = Long.parseLong(s.substring(from + 1, to));
        from = to + 1;
        to = s.indexOf(SPA, from + 1);
        model.referer = s.substring(from + 1, to - 1);
        from = to;
        to = s.indexOf(SPA, from + 1);
        model.language = s.substring(from + 2, to - 1);
        from = s.indexOf('\"', to);
        to = s.indexOf('\"', from + 1);
        model.userAgent = s.substring(from + 1, to);
        return model;
    }
}