import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {
    
    //variable static permettant de simuler une bataille lorsque non nulle dans la méthode compare ci-dessous
    static int n = 0;

    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        List<String> joueur1 = new ArrayList(); //liste des cartes joueur 1
        List<String> joueur2 = new ArrayList(); //liste des cartes joueur 2

        //récupération des données sur les cartes joueur 1
        int n = in.nextInt(); // the number of cards for player 1
        for (int i = 0; i < n; i++) {
            joueur1.add(in.next());
        }

        //récupération des données sur les cartes joueur 2
        int m = in.nextInt(); // the number of cards for player 2
        for (int i = 0; i < m; i++) {
            joueur2.add(in.next());
        }

        int compteur =0;
        String res = "";
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        
        //tant que l'un des joueurs possèdent encore des cartes le jeu se poursuit 
        while(!joueur1.isEmpty() && !joueur2.isEmpty()){

            res = compare(joueur1, joueur2);
            if(res.equals("PAT")){
                break;
            }
            compteur++;            
        }

        //affiche le joueur ayant encore des cartes à la fin de la partie + le nombre de manche jouée ou une égalité
        if(joueur1.isEmpty()){
            System.out.println("2 "+compteur);
        }
        if(joueur2.isEmpty()){
            System.out.println("1 "+compteur);
        }
        if(res.equals("PAT")){
            System.out.println(res);
        }        
    }
    
    public static String compare(List<String> joueur1, List<String> joueur2){
        
        char p1;
        char p2;
        
        try { //on récupère le premier caractère de la 4*n ième carte ( ex : '1' si la carte est "10D"), si valeur null on return le string "PAT"
			p1 = joueur1.get(4*n).charAt(0);
            p2 = joueur2.get(4*n).charAt(0);
		} catch (Exception e) {
            return "PAT";
		}
        
        //permet de convertir le caractère avec l'équivalent ASCII base 10    
        int cardp1 = p1;
        int cardp2 = p2;
        
        //rectification des valeurs de manière à correspondre à l'ordre des cartes du jeu de la bataille 
        switch (cardp1) {
        case 49: cardp1 = 58; // 49 correspond à '1' qui est en réalité '10' car on récupère que le premier caractère
	    break;
        case 65: cardp1 = 83; // 65 correspond à l'As 'A'
	    break;
	    case 75: cardp1 = 82; // 75 correspond à 'K' le roi
	    default: break; 
        }
            
        switch (cardp2) {
        case 49: cardp2= 58;
	    break;
        case 65: cardp2 = 83;
	    break;
	    case 75: cardp2 = 82;
	    default: break;
        }
        
        if(cardp1 < cardp2){ // carte joueur1 < carte joueur 2
            for(int i = 0; i <= 4*n; i++){ //ajout de la/des carte(s) joueur1 à joueur2 + suppresion carte(s) joueur1
                joueur2.add(joueur1.get(0));
                joueur1.remove(0);
            }
            for(int i = 0; i <= 4*n; i++){ //permet d'inverser l'ordre des cartes du joueur2 de manière à ce qu'elles soient placées après celles du joueur1
                joueur2.add(joueur2.get(0));
                joueur2.remove(0);
            }
            n = 0;
        }else if(cardp1 > cardp2){ // carte joueur1 > carte joueur 2
            for(int i = 0; i <= 4*n; i++){ //permet d'inverser l'ordre des cartes du joueur1 de manière à ce qu'elles soient placées avant celles du joueur2
                joueur1.add(joueur1.get(0));
                joueur1.remove(0);
            }
            for(int i = 0; i <= 4*n; i++){ //ajout de la/des carte(s) joueur2 à joueur1 + suppresion carte joueur2
                joueur1.add(joueur2.get(0));
                joueur2.remove(0);
            }
            n = 0;
        }else{ // si égalité on fait appel de nouveau à la méthode compare mais avec n incrémenté de 1, de manière à simuler une bataille
            n++;
            compare(joueur1, joueur2);        
        }
        
        return "OK";
    }
}
