package com.mycompany.proyecto;
import java.util.Arrays;

public class GestionCines {
    public Cine[] cines;
    public int[][] distanciasJalisco    = new int[10][10];
    public int[][] distanciasNuevoLeon  = new int[10][10];
    public int[][] distanciasEdoMex     = new int[10][10];
    public int[][] distanciasChihuahua  = new int[10][10];
   public int[][] distanciasSinaloa    = new int[10][10];
    
    public GestionCines(){
        int INF = 999999999;
        cines = new Cine[50];
        cines[0] = new Cine("J11", "A");
        cines[1] = new Cine("J11", "B");
        cines[2] = new Cine("J11", "C");
        cines[3] = new Cine("J11", "D");
        cines[4] = new Cine("J11", "E");
        cines[5] = new Cine("J11", "F");
        cines[6] = new Cine("J11", "G");
        cines[7] = new Cine("J11", "H");
        cines[8] = new Cine("J11", "I");
        cines[9] = new Cine("J11", "J");
        cines[10] = new Cine("N11", "A");
        cines[11] = new Cine("N11", "B");
        cines[12] = new Cine("N11", "C");
        cines[13] = new Cine("N11", "D");
        cines[14] = new Cine("N11", "E");
        cines[15] = new Cine("N11", "F");
        cines[16] = new Cine("N11", "G");
        cines[17] = new Cine("N11", "H");
        cines[18] = new Cine("N11", "I");
        cines[19] = new Cine("N11", "J");
        cines[20] = new Cine("E11", "A");
        cines[21] = new Cine("E11", "B");
        cines[22] = new Cine("E11", "C");
        cines[23] = new Cine("E11", "D");
        cines[24] = new Cine("E11", "E");
        cines[25] = new Cine("E11", "F");
        cines[26] = new Cine("E11", "G");
        cines[27] = new Cine("E11", "H");
        cines[28] = new Cine("E11", "I");
        cines[29] = new Cine("E11", "J");
        cines[30] = new Cine("C11", "A");
        cines[31] = new Cine("C11", "B");
        cines[32] = new Cine("C11", "C");
        cines[33] = new Cine("C11", "D");
        cines[34] = new Cine("C11", "E");
        cines[35] = new Cine("C11", "F");
        cines[36] = new Cine("C11", "G");
        cines[37] = new Cine("C11", "H");
        cines[38] = new Cine("C11", "I");
        cines[39] = new Cine("C11", "J");
        cines[40] = new Cine("S11", "A");
        cines[41] = new Cine("S11", "B");
        cines[42] = new Cine("S11", "C");
        cines[43] = new Cine("S11", "D");
        cines[44] = new Cine("S11", "E");
        cines[45] = new Cine("S11", "F");
        cines[46] = new Cine("S11", "G");
        cines[47] = new Cine("S11", "H");
        cines[48] = new Cine("S11", "I");
        cines[49] = new Cine("S11", "J");

        // Inicializacion de matrices de distancias
        // Kilometros entre uno y otro
        distanciasJalisco = new int[][]{
            {0,   7,   6,   13,  35,  20,  160, 60,  90,  340},
            {7,   0,   8,   INF, INF, INF, INF, INF, INF, INF},
            {6,   8,   0,   7,   INF, INF, INF, INF, INF, INF},
            {13,  INF, 7,   0,   25,  INF, INF, INF, INF, INF},
            {35,  INF, INF, 25,  0,   INF, INF, INF, 55,  INF},
            {20,  INF, INF, INF, INF, 0,   INF, INF, 70,  INF},
            {160, INF, INF, INF, INF, INF, 0,   INF, 40,  INF},
            {60,  INF, INF, INF, INF, INF, INF, 0,   INF, INF},
            {90,  INF, INF, INF, 55,  70,  40,  INF, 0,   INF},
            {340, INF, INF, INF, INF, INF, INF, INF, INF, 0  }
        };
        distanciasNuevoLeon = new int[][]{
            {0,   INF, 15,  INF, INF, INF, INF, 10,  INF, INF},
            {INF, 0,   INF, INF, INF, INF, INF, 85,  INF, INF},
            {15,  INF, 0,   INF, INF, INF, INF, 20,  INF, INF},
            {INF, INF, INF, 0,   INF, INF, INF, 45,  INF, 20 },
            {INF, INF, INF, INF, 0,   INF, INF, 180, INF, INF},
            {INF, INF, INF, INF, INF, 0,   INF, 130, INF, INF},
            {INF, INF, INF, INF, INF, INF, 0,   25,  INF, INF},
            {10,  85,  20,  45,  180, 130, 25,  0,   35,  40 },
            {INF, INF, INF, INF, INF, INF, INF, 35,  0,   15 },
            {INF, INF, INF, 20,  INF, INF, INF, 40,  15,  0  }
        };
        distanciasEdoMex = new int[][]{
            {0,   INF, INF, 15,  INF, 20,  25,  INF, INF, INF},
            {INF, 0,   INF, INF, INF, INF, INF, INF, 25,  INF},
            {INF, INF, 0,   INF, INF, INF, INF, INF, INF, 80 },
            {15,  INF, INF, 0,   INF, INF, 10,  INF, INF, INF},
            {INF, INF, INF, INF, 0,   INF, INF, INF, INF, 40 },
            {20,  INF, INF, INF, INF, 0,   INF, INF, 30,  60 },
            {25,  INF, INF, 10,  INF, INF, 0,   INF, INF, INF},
            {INF, INF, INF, INF, INF, INF, INF, 0,   INF, 50 },
            {INF, 25,  INF, INF, INF, 30,  INF, INF, 0,   INF},
            {INF, INF, 80,  INF, 40,  60,  INF, 50,  INF, 0  }
        };
        distanciasChihuahua = new int[][]{
            {0,   200, INF, INF, INF, INF, INF, INF, INF, INF},
            {200, 0,   150, INF, INF, INF, INF, INF, INF, INF},
            {INF, 150, 0,   INF, INF, INF, INF, INF, 100, INF},
            {INF, INF, INF, 0,   INF, INF, INF, 120, 180, INF},
            {INF, INF, INF, INF, 0,   INF, 250, INF, INF, 200},
            {INF, INF, INF, INF, INF, 0,   INF, 60,  120, INF},
            {INF, INF, INF, INF, 250, INF, 0,   INF, INF, INF},
            {INF, INF, INF, 120, INF, 60,  INF, 0,   90,  INF},
            {INF, INF, 100, 180, INF, 120, INF, 90,  0,   INF},
            {INF, INF, INF, INF, 200, INF, INF, INF, INF, 0  }
        };
        // VALORES NUEVOS
        // 5=Culiacán, 6=Mazatlán, 7=Los Mochis, 8=Guamúchil, 9=El Fuerte
        distanciasSinaloa = new int[][]{
            {0,   INF, INF, 50,  INF, INF, INF, 80,  45,  INF},
            {INF, 0,   120, INF, INF, 30,  INF, INF, INF, INF},
            {INF, 120, 0,   INF, INF, 150, INF, INF, INF, INF},
            {50,  INF, INF, 0,   30,  INF, INF, INF, 40,  INF},
            {INF, INF, INF, 30,  0,   INF, INF, INF, 35,  INF},
            {INF, 30,  150, INF, INF, 0,   220, INF, INF, INF},
            {INF, INF, INF, INF, INF, 220, 0,   INF, INF, INF},
            {80,  INF, INF, INF, INF, INF, INF, 0,   INF, 80 },
            {45,  INF, INF, 40,  35,  INF, INF, INF, 0,   INF},
            {INF, INF, INF, INF, INF, INF, INF, 80,  INF, 0  }
        };
        
    }

    public Cine buscarCine(String idEstado, String idMunicipio){
        for(int i = 0; i < cines.length; i++){
            if(cines[i] != null &&
                    cines[i].getIdEstado().equals(idEstado) &&
                    cines[i].getIdMunicipio().equals(idMunicipio)) {

                return cines[i];
            }
        }
        return null;
    }

    public Cine seleccionar(String idEstado, String idMunicipio){
        Cine cineActual = buscarCine(idEstado, idMunicipio);
        if(cineActual != null){
            cineActual.cargarDatos(); // Carga el cine al que accede
        }
        return cineActual; // Retorna el cine encontrado para que se trabaje con el
    }


    public Cine cineMasCercano(Cine cineActual){
        int INF = 999999999;
        int[][] matriz;
        String idEstado = cineActual.getIdEstado();
        // Busca la matriz a utilizar

        switch(idEstado){
            case "J11": matriz = distanciasJalisco;    break;
            case "N11": matriz = distanciasNuevoLeon;  break;
            case "E11": matriz = distanciasEdoMex;     break;
            case "C11": matriz = distanciasChihuahua;  break;
            case "S11": matriz = distanciasSinaloa;    break;
            default: return null;
        }

        // aplicar Floyd
        int n = matriz.length;
        int[][] dist = new int[n][n];
        for(int i = 0; i < n; i++)
            dist[i] = Arrays.copyOf(matriz[i], n);

        for(int k = 0; k < n; k++)
            for(int i = 0; i < n; i++)
                for(int j = 0; j < n; j++)
                    if(dist[i][k] != INF && dist[k][j] != INF)
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);

        // encontrar el cine más cercano
        int indiceActual = cineActual.getIdMunicipio().charAt(0) - 'A';
        int minDist = INF;
        int indiceMasCercano = -1;

        for(int j = 0; j < n; j++){
            if(j != indiceActual && dist[indiceActual][j] < minDist){
                minDist = dist[indiceActual][j];
                indiceMasCercano = j;
            }
        }

        if(indiceMasCercano == -1){
            System.out.println("No se encontró un cine cercano.");
            return null;
        }

        // convertir índice de vuelta a idMunicipio
        String idMunicipioCercano = String.valueOf((char)('A' + indiceMasCercano));
        return buscarCine(idEstado, idMunicipioCercano);
    }
    
}
