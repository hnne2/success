package com.example.myapplication.adapters;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.activities.InHotell.InHotelViewModel;
import com.example.myapplication.R;


public class CustomDialogCancelOrder extends DialogFragment {
    EditText prichina;
    Button Otpravit;
    InHotelViewModel InHotelViewModel;
    int idOrder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.canseldialoglayout, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.zakrugl);
        prichina= view.findViewById(R.id.PricunatextView);
        Otpravit =view.findViewById(R.id.cancelButton);

        Otpravit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InHotelViewModel.CanselOrder(idOrder,prichina.getText().toString());
                getDialog().dismiss();
            }
        });
        return view;
    }

    public void setInHotelViewModel(InHotelViewModel inHotelViewModel) {
        this.InHotelViewModel = inHotelViewModel;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
}
