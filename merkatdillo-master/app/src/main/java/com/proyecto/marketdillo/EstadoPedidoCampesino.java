package com.proyecto.marketdillo;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.firestore.FirebaseFirestore;

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

        SingletonPedido singletonPedido = SingletonPedido.getInstance();
        Pedidos pedido = singletonPedido.getPedido();

        idDocument = pedido.getIdDocument();

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


        switch (pedido.getEstado()){
            case "Confirmado":
                confirmar.setVisibility(View.GONE);
                checkConf.setImageResource(R.drawable.check_circle);
                enviado.setVisibility(View.VISIBLE);

                break;
            case "Enviado":
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
                enviarPedido.setVisibility(View.VISIBLE);
                modificaPedido("Enviado");
            }
        });

    }

    private void modificaPedido(String estado){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> estadoPedido = new HashMap<>();
        estadoPedido.put("estado", estado);
        db.collection("Pedidos").document(idDocument).update(estadoPedido);
    }
}