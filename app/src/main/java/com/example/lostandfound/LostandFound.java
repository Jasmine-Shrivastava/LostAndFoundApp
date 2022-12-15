package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LostandFound extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button lostButton;
    private Button foundButton;
    private Button SeeButton1, SeeButton2;
    private DatabaseReference root;
    private RecyclerView recyclerView;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostand_found);

        auth = FirebaseAuth.getInstance();
        lostButton = findViewById(R.id.lost_button);
        foundButton = findViewById(R.id.found_button);
        SeeButton1 = findViewById(R.id.see_button_lost);
        root = FirebaseDatabase.getInstance().getReference();

        lostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LostandFound.this,Lost.class));
            }
        });
        foundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LostandFound.this,Found.class));
            }
        });
        SeeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LostandFound.this,showactivity.class));
            }
        });
        /*SeeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LostandFound.this,userlist.class));
            }
        });*/



    }
}