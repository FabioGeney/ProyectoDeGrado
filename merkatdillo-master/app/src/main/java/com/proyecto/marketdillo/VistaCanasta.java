package com.proyecto.marketdillo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class VistaCanasta extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter canastaAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Canasta> canastas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_canasta);

        //instancia singleton
        SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
        Mercadillo mercadillo = singletonMercadillo.getMercadillo();

        //singleton usuario

        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
        Usuario usuario = singletonUsuario.getUsuario();

        //Crea e inicializa variables
        TextView nomreMercadillo = findViewById(R.id.nombreMercadillo);
        TextView envio = findViewById(R.id.envio);
        TextView tiempo = findViewById(R.id.tiempo);
        TextView direccion = findViewById(R.id.direccion);
        Button enviar = findViewById(R.id.finalizar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarPedido();
            }
        });

        //setea textViews con datos del mercadillo y usuario

        nomreMercadillo.setText(mercadillo.getNombre());
        envio.setText("Envio $" + mercadillo.getCostoEnvio());
        tiempo.setText(mercadillo.getTiempoEntrega());
        direccion.setText(usuario.getDireccion());


        // cambia el nombre del toolbar
        this.setTitle("Canasta");
        //inicializa el toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //obtiene los productos agregados por el usuario a la canasta
        canastas = getCanastas();
        //recycler view
        mRecyclerView = findViewById(R.id.listview);
        layoutManager = new LinearLayoutManager(this);

        canastaAdapter = new CanastaAdapter(canastas, R.layout.list_item_canasta, new CanastaAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Canasta producto, int posicion) {

            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(canastaAdapter);

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

    private List<Canasta> getCanastas(){
        //llama al singleton de canasta para acceder a la informaccion
        SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();
        final  ArrayList<Canasta> canasta = new ArrayList<>();
        //obtiene los productos del singleton de canasta
        ArrayList<Producto> productos = singletonCanasta.getCanastas();
        //recorre el arreglo de los productos seleccionados por el consumidor para llenar la lista de productos de la canasta
        for(Producto producto:productos){
          canasta.add(new Canasta(producto.getNombre(), Integer.parseInt(producto.getPrecioCantidad()) , producto.getContador(), R.drawable.fruit));
        }

        return canasta;
    }

    private void enviarPedido(){
        
    }
}
