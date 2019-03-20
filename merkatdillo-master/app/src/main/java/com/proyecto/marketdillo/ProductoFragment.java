package com.proyecto.marketdillo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductoFragment extends Fragment {
    private ListView productoList;
    private ProductoAdapter productoAdapter;
    private HashMap<String, Producto> producto1 = new HashMap<>();



    public ProductoFragment() {
        // Required empty public constructor
    }


    public static ProductoFragment newInstance(/*parámetros*/) {
        ProductoFragment fragment = new ProductoFragment();
        // Setup parámetros
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Gets parámetros
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_producto, container, false);

        saveProducto(new Producto("Manzana", " la descripcion es muy grande y no se q mas poner", "$3000lb", 1));
        // Instancia del ListView.
        productoList = (ListView) root.findViewById(R.id.producto_list);
        // Inicializar el adaptador con la fuente de datos.
        productoAdapter = new ProductoAdapter(getActivity(), getProducto());
        //Relacionando la lista con el adaptador
        productoList.setAdapter(productoAdapter);

        return root;
    }
    private void saveProducto(Producto producto) {
        producto1.put(producto.getNombre(), producto);
    }
    public List<Producto> getProducto() {
        return new ArrayList<>(producto1.values());
    }
}
