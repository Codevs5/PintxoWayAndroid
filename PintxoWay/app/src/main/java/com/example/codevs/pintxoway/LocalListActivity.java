package com.example.codevs.pintxoway;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.animation;
import static android.R.attr.layout;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class LocalListActivity extends AppCompatActivity {

    private RecyclerView reciclerView;
    private LocalListAdapter adapter;
    private ArrayList<LocalListCard> cardList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** ha esta activity hay que llamarla con el parametros 'function':String
         * indicando el tipo de lista que quiere que muestre:
         * --- lista de locales por distancia: byDistance
         * --- lista de locales por nombre: byName
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_list);

        ;


        String func = getIntent().getExtras().getString("function");

        switch (func){
            case "byDistance":
                getLocalListByDistance(getIntent().getExtras().getString("lat"),getIntent().getExtras().getString("lon"),getIntent().getExtras().getString("distance"));
                break;

            default:
                //todo que hacer si no hay parametro o no es una de sus funcionalidades
                Toast.makeText(this.getBaseContext(),"sin llamada a funcionalidad",Toast.LENGTH_SHORT).show();
                break;
        }

        if(adapter == null){
            adapter = new LocalListAdapter(this,cardList);
        }
        reciclerView = (RecyclerView) findViewById(R.id.recyclerViewLocales);
        reciclerView.setAdapter(adapter);
        reciclerView.setLayoutManager(new LinearLayoutManager(this));


        /*adapter.onClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocalListActivity.this,LocalDetailActivity.class);
                startActivity(i);
            }
        });*/
    }

    private void initCards(JSONObject json){
        //TODO Consumir el servicio para generar todas tarjetas de los bares


        for (int i=0; i < 10; i++){
            json = new JSONObject();
            try {
                json.accumulate("placeId","1");
                json.accumulate("name","Google Sydney");
                json.accumulate("distance","400");
                json.accumulate("vicinity","sidney, mucho, viaje");
                json.accumulate("open now",true);
                json.accumulate("type","bar");
                json.accumulate("placeLvl","1");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            LocalListCard card = new LocalListCard(json);
            cardList.add(card);
        }
    }

    private void getLocalListByDistance(String lat , String lon, String rad){

        String path = PintxoService.getInstance(LocalListActivity.this).getDistancePath(lat,lon,rad);

        //Tiene que llamar al servicio, obtener los datos, procesarlos y crear las tarjetas
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //initCards(response);
                }catch (Exception e){
                    //Todo Que se hace con la excepcion
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LocalListActivity.this,error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        PintxoService.getInstance(LocalListActivity.this).addToRequestQue(jsonObjectRequest);

        //TOdo borrar la llamada cuendo el servicio este operativo
        initCards(new JSONObject());

    }
}
