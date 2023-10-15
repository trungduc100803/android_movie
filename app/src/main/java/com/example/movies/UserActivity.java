package com.example.movies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity {

    TextView nameavatar, nameuser, emailuser;
    Button btndangxuat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);
        init();

        int index = displayInfoUser();

        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference userg = database.getReference("users/" + index+"/status");
                userg.setValue(false);
                Intent i = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(i);
//                Toast.makeText(UserActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        nameuser = findViewById(R.id.nameuser);
        emailuser = findViewById(R.id.emailuser);
        nameavatar = findViewById(R.id.nameavatar);
        btndangxuat = findViewById(R.id.btndangxuat);
    }
    private int displayInfoUser(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return 0;
        }
        User user = (User) bundle.get("data_user");
        int index = bundle.getInt("index");
        nameuser.setText(user.getUserName());
        emailuser.setText(user.getEmail());
        nameavatar.setText(user.getUserName());

        return index;
    }
}
