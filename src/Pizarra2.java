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
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JColorChooser;
import javax.swing.JToggleButton;

public class Pizarra2 extends javax.swing.JFrame {
    /**
     * Creates new form Pizarra2
     */

    static final int LINEA     = 0;
    static final int TRIANGULO = 1;

    static final int ANCHO     = 640;
    static final int ALTO      = 480;
        
    Point   p1, p2, p3;
    boolean bP1=false, bP2=false, bP3=false;
    
    Raster  raster;
       
    boolean firstTime=true;
         
    int figura = LINEA;
    
    JPanel        panelRaster;
    JPanel        panelControles;
    
    JButton       btnColor;
    JToggleButton  rbLinea;
    JToggleButton  rbTriang;
    
    ButtonGroup   bg;
    
    Color         color;
    JColorChooser colorChooser;
    JButton btnGuardar;
    
    ArrayList<Figura> aFiguras = new ArrayList();
        
    public Pizarra2() {      
        p1 = new Point();
        p2 = new Point();        
        p3 = new Point();
        
        bP1 = false; bP2 = false; bP3 = false;
        
        raster = new Raster(ANCHO,ALTO);        
        
        panelRaster = new JPanel();
        panelRaster.setSize(ANCHO, ALTO);
        
        panelControles = new JPanel();
        
        this.setPreferredSize(new Dimension(ANCHO+50,ALTO+50));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        this.setLayout(new BorderLayout());       
        
        color = Color.black;
        
        btnGuardar = new JButton("Guardar");
        
        btnColor = new JButton("Color");
        
        btnColor.setBorderPainted(false);
        btnColor.setFocusPainted(false);
                
        btnColor.setBackground(color);
        btnColor.setForeground(color);
        
        rbLinea  = new JToggleButton("Linea");
        rbTriang = new JToggleButton("Triangulo");
    
        bg = new ButtonGroup();
        
        rbLinea.setSelected(true);
        bg.add(rbLinea);
        bg.add(rbTriang);
       
        this.panelRaster.setBackground(Color.white);
        this.add(panelRaster,BorderLayout.CENTER);

        this.panelControles.add(rbLinea);
        this.panelControles.add(rbTriang);
        this.panelControles.add(btnColor);
        this.panelControles.add(btnGuardar);
        
        
        this.add(panelControles,BorderLayout.SOUTH);

        this.panelRaster.addMouseListener(new MouseAdapter(){
                                        @Override
                                        public void mouseClicked(MouseEvent evt) {
                                                jPanel1MouseClicked(evt);
                                        }                               
                                }); 
        
        
        this.panelRaster.addKeyListener(new KeyAdapter(){
                                       @Override
                                       public void keyReleased(KeyEvent ke){
                                             
                                                 jPanel1KeyReleased(ke);
                                             
                                       }
                                 });        
        
        this.btnColor.addActionListener(new ActionListener(){
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                        color = JColorChooser.showDialog(null,"Seleccione un color",color);
                                                        btnColor.setBackground(color);
                                                }
                                });

        this.btnGuardar.addActionListener(new ActionListener(){
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                        guardarImagen();
                                                }
                                });
                
        
        this.setVisible(true);
        this.pack();
        
    }
    
    public static BufferedImage toBufferedImage(Image img){
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    
    public void guardarImagen(){
        
        BufferedImage img = toBufferedImage(raster.toImage(this));        
        try {                        
            File outputfile = new File("saved.png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {
          
        }
                
    }
    
    @Override
    public void update(Graphics g){
        paint(g);
    }
    
     @Override
    public void paint(Graphics g){        
        if( firstTime ) {   
            firstTime = false;
            //super.paint(g);
        }
        
        super.paint(g);
        
        Image output =  raster.toImage(this);
        g.drawImage(output, 0, 0, this);
        this.panelControles.paint(g);
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
        } else {
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
    
    
    private void dibujarTriangulo(Point p1, Point p2, Point p3, Color c){
                // TODO add your handling code here:
        Vertex2D v1 = new Vertex2D(p1.x,p1.y,c.getRGB());
        Vertex2D v2 = new Vertex2D(p2.x,p2.y,c.getRGB());
        Vertex2D v3 = new Vertex2D(p3.x,p3.y,c.getRGB());
        
        TrianguloR tri = new TrianguloR(v1,v2,v3);  

        tri.dibujar(raster);

    }
    
    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
        figura = this.rbLinea.isSelected()?LINEA:TRIANGULO;
        
        if (figura==TRIANGULO && bP1 && bP2 && !bP3){
            p3.x=evt.getX();
            p3.y=evt.getY();
            bP3 = true;              
            System.out.println("Tercer punto");
            
        } 

        if (bP1 && !bP2){
            p2.x=evt.getX();
            p2.y=evt.getY();
            bP2 = true;                       
            System.out.println("Segundo punto");
            dibujarLinea(p2,p2,color);
        } 
        
        if (!bP1){
            p1.x=evt.getX();
            p1.y=evt.getY();
            bP1 = true;
            System.out.println("Primer punto");
            dibujarLinea(p1,p1,color);
        }         
        
        if(figura==LINEA && bP1 && bP2){
            dibujarLinea(p1,p2,color);
            bP1=false;bP2=false;bP3=false;
        }
        
        if(figura==TRIANGULO && bP1 &&bP2 &&bP3){
            System.out.println("Dibujando triangulo");
            dibujarTriangulo(p1,p2,p3,color);
            bP1=false;bP2=false;bP3=false;
        }               
    }  
    
    public void jPanel1KeyReleased(KeyEvent ke) {      
        
        if(ke.getKeyCode()==KeyEvent.VK_T){
            this.figura = 1;
        }

        if(ke.getKeyCode()==KeyEvent.VK_L){
            this.figura = 0;
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
