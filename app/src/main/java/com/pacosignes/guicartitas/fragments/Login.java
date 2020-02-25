package com.pacosignes.guicartitas.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.pacosignes.guicartitas.APIService;
import com.pacosignes.guicartitas.APIUtils;
import com.pacosignes.guicartitas.R;
import com.pacosignes.guicartitas.model.Jugador;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Fragment {

    private EditText etEmail;
    private EditText etContrasenya;
    private Button btLogin;
    private APIService mAPIService;
    private Jugador j;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.login_layout,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etEmail=getActivity().findViewById(R.id.etEmail);
        etContrasenya=getActivity().findViewById(R.id.etPassword);
        btLogin=getActivity().findViewById(R.id.btLogin);
        mAPIService= APIUtils.getAPIService();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEmail.getText()!=null && etContrasenya.getText()!=null){
                    comprobarContrasenya();
                }
            }
        });

    }

    private void comprobarContrasenya(){
        mAPIService.getJugador(etEmail.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==200){
                            try {
                                j=new Gson()
                                        .fromJson(response.body().string(),Jugador.class);
                                if(j.getContrasenya().equals(etContrasenya.getText().toString())){
                                    goToGame();
                                }else{
                                    Toast.makeText(getActivity(),"El usuario o la contraseña son incorrectos",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                Toast.makeText(getActivity(),"El usuario o la contraseña son incorrectos",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(),"El usuario o la contraseña son incorrectos",
                                Toast.LENGTH_SHORT).show();
                    }
                });


    }


    private void goToGame(){
        MenuInicio m=new MenuInicio(etEmail.getText().toString());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flMainFrame,m).commit();

    }


}
