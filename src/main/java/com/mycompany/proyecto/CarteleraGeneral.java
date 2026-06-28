package com.mycompany.proyecto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CarteleraGeneral {

    public String nombrePelicula;
    public int sala;
    public String hora;
    public String fecha;

    public CarteleraGeneral(String nombrePelicula, int sala, String hora, String fecha){
        this.nombrePelicula = nombrePelicula;
        this.sala = sala;
        this.hora = hora;
        this.fecha = fecha;
    }

    public static void swap (CarteleraGeneral[] arr, int i, int j){
        CarteleraGeneral temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int partition(CarteleraGeneral[] a, int start, int end, boolean ascendente, boolean porFecha){
        CarteleraGeneral pivot = a[end];
        int pIndex = start;
        for (int i = start; i < end; i++){
            if(ascendente){
                if(clave(a[i], porFecha).compareTo(clave(pivot, porFecha)) <= 0){ // Compara las Strings para hacer los intercambios
                    swap(a, i, pIndex);
                    pIndex++;
                }
            }
            else{
                if(clave(a[i], porFecha).compareTo(clave(pivot, porFecha)) >= 0){
                    swap(a, i, pIndex);
                    pIndex++;
                }
            }
        }
        swap(a, end, pIndex);
        return pIndex;
    }

    public static void quicksort(CarteleraGeneral[] a, int start, int end, boolean ascendente, boolean porFecha){
        if (start >= end) { // Condicion base
            return;
        }
        int pivot = partition(a, start, end, ascendente, porFecha);
        quicksort(a, start, pivot - 1, ascendente, porFecha);
        quicksort(a, pivot+1, end, ascendente, porFecha);
    }

    public static String clave(CarteleraGeneral c, boolean porFecha){
        if(porFecha){
            try{
                DateTimeFormatter fFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.parse(c.fecha, fFecha);
                LocalTime hora = LocalTime.parse(c.hora);

                return fecha.toString().replace("-", "") + hora.toString().replace(":", ""); // Retorna un String en el formato "yyyyMMddHHmm"
            } catch (Exception e){
                return "000000000000"; // Para casos de fecha invalida
            }
        }
        else{
            return c.nombrePelicula != null ? c.nombrePelicula : ""; // Si el nombre de la pelicula es null retorna "" para evitar un error
        }
    }

}
