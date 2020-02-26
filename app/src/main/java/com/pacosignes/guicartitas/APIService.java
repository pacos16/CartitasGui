package com.pacosignes.guicartitas;

import com.pacosignes.guicartitas.model.Carta;
import com.pacosignes.guicartitas.model.Jugador;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    //Cartas
    @GET("/ApiRESTFul/rest/cartas")
    Call<ResponseBody> getCartas();

    //Jugadores

    @GET("/ApiRESTFul/rest/jugadores/{correo}")
    Call<ResponseBody> getJugador(@Path("correo") String correo);

    @POST("/ApiRESTFul/rest/jugadores")
    Call<ResponseBody> saveJugador(@Body RequestBody params);

    @PUT("/ApiRESTFul/rest/jugadores/{correo}")
    Call<ResponseBody> modificarJugador(@Path("correo") String correo,@Body RequestBody params);


    //Metodos Juego

    @GET("/ApiRESTFul/rest/metodosJuego/iniciarPartida/{correo}")
    Call<ResponseBody> getInicioPartida(@Path("correo") String correo);

    @POST("/ApiRESTFul/rest/metodosJuego/jugarTurno")
    Call<ResponseBody> enviarTurno(@Body RequestBody params);

    @GET("/ApiRESTFul/rest/estadisticas/estadisticasCartas")
    Call<ResponseBody> getEstadisticasCartas();

}
