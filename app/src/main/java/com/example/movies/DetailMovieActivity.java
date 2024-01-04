package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailMovieActivity extends AppCompatActivity {
    ImageView imgDetail;
    TextView titleMovie, desc, author, time;
    Button btnXem, btnLuuYeuThich, btnBoyeuthic;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detai_movie_layout);
        List<Favorite> favoriteList = new ArrayList<>();

        Global global = Global.getInstance();
        String userN = global.getUserName();
        Bundle bundle = getIntent().getExtras();
        int i = 0;
        init();

        getFavoriteFromDatabase(favoriteList);
        CardMovie cardMovie = getCardMovie(bundle, i);

        btnBoyeuthic.setVisibility(View.INVISIBLE);

        btnLuuYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert bundle != null;
                handleYeuThich(cardMovie, favoriteList);
            }
        });

        btnBoyeuthic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBoYeuThich();
            }
        });

        assert cardMovie != null;
        displayMovie(cardMovie);

        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnPlay(cardMovie.getUrlVideo(), cardMovie.getDescriptions(), cardMovie.getTitle());
            }
        });
    }


    private void init(){
        titleMovie = findViewById(R.id.title_detail);
        desc = findViewById(R.id.desc);
        author = findViewById(R.id.author);
        time = findViewById(R.id.time);
        btnXem = findViewById(R.id.btnplay);
        imgDetail = findViewById(R.id.img_detail);
        btnLuuYeuThich = findViewById(R.id.btnluuyeuthic);
        btnBoyeuthic = findViewById(R.id.btnboluuyeuthic);
    }
    private void handleYeuThich(CardMovie cardMovie, List<Favorite> favoriteList){
        btnLuuYeuThich.setVisibility(View.GONE);
        btnBoyeuthic.setVisibility(View.VISIBLE);

        Global global = Global.getInstance();
        String userN = global.getUserName();
        addFavorite(userN, cardMovie, favoriteList);
    }

    private List<Favorite> handleVisibleBtnFavorite(List<Favorite> list){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("favorites");

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Favorite favorite  = dataSnapshot.getValue(Favorite.class);
                    list.add(favorite);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return list;
    }

    private void addFavorite(String user, CardMovie cardMovie,  List<Favorite> c) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("favorites");
        c.add(new Favorite(user, cardMovie));

        myRef1.setValue(c);
        Toast.makeText(DetailMovieActivity.this, "success", Toast.LENGTH_SHORT).show();
    }
    private void handleBoYeuThich(){
        btnLuuYeuThich.setVisibility(View.VISIBLE);
        btnBoyeuthic.setVisibility(View.GONE);
        Toast.makeText(DetailMovieActivity.this, "Bỏ lưu thành công", Toast.LENGTH_SHORT).show();
    }
    private void getFavoriteFromDatabase(List<Favorite> list){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("favorites");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Favorite favorite  = dataSnapshot.getValue(Favorite.class);
                    list.add(favorite);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private CardMovie  getCardMovie(Bundle bundle, int  i){
//        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return null;
        }

        i = bundle.getInt("index");

        return (CardMovie) bundle.get("obj_movies");
    }
    private void handleHideFavorite(Bundle bundle){
//        Bundle bundle1 = getIntent().getExtras();
        if(bundle == null){
            return;
        }

        ArrayList<String> user =  bundle.getStringArrayList("arr");
        Toast.makeText(DetailMovieActivity.this, user.size(), Toast.LENGTH_SHORT).show();
    }
    private void handleBtnPlay(String url, String descM, String title){
        Intent i = new Intent(this, VideoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("video", url );
        bundle.putString("desc", descM);
        bundle.putString("title", title);
        i.putExtras(bundle);
        startActivity(i);
    }
    private void displayMovie(CardMovie cardMovie){
        titleMovie.setText(cardMovie.getTitle());
        desc.setText(cardMovie.getDescriptions());
        author.setText("Đạo diễn:  "+cardMovie.getAuthor());
        time.setText("Thời gian:  "+cardMovie.getTime());
        Glide.with(this)
                .load(cardMovie.getResourceImage())
                .fitCenter()
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imgDetail.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}
