package cl.uchile.logaritmos.times;

import java.util.Random;

public class Utils {
    // Función que crea una permutacion aleatoria pi dado n
    public static Integer[] createN(int n) {
        // Creamos el espacio en memoria para el arreglo
        Integer[] pi = new Integer[n];

        // Asignamos valores ordenados al arreglo
        for (int i = 0; i < n; i++) {
            pi[i] = Integer.valueOf(i + 1);
        }

        // Desordenamos el arreglo
        Random rand = new Random();
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Integer temp = pi[i];
            pi[i] = pi[j];
            pi[j] = temp;
        }

        return pi;
    }

    // Funcion que crea una secuencia de busqueda con elementos alpha-probables
    public static Integer[] createC(Integer[] pi, int n, float alpha) {
        int expM = 28; // m es como minimo 2^28
        int m = 1 << expM;
        Integer[] c;

        float epsilon = 0.0000001f;
        if (Math.abs(alpha) < epsilon) {
            c = new Integer[m];
            // Caso Equiprobable
            int expN = 0;
            // Calculamos el exponente de N
            while (1 << expN < n) {
                expN++;
            }

            // Creamos phi que indica la cantidad de repeticiones de cada elemento i en pi
            // Y lo guardamos en C
            int potencia = expM - expN;
            int phi = 1<<potencia;

            int currentIndex = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < phi; j++) {
                    c[currentIndex] = pi[i];
                    currentIndex++;
                }
            }

        } else {
            // Primero calculamos el denominador de phi(i)
            double sum = 0;
            for (int i = 0; i < n; i++) {
                sum += Math.floor(Math.pow(i + 1, alpha));
            }

            int[] phis = new int[n];
            int lengthOfC = 0;
            // Calculamos phi(i) que indica la cantidad de repeticiones de cada elemento i de pi
            for (int i = 0; i < n; i++) {
                double fi = Math.floor(Math.pow(i + 1, alpha));
                int phi_i = (int) Math.ceil((m * fi) / sum);
                phis[i] = phi_i;
                lengthOfC+=phi_i;
            }
            c = new Integer[lengthOfC];
            // Llenamos C con los elementos de pi segun phi(i)
            int currentIndex = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < phis[i]; j++) {
                    if(currentIndex >= lengthOfC){
                        //throw new RuntimeException("Error en la creacion de C");
                        System.out.println("Error en la creacion de C");
                        break;
                    }
                    c[currentIndex] = pi[i];
                    currentIndex++;
                }
            }
        }

        // Desordenamos el arreglo para finalmente retornarlo
        Random rand = new Random();
        for (int i = c.length - 1; i > 0; i--) {
            int k = rand.nextInt(i + 1);
            Integer temp = c[i];
            c[i] = c[k];
            c[k] = temp;
        }

        return c;
    }


}
