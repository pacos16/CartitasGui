package com.pacosignes.guicartitas.fragments;


import android.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.pacosignes.guicartitas.APIService;
import com.pacosignes.guicartitas.APIUtils;
import com.pacosignes.guicartitas.ICartaListener;
import com.pacosignes.guicartitas.R;
import com.pacosignes.guicartitas.model.Caracteristicas;
import com.pacosignes.guicartitas.model.Carta;
import com.pacosignes.guicartitas.model.Turno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;


import java.util.ArrayList;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Juego extends Fragment implements ICartaListener {
    //variables de interfaz

    private ImageView ivCartaCpu;
    private TextView tvCaracteristicaCpu;
    private TextView tvValorCpu;
    private TextView tvTurno;
    private TextView tvMarcador;
    private TextView tvResultText;
    private ImageView ivCartaPlayer;
    private RadioGroup radioGroup;
    private TextView tvTitulo;
    /*
    private RadioButton rbMotor,rbCilindros,rbPotencia,rbRevoluciones;
    private RadioButton rbVelocidad,rbConsumo;
     */
    private RecyclerView rvCartas;
    private AdapterCartas adapterCartas;
    private Activity context;
    private Button btJugarCarta;
    private Handler handler;
    //variables de inicio
    private ArrayList<Carta> manoJugador;
    private Turno turnoInicial;
    private ArrayList<Carta> cartas;
    private APIService mAPIService;
    private ICartaListener listener;
    //variables de informacion
    private int puntuacionJugador;
    private int puntuacionCpu;
    //variables de control
    private int numTurno;
    private Carta cartaPlayer;
    private Caracteristicas caracteristicaPlayer;
    private boolean ataque;
    private Boolean automatico;
    private int idPartida;
    private Turno paqueteTurno;
    public Juego(Boolean automatico, ArrayList<Carta> manoJugador, Turno turnoInicial) {
        this.automatico = automatico;
        this.manoJugador = manoJugador;
        this.turnoInicial = turnoInicial;
        this.mAPIService= APIUtils.getAPIService();
        puntuacionJugador=0;
        puntuacionCpu=0;
        numTurno=1;
        listener=this;
        context=getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inicializar();
        ivCartaCpu=getActivity().findViewById(R.id.ivCartaCpu);
        tvCaracteristicaCpu=getActivity().findViewById(R.id.tvCaracteristicaCpu);
        tvValorCpu=getActivity().findViewById(R.id.tvValorCpu);
        radioGroup=getActivity().findViewById(R.id.radioGroup);
        tvTurno=getActivity().findViewById(R.id.tvTurno);
        tvMarcador=getActivity().findViewById(R.id.tvMarcador);
        tvResultText=getActivity().findViewById(R.id.tvResultText);
        tvMarcador.setText("0-0");
        rvCartas=getActivity().findViewById(R.id.rvCartas);
        ivCartaPlayer=getActivity().findViewById(R.id.ivMiCarta);
        btJugarCarta=getActivity().findViewById(R.id.btEnviarTurno);
        handler=new Handler();
        tvTitulo=getActivity().findViewById(R.id.tvTitulo);
        btJugarCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartaPlayer!=null){
                    generaTurno();
                }
            }
        });
        /*
        rbMotor=getActivity().findViewById(R.id.rbMotor);
        rbCilindros=getActivity().findViewById(R.id.rbCilindros);
        rbPotencia=getActivity().findViewById(R.id.rbPotencia);
        rbRevoluciones=getActivity().findViewById(R.id.rbRevoluciones);
        rbVelocidad=getActivity().findViewById(R.id.rbVelocidad);
        rbConsumo=getActivity().findViewById(R.id.rbConsumo);
         */


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.juego_layout,container,false);
    }




    /**
     * Esta funcion llama a la api para obtener las cartas
     * y da comienzo a la partida
     *
     */
    private void inicializar(){

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


                    adapterCartas=new AdapterCartas(manoJugador,listener,context);
                    LinearLayoutManager layoutManager=new LinearLayoutManager
                            (getContext(),LinearLayoutManager.VERTICAL,false);
                    DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(rvCartas.getContext(),
                            layoutManager.getOrientation());
                    rvCartas.setAdapter(adapterCartas);
                    rvCartas.addItemDecoration(dividerItemDecoration);
                    rvCartas.setLayoutManager(layoutManager);
                    //Aqui empieza la partida
                    idPartida=turnoInicial.getPartida();
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
        int id=getActivity().getResources().getIdentifier(
                "_"+t.getCartaCpu(),
                "drawable",getActivity().getPackageName());
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
            ataque=true;
            ivCartaPlayer.setImageResource(R.drawable.ic_launcher_background);
            ivCartaCpu.setImageResource(R.drawable.ic_launcher_background);
            radioGroup.setVisibility(View.VISIBLE);
            actualizaResultados(turno);

        }else{
            ataque=false;
            ivCartaPlayer.setImageResource(R.drawable.ic_launcher_background);
            radioGroup.setVisibility(View.GONE);
            paqueteTurno=turno;

            //el servidor manda la carta que ha usado para la cpu en el
            //turno anterior por medio de cartaJugador.
            if(turno.getCartaJugador()!=0) {
                Turno turnoTruqui = new Turno();
                turnoTruqui.setCaracteristica(caracteristicaPlayer.ordinal());
                turnoTruqui.setCartaCpu(turno.getCartaJugador());
                setCartaCPU(turnoTruqui);
                WaitAndSet waitAndSet=new WaitAndSet(turno);
                btJugarCarta.setEnabled(false);
                tvTitulo.setText("CPU ha jugado");

                Thread thread=new Thread(waitAndSet);
                thread.start();
            }else{

                setCartaCPU(turno);
            }


            actualizaResultados(turno);

        }

    }
    private class WaitAndSet implements Runnable{

        private Turno turno;
        public WaitAndSet(Turno turno){
            this.turno=turno;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    setCartaCPU(turno);
                    btJugarCarta.setEnabled(true);
                    tvResultText.setText("Te toca jugar");
                    tvTitulo.setText("Carta CPU");

                }
            });

        }
    }

    public void generaTurno(){
        if(ataque){
            paqueteTurno=new Turno();
            paqueteTurno.setCartaJugador(cartaPlayer.getId());
            paqueteTurno.setNumTurno(numTurno);
            paqueteTurno.setAtaque(true);
            paqueteTurno.setPartida(idPartida);
            switch (radioGroup.getCheckedRadioButtonId()){
                case R.id.rbMotor:
                    caracteristicaPlayer=Caracteristicas.MOTOR;
                    break;
                case R.id.rbCilindros:
                    caracteristicaPlayer=Caracteristicas.CILINDROS;
                    break;
                case R.id.rbPotencia:
                    caracteristicaPlayer=Caracteristicas.POTENCIA;
                    break;
                case R.id.rbRevoluciones:
                    caracteristicaPlayer=Caracteristicas.REVOLUCIONES;
                    break;
                case R.id.rbVelocidad:
                    caracteristicaPlayer=Caracteristicas.VELOCIDAD;
                    break;
                case R.id.rbConsumo:
                    caracteristicaPlayer=Caracteristicas.CONSUMO;
                    break;
            }
            paqueteTurno.setCaracteristica(caracteristicaPlayer.ordinal());
        }else {
            paqueteTurno.setCartaJugador(cartaPlayer.getId());
        }
        paqueteTurno.setNumTurno(numTurno);
        String json=new Gson().toJson(paqueteTurno);
        RequestBody body= RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),(json));
        mAPIService.enviarTurno(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    paqueteTurno=new Gson().fromJson(response.body().string(),Turno.class);
                    manoJugador.remove(cartaPlayer);
                    adapterCartas.setCartas(manoJugador);
                    cartaPlayer=null;
                    ivCartaCpu.setImageResource(R.drawable.ic_launcher_background);
                    if (numTurno<6) {
                        tvTurno.setText(String.valueOf(numTurno+1));
                        recibeTurno(paqueteTurno);
                    }else {
                        tvResultText.setText("Fin Partida");
                        btJugarCarta.setVisibility(View.GONE);
                        actualizaResultados(paqueteTurno);
                    }
                    numTurno++;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void actualizaResultados(Turno t){
        if(t.getResultado()==1){
            puntuacionJugador++;
            tvResultText.setText("Victoria");
        }else if(t.getResultado()==2){
            puntuacionCpu++;
            tvResultText.setText("Derrota");

        }
        tvMarcador.setText(puntuacionJugador+"-"+puntuacionCpu);

    }

    @Override
    public void onCartaSeleccionada(Carta c) {
        cartaPlayer=c;
        int id=getContext().getResources().getIdentifier(
                "_"+c.getId(),
                "drawable",getContext().getPackageName());
        ivCartaPlayer.setImageResource(id);
    }
}
