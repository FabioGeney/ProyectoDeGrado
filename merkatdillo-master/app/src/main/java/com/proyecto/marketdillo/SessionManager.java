package com.proyecto.marketdillo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String TIPOUSUARIO = "TIPOUSUARIO";
    public static final String USUARIO = "SUARIO";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }
    public void createSession (Usuario usuario, String tipoUsuario){
        Gson gson = new Gson();
        editor.putBoolean(LOGIN, true);
        editor.putString(USUARIO, gson.toJson(usuario));
        editor.putString(TIPOUSUARIO, tipoUsuario);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public  String getTipousuario(){
        return sharedPreferences.getString(TIPOUSUARIO, null);
    }
    public  String getUsuario(){
        return sharedPreferences.getString(USUARIO, null);
    }

    public void checkLogin(){
        if(this.isLoggin()){
            if(getTipousuario().equals("campesino")){
                Intent i = new Intent(context, VistaCampesino.class);
                context.startActivity(i);
                ((BottomActivity)context).finish();
            }else {
                Intent i = new Intent(context, VistaUsuarios.class);
                context.startActivity(i);
                ((BottomActivity)context).finish();

            }
        }
    }
    /*
    public HashMap<String, String> getUserDetail () {
        HashMap<String, String> user = new HashMap<>();
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        return user;
    }*/

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, BottomActivity.class);
        context.startActivity(i);
    }

}
