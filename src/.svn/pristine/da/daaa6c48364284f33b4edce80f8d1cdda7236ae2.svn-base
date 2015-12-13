/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

/**
 *
 * @author Paul
 */
public class Water {
    
    int step = 10;
    
    public double[][] doWater(double[][]matrix){
        System.out.println(step);
        for(int i = 0; i < matrix.length;i++){
            matrix[i][step] = 1;
        }
        for(int i = 0; i < matrix.length;i++){
            matrix[i][step-1] = 2;
        }
        for(int i = 0; i < matrix.length;i++){
            matrix[i][step-2] = 4;
        }
        for(int i = 0; i < matrix.length;i++){
            matrix[i][step-3] = 6;
        }
        for(int i = 0; i < matrix.length;i++){
            matrix[i][step-4] = 7;
        }
        for(int i = 0; i < matrix.length;i++){
            matrix[i][step-5] = 8;
        }
        for(int i = 0; i < matrix.length;i++){
            matrix[i][step-6] = 7;
        }
        for(int i = 0; i < matrix.length;i++){
            matrix[i][step-7] = 5;
        }
        for(int i = 0; i < matrix.length;i++){
            matrix[i][step-8] = 3;
        }
        for(int i = 0; i < matrix.length;i++){
            matrix[i][step-9] = 2;
        }
        for(int i = 0; i < matrix.length;i++){
            matrix[i][step-10] = 0;
        }
        
        step++;
        if(step >= matrix.length){
            step = 10;
        }
        return matrix;
    }
}
