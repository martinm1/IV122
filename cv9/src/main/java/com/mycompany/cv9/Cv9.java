/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author martin
 */
public class Cv9 {
    public static boolean MontyHallTry(Strategy strategy){
        Random rand = new Random();
        
        int correct = rand.nextInt(3);
        int guess1 = rand.nextInt(3);
        
        int incorrect;
        
        do{
            incorrect = rand.nextInt(3);
        } while (incorrect == correct || incorrect == guess1);
        
        
        int guess2 = 0;
        
        if(strategy == Strategy.KEEP){
            guess2 = guess1;
        }
        
        if(strategy == Strategy.RANDOM){
            do{
                guess2 = rand.nextInt(3);
            } while (guess2 == incorrect);
        }
        
        if(strategy == Strategy.CHANGE){
            do{
                guess2 = rand.nextInt(3);
            } while (guess2 == incorrect || guess2 == guess1);
        }
        
        return (guess2 == correct);
    }
    
    public static void AMontyHall(int number){
        int keep   = 0;
        int random = 0;
        int change = 0;
        
        for(int i = 0; i < number; i++){
            if (MontyHallTry(Strategy.KEEP)) keep++;
            if (MontyHallTry(Strategy.RANDOM)) random++;
            if (MontyHallTry(Strategy.CHANGE)) change++;
        }
        
        System.out.println("A: Monty Hall: Strategie beze změny: počet úspěchů: " + keep + " z " + number + " pokusů");
        System.out.println("A: Monty Hall: Strategie náhodná: počet úspěchů: " + random + " z " + number + " pokusů");
        System.out.println("A: Monty Hall: Strategie změna: počet úspěchů: " + change + " z " + number + " pokusů");
    }
    
    public static int[] frequencies(String text){
        
        int[] sums = new int[]{0,0,0,0,0,0};
        
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == '1'){
                sums[0]++;
            }
            if(text.charAt(i) == '2'){
                sums[1]++;
            }
            if(text.charAt(i) == '3'){
                sums[2]++;
            }
            if(text.charAt(i) == '4'){
                sums[3]++;
            }
            if(text.charAt(i) == '5'){
                sums[4]++;
            }
            if(text.charAt(i) == '6'){
                sums[5]++;
            }
        }
        return sums;
    }
    
    public static Object[] patterns(String text, int patternLength){
        
        String[] mypatterns = new String[100];
        
        int[] patternsFrequencies = new int[100];
        
        for(int i = 0; i < patternsFrequencies.length; i++){
            patternsFrequencies[i] = 0;
        }
        
        for(int i = 0; i < mypatterns.length; i++){
            mypatterns[i] = text.substring(2*i, 2*i + patternLength);
            //System.out.println(mypatterns[i]);
        }
        
        for(int i = 0; i < text.length()-patternLength; i+=2){
            for(int j = 0; j < mypatterns.length; j++){
                String substr = text.substring(i, i + patternLength);
                if(substr.equals(mypatterns[j])){
                    patternsFrequencies[j]++;
                }
            }
        }
        
        for(int i = 0; i < patternsFrequencies.length; i++){
            if((patternsFrequencies[i]) > 20){
                return new Object[]{mypatterns[i], patternsFrequencies[i]};
            }
        }
        
        return null;
    }
    
    public static String BRandomNumbers(String filename){
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        int sum4 = 0;
        int sum5 = 0;
        int sum6 = 0;
        
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get("random/"+filename)));
        } catch (IOException ex) {
            Logger.getLogger(Cv9.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(text.length());
        
                
        int[] sums = frequencies(text);
        
        //for(int i = 0; i < sums.length; i++){
        //    System.out.println(sums[i]);
        //}
        
        for(int i = 0; i < sums.length; i++){
            for(int j = 0; j < sums.length; j++){
                if(sums[i]/sums[j] > 1.2){
                    return filename + " pravděpodobně není náhodná posloupnost, protože poměr počtů číslic " + (i+1) + " a " + (j+1) +" je větší než 1.2";
                }
            }
        }
        
        for(int i = 0; i < sums.length; i++){
            for(int j = 0; j < sums.length; j++){
                for(int k = 0; k < sums.length; k++){
                    if(sums[i]== sums[j] && sums[j]== sums[k] && i!= j && i!=k && j!=k){
                        return filename + " pravděpodobně není náhodná posloupnost, protože počet číslic " + (i+1) + ", " + (j+1) + " a " + (k+1) +" je stejný";
                    }
                }
            }
        }
        
        Object[] patterntest = patterns(text, 7);
        if(patterntest != null){
            return filename + " pravděpodobně není náhodná posloupnost, protože se vzor " + patterntest[0] + " opakuje " + patterntest[1] +" krát";
        }
        
        return filename + " je pravděpodobně náhodná posloupnost";
    }
    
    public static int DiceATry(){
        Random rand = new Random();
        int dice1 = rand.nextInt(21);
        
        if      (dice1 == 0)                dice1 = 1;
        else if(dice1 >= 1  && dice1 <= 2)  dice1 = 2;
        else if(dice1 >= 3  && dice1 <= 5)  dice1 = 3;
        else if(dice1 >= 6  && dice1 <= 9)  dice1 = 4;
        else if(dice1 >= 10 && dice1 <= 14) dice1 = 5;
        else if(dice1 >= 15 && dice1 <= 20) dice1 = 6;
        
        
        return dice1;
    }
    
    public static int DiceBTry(){
        Random rand = new Random();
        int dice2 = rand.nextInt(21);
        
        if     (dice2 == 0)                 dice2 = 6;
        else if(dice2 >= 1  && dice2 <= 2)  dice2 = 5;
        else if(dice2 >= 3  && dice2 <= 5)  dice2 = 4;
        else if(dice2 >= 6  && dice2 <= 9)  dice2 = 3;
        else if(dice2 >= 10 && dice2 <= 14) dice2 = 2;
        else if(dice2 >= 15 && dice2 <= 20) dice2 = 1;
        
        return dice2;
    }
    
    public static void main(String [] args){
        //System.out.println("aaa");
        AMontyHall(10000);
        System.out.println(BRandomNumbers("random1.txt"));
        System.out.println(BRandomNumbers("random2.txt"));
        System.out.println(BRandomNumbers("random3.txt"));
        System.out.println(BRandomNumbers("random4.txt"));
        System.out.println(BRandomNumbers("random5.txt"));
        System.out.println(BRandomNumbers("random6.txt"));
        System.out.println(BRandomNumbers("random7.txt"));
        
        
    }
}
