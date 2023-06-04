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
        int expM = 28; // m es siempre 2^28
        int m = 1 << expM;
        Integer[] c = new Integer[m];

        float epsilon = 0.0000001f;
        if (Math.abs(alpha) < epsilon) {
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
                sum += Math.ceil(Math.pow(i + 1, alpha));
            }

            // Creamos phi que indica la cantidad de repeticiones de cada elemento i en pi
            // Y lo guardamos en C
            int currentIndex = 0;
            for (int i = 0; i < n; i++) {
                double fi = Math.ceil(Math.pow(i + 1, alpha));
                int phi = (int) Math.ceil((m * fi) / sum);
                for (int j = 0; j < phi; j++) {
                    if(currentIndex >= m){
                       break;
                    }
                    c[currentIndex] = pi[i];
                    currentIndex++;
                }
            }
            if(currentIndex < m){
                throw new RuntimeException("Terminando el programa debido a una excepción.");
            }
        }

        // Desordenamos el arreglo para finalmente retornarlo
        Random rand = new Random();
        for (int i = m - 1; i > 0; i--) {
            int k = rand.nextInt(i + 1);
            Integer temp = c[i];
            c[i] = c[k];
            c[k] = temp;
        }

        return c;
    }


}
