/**
 * Rectangle Klasse, Unterklasse von Volume, erlaubt nur die Erzeugung von
 * zweidimensionalen Flaechen
 * @version 05.08.17
 * @author Mark Hiltenkamp
 */
public class Rectangle extends Volume implements Comparable{

    public Rectangle(Point2D p1, Point2D p2){
        super(p1, p2);
    }
}
