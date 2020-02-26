package com.pacosignes.guicartitas.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pacosignes.guicartitas.APIService;
import com.pacosignes.guicartitas.APIUtils;
import com.pacosignes.guicartitas.R;
import com.pacosignes.guicartitas.model.Carta;
import com.pacosignes.guicartitas.model.Turno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuInicio extends Fragment {
    private String idUsuario;
    private Button btJugar;
    private Button btJugarbot;
    private Button btEstadisticas;
    private APIService mAPIService;

    public MenuInicio(String idUsuario){
        this.idUsuario=idUsuario;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_layout,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btJugar=getActivity().findViewById(R.id.btPartidaNormal);
        btJugarbot=getActivity().findViewById(R.id.btPartidaBot);
        btEstadisticas=getActivity().findViewById(R.id.btEstadisticas);
        mAPIService= APIUtils.getAPIService();

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btPartidaNormal:
                        iniciarNormal();
                        break;
                    case R.id.btPartidaBot:
                        break;
                    case R.id.btEstadisticas:
                    Winrate winrate=new Winrate();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flMainFrame,winrate).addToBackStack(null).commit();
                        break;
                }
            }
        };

        btJugarbot.setOnClickListener(listener);
        btJugar.setOnClickListener(listener);
        btEstadisticas.setOnClickListener(listener);

    }


    private void iniciarNormal(){
        mAPIService.getInicioPartida(idUsuario).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<Carta> manoJugador=new ArrayList<>();

                    JSONTokener tokener=new JSONTokener(response.body().string());

                    JSONArray array=new JSONArray(tokener);
                    for(int i=0;i<6;i++){
                        JSONObject object= array.getJSONObject(i);
                        Carta c= new Carta(object.getInt("id"),
                                object.getString("marca"),
                                object.getString("modelo"),
                                object.getInt("motor"),
                                object.getInt("cilindros"),
                                object.getString("potencia"),
                                object.getInt("revolucinoes"),
                                object.getInt("velocidad"),
                                object.getDouble("consumo")
                                );
                        manoJugador.add(c);

                    }
                    JSONObject object=array.getJSONObject(6);
                    Turno turnoInicial=new Turno(
                            object.getInt("partida"),
                            object.getInt("cartaJugador"),
                            object.getInt("cartaCpu"),
                            object.getInt("caracteristica"),
                            object.getInt("numTurno"),
                            object.getBoolean("ataque"),
                            object.getInt("resultado")
                    );
                    Juego juego=new Juego(false,manoJugador,turnoInicial);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction().replace(R.id.flMainFrame,juego).commit();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
