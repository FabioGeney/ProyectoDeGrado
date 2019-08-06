package com.proyecto.marketdillo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EstadoPedidoCampesino extends AppCompatActivity {
    private ConstraintLayout confirmar;
    private ConstraintLayout enviado;
    private ConstraintLayout finalizado;
    private Button aceptar;
    private Button rechazar;
    private Button enviarPedido;
    private Button finalizarPedido;
    private Button detalles;
    private ImageView checkConf;
    private ImageView checkSend;
    private ImageView checkFinal;
    private String idDocument;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_pedido_campesino);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra("idPedido");
        this.setTitle("Estado del pedido");


        confirmar = findViewById(R.id.confirmarLayout);
        enviado = findViewById(R.id.enviar);
        finalizado = findViewById(R.id.finalizar);
        aceptar = findViewById(R.id.aceptarPedido);
        rechazar = findViewById(R.id.rechazarPedido);
        enviarPedido = findViewById(R.id.enviarPedido);
        finalizarPedido = findViewById(R.id.finalizarPedido);
        detalles = findViewById(R.id.detalles);
        checkConf = findViewById(R.id.checkConf);
        checkSend = findViewById(R.id.chekSend);
        checkFinal = findViewById(R.id.checkFinal);


        if(id==null){
            SingletonPedido singletonPedido = SingletonPedido.getInstance();
            Pedidos pedido = singletonPedido.getPedido();

            idDocument = pedido.getIdDocument();


            switch (pedido.getEstado()){
                case "Creado":
                    finalizado.setVisibility(View.GONE);
                    enviado.setVisibility(View.GONE);
                    break;
                case "Confirmado":
                    confirmar.setVisibility(View.GONE);
                    checkConf.setImageResource(R.drawable.check_circle);
                    finalizado.setVisibility(View.GONE);
                    enviado.setVisibility(View.VISIBLE);

                    break;
                case "Enviado":
                    confirmar.setVisibility(View.GONE);
                    enviado.setVisibility(View.GONE);
                    checkConf.setImageResource(R.drawable.check_circle);
                    checkSend.setImageResource(R.drawable.check_circle);
                    enviarPedido.setVisibility(View.VISIBLE);
                    break;

            }

            aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmar.setVisibility(View.GONE);
                    checkConf.setImageResource(R.drawable.check_circle);
                    enviado.setVisibility(View.VISIBLE);
                    modificaPedido("Confirmado");


                }
            });

            enviarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enviado.setVisibility(View.GONE);
                    checkSend.setImageResource(R.drawable.check_circle);
                    finalizado.setVisibility(View.VISIBLE);
                    modificaPedido("Enviado");
                }
            });

            finalizarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalizado.setVisibility(View.GONE);
                    checkFinal.setImageResource(R.drawable.check_circle);
                    modificaPedido("Finalizado");
                }
            });

            detalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SingletonPedido singletonPedido = SingletonPedido.getInstance();
                    Pedidos pedidos = singletonPedido.getPedido();
                    ArrayList<Producto> productos = new ArrayList<>(pedidos.getProductos());
                    Intent intent = new Intent(EstadoPedidoCampesino.this, VistaDetalles.class);
                    intent.putExtra("pedido", pedidos);
                    intent.putExtra("visible", "no");
                    startActivity(intent);
                }
            });
        }else{
            getPedido(id);
        }



    }

    private void modificaPedido(String estado){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> estadoPedido = new HashMap<>();
        estadoPedido.put("estado", estado);
        db.collection("Pedidos").document(idDocument).update(estadoPedido);
    }

    private void getPedido(String id){
        final SingletonPedido singletonPedido = SingletonPedido.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Pedidos").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Pedidos pedido = document.toObject(Pedidos.class);
                    pedido.setIdDocument(document.getId());
                    singletonPedido.setPedido(pedido);
                }
                final Pedidos pedido = singletonPedido.getPedido();


                switch (pedido.getEstado()){
                    case "Creado":
                        finalizado.setVisibility(View.GONE);
                        enviado.setVisibility(View.GONE);
                        break;
                    case "Confirmado":
                        confirmar.setVisibility(View.GONE);
                        checkConf.setImageResource(R.drawable.check_circle);
                        finalizado.setVisibility(View.GONE);
                        enviado.setVisibility(View.VISIBLE);

                        break;
                    case "Enviado":
                        confirmar.setVisibility(View.GONE);
                        enviado.setVisibility(View.GONE);
                        checkConf.setImageResource(R.drawable.check_circle);
                        checkSend.setImageResource(R.drawable.check_circle);
                        enviarPedido.setVisibility(View.VISIBLE);
                        break;

                }

                aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmar.setVisibility(View.GONE);
                        checkConf.setImageResource(R.drawable.check_circle);
                        enviado.setVisibility(View.VISIBLE);
                        modificaPedido("Confirmado");


                    }
                });

                enviarPedido.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        enviado.setVisibility(View.GONE);
                        checkSend.setImageResource(R.drawable.check_circle);
                        finalizado.setVisibility(View.VISIBLE);
                        modificaPedido("Enviado");
                    }
                });

                finalizarPedido.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finalizado.setVisibility(View.GONE);
                        checkFinal.setImageResource(R.drawable.check_circle);
                        modificaPedido("Finalizado");
                    }
                });

                detalles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SingletonPedido singletonPedido = SingletonPedido.getInstance();
                        Pedidos pedidos = singletonPedido.getPedido();
                        ArrayList<Producto> productos = new ArrayList<>(pedidos.getProductos());
                        Intent intent = new Intent(EstadoPedidoCampesino.this, VistaDetalles.class);
                        intent.putExtra("pedido", pedidos);
                        intent.putExtra("visible", "no");
                        startActivity(intent);
                    }
                });
            }
            });


    }
}
