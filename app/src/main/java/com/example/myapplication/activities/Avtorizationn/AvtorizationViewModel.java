package com.example.myapplication.activities.Avtorizationn;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.myapplication.repository.api.MyApiService;
import com.example.myapplication.repository.SheredPrefsRepository;
import com.example.myapplication.repository.api.models.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class AvtorizationViewModel  extends ViewModel {
    private MutableLiveData<Boolean> MutableAlreadyInhotel;
    private MutableLiveData<Boolean> loginEror;

    Boolean isALreadyHotel;

    @Inject
    @Named("withToken")
    MyApiService apiServiceWithToken;
    @Inject
    @Named("withoutToken")
    MyApiService apiServiceWithoutToken;

    @Inject
    SheredPrefsRepository mSheredPrefsRepository;
    @Inject
     AvtorizationViewModel(){
    }

    public MutableLiveData<Boolean> getMutableAlreadyInhotel(){
        if(MutableAlreadyInhotel ==null){
            MutableAlreadyInhotel = new MutableLiveData<Boolean>();
        }
        return MutableAlreadyInhotel;
    }

    public MutableLiveData<Boolean> getMutableLoginEror(){
        if(loginEror ==null){
            loginEror = new MutableLiveData<Boolean>();
        }
        return loginEror;
    }


    public void Login(String email, String password){
        apiServiceWithoutToken.loginUser(new LoginRequest(email,password)).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String responseString = response.body().string();
                        if (responseString.contains("true")){
                            Log.e("responsstring", getTokenWithJson(responseString));
                           mSheredPrefsRepository.pootToken(getTokenWithJson(responseString));
                            isAlreadyInHotel();
                        }else getMutableLoginEror().setValue(false);
                    }catch (Exception e){
                        Log.e("eror", e.fillInStackTrace().getMessage());
                        Log.e("eror", e.fillInStackTrace().getMessage());
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("onFailure","eror");
                }
            });

    }
    public void isAlreadyInHotel(){
        apiServiceWithToken.isligin().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String responseString = response.body().string();
                    Log.e("Респонс успешен",responseString);
                    JSONObject jObject = new JSONObject(responseString);
                    JSONObject JopjectINprofile = new JSONObject(jObject.getString("profile"));
                    isALreadyHotel = JopjectINprofile.getBoolean("checked_in");
                    if (isALreadyHotel){
                        getMutableAlreadyInhotel().setValue(true);
                        mSheredPrefsRepository.ALreadyINhotel(true);

                    }else {
                       getMutableAlreadyInhotel().setValue(false);
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("loginlog","eror"+t.getMessage());
            }
        });

    }
    String getTokenWithJson(String json) throws JSONException {
        JSONObject jsonObject =new JSONObject(json);
        return jsonObject.getString("token");
    }

}
