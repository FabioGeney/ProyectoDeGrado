package com.proyecto.marketdillo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

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

        SingletonPedido singletonPedido = SingletonPedido.getInstance();
        Pedidos pedido = singletonPedido.getPedido();

        switch (pedido.getEstado()){
            case "Confirmado":
                checkConf.setImageResource(R.drawable.check_circle);
                break;
            case "Enviado":
                checkConf.setImageResource(R.drawable.check_circle);
                checkSend.setImageResource(R.drawable.check_circle);
                break;
        }
    }
}
