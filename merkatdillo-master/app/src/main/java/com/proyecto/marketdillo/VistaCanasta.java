package com.proyecto.marketdillo;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class VistaCanasta extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter canastaAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Producto> canastas;
    private DividerItemDecoration dividerItemDecoration;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Mercadillo mercadillo;
    private Usuario usuario;
    private TextView total;
    private CanastaClass canastaClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_canasta);

        //instancia singleton
        SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
        SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();
        mercadillo = singletonMercadillo.getMercadillo();
        canastaClass = singletonCanasta.getCanasta();
        dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        //recycler de categorias
        //singleton usuario

        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
        usuario = singletonUsuario.getUsuario();

        //Crea e inicializa variables
        TextView nomreMercadillo = findViewById(R.id.nombreMercadillo);
        TextView subTotal = findViewById(R.id.subTotal);
        TextView domi = findViewById(R.id.domi);
        TextView direccion = findViewById(R.id.direccion);
        total = findViewById(R.id.total);
        Button enviar = findViewById(R.id.finalizar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACProgressFlower dialog = new ACProgressFlower.Builder(VistaCanasta.this)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .text("Haciendo Pedido")
                        .fadeColor(Color.DKGRAY).build();
                dialog.show();
                enviarPedido();
                canastaClass.borraLista();

            }
        });

        //setea textViews con datos del mercadillo y usuario

        nomreMercadillo.setText(mercadillo.getNombre());
        domi.setText("$ " + mercadillo.getCostoEnvio());
        //tiempo.setText(mercadillo.getTiempoEntrega());
        direccion.setText(usuario.getDireccion());


        // cambia el nombre del toolbar
        this.setTitle("Canasta");
        //inicializa el toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //obtiene los productos agregados por el usuario a la canasta
        if(canastaClass != null){
            canastas = canastaClass.getList();
        }else{
            canastas = new ArrayList<>();
        }

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
        mRecyclerView.addItemDecoration(dividerItemDecoration);

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
/*
    private List<Producto> getCanastas(){
        //llama al singleton de canasta para acceder a la informaccion
        SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();
        ArrayList<Producto> canasta = new ArrayList<>();
        //obtiene los productos del singleton de canasta
        canasta = singletonCanasta.getCanastas();
        //recorre el arreglo de los productos seleccionados por el consumidor para llenar la lista de productos de la canasta


        return canasta;
    }
*/
    private void enviarPedido(){
        //obtiene datos para hacer pedido
        String idCampesino = mercadillo.getId();
        String idConsumidor = usuario.getId();
        String nombreMercadillo = mercadillo.getNombre();
        String direccionEntrega = usuario.getDireccion();
        String nombreUsuario = usuario.getNombre();
        //estado iniciaol del pedido
        String estado = "Creado";
        String precio = total.getText().toString();
        //fecha del pedido
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        //direccion donde se guardara el pedido en la coleccion del usuario
        CollectionReference guardaPedidoConsumidor = db.collection("Consumidor").document(idConsumidor).collection("Pedidos");
        String fecha = dateFormat.format(date);
        ArrayList<Producto> canasta = canastaClass.getList();
        //crea el objeto pedido para enviar a la base de datos
        Pedidos pedido = new Pedidos(idCampesino, idConsumidor, nombreUsuario, nombreMercadillo, direccionEntrega,estado ,canasta, precio, fecha);
        //envia el pedido a la base de datos
        guardaPedidoConsumidor.add(pedido);
        //llama el metodo que setea idDocumentConsumidor
        idPedidoConsumidor(idConsumidor, idCampesino);
        //Va a otro activity al enviar pedido
        Intent intent = new Intent(VistaCanasta.this, VistaUsuarios.class );
        startActivity(intent);
    }

    private void idPedidoConsumidor(String idConsumidor, String idCampesino){
        final CollectionReference enviarPedidoCampesino = db.collection("Campesino").document(idCampesino).collection("Pedidos");
        db.collection("Consumidor").document(idConsumidor).
           collection("Pedidos").
           whereEqualTo("idCampesino", idCampesino).
           get().
           addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Pedidos pedido = document.toObject(Pedidos.class);
                        if(pedido.getIdDocumentConsumidor()==null){
                            pedido.setIdDocumentConsumidor(document.getId());
                            enviarPedidoCampesino.add(pedido);
                        }

                    }
                }
            }
        });

    }
}
