package com.example.mi_negocio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UsuarioActivity extends AppCompatActivity {

    private Button btncerrar, btnnegocios, btnminegocio, btncanasta, btncrearnegocio;
    private TextView txtNombre, txtUserName;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        mAuth = FirebaseAuth.getInstance();
        //Botones
        btncerrar = findViewById(R.id.btn_cerrar);
        btnnegocios = findViewById(R.id.btn_negocios);
        btncrearnegocio = findViewById(R.id.btn_crearnegocio);
        btnminegocio = findViewById(R.id.btn_Minegocios);
        btncanasta = findViewById(R.id.btn_canasta);
        txtNombre = findViewById(R.id.textView3);
        txtUserName = findViewById(R.id.textView4);
        obtenerUsuario();
        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(UsuarioActivity.this, LoginActivity.class));
            }
        });

        btnnegocios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioActivity.this,NegociosProductActivity.class);
                startActivity(intent);
            }
        });


        btnminegocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioActivity.this, MiNegocioActivity.class);
                startActivity(intent);
            }
        });

        btncanasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioActivity.this, CanastaActivity.class);
                startActivity(intent);
            }
        });

        btncrearnegocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioActivity.this, CrearNegocioActivity.class);
                startActivity(intent);
            }
        });
    }

    private void obtenerUsuario() {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {
                    Usuario datos = documentSnapshot.toObject(Usuario.class);
                    txtNombre.setText(datos.getName());
                    txtUserName.setText(datos.getEmail());

                }catch (Exception e){
                    //Toast.makeText(UsuarioActivity.this,e.getMessage() + mAuth.getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}