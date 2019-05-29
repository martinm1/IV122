/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv5;

import ij.process.ColorProcessor;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author xmejzli2
 */
public class Cv5 {
    public static boolean pointOfLineOnAbscissa(double ax, double ay, double bx, double by, double px, double py){
        double vectorX = bx - ax;
        double vectorY = by - ay;
        
        double vectorPAX = px - ax;
        double vectorPAY = py - ay;
        
        double distAB = Math.sqrt(vectorX*vectorX + vectorY*vectorY);
        
        double distPA = Math.sqrt(vectorPAX*vectorPAX + vectorPAY*vectorPAY);
        
        double vectorPBX = px - bx;
        double vectorPBY = py - by;
        
        double distPB = Math.sqrt(vectorPBX*vectorPBX + vectorPBY*vectorPBY);
        
        return (distPA <= distAB && distPB <= distAB);
    }
    
    public static boolean abscissasSame(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4){
        return (
                (x1 == x3 && y1 == y3 && x2 == x4 && y2 == y4) 
              ||(x1 == x4 && y1 == y4 && x2 == x3 && y2 == y3) 
            );
    }
    
    public static double[] abscissasCross(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4){
        
        double vector1x = x2 - x1;
        double vector1y = y2 - y1;
        
        double vector2x = x4 - x3;
        double vector2y = y4 - y3;
        
        
        if(vector1x / vector2x == vector1y / vector2y) return null;
        
        
        double px = ((x1*y2-y1*x2)*(x3-x4) - (x1-x2)*(x3*y4 - y3*x4))
                   /      ((x1-x2)*(y3-y4) - (y1-y2)*(x3-x4));
        
        double py = ((x1*y2-y1*x2)*(y3-y4) - (y1-y2)*(x3*y4 - y3*x4))
                   /      ((x1-x2)*(y3-y4) - (y1-y2)*(x3-x4));
        
        
        double[] cross = new double[2];
        cross[0] = px;
        cross[1] = py;
        
        if (pointOfLineOnAbscissa(x1, y1, x2, y2, px, py) && pointOfLineOnAbscissa(x3, y3, x4, y4, px, py))
            return cross;
        else
            return null;
    }
    
    public static SVG drawAbscissa(SVG svg, double x1, double y1, double x2, double y2){
        svg.line(x1, y1, x2, y2, 0, 0, 0);
        return svg;
    }
    
    
    public static ArrayList<double[]> generateAbscissas(int w, int h, int number, double length){ //chci usecky stejne delky  
        ArrayList result = new ArrayList(); 
        
        for(int i = 0; i < number; i++){
            Random rand = new Random();
            double x1 = rand.nextDouble()*w;
            double y1 = rand.nextDouble()*h;
            
            Random rand2 = new Random();
            
            double x2;
            double y2;
            
            do{
                double angle = rand2.nextDouble()*2*Math.PI;
                x2 = x1+length*Math.cos(angle);
                y2 = y1+length*Math.sin(angle);
            } while (x1 < 0 || x1 > w - 1 || x2 < 0 || x2 > w -1 || y1 < 0 || y1 > h - 1 || y2 < 0 || y2 > h -1);
            double[] line = new double[]{x1, y1, x2, y2};
            result.add(line);
            
            
        }
        return result;
    }   
    
    public static void A(int number, double length){
        int size = 500;
        SVG svg = new SVG(size, size, "A");
        
        ArrayList<double[]> abscissas = generateAbscissas(size, size, number, length);
        
        for(double[] abscissa : abscissas){
            svg = drawAbscissa(svg, abscissa[0], abscissa[1], abscissa[2], abscissa[3]);
        }
        
        for(double[] abscissa1 : abscissas){
            for(double[] abscissa2 : abscissas){
                double[] cross = abscissasCross(abscissa1[0], abscissa1[1], abscissa1[2], abscissa1[3], abscissa2[0], abscissa2[1], abscissa2[2], abscissa2[3]);
                if (cross != null){
                    svg.circle(cross[0], cross[1], 2);
                }
            }
        }
        
        svg.save();
    }
    
    
    
    public static ArrayList<double[]> generatePoints(int w, int h, int number){
        ArrayList<double[]> points = new ArrayList();
        
        for(int i = 0; i < number; i++){
            double[] point = new double[2];
            Random rand = new Random();
            point[0] = rand.nextDouble()*w;
            point[1] = rand.nextDouble()*h;
            
            points.add(point);
        }
        
        return points;
    }
            
    public static ArrayList<double[]> findTriangulation(ArrayList<double[]> points, double tolerance){
        ArrayList<double[]> candidates = new ArrayList();
        ArrayList<double[]> chosen = new ArrayList();
        
        for(double[] point1 : points){
            for(double[] point2 : points){
                if(!(point1[0] == point2[0] && point1[1] == point2[1])){
                    double[] candidate = new double[]{point1[0], point1[1], point2[0], point2[1]};
                    candidates.add(candidate);
                }
            }
        }
        
        int numOfIterations = candidates.size();
        for(int i = 0; i < numOfIterations; i++){
            double distance = Double.MAX_VALUE;
            double[] chosenLine = null;
            
            for(double[] line : candidates){
                double vectorX = line[2] - line[0];
                double vectorY = line[3] - line[1];

                if(Math.sqrt(vectorX*vectorX + vectorY * vectorY) < distance){
                    distance = Math.sqrt(vectorX*vectorX + vectorY * vectorY);
                    chosenLine = line;
                }

            }
            
            if(chosenLine != null){
                candidates.remove(chosenLine);
                boolean acceptable = true;
                for(double[] line : chosen){
                    
                    double[] cross = abscissasCross(chosenLine[0], chosenLine[1], chosenLine[2], chosenLine[3], line[0], line[1], line[2], line[3]);
                    if(cross != null){
                        double vector1X = cross[0] - chosenLine[0];
                        double vector1Y = cross[1] - chosenLine[1];
                        double distance1 = Math.sqrt(vector1X*vector1X + vector1Y * vector1Y);
                        
                        double vector2X = cross[0] - chosenLine[2];
                        double vector2Y = cross[1] - chosenLine[3];
                        double distance2 = Math.sqrt(vector2X*vector2X + vector2Y * vector2Y);
                        
                        if(distance1 > tolerance && distance2 > tolerance){
                            acceptable = false;
                        }
                    }
                    if(abscissasSame(chosenLine[0], chosenLine[1], chosenLine[2], chosenLine[3], line[0], line[1], line[2], line[3])){
                        acceptable = false;
                    }
                }
                if(acceptable) {
                    chosen.add(chosenLine);
                }
            }
        }
        return chosen;
    }
    
    public static void BTriangulation(int number, double tolerance){
        int size = 500;
        SVG svg = new SVG(size, size, "BTriangulation");
        
        ArrayList<double[]> points = generatePoints(size, size, number);
        ArrayList<double[]> triangulation = findTriangulation(points, tolerance);
        
        for(double[] point : points){
            svg.circle(point[0], point[1], 2);
        }
        
        for(double[] abscissa : triangulation){
            svg = drawAbscissa(svg, abscissa[0], abscissa[1], abscissa[2], abscissa[3]);
        }
        
        svg.save();
    }
    
    public static double[] findLeftMostPoint(ArrayList<double[]> points){
        
        double[] leftmost = points.get(0);
        double position = points.get(0)[0];
            
        for(double[] point : points){

            if(point[0] < position){
                leftmost = point;
                position = point[0];
            }

        }
        return leftmost; 
    }
    
    public static double[] findRightMostPoint(ArrayList<double[]> points){
        
        double[] rightmost = points.get(0);
        double position = points.get(0)[0];
            
        for(double[] point : points){

            if(point[0] > position){
                rightmost = point;
                position = point[0];
            }

        }
        return rightmost; 
    }
    
    
    public static void CConvexHull(int number){
        int size = 500;
        SVG svg = new SVG(size, size, "CConvexHull");
        
        ArrayList<double[]> points = generatePoints(size, size, number);
        
        for(double[] point : points){
            svg.circle(point[0], point[1], 2);
        }
        
        double[] leftmost = findLeftMostPoint(points);
        double[] rightmost = findRightMostPoint(points);
        
        double[] current = leftmost;
        double currentAngle;
        
        double[] newPoint = leftmost;
        
        boolean leftToRight = true;
        
        do{
            if(leftToRight){
                currentAngle = 10*size;
            }
            else{ 
                currentAngle = -10*size;
            }
            for(double[] point : points){
                if(point[0] != current[0] || point[1] != current[1]){
                    double vectorX = point[0] - current[0];
                    double vectorY = point[1] - current[1];
                    
                    double distance = Math.sqrt(vectorX*vectorX + vectorY * vectorY);
                    
                    double angle = Math.asin(vectorY/distance);
                    
                    if(point[0] >= current[0] && angle < currentAngle && leftToRight){
                        newPoint = point;
                        currentAngle = angle;
                    }
                    if(point[0] <= current[0] && angle > currentAngle && (!leftToRight)){
                        newPoint = point;
                        currentAngle = angle;
                        
                    }
                }
            }
            if(rightmost[0] == newPoint[0] && leftToRight) leftToRight = false;
            svg.line(current[0], current[1], newPoint[0], newPoint[1], 0, 0, 0);
            current = newPoint;
        } while (current[0] != leftmost[0] || current[1] != leftmost[1]);
        
        svg.save();
    }
    
    public static void main(String [] args){
        A(20, 200);
        BTriangulation(10, 0.05);
        CConvexHull(25);
    }
}
