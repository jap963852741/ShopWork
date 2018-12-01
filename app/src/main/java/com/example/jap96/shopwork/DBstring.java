package com.example.jap96.shopwork;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class DBstring {

    public static String DB1(String i){
        String result="";
        try {
            HttpClient  HC = new DefaultHttpClient();
            HttpPost HP = new HttpPost("http://jap963852741.byethost9.com/");
            HP.addHeader("Cookie","__test=e1cb93b3476555f28ed2ac2b9f0473be;expires=Thu,31-Dec-37 23:55:55 GMT;path=/");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            HP.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
            HttpResponse HR = HC.execute(HP);
            result= EntityUtils.toString(HR.getEntity(),HTTP.UTF_8);
        }catch (Exception e){
            Log.i("lg",e.toString());
        }
        return result;
    }
}
