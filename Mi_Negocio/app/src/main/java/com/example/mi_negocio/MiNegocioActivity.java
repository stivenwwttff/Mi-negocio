package com.example.mi_negocio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MiNegocioActivity extends AppCompatActivity {

    private Button btnanadir, btneditar, btneliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_negocio);

        //Botoned
        btnanadir = findViewById(R.id.btn_anadir);
        btneditar = findViewById(R.id.btn_editar);
        btneliminar = findViewById(R.id.btn_eliminar);

        btnanadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiNegocioActivity.this, AnadirProductoActivity.class);
                startActivity(intent);
            }
        });

        btneditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiNegocioActivity.this, EditarProductoActivity.class);
                startActivity(intent);
            }
        });

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiNegocioActivity.this, EliminarProductoActivity.class);
                startActivity(intent);
            }
        });
    }
}