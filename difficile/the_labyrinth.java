import java.util.*;
import java.io.*;
import java.math.*;
//a
/**s
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int R = in.nextInt(); // number of rows.
        int C = in.nextInt(); // number of columns.
        int A = in.nextInt(); // number of rounds between the time the alarm countdown is activated and the time the alarm goes off.
        String result ="";
        int xbut = -1;
        int ybut = -1;
        int xorigine = -1; //position de départ x du joueur
        int yorigine = -1; // position de départ y du joueur
        boolean findC = false; // indique si le poste de commandement 'C' a été découvert 
        boolean onC = false; // indique si le joueur est déjà passé par le poste de commandement 'C'
        
        // game loop
        /*
        Pour résoudre ce problème on utilise l'algorithme A* :
        Similaire à l'algorithme de dijstra, cette fois-ci on privilégie une direction d'exploration. Pour se faire, on utilise
        une valeur que l'on calcule pour chaque noeud appelé l'heuristique.
        L'heuristique calcul hypothétiquement la distance la plus courte en prenant en compte le nombre de noeud séparant les 2 noeuds (départ et destination) 
        sans considérer d'autres facteurs comme les obstacles ( un mur par exemple ) puis on y ajoute le coût, représenté par la distance parcourue depuis le point de départ.
        Contrairement à dijstra, où on choisissait le cout minimum pour déterminer le prochain noeud à explorer, ici on choisit l'heuristique minimum.
        Cela permet d'explorer dans la direction du noeud souhaité tout en maintenant un parcours en largeur.
        */
        while (true) {
            
            int KR = in.nextInt(); // row where Kirk is located.
            int KC = in.nextInt(); // column where Kirk is located.
            // Stockage du labyrinthe dans un tableau 2D
            //Chaque élement du labyrinthe est un object Noeud (voir la classe Noeud plus bas)
            Noeud[][] laby = new Noeud[R][C];             
            for (int i = 0; i < R; i++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                //création d'un objet Noeud + remplissage des attributs 
                for(int j = 0; j < C; j++){
                    laby[i][j] = new Noeud();
                    laby[i][j].valeur = ROW.charAt(j);
                    laby[i][j].x = j;
                    laby[i][j].y = i;
                    if(laby[i][j].valeur == 'T'){ // le caractère 'T' représente la position du joueur au départ
                        xorigine = j;
                        yorigine = i;
                    }
                }
            }
            //Permet d'afficher la position joueur ( caractère 'P' ) lors de l'affichage du labyrinthe 
            if(laby[KR][KC].valeur == '.'){
                laby[KR][KC].valeur = 'P';
            }
            
            //Affichage du labyrinthe 
            for (int i = 0; i < R; i++) {
                for(int j = 0; j < C; j++){
                   System.err.print(laby[i][j].valeur);
                   if(j == C - 1){
                       System.err.print("\n");
                   }
                }
            }
            
            //Si le poste de commandement est découvert et que le joueur se trouve actuellement dessus pour la première fois
            // => alors on assigne true à la variable onC et on calcul l'heuristique par rapport à la position de départ
            if(findC && !onC && laby[KR][KC].valeur == 'C'){
                onC = true;
                for(int i = 0; i < R; i++){
                    for(int j = 0; j < C; j++){
                        laby[i][j].heuristique = Math.abs(laby[i][j].x - laby[KR][KC].x) + Math.abs(laby[i][j].y - laby[KR][KC].y);
                    }
                }
            }
            
            if(findC && !onC){ // Si 'C' trouvé mais pas encore traversé. On fait appel à la méthode qui va chercher le chemin le plus court vers 'C'
                System.err.print("vers C");
                result = cheminPlusCourt(laby, laby[ybut][xbut], laby[KR][KC], R, C);
            }else if(!findC && !onC){ // Si on a pas trouvé 'C'. On fait appel à la méthode qui va chercher le chemin le plus court vers '?'
                System.err.print("à la découverte");
                result = inconnuPlusCourt(laby, laby[KR][KC], R, C);
            }else{ // Si on est arrivé à 'C'. On fait appel à la méthode qui va chercher le chemin le plus court vers 'T'
                System.err.print("retour au point de départ");
                result = cheminPlusCourt(laby, laby[yorigine][xorigine], laby[KR][KC], R, C);
            }
            
            if(!findC && !onC && result.equals("END")){ // si l'exploration total du labyrinthe est terminée alors on initialise l'heuristique par rapport à la destination 'C'
                findC = true;
                for(int i = 0; i < R; i++){
                    for(int j = 0; j < C; j++){
                        if(laby[i][j].valeur == 'C'){
                            xbut = j; ybut = i;
                            for (int k = 0; k < R; k++) {
                                for(int l = 0; l < C; l++){
                                    laby[k][l].heuristique = Math.abs(laby[k][l].x - laby[ybut][xbut].x) + Math.abs(laby[k][l].y - laby[ybut][xbut].y);
                                }
                            }
                            
                        }
                    }
                }
                result = cheminPlusCourt(laby, laby[ybut][xbut], laby[KR][KC], R, C);
            }
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println(result); // Kirk's next move (UP DOWN LEFT or RIGHT).
        }
    }//fin main
    
    
    //Calcul le chemin le plus court vers la destination 'C' ou 'T' en utilisant l'heuristique
    public static String cheminPlusCourt(Noeud[][] lab, Noeud destination, Noeud depart, int R, int C){
        
        Noeud actuel = depart;
        
        for (int i = 0; i < R; i++) {
            for(int j = 0; j < C; j++){
                lab[i][j].cout = Integer.MAX_VALUE;
            }
        }
        lab[depart.y][depart.x].cout = 0;

        List<Noeud> listeAttente = new ArrayList();
        List<List<Noeud>> stockage = new ArrayList();
        List<Noeud> prestockage = new ArrayList();
         
        listeAttente.add(depart);
        prestockage.add(depart);
        prestockage.add(depart);
        stockage.add(prestockage);
         
        while(!listeAttente.isEmpty()){
            //System.err.println("Debug messages...");
            int index = listeAttente.indexOf(actuel);
            List<Noeud> stockageactuel = stockage.get(index);
            listeAttente.remove(actuel);
            stockage.remove(index);
             
            if(actuel.valeur == destination.valeur){
                int x = stockageactuel.get(2).x;
                int y = stockageactuel.get(2).y;

                if(x - depart.x > 0){
                    return "RIGHT";
                }else if(x - depart.x < 0){
                    return "LEFT";
                }
                if(y - depart.y > 0){
                    return "DOWN";
                }else if(y - depart.y < 0){
                    return "UP";
                }
                
                break;
            }
            /*
            On commence par vérifier que le noeud exploré se situe bien dans la grille du labyrinthe.
            Ensuite, on vérifie si la valeur du noeud n'est pas un mur ('#') ou un noeud déjà traversé par ce parcours.
            Si le coût du noeud est supérieur à celui du noeud actuel +1 alors on remplace car le parcours actuel est plus court.
            On fait de même pour l'heuristique
            */ 
            if(actuel.y+1 < R){
                if(lab[actuel.y+1][actuel.x].valeur != '#' && (stockageactuel.get(stockageactuel.size()-2).x != actuel.x || stockageactuel.get(stockageactuel.size()-2).y != actuel.y+1)){
                    if(lab[actuel.y+1][actuel.x].cout > lab[actuel.y][actuel.x].cout +1){ 
                        lab[actuel.y+1][actuel.x].cout = lab[actuel.y][actuel.x].cout +1;
                        lab[actuel.y+1][actuel.x].heuristique = lab[actuel.y+1][actuel.x].heuristique + lab[actuel.y+1][actuel.x].cout;
                        listeAttente.add(lab[actuel.y+1][actuel.x]);
                        List<Noeud> inter = new ArrayList();
                        for(Noeud n : stockageactuel){
                            inter.add(n);
                        }
                        inter.add(lab[actuel.y+1][actuel.x]);
                        stockage.add(inter);
                        for(int i = 0; i < listeAttente.size()-2; i++){
                            if(listeAttente.get(i) == lab[actuel.y+1][actuel.x]){
                                listeAttente.remove(i);
                                stockage.remove(i);
                            }
                        }
                    }
                }
            }
            if(actuel.y-1 > -1){
                if(lab[actuel.y-1][actuel.x].valeur != '#' && (stockageactuel.get(stockageactuel.size()-2).x != actuel.x || stockageactuel.get(stockageactuel.size()-2).y != actuel.y-1)){
                    if(lab[actuel.y-1][actuel.x].cout > lab[actuel.y][actuel.x].cout +1){
                        lab[actuel.y-1][actuel.x].cout = lab[actuel.y][actuel.x].cout +1;
                        lab[actuel.y-1][actuel.x].heuristique = lab[actuel.y-1][actuel.x].heuristique + lab[actuel.y-1][actuel.x].cout;
                        listeAttente.add(lab[actuel.y-1][actuel.x]);
                        List<Noeud> inter = new ArrayList();
                        for(Noeud n : stockageactuel){
                            inter.add(n);
                        }
                        inter.add(lab[actuel.y-1][actuel.x]);
                        stockage.add(inter);
                        for(int i = 0; i < listeAttente.size()-2; i++){
                            if(listeAttente.get(i) == lab[actuel.y-1][actuel.x]){
                                listeAttente.remove(i);
                                stockage.remove(i);
                            }
                        }
                    }
                }
            }
            if(actuel.x+1 < C){
                if(lab[actuel.y][actuel.x+1].valeur != '#' && (stockageactuel.get(stockageactuel.size()-2).x != actuel.x+1 || stockageactuel.get(stockageactuel.size()-2).y != actuel.y)){
                    if(lab[actuel.y][actuel.x+1].cout > lab[actuel.y][actuel.x].cout +1){
                        lab[actuel.y][actuel.x+1].cout = lab[actuel.y][actuel.x].cout +1;
                        lab[actuel.y][actuel.x+1].heuristique = lab[actuel.y][actuel.x+1].heuristique + lab[actuel.y][actuel.x+1].cout;
                        listeAttente.add(lab[actuel.y][actuel.x+1]);
                        List<Noeud> inter = new ArrayList();
                        for(Noeud n : stockageactuel){
                            inter.add(n);
                        }
                        inter.add(lab[actuel.y][actuel.x+1]);
                        stockage.add(inter);
                        for(int i = 0; i < listeAttente.size()-2; i++){
                            if(listeAttente.get(i) == lab[actuel.y][actuel.x+1]){
                                listeAttente.remove(i);
                                stockage.remove(i);
                            }
                        }
                    }
                }
            }
            if(actuel.x-1 > -1){
                if(lab[actuel.y][actuel.x-1].valeur != '#' && (stockageactuel.get(stockageactuel.size()-2).x != actuel.x-1 || stockageactuel.get(stockageactuel.size()-2).y != actuel.y)){
                    if(lab[actuel.y][actuel.x-1].cout > lab[actuel.y][actuel.x].cout +1){
                        lab[actuel.y][actuel.x-1].cout = lab[actuel.y][actuel.x].cout +1;
                        lab[actuel.y][actuel.x-1].heuristique = lab[actuel.y][actuel.x-1].heuristique + lab[actuel.y][actuel.x-1].cout;
                        listeAttente.add(lab[actuel.y][actuel.x-1]);
                        List<Noeud> inter = new ArrayList();
                        for(Noeud n : stockageactuel){
                            inter.add(n);
                        }
                        inter.add(lab[actuel.y][actuel.x-1]);
                        stockage.add(inter);
                        for(int i = 0; i < listeAttente.size()-2; i++){
                            if(listeAttente.get(i) == lab[actuel.y][actuel.x-1]){
                                listeAttente.remove(i);
                                stockage.remove(i);
                            }
                        }
                    }
                }
            }
            // on cherche l'heuristique minimum 
            int min = Integer.MAX_VALUE;
            for(Noeud n : listeAttente){
                if(n.heuristique < min){
                    min = n.heuristique;
                    actuel = n;
                }
            }
        }//fin while
        return "";
    }
    
    /*
    Même principe que pour la méthode de dijstra utilisé dans l'exercice réseau Skynet.
    Ici on cherche le noeud inconnu ('?') le pluis proche, donc pas besoin d'utiliser l'heuristique.
    */
    public static String inconnuPlusCourt(Noeud[][] lab, Noeud depart, int R, int C){
         
        Noeud actuel = depart;
        String result = "END"; // on retourne 'END' si plus rien à explorer
        List<Noeud> listeAttente = new ArrayList();
        List<List<Noeud>> stockage = new ArrayList();
        List<Noeud> prestockage = new ArrayList();
        List<Noeud> decouvert = new ArrayList(); // liste tous les noeuds déjà traversés
        
        listeAttente.add(depart);
        prestockage.add(depart);
        prestockage.add(depart);
        stockage.add(prestockage);
        decouvert.add(depart);
         
        while(!listeAttente.isEmpty()){
            int index = listeAttente.indexOf(actuel);
            List<Noeud> stockageactuel = stockage.get(index);
            listeAttente.remove(actuel);
            stockage.remove(index);

            //si la valeur actuelle correspond à '?' alors on retourne la direction pour si diriger
            if(actuel.valeur == '?'){

                int x = stockageactuel.get(2).x;
                int y = stockageactuel.get(2).y;
                
                if(x - depart.x > 0){
                    return "RIGHT";
                }else if(x - depart.x < 0){
                    return "LEFT";
                }
                if(y - depart.y > 0){
                    return "DOWN";
                }else if(y - depart.y < 0){
                    return "UP";
                }
                
                 break;
            }

            /*
            On commence par vérifier que le noeud exploré se situe bien dans la grille du labyrinthe.
            Ensuite, on vérifie si la valeur du noeud n'est pas un mur ('#') ou un noeud déjà traversé.
            On actualise le coût ( on ajoute 1 ).
            */ 
            if(actuel.y+1 < R){ 
                if(lab[actuel.y+1][actuel.x].valeur != '#' && !decouvert.contains(lab[actuel.y+1][actuel.x])){
                lab[actuel.y+1][actuel.x].cout = lab[actuel.y][actuel.x].cout +1;
                listeAttente.add(lab[actuel.y+1][actuel.x]);
                List<Noeud> inter = new ArrayList();
                for(Noeud n : stockageactuel){
                    inter.add(n);
                }
                inter.add(lab[actuel.y+1][actuel.x]);
                stockage.add(inter);
                decouvert.add(lab[actuel.y+1][actuel.x]);
                } 
            }
            if(actuel.y-1 > -1){
                if(lab[actuel.y-1][actuel.x].valeur != '#' && !decouvert.contains(lab[actuel.y-1][actuel.x])){
                lab[actuel.y-1][actuel.x].cout = lab[actuel.y][actuel.x].cout +1;
                listeAttente.add(lab[actuel.y-1][actuel.x]);
                List<Noeud> inter = new ArrayList();
                for(Noeud n : stockageactuel){
                    inter.add(n);
                }
                inter.add(lab[actuel.y-1][actuel.x]);
                stockage.add(inter);
                decouvert.add(lab[actuel.y-1][actuel.x]);
                }
            }
            if(actuel.x+1 < C){
                if(lab[actuel.y][actuel.x+1].valeur != '#' && !decouvert.contains(lab[actuel.y][actuel.x+1])){
                lab[actuel.y][actuel.x+1].cout = lab[actuel.y][actuel.x].cout +1;
                listeAttente.add(lab[actuel.y][actuel.x+1]);
                List<Noeud> inter = new ArrayList();
                for(Noeud n : stockageactuel){
                    inter.add(n);
                }
                inter.add(lab[actuel.y][actuel.x+1]);
                stockage.add(inter);
                decouvert.add(lab[actuel.y][actuel.x+1]);
                } 
            }
            if(actuel.x-1 > -1){
                if(lab[actuel.y][actuel.x-1].valeur != '#' && !decouvert.contains(lab[actuel.y][actuel.x-1])){
                lab[actuel.y][actuel.x-1].cout = lab[actuel.y][actuel.x].cout +1;
                listeAttente.add(lab[actuel.y][actuel.x-1]);
                List<Noeud> inter = new ArrayList();
                for(Noeud n : stockageactuel){
                    inter.add(n);
                }
                inter.add(lab[actuel.y][actuel.x-1]);
                stockage.add(inter);
                decouvert.add(lab[actuel.y][actuel.x-1]);
                } 
            }
             
            //on cherche le cout minimum 
            int min = Integer.MAX_VALUE;
            for(Noeud n : listeAttente){
                if(n.cout < min){
                    min = n.cout;
                    actuel = n;
                }
            }
        }//fin while
        return result;
    }
    
    
}//fin class Player

/*
Une instanciation de Noeud représente une coordonnée x,y sa valeur ( '.','#','?','T','C' ) et son cout + heuristique ( voir algorithme A* )
*/
class Noeud {
    
    int x;
    int y;
    int cout;
    int heuristique;
    char valeur;
    
    public Noeud(){}
    
    public Noeud(int px, int py, int pcout, char pvaleur){
      x = px;
      y = py;
      cout = pcout;
      valeur = pvaleur;
    }
    
    public Noeud(int px, int py, int pcout, int pheuristique, char pvaleur){
      x = px;
      y = py;
      cout = pcout;
      heuristique = pheuristique;
      valeur = pvaleur;
    }
    
}
