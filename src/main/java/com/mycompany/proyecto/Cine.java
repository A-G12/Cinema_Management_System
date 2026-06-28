package com.mycompany.proyecto;

import java.io.*; // inputoutput

public class Cine {
    public String idMunicipio;
    public String idEstado;
    public ListaPeliculas peliculas;
    public ColaDulceria colaDulceria;
    public PilaHistorial historial;

    public Cine(String idEstado, String idMunicipio) {
        this.idMunicipio = idMunicipio;
        this.idEstado = idEstado;
        this.peliculas = new ListaPeliculas();
        this.colaDulceria = new ColaDulceria();
        this.historial = new PilaHistorial();
        cargarDatos();
    }

    public void cargarDatos() { // java.io
        this.peliculas = new ListaPeliculas();

        String nombreArchivo = idEstado + "_" + idMunicipio + ".txt";
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            Pelicula peliculaActual = null;
            while ((linea = br.readLine()) != null) {
                if (linea.equals("PELICULA")) {
                    String nom = br.readLine();
                    String dir = br.readLine();
                    String prod = br.readLine();
                    int clas = Integer.parseInt(br.readLine());
                    int dur = Integer.parseInt(br.readLine());
                    int gen = Integer.parseInt(br.readLine());

                    peliculaActual = new Pelicula(nom, dir, prod, clas, dur, gen);
                    peliculas.agregar(peliculaActual);
                } else if (linea.equals("HORARIO")) {
                    int sala = Integer.parseInt(br.readLine());
                    String hora = br.readLine();
                    String fecha = br.readLine();
                    if (peliculaActual != null) {
                        peliculaActual.agregarHorario(sala, hora, fecha);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar: " + e.getMessage());
        }
    }

    public void guardarDatos() {
        String nombreArchivo = idEstado + "_" + idMunicipio + ".txt";
        try (PrintWriter f = new PrintWriter(new FileWriter(nombreArchivo, false))) {
            NodoPelicula nodoActual = peliculas.cabeza;
            while (nodoActual != null) {
                Pelicula p = nodoActual.pelicula;
                f.println("PELICULA");
                f.println(p.nombre);
                f.println(p.director);
                f.println(p.productor);
                f.println(p.clasificacion);
                f.println(p.duracion);
                f.println(p.genero);

                for (int i = 0; i < p.totalHorarios; i++) {
                    f.println("HORARIO");
                    f.println(p.horarios[i].sala);
                    f.println(p.horarios[i].hora);
                    f.println(p.horarios[i].fecha);
                }
                nodoActual = nodoActual.siguiente;
            }
            f.flush();
        } catch (IOException e) {
            System.err.println("Error critico al guardar: " + e.getMessage());
        }
    }

    public void altaPeliculavisual(String nom, String dir, String prod, int clasif, int dur, int gen) {
        if (buscarNombre(nom) != null) { // Valida que no exista una pelicula con el mismo nombre
            return;
        }

        Pelicula nueva = new Pelicula(nom, dir, prod, clasif, dur, gen);
        peliculas.agregar(nueva);// Agrega el objeto a la lista de peliculas
        guardarDatos();
    }

    public Pelicula buscarNombre(String nombre) {
        return peliculas.busquedaBinaria(nombre); // Accede al array de peliculas y busca si existe
    }


    public boolean solapamiento(int sala, int duracion, String fecha, String hora){
        NodoPelicula nodoActual = peliculas.cabeza;

        while(nodoActual != null){
            Pelicula peliculaActual = nodoActual.pelicula;
            for(int i = 0; i<peliculaActual.totalHorarios; i++){ // Itera entre los horarios de las peliculas
                if(sala == peliculaActual.horarios[i].sala && fecha.equalsIgnoreCase(peliculaActual.horarios[i].fecha)){ // Busca que tengan la misma sala y fecha
                    // Suma los minutos de la hora que inicia la pelicula que se esta ciclando
                    int inicioAntiguo = Integer.parseInt(peliculaActual.horarios[i].hora.split(":")[0])*60 + Integer.parseInt(peliculaActual.horarios[i].hora.split(":")[1]);
                    int finAntiguo = inicioAntiguo + peliculaActual.duracion + 30; // Suma los minutos de la hora de la pelicula, su duracion y los 30 extras
                    int inicioNuevo = Integer.parseInt(hora.split(":")[0])*60 + Integer.parseInt(hora.split(":")[1]);
                    int finNuevo = inicioNuevo + duracion + 30;

                    if(inicioNuevo< finAntiguo && inicioAntiguo < finNuevo){ // Valida que no choquen o se solapen, incluyendo los 30 minutos
                        return true; // Si se solapan retorna true
                    }
                }
            }
            nodoActual = nodoActual.siguiente;
        }
        return false;
    }

    public void pedirCombo(int combo) {
        colaDulceria.push(combo);

    }

    public int despacharCombo() {
        return colaDulceria.pop();

    }



    public void agregarHistorial(String nombre) {
        historial.push(nombre);

    }

    public void bajaPelicula(String nombre) {
        peliculas.eliminar(nombre);
        guardarDatos();

    }

    public String getIdEstado() {
           return idEstado; }

    public String getIdMunicipio() {
           return idMunicipio; }

    public boolean altaHorario(String nombre, int sala, String fecha, String hora) {
        Pelicula pelicula = buscarNombre(nombre);
        if (pelicula == null) return false;// Valida que exista
        // Verificamos solapamiento antes de agregar
        if (solapamiento(sala, pelicula.duracion, fecha, hora)) return false;
        if (pelicula.agregarHorario(sala, hora, fecha)) {
            guardarDatos();
            return true;

        }
        return false;

    }

}
