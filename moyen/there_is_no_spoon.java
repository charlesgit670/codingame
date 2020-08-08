import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Don't let the machines win. You are humanity's last hope...
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt(); // the number of cells on the X axis
        int height = in.nextInt(); // the number of cells on the Y axis
        
        if (in.hasNextLine()) {
            in.nextLine();
        }

        //récupération des données de la grille de jeu
        List<String> grille = new ArrayList();
        for (int i = 0; i < height; i++) {
            String line = in.nextLine(); // width characters, each either 0 or .
            grille.add(line);
        }

        //affiche la largeur, hauteur et la grille de jeu
        System.err.println("width "+width+" height "+height);
        for(String str : grille){
            System.err.println(str);
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                
                Integer X1=null; Integer Y1=null; Integer X2=null; Integer Y2=null; Integer X3=null; Integer Y3=null;
                
                String line = grille.get(i);
                char cellule = line.charAt(j);
                
                // initialisation cellule origine
                if(cellule == '.'){
                    continue;
                }else{
                    X1 = j; Y1 = i;
                }
            
                //déterminer cellule de droite
                for(int k = X1+1; k < width; k++){
                    char celluledroite = line.charAt(k);
                    if(celluledroite =='.' && k == width-1){
                        X2 = -1; Y2 = -1;
                        continue;
                    }else if(celluledroite =='.'){
                        continue;
                    }else{
                        X2 = k; Y2 = i;
                        break;
                    }
                }
                
                //déterminer cellule du bas
                for(int k = Y1+1; k < height; k++){
                    String linebas = grille.get(k);
                    char cellulebas = linebas.charAt(j);
                    
                    if(cellulebas =='.' && k == height -1){
                        X3 = -1; Y3 = -1;
                        continue;
                    }else if(cellulebas =='.'){
                        continue;
                    }else{
                        X3 = j; Y3 = k;
                        break;
                    }
                }
                
                if(X2 == null && Y2 == null){
                    X2 = -1; Y2 = -1;
                }
                if(X3 == null && Y3 == null){
                    X3 = -1; Y3 = -1;
                }
                
                String res = X1 +" "+ Y1 +" "+ X2 +" "+ Y2 +" "+ X3 +" "+ Y3;
                System.out.println(res);
            }
        }
    }
}
