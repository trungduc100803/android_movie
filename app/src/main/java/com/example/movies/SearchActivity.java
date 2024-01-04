package com.example.movies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

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
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView rcy_Movie_search;
    CardAdapter cardAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        searchView = findViewById(R.id.searchip);
        rcy_Movie_search = findViewById(R.id.rcy_Movie_search);
        List<CardMovie> listSearchMovie = new ArrayList<>();
        List<CardMovie> listSearchMovieDisplay = new ArrayList<>();
        getMovieSearch(listSearchMovie);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!s.equals("")){
                    for(CardMovie cardMovie :listSearchMovie){
                        if(cardMovie.getTitle().toLowerCase().contains(s)){
                            listSearchMovieDisplay.add(cardMovie);
                        }
                    }
                    displayListMovie(listSearchMovieDisplay);
                }else {
                    listSearchMovieDisplay.clear();
                    displayListMovie(listSearchMovieDisplay);
                }

                return false;
            }
        });
    }




    private void displayListMovie(List<CardMovie> list){
        List<CardMovie> ls = new ArrayList<>();
        cardAdapter = new CardAdapter(this, ls , new IClickEveentCardMovie() {
            @Override
            public void onClickCard(CardMovie cardMovie) {
                clickNextDetailMovie(cardMovie);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcy_Movie_search.setLayoutManager(linearLayoutManager);

        cardAdapter.setData(list);
        rcy_Movie_search.setAdapter(cardAdapter);
    }

    private List<CardMovie> getMovie(List<CardMovie> list){
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

    private List<CardMovie> getMovieSearch(List<CardMovie> list){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("movies");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CardMovie cardMovie  = dataSnapshot.getValue(CardMovie.class);
                    list.add(cardMovie);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return  list;
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
