package com.example.movies;

import android.content.Intent;
import android.os.Bundle;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    TextView txDangki;
    EditText username, password;
    Button btnLogin;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();

        List<User> ListUser = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        getUserFromDataBase(database, ListUser);

        //handle click===============
        txDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToSignup();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin(ListUser, database);
            }
        });

    }

    //method handle===============
    private void init(){
        txDangki = findViewById(R.id.txtdangki);
        username = findViewById(R.id.txtusername);
        password = findViewById(R.id.txtpass);
        btnLogin = findViewById(R.id.btndangnhap);
    }
    private void changeToSignup(){
        Intent i = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(i);
    }
    private void handleLogin(List<User> ListUser, FirebaseDatabase database){
        int index = 0;

        for(User user : ListUser){
            // lay ra ten vaf mk vua nhap
            String currentUserName = username.getText().toString().trim();
            String currentPass = password.getText().toString().trim();

            // lay ra ten va mk trong database
            String userName = user.getUserName().trim();
            String Pass = user.getPassword().trim();
            index++;

            if(!currentPass.equals(Pass) || !currentUserName.equals(userName)){
                Toast.makeText(LoginActivity.this, "Tên tài khoản hoặc mật khẩu không đúng 😢😢😢😢!!!", Toast.LENGTH_SHORT).show();
            }else {
                int userNumber = index - 1;
                DatabaseReference userg = database.getReference("users/" + userNumber+"/status");
                DatabaseReference dataUser = database.getReference("users/" + userNumber);
                userg.setValue(true);
                dataUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("obj_user", (Serializable) user);
                        bundle.putInt("index", userNumber);
                        i.putExtras(bundle);
                        startActivity(i);
                        return;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return;
            }
        };
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
}
