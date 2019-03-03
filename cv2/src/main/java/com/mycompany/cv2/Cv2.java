/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv2;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ColorProcessor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author martin
 */
public class Cv2 {
    
    public static ArrayList permutations(ArrayList list, ArrayList tupleList){
        ArrayList finalList = new ArrayList();
        if(list.isEmpty()) {
            finalList.add(tupleList);
        }
        
        for(int i = 0; i < list.size(); i++){
            ArrayList tupleList1 = new ArrayList();
            tupleList1.addAll(tupleList);
            tupleList1.add(list.get(i));
            
            ArrayList list1 = new ArrayList();
            list1.addAll(list);
            list1.remove(i);
            
            finalList.addAll(permutations(list1, tupleList1));
        }
        return finalList;
    }
    
    public static ArrayList variations(ArrayList list, ArrayList tupleList, int k){
        ArrayList finalList = new ArrayList();
        if(k==0) {
            finalList.add(tupleList);
            return finalList;
        }
        
        for(int i = 0; i < list.size(); i++){
            ArrayList tupleList1 = new ArrayList();
            tupleList1.addAll(tupleList);
            tupleList1.add(list.get(i));
            
            ArrayList list1 = new ArrayList();
            list1.addAll(list);
            list1.remove(i);
            
            finalList.addAll(variations(list1, tupleList1, k-1));
        }
        return finalList;
    }
    
    public static ArrayList variationsWithRepetition(ArrayList list, ArrayList tupleList, int k){
        ArrayList finalList = new ArrayList();
        if(k==0) {
            finalList.add(tupleList);
            return finalList;
        }
        
        for(int i = 0; i < list.size(); i++){
            ArrayList tupleList1 = new ArrayList();
            tupleList1.addAll(tupleList);
            tupleList1.add(list.get(i));
            
            finalList.addAll(variationsWithRepetition(list, tupleList1, k-1));
        }
        return finalList;
    }
    
    public static ArrayList combinations(ArrayList list, ArrayList tupleList, int k){
        ArrayList finalList = new ArrayList();
        if(k==0) {
            finalList.add(tupleList);
            return finalList;
        }
        
        if(!list.isEmpty()){
            ArrayList list1 = new ArrayList();
            list1.addAll(list);
            list1.remove(0);

            ArrayList tupleList1 = new ArrayList();
            tupleList1.addAll(tupleList);
            tupleList1.add(list.get(0));

            ArrayList tupleList2 = new ArrayList();
            tupleList2.addAll(tupleList);


            finalList.addAll(combinations(list1, tupleList1, k-1));
            finalList.addAll(combinations(list1, tupleList2, k));
        }
        
        return finalList;
    }
    
    public static ArrayList combinationsWithRepetition(ArrayList list, ArrayList tupleList, int k){
        ArrayList finalList = new ArrayList();
        if(k==0) {
            finalList.add(tupleList);
            return finalList;
        }
        
        if(!list.isEmpty()){
            ArrayList list1 = new ArrayList();
            list1.addAll(list);
            list1.remove(0);
            
            for(int i=1;i<=k;i++){
                ArrayList tupleList1 = new ArrayList();
                tupleList1.addAll(tupleList);
                for(int j=0; j<i;j++){
                    tupleList1.add(list.get(0));
                }
                finalList.addAll(combinationsWithRepetition(list1, tupleList1, k-i));
            }

            ArrayList tupleList2 = new ArrayList();
            tupleList2.addAll(tupleList);
            
            finalList.addAll(combinationsWithRepetition(list1, tupleList2, k));
        }
        
        return finalList;
    }
    
    public static void pascalTriangle(int h, int d){
        if(d>256) d=256;
        int w = 2*h+1;
        
        BigDecimal[][] field = new BigDecimal[w][h];
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                field[x][y]=new BigDecimal(0);
            }
        }
        
        field[w/2][0]=new BigDecimal(1);
        
        for (int y = 1; y < h; y++)
        {
            for(int x = w/2 - y; x <= w/2 + y; x += 2)
            {
                field[x][y] = field[x-1][y-1].add(field[x+1][y-1]);
            }
            for(int x = w/2 - y+1; x <= w/2 + y-1; x += 2)
            {
                field[x][y] = new BigDecimal(2*d);
            }
        }
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                System.out.print(" "+field[x][y]+" ");
            }
            System.out.print("\n");
        }
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        int[] pixelRGB = new int[3];
        
        pixelRGB[0] = 0;
        pixelRGB[1] = 0;
        pixelRGB[2] = 0;
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                pixelRGB[0] = 0;
                
                pixelRGB[2] = (int)((255/d)*field[x][y].remainder(new BigDecimal(d)).intValue());
                
                if(pixelRGB[2] == 0 && !field[x][y].equals(new BigDecimal(2*d)) && x >= w/2 - y && x <= w/2 + y){
                    pixelRGB[0] = 255;
                }
                
                
                ip.putPixel(x, y, pixelRGB);
            }
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("pascalTriangle.png");
    }
    
    public static double piGregoryLeibniz(int numberOfIteratons){
        double pi = 0.0;
        
        for(int k = 0; k <= numberOfIteratons-1; k++){
            pi += ((double)(4*Math.pow((-1), k)))
                   /((double)(2*k +1));
        }
        
        return pi;
    }
    
    
    public static double piArchimedes(int numberOfIteratons){
        double a = 2.0*Math.sqrt(3);
        double b = 3.0;
        
        for(int i = 0; i < numberOfIteratons; i++){
            a=2*a*b/(a+b);
            b=Math.sqrt(a*b);
        }
        
        return a;
    }
    
    public static double piMonteCarlo(int numberOfIteratons){
        double total = 0;
        double success = 0;
        
        Random rand = new Random();
        
        for(int i = 0; i < numberOfIteratons; i++){
            double a = rand.nextDouble();
            double b = rand.nextDouble();
            
            total+=1.0;
            if(1 > a*a+b*b){
                success+=1.0;
            }
        }
        
        return (4.0*success/total);
    }
    
    public static void main(String [] args){
        ArrayList list = new ArrayList();
        list.add('A');
        list.add('B');
        list.add('C');
        //list.add('D');
        /*
        System.out.println("pernutace: "+ permutations(list, new ArrayList()));
        System.out.println("variace: "+ variations(list, new ArrayList(), 2));
        System.out.println("variace s opakovanim: "+ variationsWithRepetition(list, new ArrayList(), 2));
        System.out.println("kombinace: "+ combinations(list, new ArrayList(), 2));
        System.out.println("kombinace s opakovanim: : "+ combinationsWithRepetition(list, new ArrayList(), 2));
        pascalTriangle(500, 5);
        */
        
        System.out.println(piGregoryLeibniz(600));
        System.out.println(piArchimedes(600));
        System.out.println(piMonteCarlo(6000000));
    }
}
