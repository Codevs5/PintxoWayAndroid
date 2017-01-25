package com.example.codevs.pintxoway;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by Borja on 25/01/2017.
 */

public class LocalListAdapter extends RecyclerView.Adapter<LocalListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<LocalListCard> cardsList;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mainData,data2,data3;


        public MyViewHolder(View itemView) {
            super(itemView);
            mainData = (TextView) itemView.findViewById(R.id.TVlocalListCardMainData);
            data2 = (TextView) itemView.findViewById(R.id.TVlocalListCardData2);
            data3 = (TextView) itemView.findViewById(R.id.TVlocalListCardData3);
        }
    }

    public LocalListAdapter(Context context, ArrayList<LocalListCard> cardsList){
        //los parametros no pueden ser nulos
        this.context = context;
        this.cardsList = cardsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.local_list_item_card,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LocalListCard card = cardsList.get(position);
        holder.mainData.setText(card.getMainData());
        holder.data2.setText(card.getData2());
        holder.data3.setText(card.getData3());
    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }
}
