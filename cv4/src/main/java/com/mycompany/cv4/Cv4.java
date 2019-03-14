/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv4;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ColorProcessor;

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
        img.show();
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
        img.show();
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
        img.show();
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
            
            ip.putPixel(x, y, pixelRGBinner);
        }
        
        
        ImagePlus img = new ImagePlus("image", ip);
        img.show();
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
        img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("CircleImplicit.png");
    }
    
    public static void main(String [] args)
    {
        //bitmap();
        //DiskImplicit(100);
        //CircleImplicit(100);
        //CircleParametric(100, 0.01);
        //SpiralParametric(0.01, 10*Math.PI);
        TriangleImplicit(200);
        
    }
}
