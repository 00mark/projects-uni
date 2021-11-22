package arena;

/**
 * Ermoeglicht die Erstellung von Arena Instanzen
 * @version 06.17.17
 * @author Mark Hiltenkamp
 */
public class Arena{
    
    /**
     * Gibt das passende Gebiet zu den eingegebenen Koordinaten wieder
     * (1 : 0 <= n < 30,    2 : 30 <= n < 60, ...)
     * @param x Die x-Koordinate
     * @param y Die y-Koordinate
     * @throws IllegalArgumentException, falls x und y 0 sind
     * @return -1, falls Die Koordinaten ausserhalb der Arena sind, sonst das
     *         jeweilige Gebiet
     */
    public int getArea(double x, double y){
        if(x == 0 && y == 0)
            throw new IllegalArgumentException("x und y duerfen nicht beide" +
                    " 0 sein");
        // Sind die Koordinaten ausserhalb der Arena?
        if(Math.sqrt(y*y + x*x) > 1.5)
            return -1;
        int degree = (int)Math.toDegrees(Math.atan2(x, y));
        return degree < 0 ? (390 + degree) / 30 : (30 + degree) / 30;
    }
}
