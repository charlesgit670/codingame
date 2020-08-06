import java.util.*;
import java.io.*;
import java.math.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // Nombre d'associations extension/MIME type
        int Q = in.nextInt(); // Nombre de fichier à analyser
        //stockage des associations extensions/MIME type dans une Map
        Map<String, String> EXT = new HashMap<>();
        //liste des résultats
        List<String> RES = new ArrayList<>();
        int position;
        String ent="";
        
        //remplissage de la Map EXT
        for (int i = 0; i < N; i++) {
           EXT.put(in.next().toLowerCase(), in.next()); 
        }
        Set key = EXT.keySet();
        in.nextLine();
        
        for (int i = 0; i < Q; i++) {
            String FNAME = in.nextLine(); // on récupère un fichier par ligne
            
                position = FNAME.lastIndexOf("."); // position du point qui sépare le nom du fichier de son extension
                //si "." n'existe pas, on ajoute "UNKNOW" à la slite des résultats
                if(position == -1){
                    RES.add("UNKNOWN");
                    continue;
                }
                //on récupère l'extension
                for(int j = position+1; j < FNAME.length(); j++){
                    ent = ent + FNAME.charAt(j);
                }
                ent = ent.toLowerCase();     
                //si l'extention existe, on l'ajoute à la liste des résultats sinon on ajoute" UNKNOW"
                if(key.contains(ent)){
                        RES.add(EXT.get(ent));
                    }
                if(RES.size() < i+1){
                    RES.add("UNKNOWN");
                }
            ent = "";
        }

        
        for(String element : RES){
            System.out.println(element);
        }

    }
}
