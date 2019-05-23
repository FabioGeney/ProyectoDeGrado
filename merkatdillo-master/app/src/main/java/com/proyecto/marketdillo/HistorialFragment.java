package com.proyecto.marketdillo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistorialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter historialAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Pedidos> historialList;



    public HistorialFragment() {
        // Required empty public constructor
    }


    public static HistorialFragment newInstance(/*parámetros*/) {
        HistorialFragment fragment = new HistorialFragment();
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

        View root = inflater.inflate(R.layout.fragment_historial, container, false);

        historialList = getHistorial();
        layoutManager = new LinearLayoutManager(getContext());
        // Instancia del ListView.
        mRecyclerView = (RecyclerView) root.findViewById(R.id.historial_recycler);

        historialAdapter = new HistorialAdapter(historialList, R.layout.list_item_mercadillo, new HistorialAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Pedidos pedidos, int posicion) {
            }
        });

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(historialAdapter);


        return root;

    }
    private ArrayList<Pedidos> getHistorial() {
        ArrayList<Pedidos> pedidos = new ArrayList<>();

        return pedidos;
    }

}
