package com.proyecto.marketdillo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EstadoPedido extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_pedido);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView checkConf = findViewById(R.id.checkConfirmado);
        ImageView checkSend = findViewById(R.id.checkEnviado);
        ImageView checkFinal = findViewById(R.id.checkFinalizado);
        TextView verDetalles = findViewById(R.id.detalles);

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
    private void nextActivity(Pedidos pedido, String visible){
        ArrayList<Producto> productos = new ArrayList<>(pedido.getProductos());
        Intent intent = new Intent(EstadoPedido.this, VistaDetalles.class);
        intent.putExtra("pedido", pedido);
        intent.putExtra("visible", visible);
        startActivity(intent);
    }
}
