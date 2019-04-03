/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv7;

/**
 *
 * @author martin
 */
public class ComplexNum {
    private double real;
    private double imaginary;
    
    public ComplexNum(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }
    
    public double getR() {
        return real;
    }

    public double getIm() {
        return imaginary;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }
    
    public double getAbs() {
        return real*real + imaginary*imaginary;
    }
    
    public ComplexNum plus(ComplexNum o){
        return new ComplexNum(
            this.getR() + o.getR(),
            this.getIm() + o.getIm()
        );
    }
    
    public ComplexNum minus(ComplexNum o){
        return new ComplexNum(
            this.getR() - o.getR(),
            this.getIm() - o.getIm()
        );
    }
    
    public ComplexNum mult(ComplexNum other){
        return new ComplexNum(
            this.getR()*other.getR() - this.getIm()*other.getIm(),
            this.getR()*other.getIm() + other.getR()*this.getIm()
        );
    }
    
    public ComplexNum div(ComplexNum o){
        return new ComplexNum(
            (this.getR()*o.getR() + this.getIm()*o.getIm())/(o.getR()*o.getR() + o.getIm()*o.getIm()),
            (o.getR()*this.getIm() - this.getR()*o.getIm())/(o.getR()*o.getR() + o.getIm()*o.getIm())
        );
    }
}
