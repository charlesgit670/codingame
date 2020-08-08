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
        int nbFloors = in.nextInt(); // number of floors
        int width = in.nextInt(); // width of the area
        int nbRounds = in.nextInt(); // maximum number of rounds
        int exitFloor = in.nextInt(); // floor on which the exit is found
        int exitPos = in.nextInt(); // position of the exit on its floor
        int nbTotalClones = in.nextInt(); // number of generated clones
        int nbAdditionalElevators = in.nextInt(); // ignore (always zero)
        int nbElevators = in.nextInt(); // number of elevators

        //récupération des données des positions des ascenceurs
        Map<Integer,Integer> elevator = new HashMap();
        for (int i = 0; i < nbElevators; i++) {
            int elevatorFloor = in.nextInt(); // floor on which this elevator is found
            int elevatorPos = in.nextInt(); // position of the elevator on its floor
            elevator.put(elevatorFloor,elevatorPos);
        }

        // game loop
        while (true) {
            int cloneFloor = in.nextInt(); // floor of the leading clone
            int clonePos = in.nextInt(); // position of the leading clone on its floor
            String direction = in.next(); // direction of the leading clone: LEFT or RIGHT

            //on récupère la position de l'ascenceur à l'étage du clone leader ou de la sortie 
            int elevatorPos = -1;
            if(elevator.keySet().contains(cloneFloor)){
                elevatorPos = elevator.get(cloneFloor);
            }else{
                elevatorPos = exitPos;
            }
            
            // si dir positive -> ascenceur à droite / si dir négative -> ascenceur à gauche
            int dir = elevatorPos - clonePos;
            String sortie="";
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            switch (direction) {
		    case "RIGHT": if(dir >= 0){ // ascenceur à droite et direction droite -> WAIT sinon BLOCK
		        sortie = "WAIT";
		    }else{
		        sortie="BLOCK";
		    }
			break;
		    default: if(dir <= 0){ // ascenceur à gauche et direction gauche -> WAIT sinon BLOCK
		        sortie = "WAIT";
		    }else{
		        sortie = "BLOCK";
		    }
			break;
		    }

            System.out.println(sortie); // action: WAIT or BLOCK
        }
    }
}
