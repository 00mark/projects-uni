/******************************  Firma.java  **********************************/

import AlgoTools.IO;

/**
 * @version 28.01.17
 * Klasse, die es ermoeglicht Firmen anzulegen 
 * @author  mhiltenkamp
 */

public class Firma implements Comparable{

   private String name;
   private double umsatz;
   private long ustId;
   /**
	* Weist der Firma einen Namen, Umsatz , und 
	* Umsatzsteueridentifikationsnummer zu
	* @param name String Name der Firma
	* @param umsatz double Umsatz der Firma
	* @param ustId Umsatzsteueridentifikationsnummer der Firma
	* @throws RuntimeException bei Umsatz < 0
	*/
   public Firma(String name, double umsatz, long ustId){
	  setName(name);
	  setUmsatz(umsatz);
	  setUstId(ustId);
   }
   /**
	* Vergleicht die aktuelle Firma mit einer Anderen
	* @param x Object eine andere Firma
	* @throws RuntimeException wenn anderes Objekt keine Firma ist
	* @return < 0, falls ustId dieser Firma < ustId der anderen Firma,
	*		  > 0, falls ustId dieser Firma > ustId der anderen Firma,
	*		  0 sonst
	*/
   public int compareTo(Object x){
	  if(!(x instanceof Firma))
		 throw new RuntimeException("das andere Objekt muss eine Firma sein");
	  return (int)(getUstId() - ((Firma)x).getUstId());
   }
   /**
	* Gibt den Namen der Firma wieder
	* @return den Namen der Firma
	*/
   public String getName(){
	  return name;
   }
   /**
	* Aendert den Namen der Firma
	* @param name String neuer Name
	*/
   public void setName(String name){
	  this.name = name;
   }
   /**
	* Gibt den Umsatz der Firma wieder
	* @return den Umsatz
	*/
   public double getUmsatz(){
	  return ustId;
   }
   /**
	* Aendert den Umsatz der Firma
	* @param umsatz double der neue Umsatz
	* @throws Runtimeexception bei neuem Umsatz < 0
	*/
   public void setUmsatz(double umsatz){
	  if(umsatz < 0)
		 throw new RuntimeException("Umsatz muss mindestens 0 sein");
	  this.umsatz = umsatz;
   }
   /**
	* Gibt die Umsatzsteueridentifikationsnummer der Firma wieder
	* @return die Umsatzsteueridentifikationsnummer
	*/
   public long getUstId(){
	  return ustId;
   }
   /**
	* Aendert die Umsatzsteueridentifikationsnummer der Firma
	* @param ustId long neue Umsatzsteueridentifikationsnummer
	*/
   public void setUstId(long ustId){
	  if(ustId < 0)
		 throw new RuntimeException("UstId muss groesser als 0 sein");
	  this.ustId = ustId;
   }
   /**
	* Gibt die Eigenschaften der Firma als String wieder
	* @return die Eigenschaften der Firma
	*/
   public String toString(){
	  return "\nFirma\t:\t"+name+"\nUmsatz\t:\t"+umsatz+"\nUstID\t:\t"+ustId;
   }
   /**
	* Errechnet den Hashcode der Firma
	* @return den Hashcode
	*/
   public int hashCode(){
	  long ustTmp = ustId;
	  long hashC = 1;
	  // Gehe durch die ustId und multipliziere die einzelnen Ziffern
	  while(ustTmp != 0){
		 if(ustTmp % 10 != 0)
			hashC *= (ustTmp % 10);
		 ustTmp /= 10;
	  }
	  return (int)hashC;
   }

   /*public int hashCode(){
	 return (int)ustId;
	 }
	*/
   /**
	* Prueft, ob die Umsatzsteueridentifikationsnummer der beiden
	* Firmen uebereinstimmen
	* @return true falls die Umsatzsteueridentifikationsnummern
	*		  uebereinstimmen, sonst false
	*/
   public boolean equals(Object x){
	  if(x == null || compareTo(x) != 0)
		 return false;
	  return true;
   }

}

