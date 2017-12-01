package com.ghifa.mobile.kelompokproyek;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ghifa.mobile.kelompokproyek.component.ControllerUrl;
import com.ghifa.mobile.kelompokproyek.component.Fungsi;
import com.ghifa.mobile.kelompokproyek.http.HttpClient;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mobile on 11/23/2017.
 */

public class IndexActivity extends AppCompatActivity {

    ProgressDialog dialog;
    SharedPreferences session;
    ControllerUrl link_url;
    String username, password;
    private UserLoginTask mAuthTask = null;
    private EditText emailEditText, passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        dialog = new ProgressDialog(IndexActivity.this);
        dialog.setMessage("Loading..");
        dialog.setTitle("Please Wait");
        dialog.setCancelable(false);

        // field inputan pada android
        emailEditText   = (EditText) findViewById(R.id.username);
        passEditText    = (EditText) findViewById(R.id.password);

        session     = PreferenceManager.getDefaultSharedPreferences(IndexActivity.this);
        link_url    = new ControllerUrl(IndexActivity.this);

        int login = session.getInt(link_url.TAG_IS_LOGIN, 0);

        Log.d("status_login", Integer.toString(login));

        if(login==link_url.IS_LOGIN) {
//            Intent utama = new Intent(IndexActivity.this, Main2Activity.class);
//            startActivity(utama);
//            finish();
            Log.d("status_session","sesion ditemukan");
        }else{
            Log.d("status_session","session not found");
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private final String mUsername;
        private final String mPassword;
        private final String mToken;

        UserLoginTask(String username, String password, String token) {
            Fungsi generate = new Fungsi();
            mUsername = username;
            mPassword = generate.MD5_Hash(password) + generate.MD5_Hash(username);
            mToken = token;
        }

        ProgressDialog pDialog;

        protected void onPreExecute() {
            //create and display your alert here
            pDialog = ProgressDialog.show(IndexActivity.this,"Please wait...", "Now Loading ..", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                HttpClient client = new HttpClient();
                String json = client.setLoginJson(mUsername, mPassword,mToken);
                String postResponse = client.doPostRequest(link_url.getURLLogin(), json);

                Log.d("onSuccess", postResponse);

                JSONObject response = new JSONObject(postResponse);

                // cek apakah berhasil atau tidak
                if (response.getInt("sukses") == 0 ) {

                    return false;

                }else{

                    SharedPreferences.Editor setSession = session.edit();
                    /**
                     * menyimpan session login
                     */
                    setSession.putString("status_login", response.getString("sukses"));
                    setSession.putInt(link_url.TAG_IS_LOGIN, link_url.IS_LOGIN);
                    setSession.putString("email", response.getString("email"));
                    setSession.putString("nama", response.getString("name"));
                    setSession.apply();

                }

            } catch (IOException e) {
                Log.d("IOException", e.toString());
                return false;
            } catch (JSONException e) {
                Log.d("JSONException", e.toString());
                return false;
            }

            return true;
        }

        protected  void onPostExecute(final Boolean success) {
            pDialog.dismiss();

            if (success) {
                Intent utama = new Intent(IndexActivity.this, Main2Activity.class);
                startActivity(utama);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "email atau password anda salah !!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    public void checkLogin(View arg0) {

        final String email = emailEditText.getText().toString();
        if (!isValidEmail(email)) {
            emailEditText.setError("masukan email dengan benar");
        }

        final String pass = passEditText.getText().toString();
        if (!isValidPassword(pass)) {
            passEditText.setError("Password tidak boleh kosong");
        }

        if(isValidEmail(email) && isValidPassword(pass))
        {
            final String tokenID = FirebaseInstanceId.getInstance().getToken();

            username = emailEditText.getText().toString();
            password = passEditText.getText().toString();

            mAuthTask = new UserLoginTask(username, password, tokenID);
            mAuthTask.execute((Void) null);

 //           SharedPreferences.Editor setSession = session.edit();

//            setSession.putString("status_login", "1");
//            setSession.apply();

//            Log.d("firebase","sukses buat akun");
//
//            Intent utama = new Intent(IndexActivity.this, MainActivity.class);
//            startActivity(utama);
//            finish();
        }


    }

    // validating email id
    private boolean isValidEmail(String email) {
        /*fungsi validasi email*/
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 1) {
            return true;
        }
        return false;
    }


}
