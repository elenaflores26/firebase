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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveIntanceState){
        super.onCreate(saveIntanceState);
        setContentView(R.layout.activity_edit_user);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextInputEditText firstName = findViewById(R.id.firstNameET);
        TextInputEditText lastName = findViewById(R.id.lastNameET);
        TextInputEditText phone = findViewById(R.id.phoneNumberET);
        TextInputEditText bio = findViewById(R.id.bioET);
        MaterialButton save = findViewById(R.id.save);
        MaterialButton delete = findViewById(R.id.delete);

        firstName.setText(App.user.getFirstName());
        lastName.setText(App.user.getLastName());
        phone.setText(App.user.getPhone());
        bio.setText(App.user.getBio());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("users").document(App.user.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditUserActivity.this,"Usuario eliminado correctamente",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUserActivity.this,"Error al eliminar usuario",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> user = new HashMap<>();
                user.put("firstName", Objects.requireNonNull(firstName.getText()).toString());
                user.put("lastName", Objects.requireNonNull(lastName.getText()).toString());
                user.put("phone", Objects.requireNonNull(phone.getText()).toString());
                user.put("bio", Objects.requireNonNull(bio.getText()).toString());

                db.collection("users").document(App.user.getId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditUserActivity.this,"Exito al actualizar",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditUserActivity.this," Error al actualizar",Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                });
            }
        });
    }
}
