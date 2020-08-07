import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); //nombre de valeur totale
        //récupération des différentes valeurs
        int[] courbe = new int[n];
        for (int i = 0; i < n; i++) {
            courbe[i] = in.nextInt();
        }

        //initialisation max,min et dif (min - max)
        int dif = 0;
        int max = courbe[0];
        int min = courbe[0];

        /*
        On calcule la perte maximale entre 2 points de la courbe. 
        On fixe le point maximal et on fait varier le point minimal tant que celui-ci est plus petit que le max et le min pour les points suivants.
        Si le point suivant dépasse le max on réinitialise les points min/max à la valeur du point actuel et on calcule la différence min - max avant la réinitialisation.
        */
        for (int i = 1; i < n; i++) {
            if( courbe[i] <= max ){
                if(courbe[i] < min){
                    min = courbe[i];
                }
                if(i == n-1 && min - max < dif){
                    dif = min - max;
                }
            }else{
                if(min - max < dif){
                    dif = min - max;
                }
                max = courbe[i];
                min = courbe[i];             
            }
        }
        
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(dif);
    }
}
