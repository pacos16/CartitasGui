package com.pacosignes.guicartitas;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pacosignes.guicartitas.model.Caracteristicas;
import com.pacosignes.guicartitas.model.Carta;
import com.pacosignes.guicartitas.model.Jugador;
import com.pacosignes.guicartitas.model.Turno;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private APIService mAPIService;
    Carta c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tvHello=findViewById(R.id.tvHello);
        mAPIService = APIUtils.getAPIService();

        mAPIService.getCarta().enqueue(new Callback<List<Carta>>() {
            @Override
            public void onResponse(Call<List<Carta>> call, Response<List<Carta>> response) {
               c=response.body().get(0);

            }

            @Override
            public void onFailure(Call<List<Carta>> call, Throwable t) {
                tvHello.setText(t.getMessage());

            }
        });
        /*
        Jugador j=new Jugador("purutruoasdff@gmail.com","asldkfj","Megapollon");
        String jug= new Gson().toJson(j);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jug));
        mAPIService.saveJugador(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Jugador jugador=new Gson().fromJson(response.body().string(),Jugador.class);
                    tvHello.setText(jugador.toString());
                }catch (IOException ioe){
                    tvHello.setText("noooo");
                }catch (NullPointerException npe){
                    tvHello.setText("npe");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                tvHello.setText("whaatnoooo");
            }
        });

        Jugador j1=new Jugador("purutruoasdff@gmail.com","asldkfj","pichacorta");
        String jug=new Gson().toJson(j1);
        RequestBody body1 = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jug));
        mAPIService.modificarJugador("asdlfkj",body1).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Jugador jugador=new Gson().fromJson(response.body().string(),Jugador.class);
                    tvHello.setText(jugador.toString());
                }catch (IOException ioe){
                    tvHello.setText("noooo");
                }catch (NullPointerException npe){
                    tvHello.setText("npe");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                tvHello.setText("whaatnoooo");
            }
        });


        Jugador j1=new Jugador("purutruoasdff@gmail.com","asldkfj","pichacorta");
        String jug=new Gson().toJson(j1);
        RequestBody body1 = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jug));
        mAPIService.borrarJugador("putasdfasdf@gmail.com").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    tvHello.setText(response.code()+" " +response.body());
                }catch (NullPointerException npe){
                    tvHello.setText("npe");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                tvHello.setText("whaatnoooo");
            }
        });
        */

        Turno t=new Turno(3,1,2, Caracteristicas.CILINDROS.ordinal(),2,false,2);
        String json=new Gson().toJson(t);
        RequestBody body1 = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(json));
        mAPIService.saveTurno(body1).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                tvHello.setText(response.code()+"");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                tvHello.setText(t.getMessage());
            }
        });

    }
}
