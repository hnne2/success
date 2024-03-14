package com.example.myapplication.activities.registr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activities.Avtorizationn.avtorization;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class regestration extends AppCompatActivity {

    EditText regName;
    EditText regFimail;
    EditText regEmail;
    EditText regPassword;
    Button ToRegist;
    RegistrViewModel RegistrationViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);
        regName =findViewById(R.id.emailEditTextImya);
        regFimail =findViewById(R.id.emailEditTextFamili);
        regEmail =findViewById(R.id.emailEditTextPochta);
        regPassword =findViewById(R.id.emailEditTextParol);
        ToRegist = findViewById(R.id.to_registr_but);
        RegistrationViewModel = new ViewModelProvider(this).get(RegistrViewModel.class);

        ToRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationViewModel.registr(regName.getText().toString(), regFimail.getText().toString(), regEmail.getText().toString(), regPassword.getText().toString());
            }
        });
        RegistrationViewModel.getMutableRegistrCuccses().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    startActivity(new Intent(regestration.this, avtorization.class));
                }else Toast.makeText(regestration.this,"ошибка",Toast.LENGTH_LONG).show();
            }
        });

    }
}