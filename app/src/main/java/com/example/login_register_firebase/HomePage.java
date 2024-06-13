package com.example.login_register_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        FirebaseApp.initializeApp(HomePage.this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recycler);

        FloatingActionButton add = findViewById(R.id.addUser);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,AddUserActivity.class));
            }
        });
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<User> arrayList = new ArrayList<>();
                    for(QueryDocumentSnapshot doc: task.getResult()){
                        User user = doc.toObject(User.class);
                        user.setId(doc.getId());
                        arrayList.add(user);

                    }
                    UserAdapter adapter = new UserAdapter(HomePage.this,arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(User user) {
                            App.user = user;
                            startActivity(new Intent(HomePage.this,EditUserActivity.class));
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomePage.this,"Failed to get all users list",Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            ArrayList<User> arrayList = new ArrayList<>();
                            for(QueryDocumentSnapshot doc: task.getResult()){
                                User user = doc.toObject(User.class);
                                user.setId(doc.getId());
                                arrayList.add(user);

                            }
                            UserAdapter adapter = new UserAdapter(HomePage.this,arrayList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(User user) {
                                    App.user = user;
                                    startActivity(new Intent(HomePage.this,EditUserActivity.class));
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomePage.this,"Failed to get all users list",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
