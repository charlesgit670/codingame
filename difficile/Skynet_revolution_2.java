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
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
                        
        //création de la matrice de lien à partir des données récupérées
        int[][] lien = new int[N][N];
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            lien[N1][N2] = 1; lien[N2][N1] = 1;
        }
        
        //La liste des noeuds de sortie
        List<Integer> sortie = new ArrayList();
        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            sortie.add(EI);
        }

        //affiche la matrice de lien
        System.err.println("Matrice de lien");
        for(int[] i : lien){
            for(int j : i){
                System.err.print(j + " ");
            }
            System.err.print("\n");
        }

        //afiche la liste des sorties        
        System.err.println("========================");
        System.err.println("Sortie");
        for(int i : sortie){
            System.err.print(i + " ");
            System.err.print("\n");
        }
        System.err.println("========================");
        
        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn            
            System.err.println("Positon actuelle : "+SI);
            System.err.println("\n========================");

            int[] rank = ranked(N, sortie, lien); //permet de calculer la dengerosité des noeuds à chaque tour
            System.err.println("ranked : ");
            for(int i : rank){
                System.err.print(i+" ");
            }
            System.err.println("\n========================");

            int min = Integer.MAX_VALUE;
            int min2 = Integer.MAX_VALUE;
            int prenoeud=-1;
            int noeud=-1;
            for(int s : sortie){
                boolean compteur=false; // Si une sortie n'a plus de lien alors on passe au noeud suivant
                for(int i=0; i < N; i++){
                    if(lien[s][i] == 1){
                        compteur=true;
                    }
                }
                if(compteur){
                    System.err.println("sortie : "+s);
                    int[] resultat = new int[4];
                    resultat = dijkstra(N, lien, s, sortie, SI, rank);
                    System.err.println("resultat : "+resultat[0]+" "+resultat[1]+" "+resultat[2]+" "+resultat[3]);
                    
                    if(min > resultat[2]){
                        min = resultat[2];
                        min2 = resultat[3];
                        prenoeud = resultat[0];
                        noeud = resultat[1];
                    }else if(min == resultat[2] && min2 > resultat[3]){
                        min = resultat[2];
                        min2 = resultat[3];
                        prenoeud = resultat[0];
                        noeud = resultat[1];
                    }
                }
            }    
            // Example: 0 1 are the indices of the nodes you wish to sever the link between
            System.out.println(prenoeud +" "+ noeud);
            //on ajuste la matrice de lien par rapport au noeud que l'on a coupé
            lien[prenoeud][noeud] = 0; 
            lien[noeud][prenoeud] = 0;
        }
    }
    
    //méthode permettant de calculer le nombre de sortie voisine possible pour chaque noeud ( plus un noeud possède de sortie, plus il est dangeureux )
    public static int[] ranked(int N, List<Integer> sortie, int[][] lien){
    int[] rank = new int[N];
    for(int i=0; i < N; i++){
        for(int j : sortie){
            if( lien[i][j] == 1){
                rank[i]++; 
            }
        }
    }
    return rank;
    } 

    /*
    Méthode Dijkstra permettant de calculer le chemin le plus court pour une position et une sortie donnée dans un graph de noeud.
    On modifie le calcul de la distance en prenant en compte le nombre de sortie liée à un noeud. On appellera cela, la dangerosité d'un noeud.
    Plus le nombre de sortie voisine au noeud est grand plus la dangerosité du noeud est grande.
    Si un noeud possède 4 sorties voisines, il faudra 4 tours pour bloquer toutes les sorties. Si ce noeud se trouve à une distance de 3 noeuds 
    de la position de départ et 4 noeuds jusqu'à la sortie, alors il faudra couper les liens immédiatement. On peut voir ça de la facon suivante :
    distance séparant la position de départ et la sortie (4) - nombre de sorties possibles (4) = 0 tour restant avant de commencer à couper les liens
    */
    public static int[] dijkstra(int N, int[][] lien, int sortie, List<Integer> s, int position, int[] rank){
        int[] distance = new int[N]; //stock la valeur des distances entre le noeud de départ et le noeud d'arrivé 
        int[] resultat = new int[4]; //stock les resultats à retourner
        int noeudpre=-1; // noeud precedent
        int noeud=-1; //noeud actuel
        int distmin = Integer.MAX_VALUE; //distance calculée en prenant en compte la dangerosité
        int distmin2 = Integer.MAX_VALUE; //distance calculée sans prendre en compte la dangerosité
        List<Integer> listeattente = new ArrayList(); // création d'une liste d'attente des noeuds à explorer
        List<List<Integer>> positionpre = new ArrayList(); // liste les noeuds parcourus pour arriver à chaque noeud de la liste des noeuds explorés (listinposition)
        List<Integer> listinposition = new ArrayList(); // liste les noeuds explorés
        //initialisation des listes
        listinposition.add(position);
        listeattente.add(position);
        positionpre.add(listinposition);
        
        //initialisation des distances à l'infinie
        for(int i=0; i < N; i++){
            distance[i] = Integer.MAX_VALUE;
        }    
        distance[position] = 0; // initialisation de la distance pour la position de départ à 0

        System.err.println("NEW DIJKSTRA"); 
        while(!listeattente.isEmpty()){ //tant que la liste d'attente est non vide, on continue d'explorer les noeuds en file d'attente
           
            int index = listeattente.indexOf(new Integer(position)); // récupère l'index de la position 
            List<Integer> pre = positionpre.get(index); // on récupère la liste du parcours de la position à explorer lors de cette itération
            
            listeattente.remove(new Integer(position)); // suppression de la position de la liste d'attente
            positionpre.remove(index); // suppression du parcours associé à la position
            
            boolean test = false;
            
            // on regarde d'abord si la sortie entrée en argument est voisin de la position
            if(lien[position][sortie] == 1){                
                int preresultat = calculdistancesortie(position, sortie, distance, rank); //distance séparant la position de la sortie
                if(distmin > preresultat){ // si la nouvelle distance est plus petite alors on stock les informations
                    distance[sortie] = preresultat; // mise a jour du tableau de distance
                    distmin2 = pre.size();  //nombre de noeud parcouru 
                    distmin = distance[sortie]; // nouvelle distance min
                    noeudpre = position; // noeud avant sortie
                    noeud = sortie; // noeud sortie
                }else if(distmin == preresultat && distmin2 > pre.size()){ // si la distance distmin (avec dangerosité ) est la même on choisit la distance distmin2 (sans dangerosité ) la plus petite
                    distance[sortie] = preresultat;
                    distmin2 = pre.size();
                    distmin = distance[sortie];
                    noeudpre = position;
                    noeud = sortie; 
                }
                position = minimum(listeattente, distance); // on choisit la nouvelle position à explorer dans la liste d'attente en fonction de la distance la plus courte
                test = true;
            }

            // si une sortie est voisine alors on passe au noeud suivant ( variable position )                
            if(test){
                continue;
            }
            
            //calcul distance 
            for(int j=0; j < N; j++){
                boolean verif = false;
                if(lien[position][j] == 1 && !pre.contains(j) && !s.contains(j)){ // on cherche un lien avec le noeud position qui ne soit pas un noeud déjà traversé ou une sortie 
                    int dist = distance[j]; // sauvegarde distance de j
                    distance[j] = calculdistance(position, j, distance, rank); // mise à jour de la distance de j
                    if(distance[j] != dist){ //si la distance à été mise à jour 
                        // si le noeud voisin possède d'autres voisins on l'ajoute à la liste d'attente sinon on passe au noeud suivant ( permet d'éviter d'ajouter des noeuds sans interêt à la liste d'attente)
                        for(int i=0; i < N; i++){
                            if(lien[j][i] == 1 && !pre.contains(i)){                            
                                listeattente.add(j); //ajout à la liste d'attente
                                //on clone la liste des positions précédente
                                List<Integer> inter = new ArrayList();
                                for(int w : pre){
                                    inter.add(w);
                                }
                                inter.add(j); // on y ajoute le noeud j qui a été ajouté à la liste d'attente pour former la nouvelle liste des noeuds précédents
                                positionpre.add(inter); // ajoute la liste des noeuds précédents correspondant au noeud j ajouté à la liste d'attente

                                for(int w=0; w < listeattente.size()-2; w++){
                                   if( listeattente.get(w) == j){ // si la liste d'attente possède plusieurs même noeud j on supprime les précédents et on garde l'actuel ( qui a forcément une distance plus petite )
                                       positionpre.remove(w);
                                       listeattente.remove(w);
                                   }
                                }
                                break;
                            }
                        }
                    }
                }
            }
            // on choisit la nouvelle position à explorer dans la liste d'attente en fonction de la distance la plus courte
            position = minimum(listeattente, distance);
            
        }
        // Après avoir trouvé le chemin avec la distance distmin et distmin2 les plus petites
        // on retourne le noeud avant la sortie, la sortie, distmin et distmin2
        resultat[0] = noeudpre; resultat[1] = noeud; resultat[2] = distmin; resultat[3] = distmin2;
        return resultat;
    }
    
    // calcul le noeud de la liste ayant la distance la plus petite
    public static int minimum(List<Integer> list, int[] distance){
        int min = Integer.MAX_VALUE;
        int position=-1;
            for(Integer noeud : list){
                if(distance[noeud] < min){
                    min = distance[noeud];
                    position = noeud;
                }
            }
        return position;
    }

    // calcul la distance entre la position actuelle et le noeud voisin. Retourne la distance calculée si plus petite, sinon returne la valeur stockée 
    public static int calculdistance(int d1, int d2, int[] distance,int[] rank){
        if(distance[d1] + 1 - rank[d1] < distance[d2]){
            return distance[d1] + 1 - rank[d1];
        }else{
            return distance[d2];
        }
    }
        
    //retourne la distance ( avec dangerosité ) entre la position et la sortie
    public static int calculdistancesortie(int d1, int d2, int[] distance,int[] rank){
        if(distance[d1] + 1 - rank[d1] < distance[d2]){                
             return distance[d1] + 1 - rank[d1];
        }else{
             return distance[d1] + 1 - rank[d1];
        }
    } 
}

