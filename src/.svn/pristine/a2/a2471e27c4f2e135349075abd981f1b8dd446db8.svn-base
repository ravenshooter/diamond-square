/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

import java.util.ArrayList;
import java.awt.Point;

/**
 *
 * @author Paul
 */
public class Ocean {
    /**
     * ocean
     * beach
     * forest
     * mountian
     * glacier
     */
    long [] lvl = new long [5];
    double [][] matrix;
    final double HIGH, LOW;
    final int STEP;
    
    private static final int DEPTH = 10;
    
    ArrayList<Point>[] lists = new ArrayList[DEPTH];
    
    public Ocean(double[][] matrix){
        this.matrix = matrix;
        HIGH = scanHighest();
        LOW = scanLowest();
        STEP = (int) (Math.abs(LOW) + Math.abs(HIGH))/10;
        System.out.println("HIGH: " + HIGH + " LOW: " + LOW + " STEP: " + STEP);
        for(int i = 0; i < DEPTH; i++){
            lists[i] = new ArrayList<Point>();
        }
        
    }
    
    public double[][] makeOcean(double percent){
        scan();
        double curPer = 0;
        double all = matrix.length*matrix.length;
        int counter = 0;
        while(percent > curPer){
            curPer += (lists[counter].size() / all)*100;
            counter++;
        }
        
        lvl[0] = Math.round(LOW + (STEP*counter));
        for(int i = 0; i < counter;i++){
            for(int j = 0; j < lists[i].size();j++){
                matrix[lists[i].get(j).x][lists[i].get(j).y] = lvl[0];
            }
        }
        counter++;
        lvl[1] = Math.round(LOW + (STEP*counter));
        counter++;
        lvl[2] = Math.round(LOW + (STEP*counter));
        counter++;
        lvl[3] = Math.round(LOW + (STEP*counter));
        lvl[4] = Math.round(HIGH);
        
        PRINT_THE_SHIT_OUT();
        return matrix;
    }
    
    public long[] getLevel(){
        return lvl;
    }
    
    private void scan(){
        for(int i = 0; i < matrix.length;i++){
            for(int j = 0; j < matrix.length;j++){
                int corner = (int)((-((LOW-matrix[i][j])/STEP)));
                if(corner == 10) {
                    corner = 9;
                }
                lists[corner].add(new Point(i,j));
            }
        }
    }
    
    private double scanHighest(){
        double value = matrix[0][0];
        for(int i = 0; i < matrix.length;i++){
            for(int j = 0; j < matrix.length;j++){
                if(value < matrix[i][j]){
                    value = matrix[i][j];
                }
            }
        }
        return value;
    }
    
    private double scanLowest(){
        double value = matrix[0][0];
        for(int i = 0; i < matrix.length;i++){
            for(int j = 0; j < matrix.length;j++){
                if(value > matrix[i][j]){
                    value = matrix[i][j];
                }
            }
        }
        return value;
    }
    /*public static void main(String [] args){
        double [][] m = new double [2][2];
        m[0][0] = 0;
        m[1][0] = 40;
        m[0][1] = 60;
        m[1][1] = 120;
        new Ocean(m, 0);
    }*/
    
    private void PRINT_THE_SHIT_OUT(){
        double all = matrix.length*matrix.length;
        for(int i = 0; i < DEPTH; i++){
            double percent = (lists[i].size() / all)*100;
            System.out.println("LIST " + i + " : " + percent + "%");
        }
    }
}
