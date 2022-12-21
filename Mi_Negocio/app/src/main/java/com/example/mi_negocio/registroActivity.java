package com.example.mi_negocio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registroActivity extends AppCompatActivity {

    private Button btnregistrar;
    private EditText textcorreo, textpass, textnombre;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    String nameUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        textcorreo = findViewById(R.id.txt_EmailAddressR);
        textpass = findViewById(R.id.txt_NumberPasswordR);
        textnombre = findViewById(R.id.editTextNombre);
        btnregistrar = findViewById(R.id.btn_registroR);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameUser = textnombre.getText().toString().trim();
                String emailUser = textcorreo.getText().toString().trim();
                String passUser = textpass.getText().toString().trim();

                if(nameUser.isEmpty() && emailUser.isEmpty() && passUser.isEmpty()){
                    Toast.makeText(registroActivity.this, "Complete los datos",Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(nameUser, emailUser, passUser);
                }
            }
        });
    }

    private void registerUser(String nameUser,String emailUser, String passUser) {
        mAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user = mAuth.getCurrentUser();
                registerToDatabase(user);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(registroActivity.this,"Error al registrar, el usuario ya se ha creado.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void registerToDatabase(FirebaseUser user){
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getUid());
        map.put("name", nameUser);
        map.put("email", user.getEmail());

        mFirestore.collection("user").document(user.getUid()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                finish();
                startActivity(new Intent(registroActivity.this, UsuarioActivity.class));
                Toast.makeText(registroActivity.this,"Usuario Registrado con Exito",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(registroActivity.this, "Error al guardar",Toast.LENGTH_SHORT).show();
            }
        });
    }
}