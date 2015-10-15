package com.company;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    static final int MATCH = 2;
    static final int MISMATCH = -1;
    static final int GAP = -2;

    static final int MOVE_ALIGN = 1;
    static final int MOVE_GAP_FIRST = 2;
    static final int MOVE_GAP_SECOND = 3;

    static final int NOT_INITIALIZED = 1_000_000_000;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        PrintStream output = System.out;

        while(input.hasNextLine()){
            String first = input.nextLine().trim();
            String second = input.nextLine().trim();

            printMinAligment(first, second);
        }

    }

    private static void printMinAligment(String first, String second){
        int[][] shifts = new int[first.length()+1][second.length() + 1];
        for (int[] curArray : shifts){
            Arrays.fill(curArray, NOT_INITIALIZED);
        }

        int[][] moves = new int[first.length()][second.length()];

        getMinAlignment(first, second, shifts, moves, 0,0);

        int firstPos = 0;
        int secondPos = 0;
        StringBuilder firstString = new StringBuilder();
        StringBuilder secondString = new StringBuilder();

        while(true){

            if (firstPos == first.length()) {
                secondString.append(second.substring(secondPos));
                break;
            }
            if (secondPos == second.length()) {
                firstString.append(first.substring(firstPos));
                break;
            }
            if (moves[firstPos][secondPos] == MOVE_ALIGN ){
                firstString.append(first.charAt(firstPos++));
                secondString.append(second.charAt(secondPos++));
            } else if (moves[firstPos][secondPos] == MOVE_GAP_FIRST ){
                if (firstPos==0) {
                    firstString.append(" ");
                } else {
                    firstString.append("-");
                }
                secondString.append(second.charAt(secondPos++));
            } else {
                firstString.append(first.charAt(firstPos++));
                if (secondPos==0) {
                    secondString.append(" ");
                } else {
                    secondString.append("-");
                }
        }
    }
        System.out.println(shifts[0][0]);
        System.out.println(firstString);
        System.out.println(secondString);
    }

    private static void getMinAlignment(String  first, String second, int[][] shifts,
                                        int [][] moves, int firstPos,
                                        int secondPos) {

        /* Base case here. */
        if (shifts[firstPos][secondPos] != NOT_INITIALIZED) {
            return;
        }
        if (firstPos == first.length() || secondPos == second.length()) {
            shifts[firstPos][secondPos] = 0;
            return;
        }
        // recursive
        getMinAlignment(first, second, shifts, moves, firstPos+1, secondPos+1);
        getMinAlignment(first, second, shifts, moves, firstPos, secondPos+1);
        getMinAlignment(first, second, shifts, moves, firstPos+1, secondPos);
        boolean match = first.charAt(firstPos) == second.charAt(secondPos);
        int matchScore = (match?MATCH:MISMATCH) + shifts[firstPos+1][secondPos+1];
        int secondShiftScore = (secondPos==0?0:GAP) + shifts[firstPos+1][secondPos];
        int firstShiftScore = (firstPos==0?0:GAP) + shifts[firstPos][secondPos+1];

        if (matchScore >= secondShiftScore && matchScore >= firstShiftScore) {
            shifts[firstPos][secondPos] = matchScore;
            moves[firstPos][secondPos] = MOVE_ALIGN;
        } else if (firstShiftScore >= secondShiftScore){
            shifts[firstPos][secondPos] = firstShiftScore;
            moves[firstPos][secondPos] = MOVE_GAP_FIRST;
        } else {
            shifts[firstPos][secondPos] = secondShiftScore;
            moves[firstPos][secondPos] = MOVE_GAP_SECOND;
        }
    }

}
