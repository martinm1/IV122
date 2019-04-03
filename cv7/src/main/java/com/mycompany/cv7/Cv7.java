/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv7;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ColorProcessor;

import com.mycompany.cv7.ComplexNum;
/**
 *
 * @author xmejzli2
 */
public class Cv7 {
    
    
    
    public static void mandelbrot(double rMin, double rMax, double iMin, double iMax, double step, String name){
        int w = (int) Math.floor((rMax - rMin)/step);
        int h = (int) Math.floor((iMax - iMin)/step);
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        int[] pixelRGB = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        int[] pixelRGBinner = new int[3];
        
        pixelRGBinner[0] = 0;
        pixelRGBinner[1] = 0;
        pixelRGBinner[2] = 0;
        
        int xPos = 0;
        int yPos = 0;
        for (double r = rMin; r < rMax; r += step)
        {
            yPos = 0;
            for (double i = iMin; i < iMax; i += step)
            {
                //int xPos = (int) Math.floor(w*(r - rMin)/(rMax - rMin));
                //int yPos = (int) Math.floor(h*(i - iMin)/(iMax - iMin));
                
                double x = 0;
                double y = 0;
                
                int j = 0;
                for(; j < 350; j++){
                    double xn = x;
                    x = x*x-y*y + r;
                    y = 2*xn*y + i;
                    if(x*x+y*y > 4) break;
                }

                //System.out.println(xPos + " " + yPos + " " + Math.sqrt(x*x+y*y));
                if(x*x+y*y < 4){
                    ip.putPixel(xPos, yPos, pixelRGBinner);
                }
                else{
                    pixelRGB[0] = (int) Math.floor((double) j * 255.0/30.0);
                    pixelRGB[1] = j;
                    pixelRGB[2] = (int) Math.floor((double) (30-j) * 255.0/30.0);
                    ip.putPixel(xPos, yPos, pixelRGB);
                }
                yPos++;
            }
            xPos++;
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng(name);
    }
    
    public static void julia(double rMin, double rMax, double iMin, double iMax, double step, double cR, double cI){
        int w = (int) Math.floor((rMax - rMin)/step);
        int h = (int) Math.floor((iMax - iMin)/step);
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        int[] pixelRGB = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        int[] pixelRGBinner = new int[3];
        
        pixelRGBinner[0] = 0;
        pixelRGBinner[1] = 0;
        pixelRGBinner[2] = 0;
        
        for (double r = rMin; r < rMax; r += step)
        {
            for (double i = iMin; i < iMax; i += step)
            {
                int xPos = (int) Math.floor(w*(r - rMin)/(rMax - rMin));
                int yPos = (int) Math.floor(h*(i - iMin)/(iMax - iMin));
                
                double x = r;
                double y = i;
                
                int j = 0;
                for(; j < 250; j++){
                    double xn = x;
                    x = x*x-y*y + cR;
                    y = 2*xn*y + cI;
                    if(Math.sqrt(x*x+y*y) > 2) break;
                }

                //System.out.println(xPos + " " + yPos + " " + Math.sqrt(x*x+y*y));
                if(Math.sqrt(x*x+y*y) < 2){
                    ip.putPixel(xPos, yPos, pixelRGBinner);
                }
                else{
                    pixelRGB[0] = (int) Math.floor((double) j * 255.0/30.0);
                    pixelRGB[1] = j;
                    pixelRGB[2] = (int) Math.floor((double) (30-j) * 255.0/30.0);
                    ip.putPixel(xPos, yPos, pixelRGB);
                }
            }
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("julia.png");
    }
    
    public static void newton(double rMin, double rMax, double iMin, double iMax, double step, int numOfIterations){
        int w = (int) Math.floor((rMax - rMin)/step);
        int h = (int) Math.floor((iMax - iMin)/step);
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        int[] pixelRGB = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        int[] pixelRGBinner = new int[3];
        
        pixelRGBinner[0] = 0;
        pixelRGBinner[1] = 0;
        pixelRGBinner[2] = 0;
        
        for (double r = rMin; r < rMax; r += step)
        {
            for (double i = iMin; i < iMax; i += step)
            {
                int xPos = (int) Math.floor(w*(r - rMin)/(rMax - rMin));
                int yPos = (int) Math.floor(h*(i - iMin)/(iMax - iMin));
                
                double x = r;
                double y = i;
                
                int j = 0;
                ComplexNum z = new ComplexNum(r, i);
                
                for(; j < numOfIterations; j++){
                    z = z.minus(
                            ( (z.mult(z).mult(z) ).minus(new ComplexNum(1, 0)) )
                            .div(new ComplexNum(3, 0).mult(z.mult(z))) 
                        );
                }
                ComplexNum red = new ComplexNum(1,0).minus(z);
                ComplexNum blue = new ComplexNum(-0.5, Math.sqrt(3/2)).minus(z);
                
                //System.out.println(xPos + " " + yPos + " " + Math.sqrt(x*x+y*y));
                if(red.getAbs() < 0.1){
                    pixelRGBinner[0] = 255;
                    pixelRGBinner[1] = 0;
                    pixelRGBinner[2] = 0;
                    ip.putPixel(xPos, yPos, pixelRGBinner);
                }
                else if(blue.getAbs() < 0.1){
                    pixelRGBinner[0] = 0;
                    pixelRGBinner[1] = 0;
                    pixelRGBinner[2] = 255;
                    ip.putPixel(xPos, yPos, pixelRGBinner);
                }
                else{
                    pixelRGBinner[0] = 0;
                    pixelRGBinner[1] = 255;
                    pixelRGBinner[2] = 0;
                    ip.putPixel(xPos, yPos, pixelRGBinner);
                }
            }
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("newton.png");
    }
    
    public static void videoMandelbrot(int numOfPics, double zoom, double focusR, double focusI){
        
        double rMin = -1.5;
        double rMax = 1.5;
        double iMin = -1.5;
        double iMax = 1.5;
        double step = 0.005;
        
        double stepRatio = (rMax - rMin)/step;
        
        for(int i = 10000; i < numOfPics + 10000 ; i++){
            mandelbrot(rMin + focusR, rMax + focusR, iMin + focusI, iMax + focusI, step, "video/000" + i +".png");
            
            rMin/=zoom;
            rMax/=zoom;
            iMin/=zoom;
            iMax/=zoom;
            step = (rMax - rMin)/stepRatio;
        }
        
    }
    
    public static void main(String [] args){
        mandelbrot(-2.0, 1.0, -1.0, 1.0, 0.001, "mandelbrot.png");
        julia(-1.5, 1.5, -1.2, 1.2, 0.001, -0.13, 0.75);
        newton(-1.5, 1.5, -1.2, 1.2, 0.001, 20);
        videoMandelbrot(600, 1.008, -1.403, 0);
    }
}
