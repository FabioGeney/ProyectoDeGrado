package com.proyecto.marketdillo;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VistaCanasta extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter canastaAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Producto> canastas;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference enviarPedido = db.collection("Pedido");
    private Mercadillo mercadillo;
    private Usuario usuario;
    private TextView total;
    private SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_canasta);

        //instancia singleton
        SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
        mercadillo = singletonMercadillo.getMercadillo();

        //singleton usuario

        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
        usuario = singletonUsuario.getUsuario();

        //Crea e inicializa variables
        TextView nomreMercadillo = findViewById(R.id.nombreMercadillo);
        TextView envio = findViewById(R.id.envio);
        TextView tiempo = findViewById(R.id.tiempo);
        TextView direccion = findViewById(R.id.direccion);
        total = findViewById(R.id.total);
        Button enviar = findViewById(R.id.finalizar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarPedido();
                singletonCanasta.borrarLista();

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
            public void OnItemClick(Producto producto, int posicion) {

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

    private List<Producto> getCanastas(){
        //llama al singleton de canasta para acceder a la informaccion
        SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();
        ArrayList<Producto> canasta = new ArrayList<>();
        //obtiene los productos del singleton de canasta
        canasta = singletonCanasta.getCanastas();
        //recorre el arreglo de los productos seleccionados por el consumidor para llenar la lista de productos de la canasta


        return canasta;
    }

    private void enviarPedido(){
        String idCampesino = mercadillo.getId();
        String idConsumidor = usuario.getId();
        String nombreMercadillo = mercadillo.getNombre();
        String direccionEntrega = usuario.getDireccion();
        String estado = "Creado";
        String precio = total.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        String fecha = dateFormat.format(date);

        ArrayList<Producto> canasta = singletonCanasta.getCanastas();

        Pedidos pedido = new Pedidos(idCampesino, idConsumidor, nombreMercadillo, direccionEntrega,estado ,canasta, precio, fecha);
        Toast.makeText(this, ""+ canasta.size(), Toast.LENGTH_SHORT).show();
        enviarPedido.add(pedido);
        Intent intent = new Intent(VistaCanasta.this, VistaUsuarios.class );
        startActivity(intent);
    }
}
