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
        String LON = in.next(); //longitude
        LON=LON.replace(',','.'); //on remplace "," par "." pour pouvoir convertir en double
        double LON2 = Double.parseDouble(LON);// convertion en double

        String LAT = in.next();//lattitude
        LAT=LAT.replace(',','.');
        double LAT2 = Double.parseDouble(LAT);

        int N = in.nextInt();//nombre de défibrillateurs
        if (in.hasNextLine()) {
          in.nextLine();
        }

        double min = Double.MAX_VALUE; //stock la distance la plus petite
        String adress = ""; //stock de l'adresse du défibrillateur
            
        for (int i = 0; i < N; i++) {
            String DEFIB = in.nextLine();//description du défibrillateur
            String[] split = DEFIB.split(";",-1);//on split chaque partie séparé par ";"
            String lon = split[4];//on récupère la longitude du défibrillateur
            String lat = split[5];//on récupère la latittude du défibrillateur

            //convertion lattitude/longitude en double
            lon=lon.replace(',','.');
            lat=lat.replace(',','.');
            double lon2 = Double.parseDouble(lon);
            double lat2 = Double.parseDouble(lat);

            //calcul de la distance séparant le défibrillateur de la victime
            double x = (lon2-LON2)*Math.cos((lat2+LAT2)/2);
            double y = lat2-LAT2;
            double dist = Math.sqrt(Math.pow(x,2)+Math.pow(y,2))*6371;

            //Si la distance est plus petite que min on remplace
            if(dist < min){
                min = dist;
                adress = split[1];
            }            
        }
        
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(adress);
    }
}
