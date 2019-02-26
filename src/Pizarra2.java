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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Pizarra2 extends javax.swing.JFrame {

    /**
     * Creates new form Pizarra
     */
    Point   p1, p2;
    boolean bDibujar;
    
    Raster  raster;
   
    int ancho = 640;
    int alto  = 480;
    
    boolean firstTime=true;    
            
    public Pizarra2() {      
        p1 = new Point();
        p2 = new Point();        
        bDibujar = false;
        raster = new Raster(640,480);        
        this.setSize(ancho+10, alto+10);
        
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        this.setLayout(new BorderLayout());
     
        
        this.addMouseListener(new MouseAdapter(){
                                        @Override
                                        public void mouseClicked(MouseEvent evt) {
                                                jPanel1MouseClicked(evt);
                                        }                               
                                }); 
        
        
        this.addKeyListener(new KeyAdapter(){
                                       @Override
                                       public void keyReleased(KeyEvent ke){
                                             
                                                 jPanel1KeyReleased();
                                             
                                       }
                                 });
        this.setBackground(Color.white);
        this.setVisible(true);
        
    }
    
    @Override
    public void update(Graphics g){
        paint(g);
    }

    
    public void paint(Graphics g){        
        if( firstTime )
        {   
            firstTime = false;
            super.paint(g);
        }
        
        Image output =  raster.toImage(this);
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
    
    public void lineaMejorada(int x0, int y0, int x1, int y1, Color color) {
        int pix = color.getRGB();
        int dx = x1 - x0;  int dy = y1 - y0;
        raster.setPixel(pix, x0, y0);
        if (Math.abs(dx) > Math.abs(dy)) {     // inclinacion < 1
            float m = (float) dy / (float) dx; // calcular inclinacion
            float b = y0 - m*x0;
            dx = (dx < 0) ? -1 : 1;
            while (x0 != x1) {
                x0 += dx;
                raster.setPixel(pix, x0, Math.round(m*x0 + b));
            }
        } else
        if (dy != 0) {                         // inclinacion >= 1
            float m = (float) dx / (float) dy; // Calcular inclinacion
            float b = x0 - m*y0;
            dy = (dy < 0) ? -1 : 1;
            while (y0 != y1) {
                y0 += dy;
                raster.setPixel(pix, Math.round(m*y0 + b), y0);
            }
        }
    }

    public void lineFast(int x0, int y0, int x1, int y1, Color color) {
        int pix = color.getRGB();
        int dy = y1 - y0;  int dx = x1 - x0;  int stepx, stepy;
        if (dy < 0) { dy = -dy;  stepy = -raster.width; } else { stepy = raster.width; }
        if (dx < 0) { dx = -dx;  stepx = -1; } else { stepx = 1; }
        dy <<= 1;   dx <<= 1;
        y0 *= raster.width;  y1 *= raster.width;   raster.pixel[x0+y0] = pix;
        if (dx > dy) {
            int fraction = dy - (dx >> 1);
            while (x0 != x1) {
                if (fraction >= 0) {
                    y0 += stepy; fraction -= dx;
                }
                x0 += stepx;  fraction += dy;
                raster.pixel[x0+y0] = pix;
            }
        } else {
            int fraction = dx - (dy >> 1);
            while (y0 != y1) {
                if (fraction >= 0) {
                    x0 += stepx; fraction -= dy;
                }
                y0 += stepy; fraction += dx;
                raster.pixel[x0+y0] = pix;
            }
        }
    }

    
    private void dibujarLinea(Point _p1, Point _p2, Color color) {
             long inicio=0, fin=0;
             inicio = System.nanoTime();
             lineaMejorada(_p1.x,_p1.y,_p2.x,_p2.y,color);
             fin    = System.nanoTime();
             
             System.out.printf("Tiempo transcurrido, simple: %d\n",(fin-inicio));
             
             inicio = System.nanoTime();
             lineFast(_p1.x,_p1.y,_p2.x,_p2.y,color);
             fin    = System.nanoTime();
             
             System.out.printf("Tiempo transcurrido, fast  : %d\n",(fin-inicio));             
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
    
    public void jPanel1KeyReleased() {                                     
        // TODO add your handling code here:
        Vertex2D v1 = new Vertex2D((float)10.0,(float) 10.0,Color.red.getRGB());
        Vertex2D v2 = new Vertex2D((float)20.0,(float) 20.0,Color.red.getRGB());
        Vertex2D v3 = new Vertex2D((float)10.0,(float) 30.0,Color.red.getRGB());
        
        TrianguloR tri = new TrianguloR(v1,v2,v3);  

        tri.dibujar(raster);
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
