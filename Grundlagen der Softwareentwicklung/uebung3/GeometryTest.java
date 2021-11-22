/**
 * Testet die Funktionalitaeten der Unterklassen von Geometry
 * @version 05.08.17
 * @author Mark Hiltenkamp
 */
public class GeometryTest{
    public static void main(String[] args){

        Point2D p2D1 = new Point2D(2, 20);
        Point2D p2D2 = new Point2D(1, 5);
        Point2D p2D3 = new Point2D(-7, 5);
        Point2D p2D4 = new Point2D(3, -11);
        
        // Volumen eines Punktes ist gleich 0
        System.out.println("[Point2D1-4] : korrektes Volumen?\n> "
                + (p2D1.volume() == 0 && p2D2.volume() == 0 
                    && p2D3.volume() == 0 && p2D4.volume() == 0) + "\n");

        // Erstelle Rechteck mit Hilfe des Konstruktors
        Rectangle rec1 = new Rectangle(p2D1, p2D2);
        System.out.println("> new Rectangle(Point2D1, Point2D2)");
        System.out.println("[Rectangle1] : korrektes Volumen?\n> " 
                + (rec1.volume() == 15) + "\n");

        // Erstelle Rechteck, indem 2 Punkte eingekapselt werden
        Geometry rec2 = p2D3.encapsulate(p2D4);
        System.out.println("> Point2D3.encapsulate(Point2D4)");
        System.out.println("[Rectangle2] : korrektes Volumen?\n> "
                + (rec2.volume() == 160) + "\n");

        // Erstelle Rechteck, indem 1 Punkt und ein Rechteck eingekapselt werden
        Geometry rec3 = p2D1.encapsulate(rec2);
        System.out.println("> Point2D1.encapsulate(Rectangle2)");
        System.out.println("[Rectangle3] : korrektes Volumen?\n> "
                + (rec3.volume() == 310) + "\n");

        // Erstelle Rechteck mit 2 Rechtecken
        Geometry rec4 = rec1.encapsulate(rec2);
        System.out.println("> Rectangle1.encapsulate(Rectangle2)");
        System.out.println("[Rectangle4] : korrektes Volumen?\n> "
                + (rec4.volume() == 310) + "\n");


        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 5, 6);
        Point p3 = new Point(7, -7, 7);
        Point p4 = new Point(-8, 8, 8);

        System.out.println("[Point1-4] : korrektes Volumen?\n> "
                + (p1.volume() == 0 && p2.volume() == 0 
                    && p3.volume() == 0 && p4.volume() == 0) + "\n");

        // Erstelle Volumen mit Hilfe des Konstruktors
        Volume vol1 = new Volume(p1, p2);
        System.out.println("> new Volume(Point1, Point2)");
        System.out.println("[Volume1] : korrektes Volumen?\n> " 
                + (vol1.volume() == 27) + "\n");

        // Erstelle Volumen, indem zwei Punkte eingekapselt werden
        Geometry vol2 = p3.encapsulate(p4);
        System.out.println("> Point3.encapsulate(Point4)");
        System.out.println("[Volume2] : korrektes Volumen?\n> "
                + (vol2.volume() == 225) + "\n");

        // Erstelle Volumen, indem eine Punkt und ein Volumen eingekapselt werden
        Geometry vol3 = p1.encapsulate(vol2);
        System.out.println("> Point1.encapsulate(Volume2)");
        System.out.println("[Volume3] : korrektes Volumen?\n> "
                + (vol3.volume() == 1125) + "\n");

        // Erstelle Volumen mit 2 Volumen
        Geometry vol4 = vol1.encapsulate(vol2);
        System.out.println("> Volume1.encapsulate(Volume2)");
        System.out.println("[Volume4] : korrektes Volumen?\n> "
                + (vol4.volume() == 1125) + "\n");

        // Erster compareTo Test
        System.out.println("> Point2D.compareTo(Reactangle1)");
        System.out.println("Korrekte Ausgabe?\n> " + (p2D1.compareTo(rec1) < 0));
        
        // Zweiter compareTo Test
        System.out.println("\n> Rectangle3.compareTo(Rectangle4)");
        System.out.println("Korrekte Ausgabe?\n> " + (((Volume)rec3).compareTo(rec4) == 0));

        // Dritter compareTo Test
        System.out.println("\n> Volume4.compareTo(Point1)");
        System.out.println("Korrekt Ausgabe?\n> " + (((Volume)vol4).compareTo(p1) > 0));
    }
}
