/******************************  OffeneHashtabelle.java  **********************/

import AlgoTools.IO;

/**
 * @version 28.01.17
 * Legt eine offene Hashtabelle mit 7 Feldern an und ermoeglicht Operationen
 * des Interface Menge
 * @author  mhiltenkamp
 */

public class OffeneHashtabelle implements Menge{

   private Liste[] tabelle = new Liste[7];
   /**
    * Legt einen neue Hashtabelle an und erstellt leere Listen in den Feldern
	*/
   public OffeneHashtabelle(){
	  for(byte i = 0; i < tabelle.length; i++)
		 tabelle[i] = new VerweisListe();
   }
   /**
    * Prueft, ob die Hashtabelle leer ist
	* @return true falls leer, sonst false
	*/
   public boolean empty(){
	  for(byte i = 0; i < tabelle.length; i++)
		 if(!tabelle[i].empty())
			return false;
	  return true;
   }
   /**
    * Prueft, ob sich das eingegebene Element in der Tabelle befindet
	* @return true falls das Element in der Tabelle ist, sonst false
	*/
   public boolean elemInTabelle(Comparable x){
	  // Errechne die Position des Elements
	  byte location = (byte)(x.hashCode() % 7);
	  tabelle[location].reset();
	  // Gehe die Liste an der Postion des Elements so lange durch,
	  // bis das Element gefunden wurde, oder das Ende der Liste 
	  // erreicht wurde
	  while(!tabelle[location].endpos() &&
			!tabelle[location].elem().equals(x))
		 tabelle[location].advance();
	  if(!tabelle[location].endpos())
		 return true;
	  return false;
   }
   /**
    * Gibt das eingegebene Element wieder, falls es sich in der Tabelle befindet
	* @param x Comparable das gesuchte Element
	* @return das Element, falls es in der Tabelle ist, sonst null
	*/
   public Comparable lookup(Comparable x){
	  if(elemInTabelle(x))
		 return x;
	  return null;
   }
   /**
    * Versucht das eingegebene Element in die Tabelle einzufuegen
	* @param x Comparable das einzufuegende Element
	* @return true falls erfolgreich, sonst false
	*/
   public boolean insert(Comparable x){
	  if(elemInTabelle(x))
		 return false;
	  tabelle[x.hashCode() % 7].reset();
	  tabelle[x.hashCode() % 7].insert(x);
	  return true;
   }
   /**
    * Versucht das eingegebene Element aus der Tabelle zu loeschen
	* @param x Comparable das zu loeschende Element
	* @return true falls erfolgreicht, sonst false
	*/
   public boolean delete(Comparable x){
	  if(!elemInTabelle(x))
		 return false;
	  tabelle[x.hashCode() % 7].delete();
	  return true;
   }


}

