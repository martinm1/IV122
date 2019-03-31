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
import java.util.List;
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
    
    public static void BFeigenbaum(double xMin, double xMax, double x0, double rMin, double rMax, double rStep){
        int w = (int)Math.floor((rMax - rMin) / rStep);
        int h = (int)Math.floor((xMax - xMin) / rStep);

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
        
        for(double r = rMin; r < rMax; r += rStep)
        {
            double x = x0;
            for (int i = 0; i < 100; i++)
            {
                x = x*(1-x)*r;
            }
            
            for (int i = 100; i < 200; i++)
            {
                x = x*(1-x)*r;
                
                int pointX = (int)Math.floor((r - rMin) / rStep);
                int pointY = (int)Math.floor((x - xMin) / rStep);
                ip.putPixel(pointX, pointY, pixelRGBinner);
            }
        }
        
        ImagePlus img = new ImagePlus("image", ip);
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("BFeigenbaum.png");
    }
    
    
    
    
    public static void CLSystem(SVG svg, Turtle turtle, String name, String first, int numOfIt, Object[][] rewritingRules, Object[][] turningRules, Object[][] drawingRules, char push, char pop){
        
        for(int i = 0; i < numOfIt; i++){
            String second = "";
            for(int j = 0; j < first.length(); j++){
                boolean rewritten = false;
                
                for(int k = 0; k < rewritingRules.length; k++){
                    
                    if(first.charAt(j) == (char) rewritingRules[k][0]){
                        second = second + rewritingRules[k][1];
                        rewritten = true;
                    }
                    
                }
                if(!rewritten) second = second + first.charAt(j);
            }
            first = second;
        }
        
        for(int j = 0; j < first.length(); j++){
            for(int k = 0; k < drawingRules.length; k++){
                if(first.charAt(j) == (char) drawingRules[k][0]){
                    turtle.forward((double) drawingRules[k][1]);
                }
            }
            
            for(int k = 0; k < turningRules.length; k++){
                if(first.charAt(j) == (char) turningRules[k][0]){
                    turtle.right(((double) turningRules[k][1])* 2*Math.PI/360);
                }
            }
            if(first.charAt(j) == push) turtle.push();
            if(first.charAt(j) == pop) turtle.pop();
        }
        
        
    }
    
    public static void CLSystem1Koch(){
        Object[][] rewritingRules = new Object[][]{{'F', "F+F--F+F"}};
        Object[][] turningRules = new Object[][]{{'+', 60.0}, {'-', 300.0}};
        Object[][] drawingRules = new Object[][]{{'F', 10.0}};
        
        
        int size = 1000;
        SVG svg = new SVG(size, size, "CLSystem1Koch");

        Turtle turtle = new Turtle();
        turtle.initTurtle(size/2, size/2, 0, svg);
        turtle.pendown();
        
        for(int i = 0; i < 3; i++){
            CLSystem(svg, turtle,"CLSystem1Koch", "F", 3, rewritingRules, turningRules, drawingRules, 'x', 'x');
            turtle.left(120* 2*Math.PI/360);
        }
        
        svg.save();
    }
    
    public static void CLSystem2Sierpinski(){
        Object[][] rewritingRules = new Object[][]{{'A', "B-A-B"}, {'B', "A+B+A"}};
        Object[][] turningRules = new Object[][]{{'-', 60.0}, {'+', 300.0}};
        Object[][] drawingRules = new Object[][]{{'A', 10.0}, {'B', 10.0}};
        
        
        int size = 1000;
        SVG svg = new SVG(size, size, "CLSystem2Sierpinski");

        Turtle turtle = new Turtle();
        turtle.initTurtle(size/4, size/1.5, 0, svg);
        turtle.pendown();
        
        CLSystem(svg, turtle, "CLSystem2Sierpinski", "A", 6, rewritingRules, turningRules, drawingRules, 'x', 'x');

        svg.save();
    }
    
    public static void CLSystem3Hilbert(){
        Object[][] rewritingRules = new Object[][]{{'A', "- BF+AFA+FB-"}, {'B', "+AF-BFB-FA+"}};
        Object[][] turningRules = new Object[][]{{'-', 90.0}, {'+', 270.0}};
        Object[][] drawingRules = new Object[][]{{'A', 10.0}, {'B', 10.0}, {'F', 10.0}};
        
        
        int size = 1000;
        SVG svg = new SVG(size, size, "CLSystem3Hilbert");

        Turtle turtle = new Turtle();
        turtle.initTurtle(size/4, size/4, 0, svg);
        turtle.pendown();
        
        CLSystem(svg, turtle, "CLSystem3Hilbert", "A", 4, rewritingRules, turningRules, drawingRules, 'x', 'x');

        svg.save();
    }
    
    public static void CLSystem4Tree(){
        
        Object[][] rewritingRules = new Object[][]{{'A', "F[+A]-A"}, {'F', "FF"}};
        Object[][] turningRules = new Object[][]{{'-', 45.0}, {'+', 315.0}};
        Object[][] drawingRules = new Object[][]{{'A', 10.0}, {'F', 10.0}};
        
        int size = 1000;
        SVG svg = new SVG(size, size, "CLSystem4Tree");

        Turtle turtle = new Turtle();
        turtle.initTurtle(size/2, size/2, 0, svg);
        turtle.pendown();
        
        
        turtle.left(90* 2*Math.PI/360);
        CLSystem(svg, turtle, "CLSystem4Tree", "A", 5, rewritingRules, turningRules, drawingRules, '[', ']');

        svg.save();
    }
    
    public static void CLSystem5Tree2(){
        
        Object[][] rewritingRules = new Object[][]{{'A', "F-[[A]+A]+F[+FA]-A"}, {'F', "FF"}};
        Object[][] turningRules = new Object[][]{{'-', 25.0}, {'+', 335.0}};
        Object[][] drawingRules = new Object[][]{{'A', 10.0}, {'F', 10.0}};
        
        int size = 1000;
        SVG svg = new SVG(size, size, "CLSystem5Tree2");

        Turtle turtle = new Turtle();
        turtle.initTurtle(size/2, size/1.2, 0, svg);
        turtle.pendown();
        
        
        turtle.left(90* 2*Math.PI/360);
        CLSystem(svg, turtle, "CLSystem5Tree2", "A", 4, rewritingRules, turningRules, drawingRules, '[', ']');

        svg.save();
    }
    
    public static void CLSystem6(){
        
        Object[][] rewritingRules = new Object[][]{{'F', "FF-F-F-F-F-F+F"}};
        Object[][] turningRules = new Object[][]{{'-', 90.0}, {'+', 270.0}};
        Object[][] drawingRules = new Object[][]{{'F', 10.0}};
        
        int size = 1000;
        SVG svg = new SVG(size, size, "CLSystem6");

        Turtle turtle = new Turtle();
        turtle.initTurtle(size/2, size/2, 0, svg);
        turtle.pendown();
        
        
        CLSystem(svg, turtle, "CLSystem6", "F-F-F-F", 3, rewritingRules, turningRules, drawingRules, '[', ']');

        svg.save();
    }
    
    public static void CLSystem7(){
        
        Object[][] rewritingRules = new Object[][]{{'F', "FF-F-F-F-FF"}};
        Object[][] turningRules = new Object[][]{{'-', 90.0}, {'+', 270.0}};
        Object[][] drawingRules = new Object[][]{{'F', 10.0}};
        
        int size = 1000;
        SVG svg = new SVG(size, size, "CLSystem7");

        Turtle turtle = new Turtle();
        turtle.initTurtle(size/2, size/2, 0, svg);
        turtle.pendown();
        
        
        CLSystem(svg, turtle, "CLSystem7", "F-F-F-F", 3, rewritingRules, turningRules, drawingRules, '[', ']');

        svg.save();
    }
    
    public static void main(String [] args){
        AChaosGame(3, 100, 0.5, 60000);
        AChaosGame(5, 100, (1.0/3.0), 60000);
        AChaosGame(5, 100, (3.0/8.0), 60000);
        AChaosGame(6, 100, (1.0/3.0), 60000);
        BFeigenbaum(0, 1, 0.5, 2.4, 4.0, 0.0005);
        
        CLSystem1Koch();
        CLSystem2Sierpinski();
        CLSystem3Hilbert();
        CLSystem4Tree();
        CLSystem5Tree2();
        CLSystem6();
        CLSystem7();
    }
}
