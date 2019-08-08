package com.proyecto.marketdillo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EstadoPedido extends AppCompatActivity {

    private ImageView checkConf;
    private ImageView checkSend;
    private ImageView checkFinal;
    private TextView verDetalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_pedido);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Estado del pedido");
        checkConf = findViewById(R.id.checkConfirmado);
        checkSend = findViewById(R.id.checkEnviado);
        checkFinal = findViewById(R.id.checkFinalizado);
        verDetalles = findViewById(R.id.detalles);

        String id = getIntent().getStringExtra("idPedido");

        if(id==null){
            SingletonPedido singletonPedido = SingletonPedido.getInstance();
            final Pedidos pedido = singletonPedido.getPedido();

            switch (pedido.getEstado()){
                case "Confirmado":
                    checkConf.setImageResource(R.drawable.check_circle);
                    break;
                case "Enviado":
                    checkConf.setImageResource(R.drawable.check_circle);
                    checkSend.setImageResource(R.drawable.check_circle);
                    break;
                case "Finalizado":
                    checkConf.setImageResource(R.drawable.check_circle);
                    checkSend.setImageResource(R.drawable.check_circle);
                    checkFinal.setImageResource(R.drawable.check_circle);
                    nextActivity(pedido,"si");
                    break;
            }

            verDetalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextActivity(pedido,"no");
                }
            });
        }else {
            getPedido(id);
        }


    }
    private void nextActivity(Pedidos pedido, String visible){
        ArrayList<Producto> productos = new ArrayList<>(pedido.getProductos());
        Intent intent = new Intent(EstadoPedido.this, VistaDetalles.class);
        Toast.makeText(this, ""+pedido.getProductos().get(0), Toast.LENGTH_SHORT).show();
        intent.putExtra("pedido", pedido);
        intent.putExtra("visible", visible);
        startActivity(intent);
    }

    private void getPedido(String id){
        final SingletonPedido singletonPedido = SingletonPedido.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Pedidos").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Pedidos pedido = document.toObject(Pedidos.class);
                    pedido.setIdDocument(document.getId());
                    singletonPedido.setPedido(pedido);
                }
                SingletonPedido singletonPedido = SingletonPedido.getInstance();
                final Pedidos pedido = singletonPedido.getPedido();

                switch (pedido.getEstado()){
                    case "Confirmado":
                        checkConf.setImageResource(R.drawable.check_circle);
                        break;
                    case "Enviado":
                        checkConf.setImageResource(R.drawable.check_circle);
                        checkSend.setImageResource(R.drawable.check_circle);
                        break;
                    case "Finalizado":
                        checkConf.setImageResource(R.drawable.check_circle);
                        checkSend.setImageResource(R.drawable.check_circle);
                        checkFinal.setImageResource(R.drawable.check_circle);
                        nextActivity(pedido,"si");
                        break;
                }

                verDetalles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextActivity(pedido,"no");
                    }
                });

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
