/* Create by ruanmingcong on   */
//初始化全局变量
!function () {
    let BASE_URL, isTest = false, homePageUrl = '/static/index.html';
    let uri = window.parent.location.href.toLowerCase();
    let serverUrl = uri.split("/")[2];
    if (serverUrl.indexOf("backend.gudsen.vip") >= 0) {
        BASE_URL = "https://backend.gudsen.vip";
    } else if (serverUrl.indexOf("backend-test.gudsen.vip") >= 0) {
        BASE_URL = "http://backend-test.gudsen.vip";
        isTest = true;
    } else {
        BASE_URL = "http://127.0.0.1:10012";
        isTest = true;
        homePageUrl = "inde x.html";
    }
    let lang = getCurrentLanguage();
    window.localStorage.setItem("BASE_URL", BASE_URL);
    window.localStorage.setItem("isTest", isTest);
    window.localStorage.setItem("language", lang);
    window.localStorage.setItem("homePageUrl", homePageUrl);
}(window);

var
    BASE_URL = window.localStorage.getItem("BASE_URL"),
    isTest = window.localStorage.getItem("isTest"),
    language = window.localStorage.getItem("language"),
    colorArray = ['#141614', '#E8BF6A', '#1677DD', '#E81B0E', '#075bba', '#E88D2F', '#5BE817', '#C370E8',];

/**
 *  http post method
 * @param url
 * @param param js 对象
 * @param done 响应完成后的callback
 */
function httpPost(url, param, done) {
    httpRequest('post', url, param, true, done);
}

/**
 *  http post method
 * @param url
 * @param param js 对象
 * @param done 响应完成后的callback
 */
function httpGet(url, param, done) {
    httpRequest('get', url, param, true, done);
}


/**
 *  http get method sync
 * @param url
 * @param param js 对象
 * @param done 响应完成后的callback
 */
function httpGetSync(url, param, done) {
    httpRequest('get', url, param, false, done);
}

/**
 * http 请求
 * @param method
 * @param url
 * @param param
 * @param done
 */
function httpRequest(method, url, param, async, done) {
    let xhr = new XMLHttpRequest();
    //删除空属性
    for (let paramKey in param) {
        if (!param[paramKey]) {
            delete param[paramKey];
        }
    }
    if (method == 'get' && param != null) {
        let qs = object2QueryString(param);
        if (qs.length > 0) {
            url = url + "?" + qs;
        }
    }
    xhr.open(method, url, async);
    if (method == 'post') {
        xhr.setRequestHeader("Content-type", "application/json;charset=utf-8");
    }
    xhr.setRequestHeader("AccessToken", window.localStorage.getItem("AccessToken"));
    xhr.withCredentials = true;
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            let res = JSON.parse(xhr.responseText);
            if (interceptor(res)) {
                done(res);
            }
        }
    };
    try {
        if (method == 'post') {
            xhr.send(JSON.stringify(param));
        } else {
            xhr.send();
        }
    } catch (e) {
        alert("网络异常，请检查你的网络连接")
    }
}


/**
 * 拦截器
 * @param res
 * @returns {boolean} true 通过，false拦截
 */
function interceptor(res) {
//    do something
    if (res.code == 7 || res.code == 6) {
        window.location.href = window.localStorage.getItem("homePageUrl");
        return false;
    }
    return true;
}


/**
 * 将 js 对象转换为 query string
 * @param obj
 * @returns {string}  key=value&k2=v2
 */
function object2QueryString(obj) {
    var queryStr = "";
    for (var f in obj) {
        queryStr += f + "=" + obj[f] + "&";
    }
    return queryStr.slice(0, queryStr.length - 1);
}

/**
 * xor 简单加密
 * @param text
 * @returns {string}
 */
function xor(text) {
    if (!text) return "";
    var n = "";
    for (var i = 0; i < text.length; i++)
        n += String.fromCharCode(text[i].charCodeAt() ^ 17);
    return n;
}


function log(msg) {
    if (window.localStorage.getItem("isTest"))
        console.log(msg)
}

function isPhone(phone) {
    return /^1\d{10}$/.test(phone);
}

function isEmail(email) {
    return /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test(email);
}


/**
 * 获取请求url中的指定名称参数
 * @param name
 * @returns {*}
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
    if (r != null) return decodeURI(r[2]);
    return null;
}

function getCurrentLanguage() {
    var type = navigator.appName;
    if (type == "Netscape") {
        var lang = navigator.language;//获取浏览器配置语言，支持非IE浏览器
    } else {
        var lang = navigator.userLanguage;//获取浏览器配置语言，支持IE5+ == navigator.systemLanguage
    }
    var lang = lang.substr(0, 2);//获取浏览器配置语言前两位
    return lang;
}


/**
 * 判断一个数组是否包含一个数据
 * 若包含：返回data
 */
function isContains(arr, condition) {
    if (arr == null || arr.length == 0) return;
    for (var i = 0; i < arr.length; i++) {
        var c = condition(arr[i])
        if (arr[i] == c) {
            return arr[i];
        }
    }
    return null;
}

/**
 * 分组
 * @param arr
 * @param condition
 */
function groupBy(arr, condition) {
    var res = {};
    if (arr != null) {
        for (var i = 0; i < arr.length; i++) {
            var key = condition(arr[i]);
            if (res[key] == null) {
                res[key] = [];
            }
            res[key].push(arr[i]);
        }
    }
    return res;
}

/**
 * 通过字符串hash获取颜色
 * @param string
 * @returns {string}
 */
function getColorByHash(string) {
    var index = 0;
    index = (Math.abs(stringHashCode(string))) % (colorArray.length);
    return colorArray[index];
}

/**
 * 计算字符串hash
 * @param str
 * @returns {number}
 */
function stringHashCode(str) {
    var hash = 0;
    if (str != null && str.length > 0) {
        for (let i = 0; i < str.length; i++) {
            let char = str.charCodeAt(i);
            hash = 31 * hash + char;
            hash = hash & hash;
        }
    }
    return hash;
}

/**
 * 获取对象中第 index 属性值
 * @param srcObj
 * @param index from 0
 * @returns {*}
 */
function getValueByIndex(srcObj, index) {
    if (srcObj == null) return null;
    index = index ? index : 0;
    var arr = [];
    for (k in srcObj) {
        arr.push(srcObj[k]);
    }
    if (index >= arr.length) return null;
    return arr[index];
}

function getEncryptPass(str, num) {
    let r = str;
    num = num || 3;
    for (let i = 0; i < num; i++) {
        r = window.btoa(r);
    }
    return r;
}