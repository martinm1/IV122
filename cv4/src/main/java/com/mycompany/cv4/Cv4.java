/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv4;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ColorProcessor;
import java.util.ArrayList;

/**
 *
 * @author xmejzli2
 */
public class Cv4 {
    
    public static void bitmap(){
        int w = 500;
        int h = 500;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        int[] pixelRGB = new int[3];
        
        pixelRGB[0] = 0;
        pixelRGB[1] = 0;
        pixelRGB[2] = 0;
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                pixelRGB[0] = x*(256-1)/w;
                pixelRGB[2] = y*(256-1)/h;
                ip.putPixel(x, y, pixelRGB);
            }
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("bitmap.png");
    }
    
    public static void DiskImplicit(double radius){
        int w = 500;
        int h = 500;
        
        int centerX = 250;
        int centerY = 250;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        int[] pixelRGB = new int[3];
        int[] pixelRGBinner = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        pixelRGBinner[0] = 0;
        pixelRGBinner[1] = 0;
        pixelRGBinner[2] = 0;
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                if((x-centerX)*(x-centerX) + (y-centerY)*(y-centerY) < radius*radius)
                    ip.putPixel(x, y, pixelRGBinner);
                else
                    ip.putPixel(x, y, pixelRGB);
            }
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("DiskImplicit.png");
    }
    
    public static void CircleImplicit(double radius){
        int w = 500;
        int h = 500;
        
        int centerX = 250;
        int centerY = 250;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        int[] pixelRGB = new int[3];
        int[] pixelRGBinner = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        pixelRGBinner[0] = 0;
        pixelRGBinner[1] = 0;
        pixelRGBinner[2] = 0;
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                if((x-centerX)*(x-centerX) + (y-centerY)*(y-centerY) < radius*radius
                 && (x-centerX)*(x-centerX) + (y-centerY)*(y-centerY) > (radius-3)*(radius-3)
                  )
                    ip.putPixel(x, y, pixelRGBinner);
                else
                    ip.putPixel(x, y, pixelRGB);
            }
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("CircleImplicit.png");
    }
    
    public static void CircleParametric(double radius, double step){
        int w = 500;
        int h = 500;
        
        int centerX = 250;
        int centerY = 250;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        int[] pixelRGB = new int[3];
        int[] pixelRGBinner = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        pixelRGBinner[0] = 0;
        pixelRGBinner[1] = 0;
        pixelRGBinner[2] = 0;
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                ip.putPixel(x, y, pixelRGB);
            }
        }
        
        for(double i = 0; i < 2*Math.PI; i+=step){
            int x = centerX + (int) Math.floor(radius*Math.cos(i)+0.5);
            int y = centerY + (int) Math.floor(radius*Math.sin(i)+0.5);
            
            ip.putPixel(x, y, pixelRGBinner);
        }
        
        
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("CircleParametric.png");
    }
    
    public static void SpiralParametric(double step, double limit){
        int w = 500;
        int h = 500;
        
        int centerX = 250;
        int centerY = 250;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        int[] pixelRGB = new int[3];
        int[] pixelRGBinner = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        pixelRGBinner[0] = 0;
        pixelRGBinner[1] = 0;
        pixelRGBinner[2] = 0;
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                ip.putPixel(x, y, pixelRGB);
            }
        }
        
        double radius = 0;
        
        for(double i = 0; i < limit; i+=step){
            radius += step;
            int x = centerX + (int) Math.floor(radius*Math.cos(i)+0.5);
            int y = centerY + (int) Math.floor(radius*Math.sin(i)+0.5);
            
            pixelRGBinner[0] = (int) (255*(x)/(w));
            pixelRGBinner[2] = (int) (255*(y)/(w));
            pixelRGBinner[1] = (int) (255*(w - x)/(w));
            
            ip.putPixel(x, y, pixelRGBinner);
        }
        
        
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("SpiralParametric.png");
    }
    
    public static void TriangleImplicit(int side){
        int w = 500;
        int h = 500;
        
        int centerX = 250;
        int centerY = 250;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        int[] pixelRGB = new int[3];
        int[] pixelRGBinner = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        pixelRGBinner[0] = 0;
        pixelRGBinner[1] = 0;
        pixelRGBinner[2] = 0;
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                if(y <= centerY
                   && x>= centerX && x<= centerX + side
                   && y >= centerY - Math.tan(60 * 2*Math.PI/360)*(x-centerX)
                   && y >= centerY - Math.tan(60 * 2*Math.PI/360)*(centerX+ side - x)){
                    
                    pixelRGBinner[0] = (int) (255*(x - centerX)/(side)); //x*(256-1)/w;
                    pixelRGBinner[2] = (int) (255*(y - centerY)/((side/2)*Math.tan(60 * 2*Math.PI/360)));
                    pixelRGBinner[1] = (int) (255*(side - x + centerX)/(side));
                    ip.putPixel(x, y, pixelRGBinner);
                
                }
                else
                    ip.putPixel(x, y, pixelRGB);
            }
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("TriangleImplicit.png");
    }
    
    public static void ellipseImplicit(double distance){
        int w = 500;
        int h = 500;
        
        int centerX = 250;
        int centerY = 250;
        
        int focus1X = centerX - 50;
        int focus2X = centerX + 50;
        
        int focus1Y = centerY + 50;
        int focus2Y = centerY - 50;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        int[] pixelRGB = new int[3];
        int[] pixelRGBinner = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        pixelRGBinner[0] = 0;
        pixelRGBinner[1] = 0;
        pixelRGBinner[2] = 0;
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                double sumOfDistances = Math.sqrt((x-focus1X)*(x-focus1X) + (y-focus1Y)*(y-focus1Y)) + Math.sqrt((x-focus2X)*(x-focus2X) + (y-focus2Y)*(y-focus2Y));
                if(sumOfDistances < distance)
                {
                    int value = (int) (255*Math.pow((sumOfDistances/distance), 8));
                    pixelRGBinner[0] = value;
                    pixelRGBinner[1] = value;
                    pixelRGBinner[2] = value;
                    ip.putPixel(x, y, pixelRGBinner);
                }
                else
                    ip.putPixel(x, y, pixelRGB);
            }
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("ellipseImplicit.png");
    }
    
    public static ColorProcessor drawLine(ColorProcessor ip, double ax, double ay, double bx, double by, double tolerance){
        int w = ip.getWidth();
        int h = ip.getHeight();
        
        int[] pixelRGBinner = new int[3];
        
        pixelRGBinner[0] = 0;
        pixelRGBinner[1] = 0;
        pixelRGBinner[2] = 0;
        
        double vectorX = bx - ax;
        double vectorY = by - ay;
        
        double distAB = Math.sqrt(vectorX*vectorX + vectorY*vectorY);
        
        double c = vectorY*ax - vectorX*ay;
        double a = -vectorY;
        double b = vectorX;
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                double vectorPAX = x - ax;
                double vectorPAY = y - ay;
                double distPA = Math.sqrt(vectorPAX*vectorPAX + vectorPAY*vectorPAY);
                
                double vectorPBX = x - bx;
                double vectorPBY = y - by;
                double distPB = Math.sqrt(vectorPBX*vectorPBX + vectorPBY*vectorPBY);
                
                if(Math.abs((a*x + b*y + c)/Math.sqrt(a*a+b*b)) < tolerance && distPA <= distAB && distPB <= distAB)
                        ip.putPixel(x, y, pixelRGBinner);
            }
        }
        return ip;
    }
        
    public static ColorProcessor drawOutline(ColorProcessor ip, ArrayList pointsX, ArrayList pointsY, double tolerance){
        if(pointsX.size() > 1){
            for(int i = 0; i < pointsX.size() - 1; i++){
                ip = drawLine(ip, (double) pointsX.get(i), (double) pointsY.get(i), (double) pointsX.get(i+1), (double) pointsY.get(i+1), tolerance);
            }
            ip = drawLine(ip, (double) pointsX.get(0), (double) pointsY.get(0), (double) pointsX.get(pointsX.size() - 1), (double) pointsY.get(pointsX.size() - 1), tolerance);
        }
        return ip;
    }
    
    public static ColorProcessor fillOutline(ColorProcessor ip){
        int w = ip.getWidth();
        int h = ip.getHeight();
        
        int[] pixelRGBinner = new int[3];
        int[] pixelRGBcheck = new int[3];
        
        pixelRGBinner[0] = 0;
        pixelRGBinner[1] = 0;
        pixelRGBinner[2] = 0;
        
        boolean readyToChange = false;
        boolean draw = false;
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                ip.getPixel(x, y, pixelRGBcheck);
                
                if(pixelRGBcheck[0] == 0) readyToChange = true;
                
                if(pixelRGBcheck[0] == 255 && readyToChange){
                    readyToChange = false;
                    
                    boolean hasEnd = false; //check if the region has any end, required for drawing only
                    for (int y2 = y+1; y2 < h; y2++){
                        ip.getPixel(x, y2, pixelRGBcheck);
                        if(pixelRGBcheck[0] == 0) hasEnd = true;
                    }
                    if(hasEnd || draw) draw = !draw;
                }
                
                if (draw) ip.putPixel(x, y, pixelRGBinner);
            }
        }
        
        return ip;
    }
    
    public static void B(){
        int w = 500;
        int h = 500;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        int[] pixelRGB = new int[3];
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        
        ArrayList pointsX = new ArrayList();
        ArrayList pointsY = new ArrayList();
        
        pointsX.add(100.0);
        pointsY.add(100.0);
        
        pointsX.add(250.0);
        pointsY.add(250.0);
        
        pointsX.add(280.0);
        pointsY.add(120.0);
        
        pointsX.add(300.0);
        pointsY.add(300.0);
        
        pointsX.add(80.0);
        pointsY.add(320.0);
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                ip.putPixel(x, y, pixelRGB);
            }
        }
        
        ip = drawOutline(ip, pointsX, pointsY, 1);
        ip = fillOutline(ip);
        
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("B.png");
        
    }
        
    public static void main(String [] args)
    {
        //bitmap();
        DiskImplicit(100);
        CircleImplicit(100);
        CircleParametric(100, 0.01);
        SpiralParametric(0.001, 50*Math.PI);
        TriangleImplicit(200);
        ellipseImplicit(200);
        B();
    }
}
