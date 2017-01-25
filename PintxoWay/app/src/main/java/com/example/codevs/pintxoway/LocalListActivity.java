package com.example.codevs.pintxoway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class LocalListActivity extends AppCompatActivity {

    private RecyclerView reciclerView;
    private LocalListAdapter adapter;
    private ArrayList<LocalListCard> cardList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_list);

        if(adapter == null){
            adapter = new LocalListAdapter(this,cardList);
        }
        reciclerView = (RecyclerView) findViewById(R.id.recyclerViewLocales);
        reciclerView.setAdapter(adapter);
        reciclerView.setLayoutManager(new LinearLayoutManager(this));
        //reciclerView.setAnimation(new DefaultItemAnimator());

        initCards();
    }

    private void initCards(){
        for (int i=0; i < 10; i++){
            LocalListCard card = new LocalListCard("Bar Manolo", "Avenida Tolosa, Donostia", "tel: 5214785262");
            cardList.add(card);
        }
    }
}
