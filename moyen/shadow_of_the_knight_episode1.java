import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int W = in.nextInt(); // width of the building.
        int H = in.nextInt(); // height of the building.
        int N = in.nextInt(); // maximum number of turns before game over.
        int X0 = in.nextInt(); // position de départ X
        int Y0 = in.nextInt(); // position de départ Y
        int X = X0;
        int Y = Y0;
        //initialisation des variables pour la recherche dichotomique : largeur (g et d) et hauteur (h et b)
        int g = 0;
        int d = W;
        int h = 0;
        int b = H;
        // game loop
        while (true) {
            String bombDir = in.next(); // the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)
            switch (bombDir) {
		      case "U": b = Y - 1; Y = Y - 1 - (b - h)/2;  		         
			  break;
			  case "UR": b = Y - 1; g = X + 1; X = X + 1 + (d - g)/2; Y = Y - 1 - (b - h)/2;
			  break;
			  case "R": g = X + 1; X = X + 1 + (d - g)/2;
			  break;
			  case "DR": h = Y + 1; g = X + 1; X = X + 1 + (d - g)/2; Y = Y + 1 + (b - h)/2; 
			  break;
			  case "D": h = Y + 1; Y = Y + 1 + (b - h)/2;
			  break;
			  case "DL": h = Y + 1; d = X - 1; X = X - 1 - (d - g)/2; Y = Y + 1 + (b - h)/2;
			  break;
			  case "L": d = X - 1; X = X - 1 - (d - g)/2;
			  break;
              default: b = Y - 1; d = X - 1; X = X - 1 - (d - g)/2; Y = Y - 1 - (b - h)/2;
              break;
		    }
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // the location of the next window Batman should jump to.
            System.out.println(X+" "+Y);
        }
    }
}
