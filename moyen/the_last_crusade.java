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
        int W = in.nextInt(); // number of columns.
        int H = in.nextInt(); // number of rows.
        
        if (in.hasNextLine()) {
            in.nextLine();
        }

        //stockage de la carte du tunnel
        int[][] carte = new int[H][W]; 
        for (int i = 0; i < H; i++) {
            String LINE = in.nextLine(); // represents a line in the grid and contains W integers. Each integer represents one room of a given type.
            for(int j = 0; j < W; j++){
                carte[i][j] = Integer.parseInt(LINE.split(" ",-1)[j]);
            }
        }
        
        int EX = in.nextInt(); // the coordinate along the X axis of the exit (not useful for this first mission, but must be read).

        //Stockage des différentes pièces dans une Map avec les différentes possibilités de mouvement pour chacune d'entre elles
        Map<Integer, Map<String,String>> piece = new HashMap();
        Map<String, String> stock = new HashMap();
        stock.put("LEFT","BOT"); stock.put("TOP","BOT"); stock.put("RIGHT","BOT");
        piece.put(1, stock); 
        Map<String, String> stock2 = new HashMap();
        stock2.put("LEFT","RIGHT"); stock2.put("RIGHT","LEFT");
        piece.put(2, stock2); 
        Map<String, String> stock3 = new HashMap();
        stock3.put("TOP","BOT");
        piece.put(3, stock3); 
        Map<String, String> stock4 = new HashMap();
        stock4.put("TOP","LEFT"); stock4.put("RIGHT","BOT");
        piece.put(4, stock4); 
        Map<String, String> stock5 = new HashMap();
        stock5.put("TOP","RIGHT"); stock5.put("LEFT","BOT");
        piece.put(5, stock5); 
        Map<String, String> stock6 = new HashMap();
        stock6.put("LEFT","RIGHT"); stock6.put("RIGHT","LEFT");
        piece.put(6, stock6); 
        Map<String, String> stock7 = new HashMap();
        stock7.put("TOP","BOT"); stock7.put("RIGHT","BOT");
        piece.put(7, stock7);
        Map<String, String> stock8 = new HashMap();
        stock8.put("LEFT","BOT"); stock8.put("RIGHT","BOT");
        piece.put(8, stock8);
        Map<String, String> stock9 = new HashMap();
        stock9.put("TOP","BOT"); stock9.put("LEFT","BOT");
        piece.put(9, stock9); 
        Map<String, String> stock10 = new HashMap();
        stock10.put("TOP","LEFT");
        piece.put(10, stock10);
        Map<String, String> stock11 = new HashMap();
        stock11.put("TOP","RIGHT");
        piece.put(11, stock11); 
        Map<String, String> stock12 = new HashMap();
        stock12.put("RIGHT","BOT");
        piece.put(12, stock12);
        Map<String, String> stock13 = new HashMap();
        stock13.put("LEFT","BOT");
        piece.put(13, stock13); 
                
        // game loop
        while (true) {
            int XI = in.nextInt(); //numéro de la colonne sur la carte
            int YI = in.nextInt(); //numéro de la ligne sur la carte
            String POS = in.next(); //position d'arrivé sur la case (TOP, BOT, RIGHT, LEFT)

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            
            Map<String, String> recup = piece.get(carte[YI][XI]); //récupération des caractéristique de la pièce           
            String sortie = recup.get(POS); // on récupère la position de sortie pour la pièce correspondante
            
            //à partir de la direction de sortie, on détermine la pièce suivante
            switch(sortie){
                case "BOT": YI = YI + 1; 
                    break;
                case "LEFT": XI = XI - 1;
                    break;
                case "RIGHT": XI = XI + 1;
                    break;
                default : break;
            } 
            // One line containing the X Y coordinates of the room in which you believe Indy will be on the next turn.
            System.out.println(XI +" "+ YI);
        }
    }
}
