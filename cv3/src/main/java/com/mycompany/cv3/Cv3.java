/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv3;

/**
 *
 * @author xmejzli2
 */



public class Cv3 {
    
    public static void weirdThing(){
        int size = 500;
        
        SVG svg = new SVG(size, size, "weirdThing");
        for(int i = 0; i <= size; i = i+25){
            svg.line(size, i, size-i, size, 0, 0, 0);
            svg.line(size, i, i, 0, 0, 0, 0);
            
            svg.line(0, i, size-i, 0, 0, 0, 0);
            svg.line(0, i, i, size, 0, 0, 0);
        }
        svg.save();
    }
    
    
    public static void A1(double step){
        int size = 500;
        SVG svg = new SVG(size, size, "A1");
        
        Turtle turtle = new Turtle();
        turtle.initTurtle(size/2, size/2, 0, svg);
        turtle.pendown();
        
        for(int i = 0; i< 8; i++){
            turtle.forward(step);
            turtle.left(45* 2*Math.PI/360);
        }
        svg.save();
    }
    
    public static void A2(double step){
        int size = 500;
        SVG svg = new SVG(size, size, "A2");
        
        Turtle turtle = new Turtle();
        turtle.initTurtle(size/2, size/2, 0, svg);
        turtle.pendown();
        
        for(int i = 0; i< 9; i++){
            turtle.forward(step);
            turtle.left(160* 2*Math.PI/360);
        }
        svg.save();
    }
    
    public static void BARelative(double step){
        int size = 500;
        SVG svg = new SVG(size, size, "BARelative");
        
        Turtle turtle = new Turtle();
        turtle.initTurtle(size/2, size/2, 0, svg);
        turtle.pendown();
        
        for(int i = 0; i< 5; i++){ //pentagram
            turtle.forward(step);
            turtle.right(144* 2*Math.PI/360);
        }
        
        turtle.left(72* 2*Math.PI/360);
        turtle.right(36* 2*Math.PI/360);
        
        
        double side = step/(2*Math.cos(36* 2*Math.PI/360));
        
        for(int i = 0; i< 5; i++){ //polygon
            turtle.forward(side);
            turtle.right(72* 2*Math.PI/360);
        }
        
        svg.save();
    }
    
    public static void BAAbsolutive(double step){
        int size = 500;
        SVG svg = new SVG(size, size, "BAAbsolutive");
        
        double pos1X = size/2;
        double pos1Y = size/2;
        
        double angle = 0;
        
        for(int i = 0; i < 5; i++){ //pentagram
            double pos2X = pos1X + step*Math.cos(angle); //generating sequence of points
            double pos2Y = pos1Y + step*Math.sin(angle);
            
            svg.line(pos1X, pos1Y, pos2X, pos2Y, 0, 0, 0);
            
            pos1X = pos2X;
            pos1Y = pos2Y;
            
            angle += 144* 2*Math.PI/360;
        }
        
        angle -= 72* 2*Math.PI/360;
        angle += 36* 2*Math.PI/360;
        
        double side = step/(2*Math.cos(36* 2*Math.PI/360));
        
        for(int i = 0; i< 5; i++){ //polygon
            double pos2X = pos1X + side*Math.cos(angle); //generating sequence of points
            double pos2Y = pos1Y + side*Math.sin(angle);
            
            svg.line(pos1X, pos1Y, pos2X, pos2Y, 0, 0, 0);
            
            pos1X = pos2X;
            pos1Y = pos2Y;
            angle += 72* 2*Math.PI/360;
        }
        
        svg.save();
    }
    
    public static void BBAbsolutive(int numberOfIterations){
        int size = 500;
        SVG svg = new SVG(size, size, "BBAbsolutive");
        
        double[] pointsX = new double[4];
        double[] pointsY = new double[4];
        
        pointsX[0] = 0;
        pointsY[0] = 0;
        
        pointsX[1] = size;
        pointsY[1] = 0;
        
        pointsX[2] = size;
        pointsY[2] = size;
        
        pointsX[3] = 0;
        pointsY[3] = size;
        
        for(int i = 0; i< numberOfIterations; i++){
            for(int j = 0; j < 4; j++){
                svg.line(pointsX[j], pointsY[j], pointsX[(j+1)%4], pointsY[(j+1)%4], 0, 0, 0);
            }
            
            double point0Xorig = pointsX[0];
            double point0Yorig = pointsY[0];
            
            
            for(int j = 0; j < 3; j++){
                double numberX = pointsX[j] + (1.0/4.0)*(pointsX[j+1] - pointsX[j]);
                pointsX[j] = numberX;
                
                double numberY = pointsY[j] + (1.0/4.0)*(pointsY[j+1] - pointsY[j]);
                pointsY[j] = numberY;
            }
            
            double numberX = pointsX[3] + (1.0/4.0)*(point0Xorig - pointsX[3]);
            pointsX[3] = numberX;
                
            double numberY = pointsY[3] + (1.0/4.0)*(point0Yorig - pointsY[3]);
            pointsY[3] = numberY;
        }
        
        svg.save();
    }
    
    public static void BCAbsolutive(double radius, double step){
        int size = 500;
        SVG svg = new SVG(size, size, "BCAbsolutive");
        
        double centerX = size/2;
        double centerY = size/2;
        
        for(double i = -radius; i < radius; i+=step){
            
            double angle = Math.acos(i/radius);
            
            svg.line(centerX + i, centerY + radius*Math.sin(angle), centerX + i, centerY - radius*Math.sin(angle), 0, 0, 0);
            svg.line(centerY + radius*Math.sin(angle), centerX + i, centerY - radius*Math.sin(angle), centerX + i, 0, 0, 0);
        }
        
        svg.save();
    }
    
    public static void BDAbsolutive(double radius, double step){
        int size = 500;
        SVG svg = new SVG(size, size, "BDAbsolutive");
        
        double centerX = size/2;
        double centerY = size/2;
        
        
        for(double i = radius; i > 0; i-=step){
            svg.line(centerX + i*Math.cos(-90* 2*Math.PI/360), centerY + i*Math.sin(-90* 2*Math.PI/360), 
                     centerX + i*Math.cos(-210* 2*Math.PI/360), centerY + i*Math.sin(-210* 2*Math.PI/360), 
                    0, 0, 0);
            
            svg.line(centerX + i*Math.cos(-210* 2*Math.PI/360), centerY + i*Math.sin(-210* 2*Math.PI/360), 
                     centerX + i*Math.cos(-330* 2*Math.PI/360), centerY + i*Math.sin(-330* 2*Math.PI/360), 
                    0, 0, 0);
            
            svg.line(centerX + i*Math.cos(-90* 2*Math.PI/360), centerY + i*Math.sin(-90* 2*Math.PI/360), 
                     centerX + i*Math.cos(-330* 2*Math.PI/360), centerY + i*Math.sin(-330* 2*Math.PI/360), 
                    0, 0, 0);
        }
        
        svg.save();
    }
    
    public static void BEAbsolutive(double radius){
        int size = 500;
        SVG svg = new SVG(size, size, "BEAbsolutive");
        
        double centerX = size/2;
        double centerY = size/2;
        
        double i = radius;
        double angle = 15;
        //for(double i = radius; i > 0; i-=10){
            for(int j = 0; j < 12; j++){
                svg.line(centerX + i*Math.cos(angle* 2*Math.PI/360), centerY + i*Math.sin(angle* 2*Math.PI/360), 
                     centerX + i*Math.cos((angle+30)* 2*Math.PI/360), centerY + i*Math.sin((angle+30)* 2*Math.PI/360), 
                    0, 0, 0);
                angle+=30;
            
            }
        
        //}
        
        svg.save();
    }
    
    public static void main(String [] args)
    {
        A1(90);
        A2(90);
        BARelative(90);
        BAAbsolutive(90);
        BBAbsolutive(16);
        BCAbsolutive(200, 10);
        BDAbsolutive(101, 10);
        BEAbsolutive(100); //NENI HOTOVE
    }
}
