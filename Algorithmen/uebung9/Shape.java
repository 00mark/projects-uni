/******************************  Shape.java  **********************************/

import AlgoTools.IO;

/**
 * @version 05.01.17
 *  
 * @author  mhiltenkamp
 */

public class Shape {
   // Enthaelt die Farbe der Form
   public String colour;
   // Die Koordinate der Form im Raum
   public double x, y, z; 

   public Shape(String colour, double x, double y, double z){
	  this.colour = colour;
	  this.x = x;
	  this.y = y;
	  this.z = z;
   }

   public Shape(){
	  colour = "schwarz";
	  x = y = z = 0;
   }
   /**
	* Bewegt die Form um dX in x-Richtung, dY in y-Richtung und 
	* dZ in z-Richtung
	* @param dX Bewegung in x-Richtung
	* @param dY Bewegung in y-Richtung
	* @param dZ Bewegung in z-Richtung
	*/
   public void move(double dX, double dY, double dZ){
	  x += dX;
	  y += dY;
	  z += dZ;
   }
   /**
	* Gibt das Volumen der Form wieder
	* @return das Volumen (= 0, da Form unbestimmt)
	*/
   public double getVolume(){
	  return 0.0;
   }
   /**
	* Gibt die Flaeche der Form wieder
	* @return die Flaeche (= 0, da Form unbestimmt)
	*/
   public double getArea(){
	  return 0.0;
   }
   /**
	* Gibt die Entfernung einer Form zu einer Anderen wieder
	* @param other Andere Form
	* @return Die Entfernung zu der anderen Form
	*/
   public double getDistanceTo(Shape other){
	  return Math.sqrt( (other.x - x) * (other.x - x) + (other.y - y) 
			* (other.y - y) + (other.z - z) * (other.z - z));
   }
   /**
	* Gibt die Farbe und die Koordinaten (x, y, z) der Form aus
	*/
   public String toString(){
	  return "Die Form ist "+ colour +" und hat die Koordinaten ("
		 + x +", "+ y +", "+ z +")";
   }
}

