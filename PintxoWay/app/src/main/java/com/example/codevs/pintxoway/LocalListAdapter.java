package com.example.codevs.pintxoway;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.M;
import static android.support.v7.widget.RecyclerView.*;


/**
 * Created by Borja on 25/01/2017.
 */

public class LocalListAdapter extends RecyclerView.Adapter<LocalListAdapter.MyViewHolder> implements OnClickListener {

    private Context context;
    private ArrayList<LocalListCard> cardsList;
    private View.OnClickListener listener;

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public void onClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name,distance,vicinity,type;
        public ImageView cardImage;


        public MyViewHolder(View itemView) {
            super(itemView);
            cardImage   = (ImageView) itemView.findViewById(R.id.localListItemCardImage);
            name        = (TextView) itemView.findViewById(R.id.TVLocalListCardName);
            distance    = (TextView) itemView.findViewById(R.id.TVLocalListCardDistance);
            vicinity    = (TextView) itemView.findViewById(R.id.TVLocalListCardVicinity);
            type        = (TextView) itemView.findViewById(R.id.TVLocalListCardType);
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
        itemView.setOnClickListener(this);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LocalListCard card = cardsList.get(position);
        holder.name.setText(card.getName());
        holder.distance.setText(card.getDistance());
        holder.vicinity.setText(card.getVicinity());
        holder.type.setText(card.getType());
        holder.cardImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"clicada",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }

}
