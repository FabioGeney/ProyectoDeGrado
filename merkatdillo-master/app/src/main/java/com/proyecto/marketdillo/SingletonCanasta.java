package com.proyecto.marketdillo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SingletonCanasta {

    private static SingletonCanasta singletonCanasta = new SingletonCanasta();
    public static SingletonCanasta getInstance(){
        return singletonCanasta;
    }

    public static void setSingletonCanasta(SingletonCanasta singletonUsuario){
        SingletonCanasta.singletonCanasta = singletonUsuario;
    }

    private CanastaClass canasta;


    private SingletonCanasta(){

    }

    public void setCanasta(CanastaClass canasta){
        this.canasta = canasta;
    }

    public CanastaClass getCanasta(){
        return canasta;
    }

}
