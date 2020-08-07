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
        int speed = in.nextInt(); //vitesse max
        int lightCount = in.nextInt(); //nombre de feu

        //stockage des données des feux rouges distance + duration
        List<Integer> listdistance = new ArrayList();
        List<Integer> listduration = new ArrayList();        
        for (int i = 0; i < lightCount; i++) {
            int distance = in.nextInt();
            int duration = in.nextInt();
            listdistance.add(distance);
            listduration.add(duration);
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        double max = (double)(speed/3.6); // convertion vitesse de km/h à m/s
        /*
        On calcul la vitesse minimale pour passer au feu vert pour chaque feu (boucle for). 
        Si la vitesse minimale dépasse la vitesse maximale autorisé on réduit la vitesse maximale de façon à passer au feu vert suivant (2*duration)
        Lors du parcours de chaque feu, si il y a eu modification de la vitesse max alors on recommence ( boucle while )
        */
        boolean verif = true;
        while(verif){
            verif = false;
        
            for(int i = 0; i < lightCount; i++){
            
                double lastspeed = (double)listdistance.get(i)/listduration.get(i); // vitesse minimal pour passer au premier vert
                int n=2;
                double newlastspeed = lastspeed;
                while(newlastspeed >= max){ //tant que vitesse minimal >= vitesse max, on calcul la vitesse minimal suivante
                    newlastspeed = (double)listdistance.get(i)/(listduration.get(i)*n);
                    if(n % 2 == 0 && newlastspeed < max){ //si newlastspeed < max et le feu est vert on enregistre le nouveau max
                       max = newlastspeed;
                       verif = true;
                    }
                    n = n+1;
                }//while
            }
            //permet de résoudre les problèmes de convertion car l'énoncé demande une vitesse en km/h entière
            if(max*3.6 - (int)(max*3.6) < 0.0001){
                continue;
            }else if(max*3.6 - (int)(max*3.6) > 0.999){
                continue;
            }else{
                max = (double)(((int)(max*3.6))/3.6);
                System.err.println("max "+max);
            }
        }
        //résultat convertie en km/h
        if(max*3.6 - (int)(max*3.6) < 0.0001){
            System.out.println((int)(max*3.6));
        }else if(max*3.6 - (int)(max*3.6) > 0.999){
            System.out.println(Math.round(max*3.6));
        }else{
            System.out.println((int)(max*3.6));
        }
    }
}
