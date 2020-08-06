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
        int L = in.nextInt(); // Largeur des lettre ASCII
        int H = in.nextInt(); //Hauteur des lettre ASCII
        StringBuilder RES = new StringBuilder(); //Stockage résultats
        if (in.hasNextLine()) {
            in.nextLine();
        }
        String T = in.nextLine(); // mot à écrire en ASCII
        T = T.toUpperCase();
        String result="";
        //style de l'alphabet ASCII récupéré dans un String 
        for (int i = 0; i < H; i++) {
            String ROW = in.nextLine();
            result = result + ROW;
        }
        char[] c = T.toCharArray(); //convertion du string en vecteur de caractère 
        /* 
        Pour chaque caractère on récupère la position dans l'alphabet de la lettre allant de 0 à 25 et 26 si inconnue
        à l'aide de l'association caractère/nombre du système ASCII.
        La variable start détermine le point de départ ou l'on récupère la portion du String result qui correspond
        au char c[i]. 
        */
        for(int j =0; j < H; j++){
            for(int i = 0; i < c.length; i++){
                int position = c[i] < 65 || c[i] > 90 ? 26 : c[i] - 65;
                int start = (j * L * 27) + position*L;
            
                RES.append(result.substring(start,start+L));
            }
            RES.append("\n");

        }
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        System.out.println(RES);
    }
}
