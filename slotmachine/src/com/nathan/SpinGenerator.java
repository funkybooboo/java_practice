package com.nathan;

import java.util.Random;
import java.util.Stack;

public class SpinGenerator {

    Stack<Object> stack = new Stack<>();

    public void generateSpins(int n){

        Random r = new Random();


        for(int i = 0; i < n; i++){
            int[][] spin = {{r.nextInt(5), r.nextInt(5), r.nextInt(5)},
                    {r.nextInt(5), r.nextInt(5), r.nextInt(5)},
                    {r.nextInt(5), r.nextInt(5), r.nextInt(5)}};
            stack.add(spin);

        }

    }

    public int checkWins(){
        int numWins = 0;
        while (!stack.isEmpty()){
            int[][] spin = (int[][])stack.pop();

            if (spin[0][0] == spin[0][1] && spin[0][1] == spin[0][2]) {
                numWins++;
            }else if(spin[1][0] == spin[1][1] && spin[0][1] == spin[1][2]){
                numWins++;
            }else if(spin[2][0] == spin[2][1] && spin[2][1] == spin[2][2]){
                numWins++;
            }else if(spin[0][0] == spin[1][0] && spin[1][0] == spin[2][0]){
                numWins++;
            }else if(spin[0][1] == spin[1][1] && spin[1][1] == spin[2][1]){
                numWins++;
            }else if(spin[0][2] == spin[1][2] && spin[1][2] == spin[2][2]){
                numWins++;
            }else if(spin[0][0] == spin[1][1] && spin[1][1] == spin[2][2]){
                numWins++;
            }else if(spin[0][2] == spin[1][1] && spin[1][1] == spin[2][0]){
                numWins++;
            }

            for(int[] row : spin){
                for(int n : row){
                    System.out.print(n);
                }
                System.out.println();
            }
            System.out.println();
        }
        return numWins;
    }

}
