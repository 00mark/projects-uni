/**
 * Point Klasse, die es ermoeglicht einen n-dimensionalen Punkt zu instanziieren
 * @version 05.08.17
 * @author Mark Hiltenkamp
 */
public class Point extends Geometry implements Comparable{

    private double[] coordinates;

    public Point(double ... coordinates){
        // Dimension = Anzahl der Koordinaten
        super(coordinates.length);
        this.coordinates = coordinates;
    }

    /**
     * Gibt die Kooridinaten des Punktes wieder
     * @return die Koordinaten des Punktes
     */
    public double[] getCoordinates(){
        return coordinates;
    }

    /**
     * Kapselt diese und eine Andere Geometry Instanz in einer neuen
     * Geometry Instanz ein
     * @param other Andere Geometry Instanz
     * @return Die neue Geometry Instanz
     */
    public Geometry encapsulate(Geometry other){
        if(dimensions() != other.dimensions())
            return null;
        if(other instanceof Point)
            return new Volume(this, (Point)other);
        if(other instanceof Volume)
            return other.encapsulate(this);
        return null;
    }
    /**
     * Gibt das Volumen wieder
     * @return 0, da Punkte kein Volumen haben
     */
    public double volume(){
        return 0;
    }

    /**
     * Vergleicht dieses Geometry Objekt mit einem anderen Geometry Objekt
     * @param o Das andere Geometry Objekt
     * @throws RuntimeException fals das eingegebenen Objekt kein Geometry
     *                          Objekt ist
     * @return 0 bei gleichem Volumen, <0, falls dieses Objekt ein geringeres
     *         Volumen hat, sonst >0
     */
    public int compareTo(Object o){
        if(!(o instanceof Geometry))
            throw new RuntimeException("Keine Geometry Instanz");
        return (int)(this.volume() - ((Geometry)o).volume());
    }
}
