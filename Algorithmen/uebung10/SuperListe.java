/******************************  SuperListe.java  *****************************/

import AlgoTools.IO;

/**
 * @version 17.01.17
 * 
 * Erweitert VerweisListe mit zusaetzlichen Methoden
 *
 * @author  mhiltenkamp
 */

public class SuperListe extends VerweisListe implements Liste{

   /**
    * Dreht die Liste um und setzt das aktuelle Element auf das Erste
	*/
   public void umdrehen(){
	  reset();
	  int i = 1;
	  while(true){
		 // Bis zum i-ten Element gehen
		 for(int n = 0; n < i; n++)
			advance();
		 // Falls das letzte Element der Liste erreicht wurde wird
		 // die Schleife verlassen
		 if(endpos())
			break;
		 // Speicher den Wert des aktuellen Elements
		 Object tmp = elem();
		 // Loesche das aktuelle Element
		 delete();
		 // Wechsel zum ersten Element und fuege das gespeicherte
		 // Element vor diesem ein
		 reset();
		 insert(tmp);
		 i++;
	  }
	  reset();
   }
   /**
    * Ueberpruefe die Liste auf Dopplungen und loesche diese
    */
   public void unique(){
	  reset();
	  // Solange nicht die Endposition erreicht wurde
	  while(!endpos()){
		 // Speicher das aktuelle Element
	     Object tmp = elem();
		 // Wechsel zum naechsten Element der Liste
		 advance();
		 if(endpos())
			break; 
		 // Ueberpruefe, ob gespeichertes Element = aktuelles Element
		 // und loesche ggf. das aktuelle Element
		 if(tmp.equals(elem()))
			delete();
	  }
	  reset();
   }
   /**
    * Gibt das n-te Element der Liste aus
	* @throws RuntimeException falls n > Anzahl Elementde der Liste
	* @return Das n-te Element
	*/
   public Object elem(int n){
	  reset();
	  int i;
	  // Wechsel solange zum naechsten Element, bis das n-te erreicht wurde
	  for(i = 1; i < n && !endpos(); i++)
		 advance();
	  if(i != n)
		 throw new RuntimeException("Element nicht in der Liste");
	  return elem();
   }
}

