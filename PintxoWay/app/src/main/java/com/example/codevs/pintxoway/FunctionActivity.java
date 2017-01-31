package com.example.codevs.pintxoway;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.security.PrivateKey;

import static android.R.attr.dial;
import static android.R.attr.id;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.M;
import static android.view.View.X;
import static android.view.View.Z;

public class FunctionActivity extends AppCompatActivity implements LocationListener{

    private Button btnListaDistancia;
    private TextView distance;
    private AlertDialog alertDialog;
    private String serverPath="https://httpbin.org/get";


    //para el GPS
    LocationManager locationManager;
    Location location;
    private double lon,lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        btnListaDistancia = (Button) findViewById(R.id.btnBuscarListaLocalesDistancia);
        distance = (TextView) findViewById(R.id.ETDistance);

        btnListaDistancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FunctionActivity.this, LocalListActivity.class);
                intent.putExtra("function","byDistance");
                intent.putExtra("distance", distance.getText());
                intent.putExtra("lat",lat);
                intent.putExtra("lon",lon);
                startActivity(intent);
            }
        });


        //para El GPs

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder  builder = new AlertDialog.Builder(this);
            builder.setMessage("La ubicación esta desactivada, ¿Desea activarla?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface,final int i) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, final int i) {
                            dialogInterface.cancel();
                        }
                    });
            alertDialog = builder.create();
            alertDialog.setIcon(R.drawable.ic_place_black_24dp);
            alertDialog.setTitle("Ubicación");
            alertDialog.show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }else{
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }else{
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        lat =  (location.getLatitude());
        lon =  (location.getLongitude());



        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3/*cada 3 minutos hace una peticion de localizacion*/,0,this);


        /*criterio para los proveedores*/
       // Criteria criteria = new Criteria();
        //criteria.setAccuracy(Criteria.ACCURACY_HIGH);
        //criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("localizacion","punto GPS actualizado");
        Toast.makeText(FunctionActivity.this,"GPS encontrado",Toast.LENGTH_SHORT).show();
        lat =  (location.getLatitude());
        lon =  (location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(getBaseContext(),"Provider enabled",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(getBaseContext(),"Provider Disabled",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }else{
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }else{
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3/*cada 3 minutos hace una peticion de localizacion*/,0,this);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }else{
                locationManager.removeUpdates(this);
            }
        }else{
            locationManager.removeUpdates(this);
        }
    }

    public void onDestroy(){
        super.onDestroy();
        if(alertDialog !=null){
            alertDialog.dismiss();
        }
    }
}
