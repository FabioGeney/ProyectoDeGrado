package com.proyecto.marketdillo;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_pedido_campesino);

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


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmar.setVisibility(View.GONE);
                checkConf.setImageResource(R.drawable.check_circle);
                enviado.setVisibility(View.VISIBLE);


            }
        });

        enviarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviado.setVisibility(View.GONE);
                checkSend.setImageResource(R.drawable.check_circle);
                enviarPedido.setVisibility(View.VISIBLE);
            }
        });

    }
}
