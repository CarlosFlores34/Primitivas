/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LaboratorioU005_11
 */
public class Matrix {
    double[][] matrix = new double[3][3];
    double xp,yp,hp;
    
    Matrix(){
        // Matriz Identidad
        clearMatrix();
    }
    
    void producto(int x, int y, int h){
        xp = x*matrix[0][0]+y*matrix[0][1]+h*matrix[0][2]; 
        yp = x*matrix[1][0]+y*matrix[1][1]+h*matrix[1][2];                
        hp = x*matrix[2][0]+y*matrix[2][1]+h*matrix[2][2];              
    }
    
    void clearMatrix(){
                // Matriz Identidad
        matrix[0][0] = 1;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = 1;
        matrix[1][2] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
    }
    
    void escalar(int escala){
        // Matriz Identidad
        matrix[0][0] = escala;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = escala;
        matrix[1][2] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
    }    
}
