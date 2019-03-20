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
 * {@link HistorialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialFragment extends Fragment {
    private ListView historialList;
    private HistorialAdapter historialAdapter;
    private HashMap<String, Historial> historial1 = new HashMap<>();



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

        saveHistorial(new Historial("Mercadillo de frutas", "30 de agosto,2018", R.mipmap.ic_merca_image));
        // Instancia del ListView.
        historialList = (ListView) root.findViewById(R.id.historial_list);
        // Inicializar el adaptador con la fuente de datos.
        historialAdapter = new HistorialAdapter(getActivity(), getHistorial());
        //Relacionando la lista con el adaptador
        historialList.setAdapter(historialAdapter);

        historialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), VistaProductosMercadillo.class);
                startActivity(intent);
            }
        });
        return root;
    }
    private void saveHistorial(Historial historial) {
        historial1.put(historial.getNombreMercadillo(), historial);

    }
    public List<Historial> getHistorial() {
        return new ArrayList<>(historial1.values());
    }
}
