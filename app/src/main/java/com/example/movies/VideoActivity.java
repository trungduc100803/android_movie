package com.example.movies;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

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

public class VideoActivity extends AppCompatActivity {

    WebView video;
    TextView descM;
    CardAdapter cardAdapter;
    RecyclerView ryc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        String urlID = (String) bundle.get("video");
        String des = bundle.getString("desc");
        video = findViewById(R.id.video);
        descM = findViewById(R.id.descM);
        ryc = findViewById(R.id.rcy_Movie);


        WebSettings webSettings = video.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String iframeCode = "<iframe width=\"310\" height=\"220\" src=\"https://www.youtube.com/embed/"+ urlID +"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";

        video.loadData(iframeCode, "text/html", "utf-8");
        descM.setText(des);
        displayListMovie();
    }

    private void displayListMovie(){
        List<CardMovie> ls = new ArrayList<>();
        cardAdapter = new CardAdapter(this, ls , new IClickEveentCardMovie() {
            @Override
            public void onClickCard(CardMovie cardMovie) {
                clickNextDetailMovie(cardMovie);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
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
}
