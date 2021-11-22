/******************************  VerweisSchlange.java  ************************/

import AlgoTools.IO;

/**
 * @version 17.01.17
 * 
 * Implementiert Schlange mit Hilfe von Eintraegen
 *
 * @author  mhiltenkamp
 */

public class VerweisSchlange implements Schlange{
   
   private Eintrag head, tail;
   /**
    * Legt zwei neue Eintraege an und setzt ihren Inhalt, sowie den
	* Verweis auf den naechsten Eintrag auf null
	*/
   public VerweisSchlange(){
	  head = new Eintrag();
	  tail = new Eintrag();
	  head.next = tail.next = null;
	  head.inhalt = tail.inhalt = null;
   }
   /**
    * Prueft ob die Schlange leer ist
	* @return true, wenn die Schlange leer ist, sonst false
	*/
   public boolean empty(){
	  // Wenn tail.next auf keinen Eintrag verweist, ist die Schlange leer
	  return head.next == null;
   }										
   /**
    * Fuegt ein neues Element ans Ende der Schlange an
	*/
   public void enq(Object x){
	  // Lege einen neuen Eintrag mit x als Inhalt an
	  Eintrag e = new Eintrag();;
	  e.inhalt = x;
	  // Wenn head noch auf nichts verweist, lasse head auf den neuen 
	  // Eintrag verweisen
	  if(head.next == null){
		 head.next = e;
		 e.next = null;
	  }
	  // Sonst lasse den aktuell letzten Eintrag auf den neuen Eintrag
	  // verweisen
	  else
		 tail.next.next = e;
	  // Lasse tail auf den neuen Eintrag verweisen
	  tail.next = e;
   }										
   /**
    * Gibt das vorderste Element wieder
	* @throws RuntimeException, wenn die Schlange leer ist
	* @return das vorderste Element
	*/
   public Object front(){
	  if(empty())
		 throw new RuntimeException("Schlange besitzt keine Elemente");
	  return head.next.inhalt;
   }										
   /**
    * Entfern das vorderste Element der Schlange
	* @throws RuntimeException, wenn die Schlange leer ist
	*/
   public void deq(){
	  if(empty())
		 throw new RuntimeException("Schlange besitzt keine Elemente");
	  // Lasse head auf den Verweis des naechsten Eintrags verweisen
	  head.next = head.next.next;
	  // Falls head auf null verweist, lasse auch tail auf null verweisen
	  if(head.next == null)
		 tail.next = null;
   }										
}

