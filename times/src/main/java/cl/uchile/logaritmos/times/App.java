package cl.uchile.logaritmos.times;


import cl.uchile.logaritmos.rbt.RedBlackTree;
import cl.uchile.logaritmos.st.SplayTree;

/**
 * Tarea 2
 * Implementacion de los arboles de busqueda binaria Splay Tree y Red-Black Tree
 */
public class App
{
    public static void main( String[] args )
    {
        float[] alphas = {0.0f, 0.5f, 1.0f, 1.5f}; // equiprobables, 50% de exito, 100% de exito, 150% de exito
        int expN = 16; //  n=2^16 == 1<<16
        while(expN <= 24){ // n=2^20 == 1<<20
            // Creamos un arreglo pi con n elementos
            int n = 1 << expN;
            Integer[] pi = Utils.createN(n);

            // Insertamos los elementos de pi en un Splay Tree
            SplayTree<Integer> splayTree = new SplayTree<>();
            for (int i = 0; i < n; i++) {
                splayTree.insert(pi[i]);
            }

            // Insertar los elementos de pi en un Red-Black Tree
            RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
            for (int i = 0; i < n; i++) {
                redBlackTree.insert(pi[i]);
            }

            for(int a=0; a<=3; a++){
                long totalTimeST = 0;
                long[] partialTimesST = new long[5];

                long totalTimeRBT = 0;
                long[] partialTimesRBT = new long[5];

                // Creamos un arreglo c con m elementos a buscar
                Integer[] c = Utils.createC(pi, n, alphas[a]);

                // Obtener la instancia de Runtime
                Runtime runtime = Runtime.getRuntime();

                // Calcular el espacio ocupado
                long totalMemory = runtime.totalMemory();
                long freeMemory = runtime.freeMemory();
                long usedMemory = totalMemory - freeMemory;

                System.out.println("Espacio ocupado: " + usedMemory + " bytes");

                for(int reps = 1; reps<=3; reps++){
                    // Buscamos los elementos de c en el Splay Tree y medimos su tiempo
                    long startTime = System.currentTimeMillis();

                    for (int i = 0; i < c.length; i++) {
                        splayTree.find(c[i]);
                    }

                    long endTime = System.currentTimeMillis();
                    long parcialTime = endTime - startTime;
                    totalTimeST += parcialTime;
                    partialTimesST[reps-1] = parcialTime;

                    System.out.println("-------------------------------------------");

                    System.out.println("expN= "+ expN +" | alpha= "+ alphas[a]+ " | Tiempo de busqueda en Splay Tree: " + parcialTime + " ms");

                    // Buscamos los elementos de c en el Red-Black Tree y medimos su tiempo
                    startTime = System.currentTimeMillis();

                    for (int i = 0; i < c.length; i++) {
                        redBlackTree.find(c[i]);
                    }

                    endTime = System.currentTimeMillis();
                    parcialTime = endTime - startTime;
                    totalTimeRBT += parcialTime;
                    partialTimesRBT[reps-1] = parcialTime;

                    System.out.println("expN= "+ expN +" | alpha= "+ alphas[a]+ " | Tiempo de busqueda en Red-Black Tree: " + parcialTime + " ms");
                }
                System.out.println("====================================================================================================");

                long averageTimeST = totalTimeST/3;
                long varianceST = 0;
                for(int i=0; i<3; i++){
                    varianceST += Math.pow(partialTimesST[i]-averageTimeST, 2);
                }
                varianceST /= 3;
                long standardDeviationST = (long) Math.sqrt(varianceST);
                System.out.println("expN= "+ expN +" | alpha= "+ alphas[a]+ " | Tiempo promedio de busqueda en Splay Tree: " + averageTimeST + " ms");
                System.out.println("expN= "+ expN +" | alpha= "+ alphas[a]+ " | Varianza en tiempo de busqueda en Splay Tree: " + varianceST + " ms^2");
                System.out.println("expN= "+ expN +" | alpha= "+ alphas[a]+ " | Desviacion estandar de busqueda en Splay Tree: " + standardDeviationST + " ms");

                System.out.println("-------------------------------------------");


                long averageTimeRBT = totalTimeRBT/3;
                long varianceRBT = 0;
                for(int i=0; i<3; i++){
                    varianceRBT += Math.pow(partialTimesRBT[i]-averageTimeRBT, 2);
                }
                varianceRBT /= 3;
                long standardDeviationRBT = (long) Math.sqrt(varianceRBT);
                System.out.println("expN= "+ expN +" | alpha= "+ alphas[a]+ " | Tiempo promedio de busqueda en Red-Black Tree: " + averageTimeRBT + " ms");
                System.out.println("expN= "+ expN +" | alpha= "+ alphas[a]+ " | Varianza en tiempo de busqueda en Red-Black Tree: " + varianceRBT + " ms^2");
                System.out.println("expN= "+ expN +" | alpha= "+ alphas[a]+ " | Desviacion estandar de busqueda en Red-Black Tree: " + standardDeviationRBT + " ms");
                System.out.println("====================================================================================================");
            }

            expN++;
        }
        
    }
}
