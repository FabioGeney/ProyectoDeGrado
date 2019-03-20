package com.proyecto.marketdillo;

import android.content.Context;
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
 * {@link PtsCampesinoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PtsCampesinoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PtsCampesinoFragment extends Fragment {
    private ListView productoList;
    private PtsCampesinoAdapter productoAdapter;
    private HashMap<String, Producto> producto1 = new HashMap<>();



    public PtsCampesinoFragment() {
        // Required empty public constructor
    }


    public static PtsCampesinoFragment newInstance(/*parámetros*/) {
        PtsCampesinoFragment fragment = new PtsCampesinoFragment();
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

        View root = inflater.inflate(R.layout.fragment_pts_campesino, container, false);

        saveProducto(new Producto("Manzana", "la descripcion es muy grande ", "$3000 por lb", R.mipmap.ic_fruit));
        // Instancia del ListView.
        productoList = (ListView) root.findViewById(R.id.producto_list_camp);
        // Inicializar el adaptador con la fuente de datos.
        productoAdapter = new PtsCampesinoAdapter(getActivity(), getProducto());
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
