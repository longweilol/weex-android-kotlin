package com.fby.promotion.Main.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;

import com.alibaba.fastjson.JSON;
import com.taobao.weex.adapter.DefaultWXHttpAdapter;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * Created by fengfeng on 2018/11/7.
 */


public class WXHttpsAdapter extends DefaultWXHttpAdapter {

    private ExecutorService mExecutorService;
    @NonNull
    @Override
    public IEventReporterDelegate getEventReporterDelegate() {
        return super.getEventReporterDelegate();
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection conn = null;
        if (url.getProtocol().toLowerCase().equals("https")) {
            trustAllHosts();
            HttpsURLConnection httpsCon = (HttpsURLConnection) url.openConnection();
            httpsCon.setRequestProperty("cookie", getCookie());
            httpsCon.setHostnameVerifier(DO_NOT_VERIFY);
            conn = httpsCon;
//            setCookies(httpsCon.getHeaderFields());
        } else {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("cookie", getCookie());

        }
        return conn;
    }

    private void execute(Runnable runnable){
        if(mExecutorService==null){
            mExecutorService = Executors.newFixedThreadPool(3);
        }
        mExecutorService.execute(runnable);
    }

    @Override
    public void sendRequest(final WXRequest request, final OnHttpListener listener) {
        if (listener != null) {
            listener.onHttpStart();
        }
        execute(new Runnable() {
            @Override
            public void run() {
                WXResponse response = new WXResponse();
                IEventReporterDelegate reporter = getEventReporterDelegate();
                try {
                    HttpURLConnection connection = openConnection(request, listener);
                    reporter.preConnect(connection, request.body);
                    Map<String,List<String>> headers = connection.getHeaderFields();
                    int responseCode = connection.getResponseCode();
                    if(listener != null){
                        listener.onHeadersReceived(responseCode,headers);
                    }
                    reporter.postConnect();
                    setCookies(connection.getHeaderFields());
                    response.statusCode = String.valueOf(responseCode);
                    if (responseCode >= 200 && responseCode<=299) {
                        InputStream rawStream = connection.getInputStream();
                        rawStream = reporter.interpretResponseStream(rawStream);
                        response.originalData = readInputStreamAsBytes(rawStream, listener);
                    } else {
                        response.errorMsg = readInputStream(connection.getErrorStream(), listener);
                    }
                    if (listener != null) {
                        listener.onHttpFinish(response);
                    }
                } catch (IOException|IllegalArgumentException e) {
                    e.printStackTrace();
                    response.statusCode = "-1";
                    response.errorCode="-1";
                    response.errorMsg=e.getMessage();
                    if(listener!=null){
                        listener.onHttpFinish(response);
                    }
                    if (e instanceof IOException) {
                        reporter.httpExchangeFailed((IOException) e);
                    }
                }
            }
        });
    }
    private byte[] readInputStreamAsBytes(InputStream inputStream,OnHttpListener listener) throws IOException{
        if(inputStream == null){
            return null;
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        int readCount = 0;
        byte[] data = new byte[2048];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
            readCount += nRead;
            if (listener != null) {
                listener.onHttpResponseProgress(readCount);
            }
        }

        buffer.flush();

        return buffer.toByteArray();
    }

    private String readInputStream(InputStream inputStream, OnHttpListener listener) throws IOException {
        if(inputStream == null){
            return null;
        }
        StringBuilder builder = new StringBuilder();
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        char[] data = new char[2048];
        int len;
        while ((len = localBufferedReader.read(data)) != -1) {
            builder.append(data, 0, len);
            if (listener != null) {
                listener.onHttpResponseProgress(builder.length());
            }
        }
        localBufferedReader.close();
        return builder.toString();
    }

    private HttpURLConnection openConnection(WXRequest request, OnHttpListener listener) throws IOException {
        URL url = new URL(request.url);
        HttpURLConnection connection = createConnection(url);
        connection.setConnectTimeout(request.timeoutMs);
        connection.setReadTimeout(request.timeoutMs);
        connection.setUseCaches(false);
        connection.setDoInput(true);

        if (request.paramMap != null) {
            Set<String> keySets = request.paramMap.keySet();
            for (String key : keySets) {
                connection.addRequestProperty(key, request.paramMap.get(key));
            }
        }

        if ("POST".equals(request.method) || "PUT".equals(request.method) || "PATCH".equals(request.method)) {
            connection.setRequestMethod(request.method);
            if (request.body != null) {
                if (listener != null) {
                    listener.onHttpUploadProgress(0);
                }
                connection.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.write(request.body.getBytes());
                out.close();
                if (listener != null) {
                    listener.onHttpUploadProgress(100);
                }
            }
        } else if (!TextUtils.isEmpty(request.method)) {
            connection.setRequestMethod(request.method);
        } else {
            connection.setRequestMethod("GET");
        }

        return connection;
    }

    //host不验证
    private HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    //信任所有证书
    private static void trustAllHosts() {
        final String TAG = "trustAllHosts";
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Log.i(TAG, "checkClientTrusted");
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Log.i(TAG, "checkServerTrusted");
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie("cookie");
        if (cookie != null) {
            return cookie;
        } else {
            return "";
        }
    }
    private void setCookie(String cookie) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie("cookie", cookie);
    }
    private void setCookies(Map<String, List<String>> headerFields) {
        if (null == headerFields) {
            return;
        }
        List<String> cookies = headerFields.get("Set-Cookie");
        Log.e("cookies", JSON.toJSONString(cookies));
        if (null == cookies) {
            return;
        }
        for (String cookie : cookies) {
            setCookie(cookie.split("\\;")[0]);
        }
    }
}
