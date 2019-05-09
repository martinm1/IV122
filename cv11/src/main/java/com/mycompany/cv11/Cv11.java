/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv11;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ColorProcessor;
import java.math.BigDecimal;
import java.util.Random;

/**
 *
 * @author martin
 */
public class Cv11 {
    
    public static double[] generatePointsX(int n, double size){
        Random r = new Random();
        
        double[] pointsX = new double[n];
                
        for(int i = 0; i < n; i++){
            pointsX[i] = r.nextDouble();
            pointsX[i]*=size;
        }
        return pointsX;
    }
    
    public static double[] generatePointsYLine(double a, double b, double[] pointsX, double sigma){
        Random r = new Random();
        
        double[] pointsY = new double[pointsX.length];
        
        for(int i = 0; i < pointsX.length; i++){
            pointsY[i] = r.nextGaussian()*sigma+(a*pointsX[i]+b);
        }
        return pointsY;
    }
    
    public static ColorProcessor drawLine(ColorProcessor ip, double a, double b, double c, double tolerance, int[] pixelRGBinner){
        int w = ip.getWidth();
        int h = ip.getHeight();
        
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                if(Math.abs((a*x + b*y + c)/Math.sqrt(a*a+b*b)) < tolerance)
                        ip.putPixel(x, y, pixelRGBinner);
            }
        }
        return ip;
    }
    
    public static double SSE(double a, double b, double[] pointsX, double[] pointsY){
        double sum = 0;
        for(int i = 0; i < pointsX.length; i++){
            sum += (pointsY[i] - (a*pointsX[i] + b))*(pointsY[i] - (a*pointsX[i] + b));
        }
        return sum;
    }
    
    public static void ALinearRegression(double a, double b, int n, double sigma, int size, double step){
        
        int w = size;
        int h = size;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        int[] pixelRGB = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        int[] pixel1RGB = new int[3];
        
        pixel1RGB[0] = 0;
        pixel1RGB[1] = 0;
        pixel1RGB[2] = 255;
        
        int[] pixel2RGB = new int[3];
        
        pixel2RGB[0] = 0;
        pixel2RGB[1] = 0;
        pixel2RGB[2] = 0;
        
        //generate data
        double[] pointsX = generatePointsX(n, size);
        double[] pointsY = generatePointsYLine(a, b, pointsX, sigma);
        
        
        //draw data
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                ip.putPixel(x, y, pixelRGB);
            }
        }
        
        for(int i = 0; i < n; i++){
            if(pointsX[i] >= 0 && pointsX[i] < size && pointsY[i] >= 0 && pointsY[i] < size){
                ip.putPixel((int) Math.floor(pointsX[i]),(int) Math.floor(pointsY[i]), pixel1RGB);
            }
        }
        
        ip = drawLine(ip, a, -1.0, b, 1.0, pixel2RGB);
        
        //grid search
        
        double bestSSE = Double.MAX_VALUE;
        double opta = 0.0;
        double optb = 0.0;
        
        for(double newa = 0.0; newa < size; newa+=step){
            for(double newb = 0.0; newb < size; newb+=step){
                if(SSE(newa, newb, pointsX, pointsY) < bestSSE){
                    opta = newa;
                    optb = newb;
                    bestSSE = SSE(newa, newb, pointsX, pointsY);
                }
            }
        }
        
        //draw result
        ip = drawLine(ip, opta, -1.0, optb, 1.0, pixel1RGB);
        
        //save
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("ALinearRegression.png");
    }
    
    
    
    
    
    public static double[][] generateClusterCenters(int k, double size, double edge){
        Random r = new Random();
        
        double[][] clusterCenters = new double[k][2];
        
        for(int i = 0; i < clusterCenters.length; i++){
            clusterCenters[i][0] = r.nextDouble()*(size-2*edge)+edge;
            clusterCenters[i][1] = r.nextDouble()*(size-2*edge)+edge;
        }
        return clusterCenters;
    }
    
    public static double[] generatePointsXOfClusters(int nForCluster, double[][] clusterCenters, double sigma){
        Random r = new Random();
        
        double[] pointsX = new double[nForCluster*clusterCenters.length];
        int k = 0;
        
        for(int i = 0; i < clusterCenters.length; i++){
            for(int j = 0; j < nForCluster; j++){
                pointsX[k] = r.nextGaussian()*sigma+clusterCenters[i][0];
                k++;
            }
        }
        return pointsX;
    }
    
    public static double[] generatePointsYOfClusters(int nForCluster, double[][] clusterCenters, double sigma){
        Random r = new Random();
        
        double[] pointsY = new double[nForCluster*clusterCenters.length];
        int k = 0;
        
        for(int i = 0; i < clusterCenters.length; i++){
            for(int j = 0; j < nForCluster; j++){
                pointsY[k] = r.nextGaussian()*sigma+clusterCenters[i][1];
                k++;
            }
        }
        return pointsY;
    }
    
    public static void BClusterDetection(int k, int nForCluster, int size, double sigma, int numOfIterations){
        int w = size;
        int h = size;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        int[] pixelRGB = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        int[] pixel1RGB = new int[3];
        
        pixel1RGB[0] = 0;
        pixel1RGB[1] = 0;
        pixel1RGB[2] = 0;
        
        
        //generate data
        double[][] clusterCenters = generateClusterCenters(k, size, 3*sigma);
        double[] pointsX = generatePointsXOfClusters(nForCluster, clusterCenters, sigma);
        double[] pointsY = generatePointsYOfClusters(nForCluster, clusterCenters, sigma);
        
        //detect clusters
        double[][] newClusterCenters = generateClusterCenters(k, size, 3*sigma);
        int[] optcluster = new int[pointsX.length];
        
        
        
        for(int i = 0; i< numOfIterations; i++){
            
            for(int j = 0; j < pointsX.length; j++){
                double mindistance = Double.MAX_VALUE;
                for(int l = 0; l < newClusterCenters.length; l++){
                    double distance = Math.sqrt(
                        (pointsX[j] - newClusterCenters[l][0]) * (pointsX[j] - newClusterCenters[l][0])
                       +(pointsY[j] - newClusterCenters[l][1]) * (pointsY[j] - newClusterCenters[l][1])
                    );
                    if(distance < mindistance){
                        mindistance = distance;
                        optcluster[j] = l;
                    }
                }
            }
            
            //draw data
            for (int x = 0; x < w; x++)
            {
                for (int y = 0; y < h; y++)
                {
                    ip.putPixel(x, y, pixelRGB);
                }
            }
            
            for(int j = 0; j < clusterCenters.length; j++){
                for(int x = (int) Math.floor(clusterCenters[j][0]) - 2; x < (int) Math.floor(clusterCenters[j][0]) + 2; x++){
                    for(int y = (int) Math.floor(clusterCenters[j][1]) - 2; y < (int) Math.floor(clusterCenters[j][1]) + 2; y++){
                        ip.putPixel(x, y, pixel1RGB);
                    }
                }
            }
            
            for(int j = 0; j < newClusterCenters.length; j++){
                for(int x = (int) Math.floor(newClusterCenters[j][0]) - 2; x < (int) Math.floor(newClusterCenters[j][0]) + 2; x++){
                    for(int y = (int) Math.floor(newClusterCenters[j][1]) - 2; y < (int) Math.floor(newClusterCenters[j][1]) + 2; y++){
                        ip.putPixel(x, y, 
                            new int[]{(150+ j*59) % 255, j*163 % 255, j*97 % 255});
                    }
                }
            }
            
            for(int j = 0; j < pointsX.length; j++){
                if(pointsX[j] >= 0 && pointsX[j] < size && pointsY[j] >= 0 && pointsY[j] < size){
                    ip.putPixel((int) Math.floor(pointsX[j]),(int) Math.floor(pointsY[j]), 
                            new int[]{(150+ optcluster[j]*59) % 255, optcluster[j]*163 % 255, optcluster[j]*97 % 255});
                }
            }
            
            //save
            ImagePlus img = new ImagePlus("image", ip);
            //img.show();
            FileSaver fs = new FileSaver(img);
            fs.saveAsPng("BClusterDetection"+i+".png");
            
            //move cluster centers
            BigDecimal[][] newCenters = new BigDecimal[k][2];
            
            
            for(int j = 0; j < newClusterCenters.length; j++){
                newCenters[j][0] = new BigDecimal(0);
                newCenters[j][1] = new BigDecimal(0);
            }
            
            int[] newClusterCentersPointsNumber = new int[k];
            for(int j = 0; j < k; j++){
                newClusterCentersPointsNumber[j] = 0;
            }
            
            
            for(int j = 0; j < pointsX.length; j++){
                newCenters[optcluster[j]][0] = newCenters[optcluster[j]][0].add(new BigDecimal(pointsX[j]));
                newCenters[optcluster[j]][1] = newCenters[optcluster[j]][1].add(new BigDecimal(pointsY[j]));
                
                newClusterCentersPointsNumber[optcluster[j]]++;
            }
            

            
            for(int j = 0; j < newClusterCenters.length; j++){
                if(newClusterCentersPointsNumber[j] == 0) continue;
                newCenters[j][0] = newCenters[j][0].divide(new BigDecimal(newClusterCentersPointsNumber[j]), 2, BigDecimal.ROUND_HALF_UP);
                newCenters[j][1] = newCenters[j][1].divide(new BigDecimal(newClusterCentersPointsNumber[j]), 2, BigDecimal.ROUND_HALF_UP);
            }
            
            for(int j = 0; j < newClusterCenters.length; j++){
                if(newClusterCentersPointsNumber[j] == 0) continue;
                newClusterCenters[j][0] = newCenters[j][0].doubleValue();
                newClusterCenters[j][1] = newCenters[j][1].doubleValue();
            }
            
        }
        
    }
    
    public static void main(String [] args)
    {
        ALinearRegression(1.1, 35.2, 200, 30, 500, 0.3);
        BClusterDetection(5, 30, 500, 30, 8);
    }
}
