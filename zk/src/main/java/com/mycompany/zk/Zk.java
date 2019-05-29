/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zk;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ColorProcessor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class Zk {

    
    public static void pascalTriangle(int h){
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
            for (int x = 1; x < w; x++)
            {
                field[x][y] = field[x-1][y-1].add(field[x][y-1]);
            }
        }
        
        //for (int y = 1; y < h; y++)
        //{
        //    for (int x = 1; x < w; x++)
        //    {
               // if(field[x][y].compareTo(new BigDecimal(0)) == 0) field[x][y] = new BigDecimal(-1);;
       //     }
        //}
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        int[] pixelRGB = new int[3];
    
        
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                ip.putPixel(x, y, pixelRGB);
            }
        }
        
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                pixelRGB[0] = 255;
                pixelRGB[1] = 255;
                pixelRGB[2] = 255;
                if(field[x][y].remainder(new BigDecimal(2)).intValue() != 0){
                    pixelRGB[0] = 0;
                    pixelRGB[1] = 0;
                    pixelRGB[2] = 0;
                }
                
                if(field[x][y].remainder(new BigDecimal(2)).intValue() != 0 &&
                        (x+y) % 5 == 0){
                
                    //pixelRGB[0] = 255;
                    //pixelRGB[1] = 255;
                    //pixelRGB[2] = 255;
                }
                
                
                
               // ip.putPixel(x + h/(y+1), y, pixelRGB);
               ip.putPixel(x, y, pixelRGB);
            }
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("pascalTriangle.png");
    }


    
    public static void C2Circles(double ratio, int limit, int pocet){
        
        
        
        int w = 500;
        int h = 500;
        int centerX = 250;
        int centerY = 250;
        
        int nevim = (int)(w-centerX)/pocet;
        
        int[] limits = new int[pocet];
        for(int i = 0; i < pocet; i++){
            limits[i] = i* nevim;
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
                double distance = Math.sqrt((x-centerX)*(x-centerX) + (y-centerY)*(y-centerY));
                
                int color = 0;
                if((1+Math.sin(ratio*distance))*127 > 128)
                    color = 255;
                //int color = (int) ((1+Math.sin(ratio*distance))*127);
                
                //if(x > limit && x < w - limit && y > limit && y < h - limit) {
                //    color = 255 - color;
                //}
                
                int prekonanelimity = pocet;
                for(int i = 0; i < pocet; i++){
                    if(x > limits[i] && x < w - limits[i] && y > limits[i] && y < h - limits[i]) {
                        prekonanelimity--;
                    }
                }
                if(prekonanelimity %2 == 0) color = 255 - color;
                
                pixelRGB[0] = color;
                pixelRGB[1] = color;
                pixelRGB[2] = color;
                ip.putPixel(x, y, pixelRGB);
            }
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("C2Circles.png");
    }
    
    public static double Mince(double n, double k){
        double probabilityFake = 1/n;
        double probabilityTrue = (n-1)/n;
        
        double headProbabilityTrue = 1.0/2.0;
        double headProbabilityFake = 3.0/4.0;
        
        double probabilityKHeadsTrue = Math.pow(headProbabilityTrue, k);
        double probabilityKHeadsFake = Math.pow(headProbabilityFake, k);
        
        double result = (probabilityKHeadsFake*probabilityFake)/(probabilityKHeadsFake*probabilityFake + probabilityKHeadsTrue*probabilityTrue);
        
        return result;
    }
    
    public static void reseniminci(int n){
        PrintWriter writer;
        try {
            writer = new PrintWriter("vysledek"+n, "UTF-8");

            for (int k = 1; k < n; k++) {
                writer.print(k + " " + Mince(n, k));
                writer.print("\n");
            }

            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Zk.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Zk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void BAAbsolutive(double step, int numoflevels, int numofpoints){
        int size = 500;
        SVG svg = new SVG(size, size, "BAAbsolutive");
        
        double pos1X = size/2;
        double pos1Y = size/2;
        
        double angle = 0;
        double angleChange = (360/15.0)* 2*Math.PI/360;
        
        for(int i0 = 0; i0< 2; i0++){
            
            for(int i = 0; i < 5; i++){ //pentagram
                double pos2X = pos1X + step*Math.cos(angle); //generating sequence of points
                double pos2Y = pos1Y + step*Math.sin(angle);

                angle += angleChange;
            }
        }
        
        svg.save();
    }
    
    public static void main(String [] args){
        reseniminci(10);
        reseniminci(20);
        reseniminci(30);
        
        /*ArrayList list = new ArrayList();
        list.add('A');
        list.add('B');
        list.add('C');
        //list.add('D');
        
        System.out.println("pernutace: "+ permutations(list, new ArrayList()));
        System.out.println("variace: "+ variations(list, new ArrayList(), 2));
        System.out.println("variace s opakovanim: "+ variationsWithRepetition(list, new ArrayList(), 2));
        System.out.println("kombinace: "+ combinations(list, new ArrayList(), 2));
        System.out.println("kombinace s opakovanim: : "+ combinationsWithRepetition(list, new ArrayList(), 2));*/
        pascalTriangle(100);
        C2Circles(0.1, 100, 10);
        
        /*
        System.out.println(piGregoryLeibniz(600));
        System.out.println(piArchimedes(600));
        System.out.println(piMonteCarlo(6000000));
        
        
        System.out.println(ApproximatePowerBisection(3.69, 4.7, 30, 1));
        System.out.println(ApproximatePowerRandom(3.69, 4.7, 30, 1));
        
        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);
        System.out.println(SeriesApproximatePower(3.69, 4.7, 100, 500, mc));

        System.out.println(Math.pow(3.69, 4.7));*/
        

    }
}
