/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv8;

import java.util.ArrayList;

/**
 *
 * @author xmejzli2
 */
public class Cv8 {
    
    public static SVG drawNgon(SVG svg, double[][][] points, double startX, double startY){
        for(int i = 0; i < points.length; i++){
            svg.line(points[i][0][0] + startX, points[i][1][0] + startY, 
                    points[(i+1)%points.length][0][0] + startX, points[(i+1)%points.length][1][0] + startY
                    , 0, 0, 0);
        }
        return svg;
    }
    
    public static double[][][] square(double side){
        double[][] coordinates1 = new double[][]{
            {0}, {0}, {1}
        };
        
        double[][] coordinates2 = new double[][]{
            {side}, {0}, {1}
        };
        
        double[][] coordinates3 = new double[][]{
            {side}, {side}, {1}
        };
        
        double[][] coordinates4 = new double[][]{
            {0}, {side}, {1}
        };
        double[][][] square = new double[][][]{coordinates1, coordinates2, coordinates3, coordinates4};
        
        return square;
    }
    
    public static double[][][] triangle(double side){
        double[][] coordinates1 = new double[][]{
            {0}, {0}, {1}
        };
        
        double[][] coordinates2 = new double[][]{
            {side}, {0}, {1}
        };
        
        double[][] coordinates3 = new double[][]{
            {side/2}, {Math.sqrt(side*side*(1 - 1/4))}, {1}
        };
        
        double[][][] triangle = new double[][][]{coordinates1, coordinates2, coordinates3};
        
        return triangle;
    }
    
    public static double[][][] someimage(double side){
        double[][] coordinates1 = new double[][]{
            {0}, {0}, {1}
        };
        
        double[][] coordinates2 = new double[][]{
            {side/2}, {Math.sqrt(side*side*(1 - 1/4))}, {1}
        };
        
        double[][] coordinates3 = new double[][]{
            {side}, {0}, {1}
        };
        
        double[][] coordinates4 = new double[][]{
            {side}, {side}, {1}
        };
        
        double[][][] someimage = new double[][][]{coordinates1, coordinates2, coordinates3, coordinates4};
        
        return someimage;
    }
    
    public static double[][][] copyField(double[][][] points){
        double[][][] result = new double[points.length][points[0].length][points[0][0].length];
        
        for(int i = 0; i < points.length; i++){
            for(int j = 0; j < points[0].length; j++){
                for(int k = 0; k < points[0][0].length; k++){
                    result[i][j][k] = points[i][j][k];
                }
            }
        }
        return result;
    }
    
    public static double[][][] transformPoints(double[][] matrix, double[][][] points){
        MyMatrixUtils myutils = new MyMatrixUtils();
        
        double[][][] newpoints = copyField(points);
        
        for(int j = 0; j < points.length; j++){
            newpoints[j] = myutils.multiplyMatrices(matrix, newpoints[j]);
        }
        return newpoints;
    }
    
    public static void A1(double side, int numOfIterations){
        int size = 1000;
        SVG svg = new SVG(size, size, "A1");
        
        double centerX = 500.0;
        double centerY = 500.0;
        
        MyMatrixUtils myutils = new MyMatrixUtils();
        
        double[][][] square = square(side);
        square = transformPoints(myutils.translation(new double[]{-side, -side}), square);
        
        for(int i = 0; i < numOfIterations; i++){
            svg = drawNgon(svg, square, centerX, centerY);

            double[][] rotation = myutils.rotation(20);
            double[][] scaling = myutils.scaling(new double[]{1.1, 1.1});
            double[][] translation = myutils.translation(new double[]{5, 10});
            
            double[][][] composition = new double[][][]{rotation, scaling, translation};
            double[][] finalMatrix = myutils.combine(composition);
            
            square = transformPoints(finalMatrix, square);
        }
        
        svg.save();
    }
    
    public static void A2(double side, int numOfIterations){
        int size = 1000;
        SVG svg = new SVG(size, size, "A2");
        
        double centerX = 500.0;
        double centerY = 500.0;
        
        MyMatrixUtils myutils = new MyMatrixUtils();
        
        double[][][] square = square(side);
        square = transformPoints(myutils.translation(new double[]{-side/2, -side/2}), square);
        
        for(int i = 0; i < numOfIterations; i++){
            svg = drawNgon(svg, square, centerX, centerY);

            double[][] rotation = myutils.rotation(10);
            double[][] scaling = myutils.scaling(new double[]{1.1, 0.8});

            double[][][] composition = new double[][][]{rotation, scaling};
            double[][] finalMatrix = myutils.combine(composition);
            
            square = transformPoints(finalMatrix, square);
        }
        
        svg.save();
    }
    
    public static double[][][] shrink(double[][][] image, double ratio){
        MyMatrixUtils myutils = new MyMatrixUtils();
        double distanceX = image[0][0][0];
        double distanceY = image[0][1][0];
        
        double[][][] newimage = copyField(image);
        
        newimage = transformPoints(myutils.translation(new double[]{-distanceX, -distanceY}), newimage);
        newimage = transformPoints(myutils.scaling(new double[]{ratio, ratio}), newimage);
        newimage = transformPoints(myutils.translation(new double[]{distanceX, distanceY}), newimage);
        return newimage;
    }
    
    public static void BSierpinski(double side, int numOfIterations){
        int size = 1000;
        SVG svg = new SVG(size, size, "BSierpinski");
        
        double centerX = 500.0;
        double centerY = 500.0;
        
        MyMatrixUtils myutils = new MyMatrixUtils();
        
        double[][][] image0 = someimage(side);
        ArrayList<double[][][]> images = new ArrayList();
        images.add(image0);
        
        ArrayList<double[][][]> newimages = new ArrayList();
        
        double translate = side;
        for(int i = 0; i < numOfIterations; i++){
            translate = translate/2.040816327;
            
            for(double[][][] image: images){
                double[][][] image1 = shrink(image, 0.49);
                double[][][] image2 = shrink(image, 0.49);
                double[][][] image3 = shrink(image, 0.49);
                
                image2 = transformPoints(myutils.translation(new double[]{translate, 0}), image2);
                image3 = transformPoints(myutils.translation(new double[]{translate/2, translate}), image3);
                
                newimages.add(image1);
                newimages.add(image2);
                newimages.add(image3);
            }
            images = newimages;
            newimages = new ArrayList();
            
        }
        
        for(double[][][] image: images){
            svg = drawNgon(svg, image, centerX, centerY);
        }
        
        svg.save();
    }
    
    public static void BSierpinskiRelative(double side, int numOfIterations){
        int size = 1000;
        SVG svg = new SVG(size, size, "BSierpinskiRelative");
        
        double centerX = 500.0;
        double centerY = 500.0;
        
        MyMatrixUtils myutils = new MyMatrixUtils();
        
        double[][][] image0 = someimage(side);
        ArrayList<double[][][]> images = new ArrayList();
        images.add(image0);
        
        ArrayList<double[][][]> newimages = new ArrayList();
        
        double translate = side;
        for(int i = 0; i < numOfIterations; i++){
            translate = translate/2.040816327;
            
            for(double[][][] image: images){
                double[][][] image1 = shrink(image, 0.49);
                double[][][] image2 = shrink(image, 0.49);
                double[][][] image3 = shrink(image, 0.49);
                
                image2 = transformPoints(myutils.translation(new double[]{translate, 0}), image2);
                image3 = transformPoints(myutils.translation(new double[]{0, translate}), image3);
                
                newimages.add(image1);
                newimages.add(image2);
                newimages.add(image3);
            }
            images = newimages;
            newimages = new ArrayList();
            
        }
        
        for(double[][][] image: images){
            svg = drawNgon(svg, image, centerX, centerY);
        }
        
        svg.save();
    }
    

    
    public static void main(String [] args){
        A1(50, 11);
        A2(100, 16);
        
        BSierpinski(400,6);
        BSierpinskiRelative(400,6);
        
    }
}
