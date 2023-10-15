package com.example.movies;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.my_interface.IClickEveentCardMovie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    CardAdapter cardAdapter;
    RecyclerView ryc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_layout);

        ryc = findViewById(R.id.rcy_Movie);
        displayListMovie();
    }

    private List<CardMovie> getMovie(){
        List<CardMovie> list = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("movies");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CardMovie cardMovie  = dataSnapshot.getValue(CardMovie.class);
                    list.add(cardMovie);
                }
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return  list;
    }
    private void displayListMovie(){
        List<CardMovie> ls = new ArrayList<>();
        cardAdapter = new CardAdapter(this, ls , new IClickEveentCardMovie() {
            @Override
            public void onClickCard(CardMovie cardMovie) {
                clickNextDetailMovie(cardMovie);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        ryc.setLayoutManager(linearLayoutManager);

        cardAdapter.setData(getMovie());
        ryc.setAdapter(cardAdapter);
    }
    private void clickNextDetailMovie(CardMovie cardMovie){
        Intent i = new Intent(this, DetailMovieActivity.class);
        Bundle bundle = new Bundle();
        if(bundle == null){
            return;
        }
        //get user tu login
        User user = (User) bundle.get("obj_user");
        int index = bundle.getInt("index");
        bundle.putSerializable("obj_movies", cardMovie );
        bundle.putInt("index", index);
        i.putExtras(bundle);
        startActivity(i);
    }
}
