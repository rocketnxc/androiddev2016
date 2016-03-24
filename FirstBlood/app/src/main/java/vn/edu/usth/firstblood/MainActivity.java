package vn.edu.usth.firstblood;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private LoginButton fbButton;
    private Intent pagerIntent;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //to initialize the SDK
        FacebookSdk.sdkInitialize(getApplicationContext());
        pagerIntent = new Intent(MainActivity.this, PagerActivity.class);

        //call CallbackManager.Factory.create to create a callback manager to handle login responses
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);


        fbButton = (LoginButton) findViewById(R.id.login_button);
        continueButton = (Button) findViewById(R.id.details);
        //initialize the continue button (invisible when no Accesstoken)
        continueButton.setVisibility(View.INVISIBLE);

        //set permissions for using facebook graph
        fbButton.setReadPermissions("public_profile email user_friends user_photos user_posts user_birthday user_education_history user_hometown");

        //Check if currently access a token, then call Request name to set text for continue button
        if (AccessToken.getCurrentAccessToken() != null) {
            continueButton.setVisibility(View.VISIBLE);
        }

        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessToken.getCurrentAccessToken() != null)
                    continueButton.setVisibility(View.INVISIBLE);
            }
        });

        //Callbacks is to inform a class synchronous / asynchronous if some work in another class is done
        //In this case Facebookcallback will inform the login status
        fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (AccessToken.getCurrentAccessToken() != null) {
                    Toast.makeText(MainActivity.this, "Successful Login!!", Toast.LENGTH_LONG).show();
                    continueButton.setVisibility(View.VISIBLE);
                    RequestUserInfo(pagerIntent);
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Login attempt canceled.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(MainActivity.this, "Login attempt failed.", Toast.LENGTH_LONG).show();
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestUserInfo(pagerIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        printKeyHash(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Print keyhash for testing the application
     *
     * @param context Activity
     * @return keyhash of the device
     */
    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    /**
     * Request name and id of the logged-in user
     * Set text of continue button to "Continue as " + name of the user
     */
    private void RequestUserInfo(final Intent intent) {
        /* make the API call */
        Bundle params = new Bundle();
        //set fields of information that needed
        params.putString("fields", "id,picture,about,age_range,birthday,cover,email,education,gender,hometown,name");
        GraphRequestAsyncTask graphRequest = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                         /* handle the result */
                        try {
                            JSONObject jsonObjectUserInfo = response.getJSONObject();
                            Log.i("UserInfos1", jsonObjectUserInfo.toString());
                            continueButton.setText("Continue as " + jsonObjectUserInfo.getString("name"));
                            intent.putExtra("JSONUserInfo", jsonObjectUserInfo.toString());
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

}
