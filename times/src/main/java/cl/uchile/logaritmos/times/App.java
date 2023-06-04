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
        while(expN <= 20){ // n=2^20 == 1<<20
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

                for(int reps = 1; reps<=3; reps++){
                    // Creamos un arreglo c con m elementos (m=2^28) con elementos repetidos segun alpha
                    Integer[] c = Utils.createC(pi, n, alphas[a]);

                    // Buscamos los elementos de c en el Splay Tree y medimos su tiempo
                    long startTime = System.currentTimeMillis();

                    for (int i = 0; i < c.length; i++) {
                        splayTree.find(c[i]);
                    }

                    long endTime = System.currentTimeMillis();
                    long parcialTime = endTime - startTime;
                    totalTimeST += parcialTime;
                    partialTimesST[reps-1] = parcialTime;

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

                long averageTimeST = totalTimeST/5;
                long varianceST = 0;
                for(int i=0; i<5; i++){
                    varianceST += Math.pow(partialTimesST[i]-averageTimeST, 2);
                }
                varianceST /= 5;
                long standardDeviationST = (long) Math.sqrt(varianceST);
                System.out.println("expN= "+ expN +" | alpha= "+ alphas[a]+ " | Tiempo promedio de busqueda en Splay Tree: " + averageTimeST + " ms");
                System.out.println("expN= "+ expN +" | alpha= "+ alphas[a]+ " | Varianza en tiempo de busqueda en Splay Tree: " + varianceST + " ms^2");
                System.out.println("expN= "+ expN +" | alpha= "+ alphas[a]+ " | Desviacion estandar de busqueda en Splay Tree: " + standardDeviationST + " ms");

                long averageTimeRBT = totalTimeRBT/5;
                long varianceRBT = 0;
                for(int i=0; i<5; i++){
                    varianceRBT += Math.pow(partialTimesRBT[i]-averageTimeRBT, 2);
                }
                varianceRBT /= 5;
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
