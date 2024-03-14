package com.example.myapplication.activities.Zaseleniyee;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.repository.api.MyApiService;
import com.example.myapplication.repository.SheredPrefsRepository;
import com.example.myapplication.repository.jsonReader;
import com.example.myapplication.repository.api.models.GetNumberRequest;
import com.example.myapplication.adapters.HotelName;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@HiltViewModel

public class ZaseleniyeViewModel extends ViewModel {
    @Inject
    ZaseleniyeViewModel(){
    }
    com.example.myapplication.repository.jsonReader jsonReader = new jsonReader();
    @Inject
    @Named("withToken")
    MyApiService apiServiceWithToken;
    @Inject
    @Named("withoutToken")
    MyApiService apiServiceWithoutToken;

    @Inject
    SheredPrefsRepository mSheredPrefsRepository;

    private MutableLiveData<List<HotelName>> ListHotelName;
    private MutableLiveData<List<HotelName>> ListNumberNomer;
    private MutableLiveData<Boolean> GetNumberSuccses;
    public MutableLiveData<List<HotelName>> getMutableListHOtel(){
        if(ListHotelName ==null){
            ListHotelName = new MutableLiveData<List<HotelName>>();
        }
        return ListHotelName;
    }
    public MutableLiveData<List<HotelName>> getMutableListNaumberNomer(){
        if(ListNumberNomer ==null){
            ListNumberNomer = new MutableLiveData<List<HotelName>>();
        }
        return ListNumberNomer;
    }
    public MutableLiveData<Boolean> getMutableGetNomberSuccses(){
        if(GetNumberSuccses ==null){
            GetNumberSuccses= new MutableLiveData<Boolean>();
        }
        return GetNumberSuccses;
    }

    public void GetListHotel(){
        apiServiceWithToken.getListHotel().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String resoonseString = response.body().string();
                    getMutableListHOtel().setValue(jsonReader.getListHotel(resoonseString));

                } catch (IOException e) {
                    Log.e("eror",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    public void getNamberByhotel(int IdHotel){
        apiServiceWithToken.getNamberByHotel(String.valueOf(IdHotel)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String ResponseString = response.body().string();
                    getMutableListNaumberNomer().setValue(jsonReader.getNumberList(ResponseString));
                } catch (IOException e) {
                    Log.e("eror",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void getNomber(int id,String date){
        apiServiceWithToken.getNumber(new GetNumberRequest(id,date)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = response.body().string();
                    if (responseString.contains("true")){
                        mSheredPrefsRepository.ALreadyINhotel(true);
                        getMutableGetNomberSuccses().setValue(true);
                    }else getMutableGetNomberSuccses().setValue(false);

                } catch (IOException e) {
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
