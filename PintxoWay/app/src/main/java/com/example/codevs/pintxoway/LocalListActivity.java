package com.example.codevs.pintxoway;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.animation;
import static android.R.attr.key;
import static android.R.attr.layout;
import static android.R.id.list;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class LocalListActivity extends AppCompatActivity {

    private RecyclerView reciclerView;
    private LocalListAdapter adapter;
    private ArrayList<LocalListCard> cardList = new ArrayList<>();
    private JSONObject localJsonList;
    private String function,lat,lon,rad;
    private SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** ha esta activity hay que llamarla con el parametros 'function':String
         * indicando el tipo de lista que quiere que muestre:
         * --- lista de locales por distancia: byDistance
         * --- lista de locales por nombre: byName
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_list);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                selectFunction();
            }
        });


        function = getIntent().getExtras().getString("function");
        lat = getIntent().getExtras().getString("lat");
        lon = getIntent().getExtras().getString("lon");
        rad = getIntent().getExtras().getString("rad");

        Log.i("func",function);
        Log.i("rad",rad);
        Log.i("lon",lon);
        Log.i("lat",lat);


        selectFunction();

    }

    private void selectFunction(){
        switch (function){
            case "byDistance":
                //lat = "43.306449";
                //lon = "-2.010487";
                //rad = "500";
                getLocalListByDistance(lat,lon,rad);
                break;

            default:
                //todo que hacer si no hay parametro o no es una de sus funcionalidades
                Toast.makeText(this.getBaseContext(),"sin llamada a funcionalidad",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void initCards(JSONObject json){
        //TODO Consumir el servicio para generar todas tarjetas de los bares
        Log.i("initCard",json.toString());
        try {
            JSONArray localListJson = json.getJSONArray("locals");
            for(int i = 0; i < localListJson.length();i++){
                JSONObject local = localListJson.getJSONObject(i);
                LocalListCard card = new LocalListCard(local);
                cardList.add(card);
            }
            drawCardList();
        }catch (Exception e){
            Log.i("err:Json del servicio:",e.toString());
        }
    }

    private void getLocalListByDistance(String lat , String lon, String rad){

        String path = "http://192.168.0.23:3000/local/list?lat=43.306449&long=-2.010487&rad=500";//
        path = PintxoService.getInstance(LocalListActivity.this).getDistancePath(lat,lon,rad);
        Log.i("path",path);

        //Tiene que llamar al servicio, obtener los datos, procesarlos y crear las tarjetas
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    localJsonList = response;
                    Log.i("json" ,localJsonList.toString());
                    initCards(localJsonList);
                    getImage(localJsonList.getJSONArray("locals").getJSONObject(0).getJSONObject("photo").getString("photoid"),"400");
                    //if(response.toString().equals("\"error\": Parámetros incorrectos"))
                }catch (Exception e){
                    //Todo Que se hace con la excepcion
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LocalListActivity.this,"No se ha podido acceder a Internet",Toast.LENGTH_LONG).show();
                //TODO hacer una asink tas para que cada cierto tiempo vuelva a recargar la vista

            }
        });

        PintxoService.getInstance(LocalListActivity.this).addToRequestQue(jsonObjectRequest);

    }

    private void drawCardList(){
        findViewById(R.id.llLocalListProgress).setVisibility(View.GONE);
        refreshLayout.setRefreshing(false);
        Log.i("refreshLayout","quitando");
        if(adapter == null){
            adapter = new LocalListAdapter(this,cardList);
        }
        reciclerView = (RecyclerView) findViewById(R.id.recyclerViewLocales);
        reciclerView.setAdapter(adapter);
        reciclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getImage(String id, String maxWith){
        Log.i("getimg","entro");
        String path ="https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photoreference="+id+"&key=AIzaSyCJNCjLiDnNGcjdYlrp0mqbN84yriB8Pnk";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    localJsonList = response;
                    Log.i("json" ,localJsonList.toString());
                }catch (Exception e){
                    //Todo Que se hace con la excepcion
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LocalListActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                //TODO hacer una asink tas para que cada cierto tiempo vuelva a recargar la vista

            }
        });

        PintxoService.getInstance(LocalListActivity.this).addToRequestQue(jsonObjectRequest);
    }
}
