package com.example.movies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    EditText username, email, pass, passRepeat;
    TextView txtdangnhap;
    Button btndangki;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        init();

        List<String> list = new ArrayList<>();   //luu phimm yeu thich
        List<User> ListUser = new ArrayList<>(); //luu user
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        getUserFromDataBase(database, ListUser);

        //handle click=============
        txtdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToLogin();
            }
        });

        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignup(ListUser, list);
            }
        });

    }

    //method handle ==========
    private void init(){
        username = findViewById(R.id.txtusername);
        email = findViewById(R.id.txtemail);
        pass = findViewById(R.id.txtpass);
        passRepeat = findViewById(R.id.txtpassRepeat);
        btndangki = findViewById(R.id.btndangki);
        txtdangnhap = findViewById(R.id.txtdangki);
    }
    private void getUserFromDataBase(FirebaseDatabase database, List<User> ListUser){
        DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    ListUser.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void  changeToLogin(){
        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(i);
    }
    private void handleSignup(List<User> ListUser, List<String> list){
        Boolean flaq = false;
        String userName = username.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Pass = pass.getText().toString().trim();
        String PassRepeat = passRepeat.getText().toString().trim();

//                addUser(userName, Email, Pass, PassRepeat, ListUser, list);

        for (User user : ListUser) {
            String userNameCurrent = user.getUserName().trim();

            if (userName.equals(userNameCurrent)) {
                Toast.makeText(SignupActivity.this, "Tên đăng nhập đã tồn tại😢😢😢😢😢", Toast.LENGTH_SHORT).show();
                flaq = false;
                return;
            } else {
                flaq = true;
            }

        }

        if(flaq){
            if (userName.equals("") || Email.equals("") || Pass.equals("") || PassRepeat.equals("")) {
                Toast.makeText(SignupActivity.this, "Yêu cầu nhập đủ các thông tin 🐱‍👤🐱‍👤🐱‍👤🐱‍👤", Toast.LENGTH_SHORT).show();
            } else {
                if (!PassRepeat.equals(Pass)) {
                    Toast.makeText(SignupActivity.this, "Mật khẩu không nhập lại không trùng khớp !!!", Toast.LENGTH_SHORT).show();
                } else {
                    addUser(userName, Email, Pass, PassRepeat, ListUser, list);
                    Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        }

    }
    private void addUser(String userName, String Email, String Pass, String PassRepeat, List<User> c, List<String> list) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("users");
        list.add(userName);
        c.add(new User(userName, Email, Pass, PassRepeat, false, list));

        myRef1.setValue(c);
        Toast.makeText(SignupActivity.this, "ok", Toast.LENGTH_SHORT).show();
    }

}
