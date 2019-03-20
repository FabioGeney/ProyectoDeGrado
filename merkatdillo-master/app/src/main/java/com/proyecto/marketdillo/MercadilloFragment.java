package com.proyecto.marketdillo;


import android.content.Intent;
import android.media.MediaCas;
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
 * Use the {@link MercadilloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MercadilloFragment extends Fragment {
    private ListView mercadilloList;
    private MercadilloAdapter mercadilloAdapter;
    private HashMap<String, Mercadillo> mercadillos1 = new HashMap<>();



    public MercadilloFragment() {
        // Required empty public constructor
    }


    public static MercadilloFragment newInstance(/*parámetros*/) {
        MercadilloFragment fragment = new MercadilloFragment();
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

        View root = inflater.inflate(R.layout.fragment_mercadillo, container, false);
        Mercadillo p = new Mercadillo("Mecadillo de frutas ", "50 min", 4, 4500, R.mipmap.ic_merca_image);
        saveMercadillo(p);
        saveMercadillo(new Mercadillo("Fruteria la 31 ", "40 min", 4, 4000, R.mipmap.ic_merca_image));
        // Instancia del ListView.
        mercadilloList = (ListView) root.findViewById(R.id.mercadillo_list);
        // Inicializar el adaptador con la fuente de datos.
        mercadilloAdapter = new MercadilloAdapter(getActivity(), getMercadillos());
        //Relacionando la lista con el adaptador
        mercadilloList.setAdapter(mercadilloAdapter);

        mercadilloList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VistaProductosMercadillo.class);
                startActivity(intent);
            }
        });
        return root;
    }
    private void saveMercadillo(Mercadillo mercadillo) {
        mercadillos1.put(mercadillo.getNombre(), mercadillo);
    }
    public List<Mercadillo> getMercadillos() {
        return new ArrayList<>(mercadillos1.values());
    }


}
