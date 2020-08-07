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
        String MESSAGE = in.nextLine(); //message à encoder
        String avresult=""; // MESSAGE converti en binaire
        String result=""; // MESSAGE encodé
        
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        /*
        Cette boucle permet de convertir le MESSAGE en binaire avec un format de 7 bits pour chaque caractère
        en remplissant avec des 0 si moins de 7 bits
        */
        for (int i = 0 ; i < MESSAGE.length() ;i++){            
            int manquant = 7 - Integer.toBinaryString(MESSAGE.charAt(i)).length();
            switch (manquant) {
                case 1:  avresult=avresult + "0" + Integer.toBinaryString(MESSAGE.charAt(i));
                    break;
                case 2:  avresult=avresult + "00" + Integer.toBinaryString(MESSAGE.charAt(i));
                    break;
                case 3:  avresult=avresult + "000" + Integer.toBinaryString(MESSAGE.charAt(i));
                    break;
                case 4:  avresult=avresult + "0000" + Integer.toBinaryString(MESSAGE.charAt(i));
                    break;
                case 5:  avresult=avresult + "00000" + Integer.toBinaryString(MESSAGE.charAt(i));
                    break;
                case 6:  avresult=avresult + "000000" + Integer.toBinaryString(MESSAGE.charAt(i));
                    break;
                default: avresult=avresult + Integer.toBinaryString(MESSAGE.charAt(i));
                    break;
            }            
        }

        //initialisation de l'encodage pour le premier bit (1 ou 0)
        if(avresult.charAt(0) == '0'){
            result = result + "00 0";
        }else{
            result = result + "0 0";
        }
        //encodage des bits suivants 
        for (int j = 1 ; j < avresult.length() ;j++){
            if(avresult.charAt(j)=='0' && avresult.charAt(j-1)=='1'){
                result = result + " 00 0";
            }else if(avresult.charAt(j)=='0' && avresult.charAt(j-1)=='0'){
                result = result + "0";
            }else if(avresult.charAt(j)=='1' && avresult.charAt(j-1)=='0'){
                result = result + " 0 0";
            }else if(avresult.charAt(j)=='1' && avresult.charAt(j-1)=='1'){
                result = result + "0";
            }
        }
        System.out.println(result);
    }
}
