package com.proyecto.marketdillo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button guardar;


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
        guardar = (Button) root.findViewById(R.id.guardaarr);
        cargar();
        nombre.addTextChangedListener(loginTextWatcher);
        apellido.addTextChangedListener(loginTextWatcher);
        celular.addTextChangedListener(loginTextWatcher);
        documentoidentidad.addTextChangedListener(loginTextWatcher);
        fechanacimiento.addTextChangedListener(loginTextWatcher);
        direccion.addTextChangedListener(loginTextWatcher);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

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

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nombre1 = nombre.getText().toString();
            String apellido1 = apellido.getText().toString();
            String celular1 = celular.getText().toString();
            String documentoidentidad1 = documentoidentidad.getText().toString();
            String fechanacimiento1 = fechanacimiento.getText().toString();
            String direccion1 = direccion.getText().toString();
            guardar.setEnabled(!nombre1.isEmpty() && !apellido1.isEmpty() && !celular1.isEmpty() && !documentoidentidad1.isEmpty() && !fechanacimiento1.isEmpty() && !direccion1.isEmpty());
            if(!nombre1.isEmpty() && !apellido1.isEmpty() && !celular1.isEmpty() && !documentoidentidad1.isEmpty() && !fechanacimiento1.isEmpty() && !direccion1.isEmpty()){
                guardar.setBackgroundColor(getResources().getColor(R.color.colorPrimary2));
            }else {
                guardar.setBackgroundColor(getResources().getColor(R.color.colorDivider));
                //guardar.setBackground(getResources().getDrawable(R.drawable.save_button));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!guardar.isEnabled()){
                guardar.setBackgroundColor(getResources().getColor(R.color.colorDivider));
            }else {
                guardar.setBackgroundColor(getResources().getColor(R.color.colorPrimary2));
            }
        }
    };

}
