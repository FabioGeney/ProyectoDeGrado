package com.proyecto.marketdillo;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VistaDetalles extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mercadilloAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Producto> canastas;
    private DividerItemDecoration dividerItemDecoration;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RatingBar ratingBar;
    private List<Producto> productos;
    double cal=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setTitle("Detalles del Pedido");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // consulta los datos del usuario almacenados en el SessionManager
        SessionManager sessionManager = new SessionManager(this);
        Gson gson = new Gson();
        String userGson = sessionManager.getUsuario();
        Usuario usuario = gson.fromJson(userGson,Usuario.class);



        //Declara e inicia variables
        TextView nombre = findViewById(R.id.nombreMercadillo);
        TextView direccion = findViewById(R.id.direccion);
        TextView total = findViewById(R.id.total);
        TextView fecha = findViewById(R.id.fecha);
        Button pedirNuevo = findViewById(R.id.button);
        ratingBar = findViewById(R.id.ratingBar);
        CardView cardCalif = findViewById(R.id.cardView4);


        //obtiene datos del intent y los almacena en variables
        final Pedidos pedidos = (Pedidos) getIntent().getSerializableExtra("pedido");
        final String nombreMercadillo = pedidos.getNombreMercadillo();
        final String idDocumentPedido = pedidos.getIdDocument();
        final String idMercadillo = pedidos.getIdCampesino();
        String nombreUsuario = pedidos.getNombreComprador();
        cal = pedidos.getCalificacion();
        String direccionEntrega = pedidos.getDireccionEntrega();
        String totalPedido = pedidos.getTotal();
        String boton = getIntent().getStringExtra("visible");
        //SingletonHistorial singletonHistorial = SingletonHistorial.getInstance();
        //ArrayList<Producto> getProductos = singletonHistorial.getHistorial();

        //Si boton es no pedirNuevo y cardCalif estaran ocultos
        if(boton.equals("no")){
            pedirNuevo.setVisibility(View.GONE);
            cardCalif.setVisibility(View.GONE);
        }

        //si cal es diferente de cero seteara el ratingBar con la calificacion del pedido
        if(cal!=0){
            float numStar = (float)cal;
            ratingBar.setRating(numStar);
        }

        //setea textviews
        fecha.setText(pedidos.getFecha());
        if(usuario.getTipoUsuario().equals("campesino")){
            nombre.setText(nombreUsuario);

            pedirNuevo.setVisibility(View.GONE);
        }else{
            nombre.setText(nombreMercadillo);
        }

        direccion.setText(direccionEntrega);
        total.setText( totalPedido);

        canastas = pedidos.getProductos();
        productos = pedidos.getProductos();
        mRecyclerView = findViewById(R.id.listview);
        layoutManager = new LinearLayoutManager(this);
        dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);

        mercadilloAdapter = new DetallesAdapter(canastas, R.layout.list_detalles, new DetallesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Producto producto, int posicion) {

            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mercadilloAdapter);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        cardCalif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
                if(cal==0 && singletonUsuario.getUsuario().getTipoUsuario().equals("consumidor")){
                    alertDialog(idMercadillo, idDocumentPedido, pedidos);
                }

            }
        });

        //pedir de nuevo
        pedirNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();
                CanastaClass canastaClass = singletonCanasta.getCanasta();
                if(singletonCanasta.getCanasta() != null  ){
                    if(singletonCanasta.getCanasta().getId().equals(idMercadillo)){
                        buscaMercadillo(idMercadillo);

                    }else{
                        AlertDialogPedir(idMercadillo);
                    }
                }else {

                    buscaMercadillo(idMercadillo);
                }

            }
        });

    }

    //este metodo muestra un alertDialog para calificar el pedido
    private void alertDialog(String docMercadillo, String docPedido, final Pedidos pedido){
        //id de campesino y consumidor
        final String idCampesino = pedido.getIdCampesino();
        final String idConsumidor = pedido.getIdConsumidor();
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Calificar pedido");
        LayoutInflater inflater = getLayoutInflater();
        // Inflar y establecer el layout para el dialogo
        // Pasar nulo como vista principal porque va en el diseño del diálogo
        View v = inflater.inflate(R.layout.calificar_pedido, null);

        //declara las variables de la vista
        final String idDocumentPedido = docPedido;
        final String idMercadillo = docMercadillo;
        final RatingBar ratingProdutos;
        final RatingBar ratingServicio;
        final RatingBar ratingCosto;
        final RatingBar ratingTiempo;
        Button enviar;

        //busca las variables de la vista
        ratingProdutos = v.findViewById(R.id.ratingProdutos);
        ratingServicio = v.findViewById(R.id.ratingBar);
        ratingCosto = v.findViewById(R.id.ratingCosto);
        ratingTiempo = v.findViewById(R.id.ratingTiempo);
        enviar = v.findViewById(R.id.enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toma el numero de estrellas de los ratingbar y saca un pormnedio
                double calificacion = (ratingProdutos.getRating() + ratingServicio.getRating() + ratingCosto.getRating() + ratingTiempo.getRating())/4;
                //Instancia 2 maps para almacenar el promedio y enviarlo a 2 colecciones de la base de datos
                Map<String, Object> update = new HashMap<>();
                Map<String, Object> promedio = new HashMap<>();
                //agrega el promedio a los 2 HashMaps
                update.put("calificacion", calificacion);
                promedio.put("promedio", calificacion);
                //envia el promedio a la coleccion de calificacion en la base de datos para que se ejecute una CloudFunction y calcule el promedio de todos los pedidos
                db.collection("Mercadillo").document(idMercadillo).collection("Calificacion").document().set(promedio);
                //actualiza el promedio en la base de datos
                db.collection("Consumidor").document(idConsumidor).collection("Pedidos").document(idDocumentPedido).update(update);
                calificaPedidoCampesino(idCampesino, idDocumentPedido, update);
                //actualiza el ratingBar con la nueva calificacion
                float numStar = (float)calificacion;
                ratingBar.setRating(numStar);
                cal = numStar;
                //cierra el alertDialog
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(v);
        alertDialog.show();
    }
    //este metodo busca y actualiza la calificacion en la coleccion del campesino
    private void calificaPedidoCampesino(final String idCampesino, String idDocumentConsumidor, final Map<String, Object> update){
        db.collection("Campesino").document(idCampesino).collection("Pedidos")
                .whereEqualTo("idDocumentConsumidor",idDocumentConsumidor)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        String id = documentSnapshot.getId();
                        db.collection("Campesino").document(idCampesino).collection("Pedidos").document(id).update(update);
                    }
                }
            }
        });
    }
    //este metodo busca el mercadillo en la base de datos
    private void buscaMercadillo(String idMercadillo){
        //inicializa singleton para almacenar el mercadillo
        final SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //busqueda en la base de datos
        db.collection("Mercadillo").document(idMercadillo).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Mercadillo mercadillo = document.toObject(Mercadillo.class);
                    //cuando encuetra el mercadillo lo almacena en el singleton
                    singletonMercadillo.setMercadillo(mercadillo);
                }
                //llama al metodo setCanastaClass con los datos del mercadillo encontrado
                setCanastaClass(singletonMercadillo.getMercadillo() ,productos);
                Intent intent = new Intent(VistaDetalles.this, VistaProductosMercadillo.class);
                startActivity(intent);
            }
        });
    }
    //crea una nueva canasta con los productos del pedido realizado
    private void setCanastaClass(Mercadillo mercadillo, List<Producto> productos  ){
        //crea una nueva canasta
        CanastaClass canastaClass = new CanastaClass(mercadillo.getId(), mercadillo.getCostoEnvio() );
        //recorre la lista de productos del pedido y los almacena en la canasta
        for(Producto producto: productos){
            canastaClass.agregarProducto(producto.getKey(), producto);
        }
        //setea el singletonCanasta con la nueva canasta
        SingletonCanasta.getInstance().setCanasta(canastaClass);

    }

    private void AlertDialogPedir(final String idMercadillo){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Aviso");
        alerta.setMessage("Para agregar porductos de este mercadillo debes vaciar la canasta");
        alerta.setPositiveButton("Vaciar Canasta", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                buscaMercadillo(idMercadillo);
            }
        });
        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });

        alerta.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
