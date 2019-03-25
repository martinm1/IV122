/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv6;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ColorProcessor;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author xmejzli2
 */
public class Cv6 {
    
   
    
    public static ArrayList<int[]> generatePoints(int posCenterX, int posCenterY, double number, double radius){
        
        ArrayList<int[]> points = new ArrayList();
        double angle = 0;
        double step = (360.0/number)* 2*Math.PI/360;
        
        for(int i = 0; i < number; i++){
            int[] point = new int[2];
            
            point[0] = posCenterX + (int) (radius*Math.cos(angle));
            point[1] = posCenterY + (int) (radius*Math.sin(angle));
            
            points.add(point);
            
            angle += step;
        }
        return points;
    }
    
    public static ColorProcessor drawBackground(ColorProcessor ip, int[] pixelRGB){
        int w = ip.getWidth();
        int h = ip.getHeight();
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                ip.putPixel(x, y, pixelRGB);
            }
        }
        return ip;
    }
    
    
    public static void AChaosGame(int number, double radius, double r, int numberOfIterations){
        int w = 1000;
        int h = 1000;
        
        int posCenterX = (int) Math.floor(w/2);
        int posCenterY = (int) Math.floor(h/2);
        
        ArrayList<int[]> points = generatePoints(posCenterX, posCenterY, number, radius);

        ColorProcessor ip = new ColorProcessor(w, h);
        
        int[] pixelRGB = new int[3];
        int[] pixelRGBinner = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        pixelRGBinner[0] = 0;
        pixelRGBinner[1] = 0;
        pixelRGBinner[2] = 0;
        
        ip = drawBackground(ip, pixelRGB);
        
        
        for(int[] point : points){
            ip.putPixel(point[0], point[1], pixelRGBinner);
        }
        
        Random randX = new Random();
        Random randY = new Random();
        
        int pointX = (int) randX.nextDouble()*w;
        int pointY = (int) randY.nextDouble()*h;
        
        Random randPoint = new Random();
        
        for(int i = 0; i < numberOfIterations; i++){
            ip.putPixel(pointX, pointY, pixelRGBinner);
            
            int pointIndex = randPoint.nextInt(number);
            
            pointX = (int)((pointX*r + points.get(pointIndex)[0]*(1.0-r)));
            pointY = (int)((pointY*r + points.get(pointIndex)[1]*(1.0-r)));
            
        }
        
        
        ImagePlus img = new ImagePlus("image", ip);
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("AChaosGame-n="+ number + ",r=" + r + ".png");
    }
    
    public static void main(String [] args){
        AChaosGame(3, 100, 0.5, 60000);
        AChaosGame(5, 100, (1.0/3.0), 60000);
        AChaosGame(5, 100, (3.0/8.0), 60000);
        AChaosGame(6, 100, (1.0/3.0), 60000);
    }
}
