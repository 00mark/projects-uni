/******************************  ShapeTest.java  ******************************/

import AlgoTools.IO;

/**
 * @version 06.01.17
 *  
 * @author  mhiltenkamp
 */

public class ShapeTest {

   public static void main(String[] argv) {

	  Shape a = new Shape();
	  Shape b = new Shape("grün", 1, 2, 3);
	  b.move(1, 3, 5);
	  IO.println(a);
	  IO.println(b);
	  IO.println(a.getVolume());
	  IO.println(b.getArea());
	  IO.println(a.getDistanceTo(b));
	  IO.println();
	  
	  Sphere c = new Sphere("blau", 3, 2, 1, 5);
	  Sphere d = new Sphere("gelb", 2, 5, 8, 1);
	  IO.println(c);
	  IO.println(d);
	  IO.println(c.getVolume());
	  IO.println(c.getArea());
	  IO.println(c.getDistanceTo(a));  
	  IO.println(c.getDistanceTo(d));
	  IO.println();

	  Cube e = new Cube("lila", 4, 5, 6, 2);
	  Cube f = new Cube("braun", 0, 0, 0, 4);
	  IO.println(e);
	  IO.println(f);
	  IO.println(e.getVolume());
	  IO.println(e.getArea());
	  IO.println(e.getDistanceTo(a));
	  IO.println(e.getDistanceTo(f));
   }
}

