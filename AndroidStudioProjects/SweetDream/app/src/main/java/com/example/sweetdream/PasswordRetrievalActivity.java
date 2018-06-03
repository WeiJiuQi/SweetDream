package com.example.sweetdream;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PasswordRetrievalActivity extends AppCompatActivity {

    private EditText edi;
    private EditText code;
    private EditText password;
    private String userName;
    private boolean truecode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_retrieval);
        edi = (EditText)findViewById(R.id.phone);
        password = (EditText)findViewById(R.id.password);
        code = (EditText)findViewById(R.id.message);
        truecode = true;
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public void onClickHandler(View view)
    {
        if(view.getId() == R.id.backTo)
        {
            final Intent intent=new Intent(PasswordRetrievalActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
        else if(view.getId() == R.id.getMessage)
        {
            String str = edi.getText().toString();
            if(isChinaPhoneLegal(str))
            {
                String phone_sha512=sha512(str);
                String stringUrl = "http://39.106.155.126:3000/isexist?user_name="+phone_sha512;
                new Interaction().execute(stringUrl);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"请输入正确格式的手机号！",Toast.LENGTH_SHORT).show();
            }
        }
        else if(view.getId() == R.id.submit)
        {
//            Log.v("userName", userName);
//            Log.v("editext", edi.getText().toString())
            if(userName == null)
            {
                Toast.makeText(getApplicationContext(),"请获取验证码！",Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(!userName.equals(edi.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"手机号错误！",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(password.getText().toString().length()<=4)
                    {
                        Toast.makeText(getApplicationContext(),"密码长度需大于4位！",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        submitCode("86", userName, code.getText().toString());
                        if(!truecode)
                        {
                            Toast.makeText(getApplicationContext(),"验证码错误！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                }
                else{
                    // TODO 处理错误的结果
                }
            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    String phone_sha512=sha512(edi.getText().toString());
                    String pwd_sha512=sha512(password.getText().toString());
                    String stringUrl = "http://39.106.155.126:3000/updatepwd?user_name="+phone_sha512+"&pwd="+pwd_sha512;
                    new WebInteraction().execute(stringUrl);

                    final Intent intent=new Intent(PasswordRetrievalActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                } else{
                    // TODO 处理错误的结果
                    truecode = false;
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    };

    public class Interaction extends AsyncTask {
        @Override
        protected String doInBackground(Object[] objects) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl((String) objects[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }

        }

        @Override
        protected void onPostExecute(Object o) {
            try {
                JSONObject obj = new JSONObject((String) o);
                int number = obj.getInt("resultNum");
                Log.v("num", String.valueOf(number));
                if(number == 0)
                {
                    Toast.makeText(getApplicationContext(),"该手机号未注册！",Toast.LENGTH_SHORT).show();
                }
                else if(number == 1)
                {
                    userName = edi.getText().toString();
                    sendCode("86", userName);
                    Toast.makeText(getApplicationContext(),"已发送验证码！",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"未知错误！",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                //Log.d(DEBUG_TAG, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                //Log.d(DEBUG_TAG, contentAsString);
                return contentAsString;
                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }


        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
    }

    public String sha512(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("sha-256");
            byte[] bytes = md5.digest((string).getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}

