package com.example.mi_negocio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText correo, contra;
    private Button ingresarbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = (EditText) findViewById(R.id.EmailAddress);
        contra = (EditText) findViewById(R.id.NumberPassword);
        ingresarbtn = (Button) findViewById(R.id.btn_iniciar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Iniciamos Boton
        ingresarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email_User = correo.getText().toString().trim();
                String password = contra.getText().toString().trim();

                if(Email_User.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Ingresar los Datos",Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(Email_User, password);
                }
            }
        });
    }

    private void loginUser(String email_user, String password) {
        mAuth.signInWithEmailAndPassword(email_user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(LoginActivity.this, UsuarioActivity.class));
                    Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,"Error al iniciar sesion",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(LoginActivity.this, UsuarioActivity.class));
            finish();
        }
    }
}