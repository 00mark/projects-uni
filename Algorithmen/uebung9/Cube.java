/******************************  Cube.java  ***********************************/

import AlgoTools.IO;

/**
 * @version 06.01.17
 *  
 * @author  mhiltenkamp
 */

public class Cube extends Shape {
   // Seitenlaenge des Wuerfels
   private double width;
   
   public Cube(String colour, double x, double y, double z, double width){
	  super(colour, x, y, z);
	  setWidth(width);
   }
   /**
    * Gibt die Seitenlaenge des Wuerfels wieder
	* @return die Seitenlaenge
	*/
   public double getWidth(){
	  return width;
   }
   /**
    * Aendert die Seitenlaenge
	* @param width die neue Seitenlaenge
	* @throws RuntimeException bei neuer Seitenlaenge <= 0
	*/
   public void setWidth(double width){
	  if(width <= 0)
		 throw new RuntimeException("Ungueltige Seitenlaenge");
	  this.width = width;
   }
   /**
    * Gibt das Volumen des Wuerfels wieder
	* @return das Volumen
	*/
   public double getVolume(){
	  return getWidth() * getWidth() * getWidth();
   }
   /**
    * Gibt die Oberflaeche des Wuerfels wieder
	* @return die Oberflaeche
	*/
   public double getArea(){
	  return 6 * getWidth() * getWidth();
   }
   /**
    * Gibt die Entfernung vom Mittelpunkt des Wuerfels zum
	* Mittelpunkt eines anderen Wuerfels wieder
	* @param other der andere Wuerfel
	* @return die Entfernung der beiden Mittelpunkte
	*/
   public double getDistanceTo(Cube other){
	  // Koordinaten des Mittelpunktes des ersten Wuerfels errechnen
	  double x1 = x - getWidth() / 2;
	  double y1 = y - getWidth() / 2;
	  double z1 = z - getWidth() / 2;
	  // Koordinaten des Mittelpunktes des zweiten Wuerfels errechnen
	  double x2 = other.x - other.getWidth() / 2;
	  double y2 = other.y - other.getWidth() / 2;
	  double z2 = other.z - other.getWidth() / 2;

	  return Math.sqrt((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1)
			   + (z2-z1) * (z2-z1));
   }
   /**
    * Gibt die Eigenschaften des Wuerfels aus
	* @return die Eigenschafen des Wuerfels
	*/
   public String toString(){
	  return "Der Wuerfel hat die Farbe "+ colour +", die Seitenlaenge "
			   + width +" und die Koordinaten ("+ x +", "+ y +", "+ z +")";
   }
}

