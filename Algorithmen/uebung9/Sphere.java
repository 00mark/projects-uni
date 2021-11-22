/******************************  Sphere.java  *********************************/

import AlgoTools.IO;

/**
 * @version 06.01.17
 *  
 * @author  mhiltenkamp
 */

public class Sphere extends Shape{
   // Radius der Kugel
   private double radius;

   public Sphere(String colour, double x, double y, double z, double radius){
	  super(colour, x, y, z);
	  setRadius(radius);
   }
   /**
	* Gibt den Radius wieder
	* @return den Radius
	*/
   public double getRadius(){
	  return radius;
   }
   /**
	* Veraendert den Radius
	* @param r der neue Radius
	* @throws RuntimeException bei neuem Radius <= 0
	*/
   public void setRadius(double r){
	  if(r <= 0)
		 throw new RuntimeException("Ungueltiger Radius");
	  radius = r;
   }
   /**
	* Gibt das Volumen der Kugel wieder
	* @return das Volumen
	*/
   public double getVolume(){
	  return 4.0 / 3 * Math.PI * getRadius() * getRadius() * getRadius();
   }
   /**
	* Gibt die Oberflaeche der Kugel wieder
	* @return die Oberflaeche
	*/
   public double getArea(){
	  return 4 * Math.PI * getRadius() * getRadius();
   }
   /**
	* Gibt die Entfernung der Kugel zu einer anderen Kugel wieder
	* @param other die andere Kugel
	* @throws RuntimeException bei ineinander liegenden Kugeln
	* @return die Entfernung zwischen den beiden Kugeln
	*/
   public double getDistanceTo(Sphere other){
	  if(super.getDistanceTo(other) - (getRadius() + other.getRadius()) < 0)
		 throw new RuntimeException("Kugeln liegen ineinander");
	  return super.getDistanceTo(other) - (getRadius() + other.getRadius());
   }
   /**
	* Gibt die Eigenschaften der Kugel aus
	*/
   public String toString(){
	  return "Die Kugel ist "+ colour +", hat den Radius "+ getRadius()
		 +" Und die Koordinaten ("+ x +", "+ y +", "+ z +")";
   }
}

