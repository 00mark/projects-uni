package arena;

/**
 * Testet die Funktionen der Arena Klasse
 * @version 06.17.17
 * @author Mark Hiltenkamp
 */
public class ArenaTest{

    public static void main(String[] args){
        Arena a = new Arena();
        // Anzahl der Durchlaeufe, in denen keine Exception geworfen wurde
        int count = 0;
        boolean success = true;
        int[] expectedResult = {0, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        double[][] testXY = {{0, 0}, {1.5, 1.5}, {0, 1.5}, {1, 1}, {0.5, 0.2},
            {1.5, 0}, {1, -0.6}, {0.3, -1.4}, {0, -1.5}, {-0.6, -1},
            {-1.4, -0.1}, {-1.5, 0}, {-1, 1}, {-0.1, 1.3}};
        for(int i = 0; i < testXY.length; i++){
            try{
                System.out.print("(" + testXY[i][0] + ", " + testXY[i][1] +
                        ")\t=> ");  
                long tmpDegree = Math.round(Math.toDegrees(Math.atan2(
                                testXY[i][0], testXY[i][1])));
                long degree = tmpDegree < 0 ? 360 + tmpDegree : tmpDegree;
                int area = a.getArea(testXY[i][0], testXY[i][1]);
                System.out.println(degree + "\u00B0\t=> " + area);
                // Ist das zurueckgegebene Gebiet gleich dem erwarteten Gebiet?
                success = success && (area == expectedResult[i]);
                count++;
            }catch(IllegalArgumentException e){
                System.out.println("IllegalArgumentException");
            }
        }
        System.out.println("Ergebnis : " + (success  && count == 13 ?
                    "Erfolg" : "Fehler"));
    }
}
