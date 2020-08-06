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
        int N = in.nextInt();
        int[] tab = new int[N];
        for (int i = 0; i < N; i++) {
            tab[i] = in.nextInt();
        }
        Arrays.sort(tab);
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

		int dif = Integer.MAX_VALUE;
        for (int i = 0; i < tab.length-1; i++) {
           if( (tab[i+1] - tab[i]) < dif){
			     dif = tab[i+1] - tab[i];
			  }
        }
        System.out.println(dif);
    }
}
