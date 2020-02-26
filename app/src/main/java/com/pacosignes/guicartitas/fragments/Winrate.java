package com.pacosignes.guicartitas.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pacosignes.guicartitas.APIService;
import com.pacosignes.guicartitas.APIUtils;
import com.pacosignes.guicartitas.R;
import com.pacosignes.guicartitas.model.CartaEstadistica;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Esta clase utiliza el endpoint
 * get estadisticas/estadisticasCartas para obtener los resultados
 * de todas las cartas en todos los turnos de la partida
 * una vez llega aqui, se ordena el array por winrate y por victorias, y se construye un recycler view para
 * visualizarlos en pantalla
 */
public class Winrate extends Fragment {

    private RecyclerView rvWinrate;
    private APIService mAPIService;
    private ArrayList<CartaEstadistica> cartaEstadisticas;
    private AdapterWinrate adapterWinrate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.winrate_layout,container,false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAPIService= APIUtils.getAPIService();
        cartaEstadisticas=new ArrayList<>();
        rvWinrate=getActivity().findViewById(R.id.rvWinrate);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        mAPIService.getEstadisticasCartas().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONTokener tokener=new JSONTokener(response.body().string());
                    JSONArray array=new JSONArray(tokener);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        cartaEstadisticas.add(new CartaEstadistica(
                                object.getInt("idCarta"),
                                object.getInt("ganadas"),
                                object.getInt("perdidas"),
                                object.getInt("empatadas")
                        ));
                    }
                    Collections.sort(cartaEstadisticas,Collections.<CartaEstadistica>reverseOrder());
                    adapterWinrate=new AdapterWinrate(cartaEstadisticas);
                    LinearLayoutManager layoutManager=new LinearLayoutManager
                            (getContext(),LinearLayoutManager.VERTICAL,false);
                    DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(rvWinrate.getContext(),
                            layoutManager.getOrientation());
                    rvWinrate.setAdapter(adapterWinrate);
                    rvWinrate.addItemDecoration(dividerItemDecoration);
                    rvWinrate.setLayoutManager(layoutManager);

                } catch (IOException e) {

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
