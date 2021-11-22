/******************************* MinMaxListe.java *****************************/
import AlgoTools.IO;

/**
 * Erweitert die VerweisListe um die Funktionalität stets Zugriff
 * auf das größte und kleinste Element zu haben. Dabei wird davon ausgegangen,
 * dass nur Objekte eingefügt werden, die Comparable implementieren.
 */
public class MinMaxListe extends VerweisListe {
  
   private Object max, min;

   /**
	* Gibt das kleinste Element der Liste zurück
	*
	* @return Das kleinste Element
	*/
   public Object getMin() {
	  return min;
   }

   /**
	* Gibt das größte Element der Liste zurück
	*
	* @return Das größte Element
	*/
   public Object getMax() {
	  return max;
   }
   /**
    * Fuegt ein neues Element in die Liste ein und veraendert das 
	* Minimum, oder Maximum, falls notwendig
	* @param x Object beliebiges vergleichbares Objekt
	*/
   public void insert(Object x){
	  if(empty()){
		 // Bei leerer Liste wird min und max auf das eingefuegte Element
		 // gesetzt
		 super.insert(x);
		 max = elem();
		 min = elem();
	  }
	  else{
		 // Sonst wird das neue Element eingefuegt und geprueft, ob
		 // es groesser als max (bzw. kleiner als min) ist
		 super.insert(x);
		 if(((Comparable)elem()).compareTo(min) < 0)
			min = elem();
		 if(((Comparable)elem()).compareTo(max) > 0)
			max = elem();
	  }
   }
   /**
    * Loescht das aktuelle Element und passt ggf. min, oder max, an
	* @throws RuntimeException Falls man am Ende der Liste ist
	*/
   public void delete(){
	  // Falls das zu loeschende Element min ist
	  if(elem().equals(min)){
		 // Loesche das Element
		 super.delete();
		 if(!empty()){
			Object tmp = elem();
			// Lege das aktuelle Element als min fest
			min = tmp;
			// Gehe bis zum Ende der Liste und suche ein kleineres Element
			while(!endpos()){
			   if(((Comparable)elem()).compareTo(min) < 0)
				  min = elem();
			   advance();
			}
			// Gehe zum Anfang der Liste und suche ein kleineres
			// Element, bis die Ausgangsposition erreicht wurde
			reset();
			while(!elem().equals(tmp)){
			   advance();
			   if(((Comparable)elem()).compareTo(min) < 0)
				  min = elem();
			}
		 }
		 // Setze min und max auf null, falls die Liste leer ist
		 else
			min = max = null;
	  }
	  else{
		 // Falls das zu loeschende Element max ist
		 if(elem().equals(max)){
			// Loesche das Element
			super.delete();
			if(!empty()){
			   Object tmp = elem();
			   // Lege das aktuelle Element als max fest;
			   max = tmp;
			   // Gehe bis zum Ende der Liste und suche ein groesseres Element
			   while(!endpos()){
				  if(((Comparable)elem()).compareTo(max) > 0)
					 max = elem();
				  advance();
			   }
			   // Gehe zum Anfang der Liste und suche ein groesseres
			   // Element, bis die Ausgangsposition erreicht wurde
			   reset();
			   while(!elem().equals(tmp)){
				  advance();
				  if(((Comparable)elem()).compareTo(max) > 0)
					 max = elem();
			   }
			}
			// Setzt min und max auf null, falls die Liste leer ist
			else
			   min = max = null;
		 }
		 // Das zu loeschende Element ist weder min, noch max
		 else
			super.delete();
	  }
   }
}
