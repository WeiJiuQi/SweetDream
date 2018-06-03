package com.example.sweetdream;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via phone/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mphoneView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private static final String DEBUG_TAG = "HttpExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mphoneView = (AutoCompleteTextView) findViewById(R.id.phone);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mphoneSignInButton = (Button) findViewById(R.id.phone_sign_in_button);
        mphoneSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mforgetPassword = (Button) findViewById(R.id.forgetPassword);
        mforgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent=new Intent(LoginActivity.this, PasswordRetrievalActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mphoneView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid phone, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mphoneView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String phone = mphoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid phone address.
        if (TextUtils.isEmpty(phone)) {
            mphoneView.setError(getString(R.string.error_field_required));
            focusView = mphoneView;
            cancel = true;
        } else if (!isphoneValid(phone)) {
            mphoneView.setError(getString(R.string.error_invalid_phone));
            focusView = mphoneView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            String phone_sha512=sha512(mphoneView.getText().toString());
            String pwd_sha512=sha512(mPasswordView.getText().toString());
            Log.d("%%%%%%%%%%",phone_sha512);
            String stringUrl = "http://39.106.155.126:3000/login?phone="+phone_sha512+"&pwd="+pwd_sha512;
            new UserLoginTask(phone, password).execute(stringUrl);
        }
    }

    public boolean isphoneValid(String phone) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only phone addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Phone
                .CONTENT_ITEM_TYPE},
                // Show primary phone addresses first. Note that there won't be
                // a primary phone address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> phones = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            phones.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addphonesToAutoComplete(phones);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addphonesToAutoComplete(List<String> phoneAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, phoneAddressCollection);

        mphoneView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask {

        private final String mphone;
        private final String mPassword;

        UserLoginTask(String phone, String password) {
            mphone = phone;
            mPassword = password;
        }


        @Override
        protected String doInBackground(Object[] objects) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl((String)objects[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }

        }

        @Override
        protected void onPostExecute(Object o) {
            try {
                JSONObject obj = new JSONObject((String)o);
                int number = obj.getInt("resultnum");

                Log.d("#######",number+"");
                if(number==2){
                    Toast.makeText(getApplicationContext(),"欢迎进入Sweet Dream！",Toast.LENGTH_SHORT).show();
                    UserManage.getInstance().saveUserInfo(LoginActivity.this, mphone, mPassword);
                    final Intent intent=new Intent(LoginActivity.this,MainPageActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }else if(number==1){
                    // TODO: 2018/5/8 所有变量取出来了
                    int pic_cnt=obj.getInt("pic_cnt");
                    float time1=(float)obj.getDouble("time1");
                    float time2=(float)obj.getDouble("time2");
                    float time3=(float)obj.getDouble("time3");
                    float time4=(float)obj.getDouble("time4");
                    float time5=(float)obj.getDouble("time5");
                    float time6=(float)obj.getDouble("time6");
                    float time7=(float)obj.getDouble("time7");
                    int sleep_hh=obj.getInt("sleep_hh");
                    int sleep_mm=obj.getInt("sleep_mm");
                    int wake_hh=obj.getInt("wake_hh");
                    int wake_mm=obj.getInt("wake_mm");

                    UserManage.getInstance().saveUserPicCnt(LoginActivity.this, pic_cnt);
                    UserManage.getInstance().updateNums(LoginActivity.this, time1, time2, time3, time4, time5, time6, time7);
                    UserManage.getInstance().saveUserSleepTime(LoginActivity.this, sleep_hh, sleep_mm);
                    String sleep_hhh, sleep_mmm, wake_hhh, wake_mmm;
                    if(String.valueOf(sleep_hh).length() == 1)
                    {
                        sleep_hhh = "0"+String.valueOf(sleep_hh);
                    }
                    else
                    {
                        sleep_hhh = String.valueOf(sleep_hh);
                    }
                    if(String.valueOf(sleep_mm).length() == 1)
                    {
                        sleep_mmm = "0"+String.valueOf(sleep_mm);
                    }
                    else
                    {
                        sleep_mmm = String.valueOf(sleep_mm);
                    }
                    UserManage.getInstance().saveUserSleepTimeS(LoginActivity.this, sleep_hhh, sleep_mmm);
                    UserManage.getInstance().saveUserWakeUpTime(LoginActivity.this, wake_hh, wake_mm);
                    if(String.valueOf(wake_hh).length() == 1)
                    {
                        wake_hhh = "0"+String.valueOf(wake_hh);
                    }
                    else
                    {
                        wake_hhh = String.valueOf(wake_hh);
                    }
                    if(String.valueOf(wake_mm).length() == 1)
                    {
                        wake_mmm = "0"+String.valueOf(wake_mm);
                    }
                    else
                    {
                        wake_mmm = String.valueOf(wake_mm);
                    }
                    UserManage.getInstance().saveUserWakeUpTimeS(LoginActivity.this, String.valueOf(wake_hhh), String.valueOf(wake_mmm));

                    Toast.makeText(getApplicationContext(),"欢迎回来！",Toast.LENGTH_SHORT).show();
                    UserManage.getInstance().saveUserInfo(LoginActivity.this, mphone, mPassword);
                    final Intent intent=new Intent(LoginActivity.this,MainPageActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }else{
                    Toast.makeText(getApplicationContext(),"用户名密码不匹配！",Toast.LENGTH_SHORT).show();
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
                Log.d(DEBUG_TAG, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                Log.d(DEBUG_TAG, contentAsString);
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


        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }

    // When user clicks button, calls AsyncTask.
    // Before attempting to fetch the URL, makes sure that there is a network connection.

    public static String sha512(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("sha-256");
            byte[] bytes = md5.digest((string ).getBytes());
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

