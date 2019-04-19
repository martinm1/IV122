/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv8;

/**
 *
 * @author xmejzli2
 */
public class Cv8 {
    
    public static SVG drawNgon(SVG svg, double[][] points){

        
        for(int i = 0; i < points.length; i++){
            svg.line(points[i][0], points[i][1], points[(i+1)%points.length][0], points[(i+1)%points.length][1], 0, 0, 0);
        }
        return svg;
    }
    
    public static void A1(double side, int numOfIterations){
        int size = 1000;
        SVG svg = new SVG(size, size, "A1");
        
        double centerX = 500.0;
        double centerY = 500.0;
        
        MyMatrixUtils myutils = new MyMatrixUtils();
        
        double[][] coordinates1 = new double[][]{
            {0}, {0}, {1}
        };
        
        double[][] coordinates2 = new double[][]{
            {0}, {-side}, {1}
        };
        
        double[][] coordinates3 = new double[][]{
            {-side}, {-side}, {1}
        };
        
        double[][] coordinates4 = new double[][]{
            {-side}, {0}, {1}
        };
        
        for(int i = 0; i < numOfIterations; i++){
            svg = drawNgon(
                svg, 
                new double[][]{
                    {coordinates1[0][0] + centerX, coordinates1[1][0] + centerY},
                    {coordinates2[0][0] + centerX, coordinates2[1][0] + centerY},
                    {coordinates3[0][0] + centerX, coordinates3[1][0] + centerY},
                    {coordinates4[0][0] + centerX, coordinates4[1][0] + centerY}
                }
            );

            double[][] rotation = myutils.rotation(20);
            double[][] scaling = myutils.scaling(new double[]{1.1, 1.1});
            double[][] translation = myutils.translation(new double[]{5, 10});

            double[][][] composition = new double[][][]{rotation, scaling, translation};


            double[][] finalMatrix = myutils.combine(composition);
            //myutils.printMatrix(finalMatrix);

            coordinates1 = myutils.multiplyMatrices(finalMatrix, coordinates1);
            coordinates2 = myutils.multiplyMatrices(finalMatrix, coordinates2);
            coordinates3 = myutils.multiplyMatrices(finalMatrix, coordinates3);
            coordinates4 = myutils.multiplyMatrices(finalMatrix, coordinates4);
        }
        
        svg.save();
    }
    
    public static void A2(double side, int numOfIterations){
        int size = 1000;
        SVG svg = new SVG(size, size, "A2");
        
        double centerX = 500.0;
        double centerY = 500.0;
        
        MyMatrixUtils myutils = new MyMatrixUtils();
        
        double[][] coordinates1 = new double[][]{
            {side/2}, {side/2}, {1}
        };
        
        double[][] coordinates2 = new double[][]{
            {side/2}, {-side/2}, {1}
        };
        
        double[][] coordinates3 = new double[][]{
            {-side/2}, {-side/2}, {1}
        };
        
        double[][] coordinates4 = new double[][]{
            {-side/2}, {side/2}, {1}
        };
        
        for(int i = 0; i < numOfIterations; i++){
            svg = drawNgon(
                svg, 
                new double[][]{
                    {coordinates1[0][0] + centerX, coordinates1[1][0] + centerY},
                    {coordinates2[0][0] + centerX, coordinates2[1][0] + centerY},
                    {coordinates3[0][0] + centerX, coordinates3[1][0] + centerY},
                    {coordinates4[0][0] + centerX, coordinates4[1][0] + centerY}
                }
            );

            double[][] rotation = myutils.rotation(10);
            double[][] scaling = myutils.scaling(new double[]{1.1, 0.8});

            double[][][] composition = new double[][][]{rotation, scaling};


            double[][] finalMatrix = myutils.combine(composition);
            //myutils.printMatrix(finalMatrix);

            coordinates1 = myutils.multiplyMatrices(finalMatrix, coordinates1);
            coordinates2 = myutils.multiplyMatrices(finalMatrix, coordinates2);
            coordinates3 = myutils.multiplyMatrices(finalMatrix, coordinates3);
            coordinates4 = myutils.multiplyMatrices(finalMatrix, coordinates4);
        }
        
        svg.save();
    }
    
    
    public static void main(String [] args){
        MyMatrixUtils utils = new MyMatrixUtils();
        //utils.printMatrixMultiplicationTest();
        A1(50, 11);
        A2(100, 16);
        
    }
}
