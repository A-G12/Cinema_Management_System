package com.mycompany.proyecto;

public class NodoPelicula {
   public Pelicula pelicula;
    public NodoPelicula siguiente;
    
    public NodoPelicula(Pelicula pelicula){
        this.pelicula = pelicula;
        this.siguiente = null;
    }
}
