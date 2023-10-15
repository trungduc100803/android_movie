package com.example.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.my_interface.IClickEveentCardMovie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardMovieHolder>{


    private Context context;
    private List<CardMovie> listMovie;
    private IClickEveentCardMovie iClickEveentCardMovie;

    public CardAdapter(Context context, List<CardMovie> listMovie, IClickEveentCardMovie iClickEveentCardMovie ) {
        this.context = context;
        this.listMovie = listMovie;
        this.iClickEveentCardMovie = iClickEveentCardMovie;
    }

    public CardAdapter() {
    }

    public CardAdapter(Context context) {
        this.context = context;
    }

    public  void setData (List<CardMovie> list){
        this.listMovie = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CardMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie, parent, false);
        return new CardMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardMovieHolder holder, int position) {
        CardMovie cardMovie = listMovie.get(position);
        if(cardMovie == null){
            return ;
        }
        String urlImage = cardMovie.getResourceImage();
        Picasso.get().load(urlImage).into(holder.img);
//        holder.img.setImageResource(cardMovie.getResourceImage());
        holder.titl.setText(cardMovie.getTitle());

        holder.card_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickEveentCardMovie.onClickCard(cardMovie);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listMovie != null){
            return listMovie.size();
        }
        return 0;
    }

    public class CardMovieHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView titl;
        private LinearLayout card_item;
        public CardMovieHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_card);
            titl = itemView.findViewById(R.id.title_card);
            card_item = itemView.findViewById(R.id.card_item);
        }
    }
}
