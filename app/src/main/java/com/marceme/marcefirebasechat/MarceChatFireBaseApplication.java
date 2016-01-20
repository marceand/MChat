package com.marceme.marcefirebasechat;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Marcel on 11/6/2015.
 */
public class MarceChatFireBaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
