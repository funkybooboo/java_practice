package com.nathan;

import java.util.Scanner;

public class SlotMachine extends SpinGenerator {

    public static void main(String[] args) {
        SlotMachine newObject = new SlotMachine();
        newObject.play();
    }

    public void play() {
        System.out.println("How many spins? (1-1000)");

        Scanner scan = new Scanner(System.in);
        int spins = scan.nextInt();

        while(spins < 1 || spins > 1000){
            System.out.println("Sorry, please enter a valid number of spins");
            spins = scan.nextInt();
        }
        System.out.println();

        generateSpins(spins);
        int wins = checkWins();
        System.out.println("You won " + wins + " times!");
    }
}
