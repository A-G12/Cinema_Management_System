package com.mycompany.proyecto;

public class PilaHistorial {
    private String[] historial;
    private int tope;
    
    public PilaHistorial(){
        this.historial = new String[50];
        this.tope = -1;
    }
    
    public void push(String nombrePelicula){
        if(tope >= 49){// Valida que el historial no este lleno
            return;
        }
        tope++;
        historial[tope] = nombrePelicula;
    }
    
    public String pop(){
        if(estaVacia()){
            return null;
        }
        String nombre = historial[tope]; // Guarda el nombre de la ultima consulta
        historial[tope] = null;
        tope--;
        return nombre;
    }
    
    public boolean estaVacia(){
        return tope == -1;
    }
    





}
