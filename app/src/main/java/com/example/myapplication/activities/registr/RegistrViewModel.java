package com.example.myapplication.activities.registr;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.repository.api.MyApiService;
import com.example.myapplication.repository.SheredPrefsRepository;
import com.example.myapplication.repository.api.models.RegistrRequest;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@HiltViewModel
public class RegistrViewModel extends ViewModel {
    @Inject
    RegistrViewModel(){
    }
    @Inject
    @Named("withToken")
    MyApiService apiServiceWithToken;
    @Inject
    @Named("withoutToken")
    MyApiService apiServiceWithoutToken;

    @Inject
    SheredPrefsRepository mSheredPrefsRepository;
    private MutableLiveData<Boolean> RegitstrSuccses;

    public MutableLiveData<Boolean> getMutableRegistrCuccses(){
        if(RegitstrSuccses ==null){
            RegitstrSuccses = new MutableLiveData<Boolean>();
        }
        return RegitstrSuccses;
    }

    public void registr(String FirstName,String LastName,String email,String password){
        apiServiceWithoutToken.registrUser(new RegistrRequest(FirstName,LastName,email,password)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responstString= response.body().string();
                    Log.e("Registr", responstString);
                    if (responstString.contains("true")){
                        getMutableRegistrCuccses().setValue(true);
                    }else getMutableRegistrCuccses().setValue(false);
                } catch (IOException e) {
                    Log.e("RegistrLog",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
