package com.pacosignes.guicartitas;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pacosignes.guicartitas.fragments.Login;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login login=new Login();
        getSupportFragmentManager().beginTransaction().replace(R.id.flMainFrame,login).commit();




    }
}
