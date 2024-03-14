package com.example.myapplication.activities.InHotell;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.repository.api.MyApiService;
import com.example.myapplication.repository.SheredPrefsRepository;
import com.example.myapplication.repository.jsonReader;
import com.example.myapplication.repository.api.models.CanselRequestt;
import com.example.myapplication.repository.api.models.FioProfil;
import com.example.myapplication.repository.api.models.Order;
import com.example.myapplication.adapters.Servise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel

public class InHotelViewModel extends ViewModel {
    @Inject
    InHotelViewModel(){
    }
    com.example.myapplication.repository.jsonReader jsonReader = new jsonReader();


    @Inject
    @Named("withToken")
    MyApiService apiServiceWithToken;
    @Inject
    @Named("withoutToken")
    MyApiService apiServiceWithoutToken;

    private MutableLiveData<Boolean> ExitSuccses;


    @Inject
    SheredPrefsRepository mSheredPrefsRepository;
    private MutableLiveData<ArrayList<Servise>> GetServiceList;
    private MutableLiveData<FioProfil> getFioProfil;
    private MutableLiveData<List<Order>> getOrderList;
    public MutableLiveData<Boolean> getMutableGetExitSuccses(){
        if(ExitSuccses ==null){
            ExitSuccses= new MutableLiveData<Boolean>();
        }
        return ExitSuccses;
    }



    public MutableLiveData<ArrayList<Servise>> getMutableListServise(){
        if(GetServiceList ==null){
            GetServiceList = new MutableLiveData<ArrayList<Servise>>();
        }
        return GetServiceList;
    }
    public MutableLiveData<FioProfil> getMutableGetFioProfil(){
        if(getFioProfil==null){
            getFioProfil= new MutableLiveData<FioProfil>();
        }
        return getFioProfil;
    }
    public MutableLiveData<List<Order>> getMutableGetListOrder(){
        if(getOrderList==null){
            getOrderList= new MutableLiveData<List<Order>>();
        }
        return getOrderList;
    }


    public void getListServices(){
        apiServiceWithToken.getServices().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String StringResponce =response.body().string();
                    Log.e("getListServices", StringResponce);
                    getMutableListServise().setValue(jsonReader.getServise(StringResponce));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    public void ProfilExequte(){
        apiServiceWithToken.getProfilExequte().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String ResponseString = response.body().string();
                    Log.e("ProfilExequte", ResponseString);
                    getMutableGetFioProfil().setValue(jsonReader.getProfil(ResponseString));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void GetOrders(){
        apiServiceWithToken.getOrders().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String RespinsString = response.body().string();
                    Log.e("oreders", RespinsString);
                    getMutableGetListOrder().setValue(jsonReader.gerOReders(RespinsString));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void exitHotel(){
        apiServiceWithToken.getExit().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String ResponseString = response.body().string();
                    if (ResponseString.contains("true")){
                        mSheredPrefsRepository.ALreadyINhotel(false);
                        getMutableGetExitSuccses().setValue(true);}
                    else getMutableGetExitSuccses().setValue(false);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void CanselOrder(int id,String coment){
        apiServiceWithToken.canselORder(String.valueOf(id),new CanselRequestt(coment)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e("cancel",response.body().string());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
