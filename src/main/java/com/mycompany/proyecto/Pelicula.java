package com.mycompany.proyecto;

public class Pelicula {
    public String nombre;
    public String director;
    public String productor;
   public int clasificacion;
    public int duracion;
   public int genero;
    public Horario[] horarios;
    public int totalHorarios;
    public int maxHorarios = 10; //ui
    
    public Pelicula(String nombre, String director, String productor, int clasificacion, int duracion, int genero){
        this.nombre = nombre;
        this.director = director;
        this.productor = productor;
        this.clasificacion = clasificacion;
        this.duracion = duracion;
        this.genero = genero;
        horarios = new Horario[10];
        totalHorarios = 0;
    }
    
    public boolean agregarHorario(int sala, String hora, String fecha){
        if(totalHorarios >= 10){
            return false; // Si hay mas de 10 horarios retorna false
        }
        horarios[totalHorarios++] = new Horario(sala, hora, fecha);
        return true; // Retorna true en caso de haberse agregado correctamente
    }


    public void eliminarHorario(int indice){
        for(int i = indice; i < totalHorarios-1; i++){ // Recorre datos desde el indice hasta el total de horarios registrados
            horarios[i] = horarios[i+1];
        }
        horarios[totalHorarios - 1] = null;
        totalHorarios--;
    }
    

    }

