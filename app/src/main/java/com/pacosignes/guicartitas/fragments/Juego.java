package com.pacosignes.guicartitas.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.pacosignes.guicartitas.APIService;
import com.pacosignes.guicartitas.APIUtils;
import com.pacosignes.guicartitas.R;
import com.pacosignes.guicartitas.model.Caracteristicas;
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

public class Juego extends Fragment {
    //variables de interfaz

    private ImageView ivCartaCpu;
    private TextView tvCaracteristicaCpu;
    private TextView tvValorCpu;
    private TextView tvTurno;
    private TextView tvMarcador;
    private TextView tvResultText;
    private ImageView ivCartaPlayer;
    private RadioGroup radioGroup;
    private RecyclerView rvCartas;
    //variables de inicio
    private ArrayList<Carta> manoJugador;
    private Turno turnoInicial;
    private ArrayList<Carta> cartas;
    private APIService mAPIService;
    //variables de informacion
    private int puntuacionJugador;
    private int puntuacionCpu;
    //variables de control
    private int numTurno;
    private Carta cartaPlayer;
    private Caracteristicas caracteristicaPlayer;
    private boolean ataque;
    private Boolean automatico;
    public Juego(Boolean automatico, ArrayList<Carta> manoJugador, Turno turnoInicial) {
        this.automatico = automatico;
        this.manoJugador = manoJugador;
        this.turnoInicial = turnoInicial;
        this.mAPIService= APIUtils.getAPIService();
        puntuacionJugador=0;
        puntuacionCpu=0;
        numTurno=1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getCartas();
        ivCartaCpu=getActivity().findViewById(R.id.ivCartaCpu);
        tvCaracteristicaCpu=getActivity().findViewById(R.id.tvCaracteristicaCpu);
        tvValorCpu=getActivity().findViewById(R.id.tvValorCpu);
        radioGroup=getActivity().findViewById(R.id.radioGroup);
        tvTurno=getActivity().findViewById(R.id.tvTurno);
        tvMarcador=getActivity().findViewById(R.id.tvMarcador);
        tvResultText=getActivity().findViewById(R.id.tvResultText);
        tvMarcador.setText("0-0");
        rvCartas=getActivity().findViewById(R.id.rvCartas);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.juego_layout,container,false);
    }




    /**
     * Esta funcion llama a la api para obtener las cartas
     *
     */
    private void getCartas(){

        cartas=new ArrayList<>();
        mAPIService.getCartas().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONTokener tokener= null;
                try {
                    tokener = new JSONTokener(response.body().string());
                    JSONArray array = new JSONArray(tokener);
                    for (int i = 0; i < 32; i++) {
                        JSONObject object = array.getJSONObject(i);
                        Carta c = new Carta(object.getInt("id"),
                                object.getString("marca"),
                                object.getString("modelo"),
                                object.getInt("motor"),
                                object.getInt("cilindros"),
                                object.getString("potencia"),
                                object.getInt("revolucinoes"),
                                object.getInt("velocidad"),
                                object.getDouble("consumo")
                        );
                        cartas.add(c);
                    }


                    AdapterCartas adapterCartas=new AdapterCartas(cartas);
                    LinearLayoutManager layoutManager=new LinearLayoutManager
                            (getContext(),LinearLayoutManager.VERTICAL,false);
                    DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(rvCartas.getContext(),
                            layoutManager.getOrientation());
                    rvCartas.setAdapter(adapterCartas);
                    rvCartas.addItemDecoration(dividerItemDecoration);
                    rvCartas.setLayoutManager(layoutManager);
                    //Aqui empieza la partida
                    recibeTurno(turnoInicial);

                }catch (JSONException jsone){
                    Log.e("json exception","json exception juego get cartas");
                }catch (IOException e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    /**
     * Esta funcion asigna valor a la imagen y los text box
     * de la cpu
     * @param t
     */
    private void setCartaCPU(Turno t){
        Carta c= cartas.get(t.getCartaCpu()-1);
        int id=getContext().getResources().getIdentifier(
                "_"+t.getCartaCpu(),
                "drawable",getContext().getPackageName());
        ivCartaCpu.setImageResource(id);
        Caracteristicas caracteristica=Caracteristicas.values()[t.getCaracteristica()];
        String valor="";
        switch (caracteristica){

            case MOTOR:
                valor=String.valueOf(c.getMotor());
                break;
            case CILINDROS:
                valor=String.valueOf(c.getCilindros());
                break;
            case POTENCIA:
                valor=c.getPotencia();
                break;
            case REVOLUCIONES:
                valor=String.valueOf(c.getRevolucinoes());
                break;
            case VELOCIDAD:
                valor=String.valueOf(c.getVelocidad());
                break;
            case CONSUMO:
                valor=String.valueOf(c.getConsumo());
                break;
        }
        tvValorCpu.setText(valor);
        tvCaracteristicaCpu.setText(caracteristica.nombre);


    }

    /**
     * A esta funcion le pasamos el turno que recibimos del servidor y actua en consequencia.
     * @param turno turno que recibe desde el servidor, puede ser ataque o defensa.
     *
     */
    public void recibeTurno(Turno turno){

        if(turno.isAtaque()){
            radioGroup.setVisibility(View.VISIBLE);
            if(turno.getCartaJugador()!=0){
                numTurno++;
                actualizaResultados(turno);
            }
        }else{
            radioGroup.setVisibility(View.GONE);
            setCartaCPU(turno);
        }



    }

    private void actualizaResultados(Turno t){
        if(t.getResultado()==1){
            puntuacionJugador++;
        }else if(t.getResultado()==2){
            puntuacionCpu++;
        }
        tvMarcador.setText(puntuacionJugador+"-"+puntuacionCpu);
        tvTurno.setText(String.valueOf(numTurno));
    }
}
