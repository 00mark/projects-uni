/**
 * Ermoeglicht die Instanziierung von Volume Objekten
 * @version 05.08.17
 * @author Mark Hiltenkamp
 */
public class Volume extends Geometry implements Comparable{

    private Point[] points = new Point[2];

    public Volume(Point p1, Point p2){
        // Dimension = Anzahl der Koordinaten
        super(p1.getCoordinates().length);
        setPoints(p1, p2);
    }
    
    /**
     * Gibt die beiden Punkte des Volumens wieder
     * @return Die Punkte als Array
     */
    public Point[] getPoints(){
        return points;
    }

    /**
     * Vergibt neue Werte fuer die Punkte des Volumens
     * @param p1 Erster Punkt
     * @param p2 Zweiter Punkt
     */
    private void setPoints(Point p1, Point p2){
        if(p1.getCoordinates().length != p2.getCoordinates().length)
            // Setze points = null, falls die Punkte unterschiedliche 
            // Dimensionen haben
            points = null;
        else{
            // Setze points = null, falls die Punkte gleiche Koordinaten haben
            int i = 0;
            for(; i < p1.getCoordinates().length; i++){
                if(p1.getCoordinates()[i] == p2.getCoordinates()[i]){
                    points = null;
                    break;
                }
            }
            if(i == p1.getCoordinates().length){
                points[0] = p1;
                points[1] = p2;
            }
        }
    }

    /**
     * Kapsele diese und eine andere Geometry Instanz in einer Neuen ein
     * @param other die andere Geometry Instanz
     * @return Die neue Geometry Instanz
     */
    public Geometry encapsulate(Geometry other){
        if(dimensions() != other.dimensions())
            // Gib null wieder, falls die Instanzen unterschiedliche Dimensionen
            // haben
            return null;
        if(other instanceof Volume || other instanceof Point){
            // other ist entweder Volumen oder Point
            double[] newCoordinates1 = new double[getPoints()[0].getCoordinates().length];
            double[] newCoordinates2 = new double[getPoints()[0].getCoordinates().length];
            double[] minMax;
            for(int i = 0; i < getPoints()[0].getCoordinates().length; i++){
                // Finde jeweils die groesste und kleinste Koordinate
                if(other instanceof Volume)
                     minMax = findMinMax(getPoints()[0].getCoordinates()[i],
                            getPoints()[1].getCoordinates()[i],
                            ((Volume)other).getPoints()[0].getCoordinates()[i],
                            ((Volume)other).getPoints()[1].getCoordinates()[i]);
                else
                     minMax = findMinMax(getPoints()[0].getCoordinates()[i],
                            getPoints()[1].getCoordinates()[i],
                            ((Point)other).getCoordinates()[i]);
                newCoordinates1[i] = minMax[0];
                newCoordinates2[i] = minMax[1];
            }
            return new Volume(new Point(newCoordinates1), new Point(newCoordinates2));
        }
        // other ist weder vom Typ Point, noch Volume
        return null;
    }

    /**
     * Gibt das Volumen der Volume Instanz wieder
     * @return das Volumen
     */
    public double volume(){
        double v = 1;
        for(int i = 0; i < getPoints()[0].getCoordinates().length; i++)
            v *= Math.abs(getPoints()[0].getCoordinates()[i] -
                    getPoints()[1].getCoordinates()[i]);
        return v;
    }

    /** 
     * Vergleicht diese Instanz mit einer anderen Geometry Instanz
     * @param o eine andere Geometry Instanz
     * @throws RuntimeException falls das andere Objekt keine Geometry 
     *                          Instanz ist
     */
    public int compareTo(Object o){
        if(!(o instanceof Geometry))
            throw new RuntimeException("Keine Geometry Instanz");
        return (int)(this.volume() - ((Geometry)(o)).volume());
    }

    /**
     * Findet das Minimum und Maximum eines variablen double-Arrays
     * @param ds variables double Array
     * @return Das Minimun und Maximum als Array
     */
    public static double[] findMinMax(double ... ds){
        double min = ds[0], max = ds[0];
        for(double d : ds){
            if(d < min)
                min = d;
            if(d > max)
                max = d;
        }
        double[] minMax = {min, max};
        return minMax;
    }
}
