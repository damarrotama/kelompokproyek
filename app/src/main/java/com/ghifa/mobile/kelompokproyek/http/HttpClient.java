package com.ghifa.mobile.kelompokproyek.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {

    OkHttpClient client = new OkHttpClient();
    Call call;
    Request request;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * dibagian ini untuk membuat parameter yang akan di post ke url webservice
     * dengan format json
     * setiap url yang berbeda format json nya harus di buat function nya sendiri seperti dibawah
     * berdasarkan parameter yang dibutuhkan
     * paramerter menggunakan Type String saja
     */


    public String setLoginJson(
            String username,
            String password,
            String token

    ){
        return "{\"username\": \"" + username + "\", " +
                "\"token\": \"" + token + "\", " +
                "\"password\": \"" + password +
                "\"}";
    }


    public String setJson(String[] kolom,String[] isi){

        String data = "{";

        for (int i = 0; i < kolom.length; ++i)
        {
            String formatIsi = "";
            String isiAsli = isi[i];
            String isFormat = "string";

            String retype = isi[i];
            String[] dataAr = retype.split(":");

            if(dataAr[0].equals("angka")){
                formatIsi = dataAr[1];
                isFormat = "angka";
            }

            if(i==( kolom.length - 1 )){
                if(isFormat.equals("string")) {
                    data = data + "\"" + kolom[i] + "\":\"" + isiAsli + "\"";
                } else if (isFormat.equals("angka")) {
                    data = data + "\"" + kolom[i] + "\":" + formatIsi + "";
                }
            } else {
                if(isFormat.equals("string")) {
                    data = data + "\"" + kolom[i] + "\":\"" + isiAsli + "\", ";
                } else if (isFormat.equals("angka")) {
                    data = data + "\"" + kolom[i] + "\":" + formatIsi + ",";
                }
            }

        }

        return data + "}";
    }

    /**
     * doGetRequest
     *
     * Mengambil data dari webervice menggunakan metode GET
     *
     * @param url
     * @return String
     *
     */
    public String doGetRequest(String url) throws IOException {
        request = new Request.Builder()
                .url(url)
                .build();

        call = client.newCall(request);

        Response response = call.execute();
        return response.body().string();
    }

    /**
     * untuk menbatalkan proses
     */
    public void doCancel(){
        call.cancel();
    }

    /**
     * doPostRequest
     *
     * disarankan menggunakan metode POST saja
     * Mengambil data dari webervice menggunakan metode POST dengan format data JSON
     *
     * @param url
     * @param json
     * @return String
     */
    public String doPostRequest(String url, String json) throws IOException {

        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.readTimeout(120, TimeUnit.SECONDS);
        b.writeTimeout(120, TimeUnit.SECONDS);

        OkHttpClient clients = b.build();

        RequestBody body = RequestBody.create(JSON, json);
        request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        call = clients.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }
    public String doPostBody(String url, RequestBody body) throws IOException {

        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.readTimeout(120, TimeUnit.SECONDS);
        b.writeTimeout(120, TimeUnit.SECONDS);

        OkHttpClient clients = b.build();

        request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        call = clients.newCall(request);
        Response response = call.execute();
        return response.body().string();

    }

    /**
     *   Contoh Penggunaannya :
     *
     *   HttpClient client = new HttpClient();
     *   String json = client.LoginJson("Jesse", "123456");
     *   String postResponse = client.doPostRequest("http://www.app.com/login", json);
     *
     *   dari request itu kan memPOST data
     *   {"username": "Jesse", "password": "123456"}
     *
     */

}
