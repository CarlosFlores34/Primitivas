/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LaboratorioU005_11
 */

public class TestMatrix {

    public static void main(String[] args){        
        double[] punto = new double[3];
        double[] punto2 = new double[3];
        
        punto[0]=3;  // x
        punto[1]=0;  // y
        punto[2]=1;  // h
                
        System.out.printf("x = %5.2f, y = %5.2f, h =%5.2f\n", punto[0], punto[1], punto[2]);
        
        Matrix m = new Matrix();
        m.loadIdentity();
        
        m.rotacion(90);
        
        punto2 = m.pprima(punto);
        
        System.out.printf("x = %5.2f, y = %5.2f, h =%5.2f\n", punto2[0], punto2[1], punto2[2]);                
    }    
}
