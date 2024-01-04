package com.example.movies;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.my_interface.IClickEveentCardMovie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

    WebView video;
    TextView descM;
    CardAdapter cardAdapter;
    CommentAdapter commentAdapter;
    RecyclerView ryc;
    Button btnte, btntb, btntot, btntv;
    EditText txtBinhluan;
    RecyclerView rycComment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);
        init();

        Global global = Global.getInstance();
        List<Comment> listComment = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }

        String urlID = (String) bundle.get("video");
        String des = bundle.getString("desc");
        String title = bundle.getString("title");
        String userName = global.getUserName();

        int colorActive = ContextCompat.getColor(VideoActivity.this, R.color.red);
        int color = ContextCompat.getColor(VideoActivity.this, R.color.gray);



        WebSettings webSettings = video.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String iframeCode = "<iframe width=\"310\" height=\"220\" src=\"https://www.youtube.com/embed/"+ urlID +"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";

        video.loadData(iframeCode, "text/html", "utf-8");
        descM.setText(des);
        displayListMovie();

        txtBinhluan.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_send_24, 0);

        txtBinhluan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Xác định vị trí của sự kiện chạm
                int drawableRightStart = txtBinhluan.getRight() - txtBinhluan.getTotalPaddingRight();
                int drawableRightEnd = txtBinhluan.getRight();
                boolean drawableRightClicked = motionEvent.getX() >= drawableRightStart && motionEvent.getX() <= drawableRightEnd;

                // Xử lý sự kiện khi người dùng click vào drawableRight
                if (drawableRightClicked && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    String binhluan = txtBinhluan.getText().toString();
                    Date currentTime = new Date();

                    if(!binhluan.equals("")){
                        addComment(title, binhluan, currentTime, userName, listComment);
                    }else {
                        Toast.makeText(VideoActivity.this, "Bạn chưa bình luận gì!", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }


                return false;
            }
        });

        handleRate(color, colorActive);

        displayListComment( listComment,title);

    }

    private void init(){
        video = findViewById(R.id.video);
        descM = findViewById(R.id.descM);
        txtBinhluan = findViewById(R.id.txtBinhluan);
        ryc = findViewById(R.id.rcy_Movie);
        btnte = findViewById(R.id.btnte);
        btntb = findViewById(R.id.btntb);
        btntot = findViewById(R.id.btntot);
        btntv = findViewById(R.id.btntv);
        rycComment = findViewById(R.id.rcy_comment);
    }
    private void handleRate(int color, int colorActive){
        btnte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnte.setBackgroundColor(colorActive);
                btntb.setBackgroundColor(color);
                btntot.setBackgroundColor(color);
                btntv.setBackgroundColor(color);
            }
        });
        btntb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnte.setBackgroundColor(color);
                btntb.setBackgroundColor(colorActive);
                btntot.setBackgroundColor(color);
                btntv.setBackgroundColor(color);
            }
        });

        btntot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnte.setBackgroundColor(color);
                btntb.setBackgroundColor(color);
                btntot.setBackgroundColor(colorActive);
                btntv.setBackgroundColor(color);
            }
        });
        btntv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnte.setBackgroundColor(color);
                btntb.setBackgroundColor(color);
                btntot.setBackgroundColor(color);
                btntv.setBackgroundColor(colorActive);
            }
        });
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

    private void addComment(String title, String cmt, Date date, String  user, List<Comment> list) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("comments");
        list.add(new Comment(user, cmt, date, title));


        myRef1.setValue(list);
        txtBinhluan.setText("");
//        displayListComment(list, title);
    }
    private void displayListComment(List<Comment> commentList ,String title){
        List<Comment> ls = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, ls );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rycComment.setLayoutManager(linearLayoutManager);

        commentAdapter.setData(getComment(commentList ,title));
//        commentAdapter.notifyDataSetChanged();
        rycComment.setAdapter(commentAdapter);
    }
    private List<Comment> getComment(List<Comment> commentList ,String title){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("comments");
        List<Comment> list = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Comment comment  = dataSnapshot.getValue(Comment.class);
                    commentList.add(comment);
                    if(comment.getNameMovie().equals(title)){
                        list.add(0,comment);
                    }
                }
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        List<Comment> listCmt = new ArrayList<>(list);
//        Collections.reverse(listCmt);

        return  list;
    }

    private void getCommentFromDatabase(List<Comment> list){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("comments");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Comment comment  = dataSnapshot.getValue(Comment.class);
                    list.add(comment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
