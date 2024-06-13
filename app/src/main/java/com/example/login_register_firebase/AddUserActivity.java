package com.example.login_register_firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_add_user);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextInputEditText firstName = findViewById(R.id.firstNameET);
        TextInputEditText lastName = findViewById(R.id.lastNameET);
        TextInputEditText phone = findViewById(R.id.phoneNumberET);
        TextInputEditText bio = findViewById(R.id.bioET);
        MaterialButton addUser = findViewById(R.id.add);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object>  user = new HashMap<>();
                user.put("firstName", Objects.requireNonNull(firstName.getText()).toString());
                user.put("lastName", Objects.requireNonNull(lastName.getText()).toString());
                user.put("phone", Objects.requireNonNull(phone.getText()).toString());
                user.put("bio", Objects.requireNonNull(bio.getText()).toString());

                db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddUserActivity.this,"Usuario agregado correctamente",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddUserActivity.this,"Error al agregar usuario",Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
    }
}
