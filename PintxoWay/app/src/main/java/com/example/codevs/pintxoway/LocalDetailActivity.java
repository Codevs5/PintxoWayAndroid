package com.example.codevs.pintxoway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import static android.R.attr.name;
import static android.R.attr.path;
import static android.R.attr.type;
import static android.R.attr.webTextViewStyle;

public class LocalDetailActivity extends AppCompatActivity {

    private JSONObject detailListJson;
    private String place_id;
    private TextView textView,nametextView,distanceTextView,vicinityTexView,typeTexView,addressTextView,telTextView,webTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_detail);

        place_id = getIntent().getExtras().getString("place_id");
        textView = (TextView) findViewById(R.id.TVPruebaJsonDetail);
        distanceTextView = (TextView) findViewById(R.id.TVLocalLocalDetailDistance);
        nametextView = (TextView) findViewById(R.id.TVLocalDetailName);
        typeTexView = (TextView) findViewById(R.id.TVLocalDetailType);
        addressTextView = (TextView) findViewById(R.id.TVAddressLocalDetail);
        telTextView = (TextView) findViewById(R.id.TVPhoneNumberLocalDetail);
        webTextView = (TextView) findViewById(R.id.TVWebSiteLocalDetail);

        getLocalDetailJson();
    }



    private void getLocalDetailJson(){

        String path = PintxoService.getInstance(LocalDetailActivity.this).getLocalDetailPath(place_id);
        Log.i("path",path);

        //Tiene que llamar al servicio, obtener los datos, procesarlos y crear las tarjetas
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    detailListJson = response;
                    Log.i("jsonLocalDetail",response.toString());
                    textView.setText(detailListJson.toString());
                    setInfoLocalDetail(detailListJson);
                    findViewById(R.id.llLocaDetailProgress).setVisibility(View.GONE);

                }catch (Exception e){
                    //Todo Que se hace con la excepcion
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LocalDetailActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                //TODO hacer una asink tas para que cada cierto tiempo vuelva a recargar la vista

            }
        });

        PintxoService.getInstance(LocalDetailActivity.this).addToRequestQue(jsonObjectRequest);

    }

    private void setInfoLocalDetail(JSONObject infoJson){
        try {
            //infoJson = infoJson.getJSONObject("local");
            Log.i("setInfo",infoJson.toString());
            infoJson = infoJson.getJSONObject("local");
            nametextView.setText(infoJson.getString("name"));
            addressTextView.setText(infoJson.getString("address"));
            distanceTextView.setText(infoJson.getString("distance"));
            typeTexView.setText(infoJson.getJSONArray("types").getString(0));
            telTextView.setText(infoJson.getString("phoneNum"));
            webTextView.setText(Html.fromHtml(infoJson.getJSONObject("web").getString("mapURL")));
        }catch (Exception e){
            Log.i("detail",e.toString());
        }
        //para el scroll de productos
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LProductList);
        for (int i = 0; i < 5; i++) {

            ImageView imageView = new ImageView(LocalDetailActivity.this);
            imageView.setImageResource(R.mipmap.imagenpruebabar);
            linearLayout.addView(imageView);
        }
    }
}
