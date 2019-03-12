
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LaboratorioU005_11
 */
public class Linea extends Figura {
    Point2D punto1;
    Point2D punto2;

    Linea(Point _p1, Point _p2, Color _color) {
        punto1 = _p1;
        punto2 = _p2;
        color  = _color;
    }    
}
