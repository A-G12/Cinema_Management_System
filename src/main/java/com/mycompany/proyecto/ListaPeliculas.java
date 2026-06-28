package com.mycompany.proyecto;

public class ListaPeliculas {
    public NodoPelicula cabeza;
    public int tamanio;
    
    public ListaPeliculas(){
        this.cabeza = null;
        this.tamanio = 0;
    }
    
    public void agregar(Pelicula pelicula){
        NodoPelicula nuevo = new NodoPelicula(pelicula);
        if(cabeza == null){
            cabeza = nuevo;
        } else {
            NodoPelicula actual = cabeza;
            while(actual.siguiente != null){
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamanio++;
    }
    
    public boolean eliminar(String nombre){
        if(cabeza == null) return false;
        
        // Caso: es la cabeza
        if(cabeza.pelicula.nombre.equalsIgnoreCase(nombre)){
            cabeza = cabeza.siguiente;
            tamanio--;
            return true;
        }
        
        // Caso: está en medio o al final
        NodoPelicula actual = cabeza;
        while(actual.siguiente != null){
            if(actual.siguiente.pelicula.nombre.equalsIgnoreCase(nombre)){
                actual.siguiente = actual.siguiente.siguiente;
                tamanio--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }
    
    public Pelicula[] toArray(){// Copia los elementos de lista peliculas a un array
        Pelicula[] arr = new Pelicula[tamanio];
        NodoPelicula actual = cabeza;
        int i = 0;
        while(actual != null){
            arr[i] = actual.pelicula;
            actual = actual.siguiente;
            i++;
        }
        return arr;
    }
    
    // En ListaPeliculas busqueda binaria
    public Pelicula busquedaBinaria(String nombre){
        Pelicula[] arr = toArray();

        // ordenar por nombre con quicksort
        quickSortNombre(arr, 0, arr.length - 1);

        // busqueda binaria
        int inicio = 0;
        int fin = arr.length - 1;

        while(inicio <= fin){
            int medio = (inicio + fin) / 2;
            int comparacion = arr[medio].nombre.compareToIgnoreCase(nombre);

            if(comparacion == 0){
                return arr[medio]; // encontro
            } else if(comparacion < 0){
                inicio = medio + 1; // buscar en la derecha
            } else {
                fin = medio - 1; // buscar en la izquierda
            }
        }
        return null; // no encontro
    }

    // QuickSort de Pelicula[] por nombre
    private void quickSortNombre(Pelicula[] arr, int inicio, int fin){
        if(inicio >= fin) return;
        int pivot = partitionNombre(arr, inicio, fin);
        quickSortNombre(arr, inicio, pivot - 1);
        quickSortNombre(arr, pivot + 1, fin);
    }

    private int partitionNombre(Pelicula[] arr, int inicio, int fin){
        Pelicula pivot = arr[fin];
        int pIndex = inicio;
        for(int i = inicio; i < fin; i++){
            if(arr[i].nombre.compareToIgnoreCase(pivot.nombre) <= 0){
                Pelicula temp = arr[i];
                arr[i] = arr[pIndex];
                arr[pIndex] = temp;
                pIndex++;
            }
        }
        Pelicula temp = arr[fin];
        arr[fin] = arr[pIndex];
        arr[pIndex] = temp;
        return pIndex;
    }
    
}
