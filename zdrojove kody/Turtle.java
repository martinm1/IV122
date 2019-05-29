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
public class Turtle {
    double positionX;
    double positionY;
    double angle;
    boolean pen = false;
    SVG svg;
    
    
    public void initTurtle(double positionX, double positionY, double angle, SVG svg){
        this.positionX = positionX;
        this.positionY = positionY;
        this.angle = angle;
        this.svg = svg;
    }
    
    public void forward(double step){
        if(pen){
            svg.line(positionX, positionY, positionX + step*Math.cos(angle), positionY + step*Math.sin(angle), 0, 0, 0);
        }
        positionX += step*Math.cos(angle);
        positionY += step*Math.sin(angle);
    }
    
    public void back(double step){
        forward(-step);
    }
    
    public void right(double angle){
        this.angle += angle;
    }
    
    public void left(double angle){
        right(-angle);
    }
    
    public void penup(){
        pen = false;
    }
    
    public void pendown(){
        pen = true;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}
