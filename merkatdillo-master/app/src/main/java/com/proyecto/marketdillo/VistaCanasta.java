package com.proyecto.marketdillo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VistaCanasta extends AppCompatActivity {

    private CanastaAdapter canastaAdapter;
    private ListView listView;
    private List<Canasta> canasta = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_canasta);
        this.setTitle("Canasta");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        canasta.add(new Canasta("Manzana", 3000, 1, R.mipmap.ic_fruit));
        canasta.add(new Canasta("Pera", 5000, 1, R.mipmap.ic_fruit));
        canasta.add(new Canasta("Mango", 3000, 1, R.mipmap.ic_fruit));
        canasta.add(new Canasta("Piña", 4000, 1, R.mipmap.ic_fruit));
        canasta.add(new Canasta("Lulo", 1000, 1, R.mipmap.ic_fruit));

        listView = findViewById(R.id.listview);
        canastaAdapter = new CanastaAdapter(this, canasta);
        listView.setAdapter(canastaAdapter);

        final TextView textView = findViewById(R.id.metodoPago);

        final String[] opcionesPago = new String[1];
        opcionesPago[0] = "Efectivo";

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VistaCanasta.this);
                builder.setTitle("Forma de pago").setItems(opcionesPago, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(opcionesPago[which]);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }


        });
    }
}
