package com.lenovohit.administrator.androidlib.net;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.lenovohit.administrator.androidlib.entity.Response;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SharkChao on 2017-07-18.
 * 使用AsyncTask的坏处，如果前边的请求还没有完成，就不会执行之后的网络请求，会堵塞掉
 */

public abstract  class BaseAsyncTask extends AsyncTask<String,Integer,Response>{
    public static final String POST="POST";
    public static final String GET="GET";
    private Map<String,String>maps=new HashMap<>();
    public BaseAsyncTask(Map<String,String>map) {
        super();
        maps=map;
    }

    //访问网络之前的准备工作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //一次访问网络完成后执行的ui操作
    @Override
    protected void onPostExecute(Response s) {
        super.onPostExecute(s);
        if (s.isError()){
            fail(s.getErrorMessage());
        }else {
            success(s.getResult());
        }

    }

    //进度条刷新操作
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    //不管访问网络是否成功，关闭时的操作
    @Override
    protected void onCancelled(Response s) {
        super.onCancelled(s);
    }

    //不管访问网络是否成功，关闭时的操作
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    //在后台执行的操作，在此处访问网络
    @Override
    protected Response doInBackground(String... params) {
        Response responseFromUrl = getResponseFromUrl(params[0],params[1]);
        return responseFromUrl;
    }

    public Response getResponseFromUrl(String url,String type){
        Response response=new Response();
        try {
            URL murl = new URL(url);
            URLConnection connection = murl.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);
            if (type.equals(POST)){

                // Post请求必须设置允许输出 默认false
                httpURLConnection.setDoOutput(true);
                //设置请求允许输入 默认是true
                httpURLConnection.setDoInput(true);
                // Post请求不能使用缓存
                httpURLConnection.setUseCaches(false);
                // 设置为Post请求
                httpURLConnection.setRequestMethod("POST");
                //设置本次连接是否自动处理重定向
                httpURLConnection.setInstanceFollowRedirects(true);
                // 配置请求Content-Type
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
                // 发送请求参数
                printWriter.write(Map2String(maps));//post的参数 xx=xx&yy=yy
            }else {
                // 设置为Get请求
                httpURLConnection.setRequestMethod("GET");
            }
            //建立连接
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200){
                InputStream inputStream = httpURLConnection.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[]bytes = new byte[254];
                int i=0;
                while ((i = inputStream.read(bytes)) !=-1){
                    outputStream.write(bytes,0,i);
                }
                String s = outputStream.toString();
                if (TextUtils.isEmpty(s)){
                    response.setError(true);
                    response.setErrorType(1);
                    response.setErrorMessage("网络异常，返回空值");
                }else {
                    response.setError(false);
                    response.setErrorType(0);
                    response.setErrorMessage("");
                    response.setResult(s);
                }
            }else {
                //不等于200说明是服务器错误
                response.setError(true);
                response.setErrorType(1);
                response.setErrorMessage("服务器端错误");
            }
            // 关闭连接
            httpURLConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(true);
            response.setErrorType(-1);
            response.setErrorMessage("客户端网络不稳定");
        }
        return response;
    }
    public abstract void success(String connect);
    public abstract void fail(String errorMessage);
    public  String Map2String(Map<String,String>map){
        StringBuilder out = new StringBuilder();
        for (String key : map.keySet()) {
            if(out.length()!=0){
                out.append("&");
            }
            out.append(key).append("=").append(map.get(key));
        }
        return String.valueOf(out);
    }
}
