package com.mycompany.proyecto;

public class ColaDulceria {
    NodoCola cabeza;
    NodoCola fin;
    
    public ColaDulceria(){
        this.cabeza = null;
        this.fin = null;
    }
    
    public void push(int combo){
        NodoCola nuevo = new NodoCola(combo);
        if(cabeza == null){
            cabeza = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
    }
    
    public int pop(){
        if(estaVacia()){
            System.out.println("No hay pedidos en cola.");
            return -1;
        }
        int combo = cabeza.combo;
        cabeza = cabeza.siguiente;
        if(cabeza == null){
            fin = null;
        }
        return combo;
    }
    
    public boolean estaVacia(){
        return cabeza == null;
    }
    
    public void mostrar(){
        if(estaVacia()){
            System.out.println("No hay pedidos en cola.");
            return;
        }
        NodoCola actual = cabeza;
        int i = 1;
        while(actual != null){// Recorre la linked list para obtener su tamaño
            System.out.println(i + ". Combo " + actual.combo);
            actual = actual.siguiente;
            i++;
        }
    }



    public String[] getListaParaAdmin() {
        int tam = 0;
        NodoCola temp = cabeza;
        while (temp != null) { tam++; temp = temp.siguiente; }

        String[] lista = new String[tam];
        temp = cabeza;


        String[] nombres = {"Familiar", "Dúo", "Personal"};
        for (int i = 0; i < tam; i++) { // Recorre el nuevo array
            // A cada elemento del array le agrega el nombre del combo en base al numero del atributo combo en el nodo actual
            lista[i] = "Combo " + nombres[temp.combo - 1];
            temp = temp.siguiente;
        }
        return lista;
    }

}
