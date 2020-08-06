import java.util.*;
import java.io.*;
import java.math.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        //vecteur pour stocker le résultat final
        Integer[] res = new Integer[N];
        //Tableau pour stocker les données fournies en entrée
        String[][] stock = new String[N][3];
        
        //on récupère l'opérateur et les arguments que l'on stock dans la variable stock 
        for (int i = 0; i < N; i++) {
            String operation = in.next();
            String arg1 = in.next();
            String arg2 = in.next();
            
            stock[i][0] = operation;
            stock[i][1] = arg1;
            stock[i][2] = arg2;
        }
        //Tant que il existe des éléments du vecteur res null on continue
        while(Arrays.toString(res).contains("null")){
            int z=0;
        for(String[] str : stock){
            int arg1;
            int arg2;
           
           //si il existe un résultat on passe à l'élément suivant
            if(res[z] != null){
                z++;
                continue;
            }
            
            //On va chercher la valeur dans le tableau si l'argument 1 est de la forme ex: $0 
            //sinon on attribut directement la valeur à la var : arg1 
            if( str[1].contains("$")){
                if(res[Integer.parseInt(str[1].substring(1))] != null){
                    arg1 = res[Integer.parseInt(str[1].substring(1))];
                }else{
                    z++;
                    continue;
                }
            }else{
                arg1 = Integer.parseInt(str[1]);
            }
            
            //On va chercher la valeur dans le tableau si l'argument 2 est de la forme ex: $0 
            //sinon on attribut directement la valeur à la var : arg2 
            if( str[2].contains("$")){
                if(res[Integer.parseInt(str[2].substring(1))] != null){
                    arg2 = res[Integer.parseInt(str[2].substring(1))];
                }else{
                    z++;
                    continue;
                }
            }else if(str[2].contains("_")){
                arg2 = 0;
            }else{
                arg2 = Integer.parseInt(str[2]);
            }

            //opération effectuée en fonction de la valeur de l'opérateur et stocké dans res
            switch (str[0]) {
		    case "VALUE": res[z] = arg1;
			    break;
            case "ADD": res[z] = arg1 + arg2;
                break;
            case "SUB": res[z] = arg1 - arg2;
                break;
		    default: res[z] = arg1 * arg2;
			    break;
		    }
            z++;
             
        }
        }
        for (int i = 0; i < N; i++) {
            System.out.println(res[i]);
        }
    }
}
