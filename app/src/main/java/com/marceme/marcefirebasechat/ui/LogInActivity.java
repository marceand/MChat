package com.marceme.marcefirebasechat.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.marceme.marcefirebasechat.FireChatHelper.ReferenceUrl;
import com.marceme.marcefirebasechat.R;

public class LogInActivity extends Activity {

    private EditText mUserEmail;
    private EditText mUserPassWord;
    private Button mLoginToMChat;
    private Button mRegisterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Hide action bar
        this.getActionBar().hide();// Handel this carefully

        // Initialize
        mUserEmail =(EditText)findViewById(R.id.userEmailChat);
        mUserPassWord =(EditText)findViewById(R.id.passWordChat);;
        mLoginToMChat =(Button)findViewById(R.id.btn_LogInChat);
        mRegisterUser=(Button)findViewById(R.id.registerUser);

        // Log In click listener
        mLoginToMChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Validate input text */

                // Get user email and password
                String userName = mUserEmail.getText().toString();
                String passWord = mUserPassWord.getText().toString();

                // Omit space
                userName = userName.trim();
                passWord = passWord.trim();

                // validate fields
                if (userName.isEmpty() || passWord.isEmpty()) {
                    // show message when field is empty
                    showErrorMessageToUser(getString(R.string.login_error_message));

                } else {
                    // Log in
                    Firebase authenticateUser = new Firebase(ReferenceUrl.FIREBASE_CHAT_URL); // Get app main firebase url
                    authenticateUser.authWithPassword(userName, passWord, authResultHandler);
                }


            }
        });

        // User wants register screen
        mRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });




    }

    // Create a handler to handle the result of the authentication
    // See Firebase doc: https://www.firebase.com/docs/android/guide/user-auth.html
    // Remember this: Tokens issued to the authenticated users are
    // valid for 24 hours by default. You can change this from
    // the Login & Auth tab on your App Dashboard.
    Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
        @Override
        public void onAuthenticated(AuthData authData) {
            // Authenticated successfully with payload authData
            // Go to main activity
            Intent intent=new Intent(LogInActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            // Authenticated failed, show Firebase error to user
            showErrorMessageToUser(firebaseError.getMessage());
        }
    };


    private void showErrorMessageToUser(String errorMessage){
        // Create an AlertDialog to show error message
        AlertDialog.Builder builder=new AlertDialog.Builder(LogInActivity.this);
        builder.setMessage(errorMessage)
                .setTitle(getString(R.string.login_error_title))
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

}
