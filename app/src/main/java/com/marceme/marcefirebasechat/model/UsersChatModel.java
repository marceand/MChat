package com.marceme.marcefirebasechat.model;

import android.nfc.Tag;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Marcel on 11/11/2015.
 */

//@JsonIgnoreProperties(ignoreUnknown = true)
//It will ignore every property you haven't defined in your POJO.
// Very useful when you are just looking for a couple of properties
// in the JSON and don't want to write the whole mapping.

//@JsonIgnoreProperties(ignoreUnknown = true) //this help you to ignore provider
public class UsersChatModel implements Parcelable{

    /*recipient info*/
    private String firstName;
    private String provider; //if you don't include this app crash
    private String userEmail;
    private String createdAt;
    private String connection;
    private int    avatarId;
    private String mRecipientUid;

    /*Current user (or sender) info*/
    private String mCurrentUserName;
    private String mCurrentUserUid;
    private String mCurrentUserEmail;
    private String mCurrentUserCreatedAt;

    public UsersChatModel(){
        //required empty username
    }

    private UsersChatModel(Parcel parcelIn) {

        //Remember the order used to read data is the same used to write them
        firstName=parcelIn.readString();
        provider=parcelIn.readString();
        userEmail=parcelIn.readString();
        createdAt=parcelIn.readString();
        connection=parcelIn.readString();
        avatarId=parcelIn.readInt();
        mRecipientUid=parcelIn.readString();
        mCurrentUserName=parcelIn.readString();
        mCurrentUserUid=parcelIn.readString();
        mCurrentUserEmail=parcelIn.readString();
        mCurrentUserCreatedAt=parcelIn.readString();

    }

    /*Recipient info*/
    public String getFirstName() {
        return firstName;
    }

    public String getUserEmail() {
        //Log.e("user email  ", userEmail);
        return userEmail;
    }

    public String getProvider() {
       return provider;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public String getConnection(){
        return connection;
    }

    public int    getAvatarId(){
        return avatarId;
    }

    public String getRecipientUid(){
        return mRecipientUid;
    }

    public void setRecipientUid(String givenUserUid){
        mRecipientUid =givenUserUid;
    }


    /*Current user (or sender) info*/
    public void setCurrentUserName(String currentUserName){
        mCurrentUserName=currentUserName;
    }

    public void setCurrentUserEmail(String currentUserEmail) {
        mCurrentUserEmail = currentUserEmail;
    }

    public void setCurrentUserCreatedAt(String currentUserCreatedAt) {
        mCurrentUserCreatedAt = currentUserCreatedAt;
    }

    public void setCurrentUserUid(String currentUserUid){
        mCurrentUserUid=currentUserUid;
    }

    public String getCurrentUserName(){
        return mCurrentUserName;
    }

    public String getCurrentUserEmail() {
        //Log.e("current user email  ", mCurrentUserEmail);
        return mCurrentUserEmail;
    }

    public String getCurrentUserCreatedAt() {
        return mCurrentUserCreatedAt;
    }

    public String getCurrentUserUid(){
        return mCurrentUserUid;
    }


    /*create chat endpoint for firebase*/
    public String getChatRef(){
        return createUniqueChatRef();
    }



    private String createUniqueChatRef(){
        String uniqueChatRef="";
        if(createdAtCurrentUser()>createdAtRecipient()){
            uniqueChatRef=cleanEmailAddress(getCurrentUserEmail())+"-"+cleanEmailAddress(getUserEmail());
        }else {

            uniqueChatRef=cleanEmailAddress(getUserEmail())+"-"+cleanEmailAddress(getCurrentUserEmail());
        }
        return uniqueChatRef;
    }

    private long createdAtCurrentUser(){
        return Long.parseLong(getCurrentUserCreatedAt());
    }

    private long createdAtRecipient(){
        return Long.parseLong(getCreatedAt());
    }

    private String cleanEmailAddress(String email){

        //replace dot with comma since firebase does not allow dot
        return email.replace(".","-");

    }


    /*Parcelable*/

    @Override
    public int describeContents() {
        return 0; //ignore
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        //Store information using parcel method
        //the order for writing and reading must be the same
        parcel.writeString(firstName);
        parcel.writeString(provider);
        parcel.writeString(userEmail);
        parcel.writeString(createdAt);
        parcel.writeString(connection);
        parcel.writeInt(avatarId);
        parcel.writeString(mRecipientUid);
        parcel.writeString(mCurrentUserName);
        parcel.writeString(mCurrentUserUid);
        parcel.writeString(mCurrentUserEmail);
        parcel.writeString(mCurrentUserCreatedAt);

    }



    public static final Creator<UsersChatModel> CREATOR= new Creator<UsersChatModel>() {
        @Override
        public UsersChatModel createFromParcel(Parcel parcel) {
            return new UsersChatModel(parcel);
        }

        @Override
        public UsersChatModel[] newArray(int size) {
            return new UsersChatModel[size];
        }
    };
}

//01-17 23:02:27.190 1944-1944/com.marceme.marcefirebasechat E/AndroidRuntime: FATAL EXCEPTION: main
//        Process: com.marceme.marcefirebasechat, PID: 1944
//        java.lang.RuntimeException: Unable to start activity ComponentInfo{com.marceme.marcefirebasechat/com.marceme.marcefirebasechat.ui.ChatActivity}: java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String java.lang.String.replace(java.lang.CharSequence, java.lang.CharSequence)' on a null object reference
//        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2298)
//        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2360)
//        at android.app.ActivityThread.access$800(ActivityThread.java:144)
//        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1278)
//        at android.os.Handler.dispatchMessage(Handler.java:102)
//        at android.os.Looper.loop(Looper.java:135)
//        at android.app.ActivityThread.main(ActivityThread.java:5221)
//        at java.lang.reflect.Method.invoke(Native Method)
//        at java.lang.reflect.Method.invoke(Method.java:372)
//        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:899)
//        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:694)
//        Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String java.lang.String.replace(java.lang.CharSequence, java.lang.CharSequence)' on a null object reference
//        at com.marceme.marcefirebasechat.model.UsersChatModel.cleanEmailAddress(UsersChatModel.java:150)
//        at com.marceme.marcefirebasechat.model.UsersChatModel.createUniqueChatRef(UsersChatModel.java:131)
//        at com.marceme.marcefirebasechat.model.UsersChatModel.getChatRef(UsersChatModel.java:123)
//        at com.marceme.marcefirebasechat.ui.ChatActivity.onCreate(ChatActivity.java:83)
//        at android.app.Activity.performCreate(Activity.java:5933)
//        at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1105)
//        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2251)
//        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2360) 
//        at android.app.ActivityThread.access$800(ActivityThread.java:144) 
//        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1278) 
//        at android.os.Handler.dispatchMessage(Handler.java:102) 
//        at android.os.Looper.loop(Looper.java:135) 
//        at android.app.ActivityThread.main(ActivityThread.java:5221) 
//        at java.lang.reflect.Method.invoke(Native Method) 
//        at java.lang.reflect.Method.invoke(Method.java:372) 
//        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:899) 
//        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:694) 