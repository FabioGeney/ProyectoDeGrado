package com.proyecto.marketdillo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class ConfiguracionUsuarioFragment extends Fragment {

    private EditText nombre;
    private EditText apellido;
    private TextView correo;
    private EditText celular;
    private EditText documentoidentidad;
    private EditText fechanacimiento;
    private EditText direccion;


    public ConfiguracionUsuarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_configuracion_usuario, container, false);
        nombre = (EditText) root.findViewById(R.id.nnnnombre);
        apellido = (EditText) root.findViewById(R.id.aaaapellido);
        correo = (TextView) root.findViewById(R.id.correoespacio);
        celular = (EditText) root.findViewById(R.id.celuco);
        documentoidentidad = (EditText) root.findViewById(R.id.identitydocument);
        fechanacimiento = (EditText) root.findViewById(R.id.birth);
        direccion = (EditText) root.findViewById(R.id.address);
        cargar();
        return root ;
    }

    public void cargar(){
        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
        Usuario usuario = singletonUsuario.getUsuario();
        nombre.setText(usuario.getNombre());
        apellido.setText(usuario.getApellidos());
        correo.setText(usuario.getEmail());
        celular.setText(usuario.getCelular());
        documentoidentidad.setText(usuario.getDoc_identidad());
        fechanacimiento.setText(usuario.getFecha());
        direccion.setText(usuario.getDireccion());
    }

}
