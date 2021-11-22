/******************************  KellerListe.java  ****************************/

import AlgoTools.IO;

/**
 * @version 17.01.17
 *
 * Implementiert Liste mit Hilfe von zwei Verweiskellern
 *
 * @author  mhiltenkamp
 */

public class KellerListe implements Liste{

   private Keller k1, k2;

   /**
    * Legt zwei neue VerweisKeller an
	*/
   public KellerListe(){
	  k1 = new VerweisKeller();
	  k2 = new VerweisKeller();
   }
   /**
    * Prueft, ob die Liste leer ist
	* @return true, wenn beide VerweisKeller leer sind, sonst false
	*/
   public boolean empty(){
	  return k1.empty() && k2.empty();
   }
   /**
    * Prueft, ob die Liste an der Endposition ist
	* @return true, wenn der erste VerweisKeller leer ist, sonst false
	*/
   public boolean endpos(){
	  // k1.empty(), da dann entweder die Liste leer ist, oder
	  // man an der letzten Stelle der Liste ist, sich also alle
	  // Elemente in k2 befinden
	  return k1.empty();
   }
   /**
    * Rueckt die Liste an den Anfang
	*/
   public void reset(){
	  // Bringe alle Elemente von k2 nach k1
	  while(!k2.empty()){
		 k1.push(k2.top());
		 k2.pop();
	  }
   }
   /**
    * Ruecke ein Element in der Liste weiter
	*/
   public void advance(){
	  if(k1.empty())
		 throw new RuntimeException("Liste ist leer, oder an Endposition");
	  // Bringe das aktuelle Elemente von k1 nach k2
	  k2.push(k1.top());
	  k1.pop();
   }
   /**
    * Gibt den Inhalt des aktuellen Elements wieder
	* @return das aktuelle Element
	*/
   public Object elem(){
	  return k1.top();
   }
   /**
    * Fuegt ein neues Element vor das aktuelle Element ein
    * und macht das neue Element zum aktuellen Element
	* @param x Object, beliebig
	*/
   public void insert(Object x){
	  k1.push(x);
   }
   /**
    * Loescht das aktuelle Element und macht seinen Nachfolger
	* zum aktuellen Element
	* @throws RuntimeException wenn die Liste leer ist
	*/
   public void delete(){
	  if(k1.empty())
		 throw new RuntimeException("Liste ist leer");
	  k1.pop();
   }
}

