package com.proyecto.marketdillo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PedidosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PedidosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedidosFragment extends Fragment {
    private ListView pedidosList;
    private PedidosAdapter pedidosAdapter;
    private HashMap<String, Pedidos> pedidos1 = new HashMap<>();



    public PedidosFragment() {
        // Required empty public constructor
    }


    public static PedidosFragment newInstance(/*parámetros*/) {
        PedidosFragment fragment = new PedidosFragment();
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

        View root = inflater.inflate(R.layout.fragment_pedidos, container, false);
        // savePedidos(new Pedidos("Mercadillo de verduras", "En camino", R.mipmap.ic_merca_image, "50 000"));
        // Instancia del ListView.
        pedidosList = (ListView) root.findViewById(R.id.pedidos_list);
        // Inicializar el adaptador con la fuente de datos.
        pedidosAdapter = new PedidosAdapter(getActivity(), getPedidos());
        //Relacionando la lista con el adaptador
        pedidosList.setAdapter(pedidosAdapter);

        pedidosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        return root;
    }
    private void savePedidos(Pedidos pedidos) {
        pedidos1.put(pedidos.getNombreMercadillo(), pedidos);
    }
    public List<Pedidos> getPedidos() {
        return new ArrayList<>(pedidos1.values());
    }
}
