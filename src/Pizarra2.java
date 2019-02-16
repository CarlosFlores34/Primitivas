/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gmendez
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class Pizarra2 extends javax.swing.JFrame {

    /**
     * Creates new form Pizarra
     */
    Point   p1, p2;
    boolean bDibujar;
    JPanel  panel1;
    Raster  raster;
   
    int ancho = 640;
    int alto  = 480;
            
    public Pizarra2() {      
        p1 = new Point();
        p2 = new Point();        
        bDibujar = false;
        raster = new Raster(640,480);        
        this.setSize(ancho+10, alto+10);
        
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        this.setLayout(new BorderLayout());
       
        panel1 = new JPanel();
        panel1.setSize(ancho, alto);
        
        panel1.addMouseListener(new MouseAdapter(){
                                        @Override
                                        public void mouseClicked(MouseEvent evt) {
                                                jPanel1MouseClicked(evt);
                                        }                               
                                });
        
        this.add(panel1,BorderLayout.CENTER);
       
        this.setVisible(true);
    }
    
    @Override
    public void update(Graphics g){
         paint(g);
    }

    @Override
    public void paint(Graphics g){                
        Image output = raster.toImage(this);
        g.drawImage(output, 0, 0, this);        
    }
    
    public void clear() {	
        int s = raster.size();
        for (int i = 0; i < s; i++) {
            raster.pixel[i] ^= 0x00ffffff;
        }
        repaint();
        return;
    }    
    
    public void lineaSimple(int x0, int y0, int x1, int y1, Color color) {
        int pix = color.getRGB();
        int dx = x1 - x0;
        int dy = y1 - y0;

        raster.setPixel(pix, x0, y0);

        if (dx != 0) {
            float m = (float) dy / (float) dx;
            float b = y0 - m*x0;

            dx = (x1 > x0) ? 1 : -1;

            while (x0 != x1) {
                x0 += dx;
                y0 = Math.round(m*x0 + b);
                raster.setPixel(pix, x0, y0);
            }
        }
    }

    private void dibujarLinea(Point _p1, Point _p2, Color color) {                        
             lineaSimple(_p1.x,_p1.y,_p2.x,_p2.y,color);
    }    
    
    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
        if (!bDibujar){
            p1.x=evt.getX();
            p1.y=evt.getY();
            bDibujar = true;
        } else {
            p2.x=evt.getX();
            p2.y=evt.getY();            
            dibujarLinea(p1,p2,Color.red);
            bDibujar = false;
        }                
    }       
                      
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pizarra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pizarra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pizarra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pizarra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Pizarra2 pizarra = new Pizarra2();
                pizarra.setVisible(true);                                                
            }
        });
    }

}
