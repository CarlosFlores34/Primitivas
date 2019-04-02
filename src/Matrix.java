/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LaboratorioU005_11
 */
import java.math.*;

public class Matrix {
    double[][] matrix = new double[3][3];
    double xp,yp,hp;
    
    Matrix(){
        // Matriz Identidad
        clearMatrix();
    }
    
    double[] producto(double punto[]){
        double[] res = new double[3];
        
        xp = punto[0]*matrix[0][0]+punto[1]*matrix[0][1]+punto[2]*matrix[0][2];     // | X | =  | A  B  C |   | x |
        yp = punto[0]*matrix[1][0]+punto[1]*matrix[1][1]+punto[2]*matrix[1][2];     // | Y | =  | D  E  F | * | y |
        hp = punto[0]*matrix[2][0]+punto[1]*matrix[2][1]+punto[2]*matrix[2][2];     // | H | =  | G  H  I |   | z |
        
        res[0] = xp;
        res[1] = yp;
        res[2] = hp;
        
        return res;
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
    
    void escalarXY(int escalaX, int escalaY){
        // Matriz Identidad
        matrix[0][0] = escalaX;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = escalaY;
        matrix[1][2] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
    }     
    
    
    void rotar(double angulo){
        // Matriz Identidad
        matrix[0][0] = Math.cos(angulo) ;    // cos +
        matrix[0][1] = Math.sin(angulo)*-1;    // -sen +
        matrix[0][2] = 0;    // 0
        matrix[1][0] = Math.sin(angulo);
        matrix[1][1] = Math.cos(angulo);
        matrix[1][2] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
    }     

    
    void traslacion(int tx, int ty){
        // Matriz Identidad
        matrix[0][0] = 1 ;    // cos +
        matrix[0][1] = 0;    // -sen +
        matrix[0][2] = tx;    // 0
        matrix[1][0] = 0;
        matrix[1][1] = 1;
        matrix[1][2] = ty;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
    }         
    
}
