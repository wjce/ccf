package com.wjc.ccf.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author wangjunce 2018/10/29 11:02
 */
public interface HttpClient {

    HttpResponse doRequest(HttpMethod method, String url, Map<String, String> userHeaders, InputStream data, Map<String, String> cookies) throws IOException;

    byte[] doGet(String url) throws IOException;

    HttpResponse doGet(String url, Map<String, String> headers) throws IOException;

    HttpResponse doGet2(String url, Map<String, String> headers, Map<String, String> cookies) throws IOException;

    HttpResponse dopost(String url, Map<String, String> headers, InputStream data, Map<String, String> cookies) throws IOException;

}