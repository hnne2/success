package com.example.myapplication.activities;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.myapplication.activities.Avtorizationn.avtorization;
import com.example.myapplication.activities.InHotell.InHotel;
import com.example.myapplication.activities.Zaseleniyee.Zaseleniye;
import com.example.myapplication.repository.MyPreferences;
import com.example.myapplication.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("FIRST_START", true)) {
            Intent i = new Intent(MainActivity.this, FirstStartL.class);
            startActivity(i);
            finish();
            Log.e("запустил приветствие","afa");
        }else {
            Log.e("не запустил приветствие","afa");

            if (MyPreferences.getEncryptedSharedPreferences(getBaseContext()).contains("token")){
                Log.e("токен есть","data");
                if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("ALREADYINHOTEL",false)){
                    Log.e("уже в отеле","00");
                    startActivity(new Intent(MainActivity.this, InHotel.class));
                    finish();

                }
                else {startActivity(new Intent(MainActivity.this, Zaseleniye.class));
                    finish();
                    Log.e("еще не в отеле","00");}
            }
            else {
                Intent i = new Intent(MainActivity.this, avtorization.class);
                startActivity(i);
                finish();
                Log.e("токена нет","в авторизацию");
            }}


    }
}