package com.example.codevs.pintxoway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FunctionActivity extends AppCompatActivity {

    Button btnListaDistancia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        btnListaDistancia = (Button) findViewById(R.id.btnBuscarListaLocalesDistancia);

        btnListaDistancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FunctionActivity.this, LocalListActivity.class);
                startActivity(intent);
            }
        });
    }
}
