package com.example.loginmvp;

public interface LoginModel {

     interface OnLoginFinishedListener{
        void onCancelled();

        void onSuccess();

        void onPasswordError();
    }

    void login(String username, String password, OnLoginFinishedListener listener );
}
