/******************************  RekursiveMethoden.java  **********************/

import AlgoTools.IO;

/**
 * @version 24.11.16
 * 
 * Ansammlung von rekursiven Methoden
 *
 * @author  mhiltenkamp
 */

public class RekursiveMethoden {

   /**
	* Testet die eingegebenen Parameter der Ackermann Funktion auf Loesbarkeit
	* @param n natuerliche Zahl, erster Parameter der Ackermann Funktion
	* @param m natuerliche Zahl, zweiter Parameter der Ackermann Funktion
	* @return Ackermann Methode, die die Berechnungen durchfuehrt
	* @throws RuntimeException wenn n oder m groesser 0, oder die eingegebenen
	*						  Parameter zu einem Stackoverflow fuehren
	*/
   public static int ackermann( int n, int m ){
	  if( n < 0 || m < 0 )
		 throw new RuntimeException("Nur natuerliche Zahlen sind erlaubt");
	  else if( n > 4 )
		 throw new RuntimeException("Zu großer Wert des ersten Parameters");
	  else if( n == 4 && m > 0 || n == 3 && m > 12 || n <= 2 && m > 10000 )
		 throw new RuntimeException("Zu großer Wert des zweiten Parameters");
	  else 
		 return ackermannRek( n, m );
   }

   /**
	* Fuehrt die Ackermann Funktion mit den getesteten Parametern durch
	* @param n int natuerliche Zahl, erster Parameter der Ackermann Funktion
	* @param m int natuerliche Zahl, zweiter Parameter der Ackermann Funktion
	* @return ausgerechneten Wert der Ackermann Funktion
	*/
   private static int ackermannRek( int n, int m ){
	  if( n == 0 )
		 return m + 1;
	  else if( m == 0 )
		 return ackermannRek( n-1, 1 );
	  else
		 return ackermannRek( n-1, ackermannRek(n, m-1 ));
   }

   /**
	* Testet, ob ein korrektes Wort eingegeben wurde
	* @param wort boolean array mit Buchstaben als Elemente
	* @return Palindrom Methode, die ueberprueft, ob das Wort Palindrom ist
	* @throws RuntimeException Bei Zeichen, die keine Buchstaben sind
	*/
   public static boolean istPalindrom( char[] wort ){
	  for( int i = 0; i < wort.length; i++ ){
		 // Ist Buchstabe?
		 if( wort[i] > 64 && wort[i] < 91 || wort[i] > 96 && wort[i] < 123 );
		 else
			throw new RuntimeException("Nur Buchstaben erlaubt");
	  }
	  return istPalindromRek( wort );
   }

   /**
	* Testet, ob das eingegebene Wort ein Palindrom ist
	* @param wort boolean array mit Buchstaben als Elemente
	* @return Wort = Palindrom : true, sonst false
	*/
   private static boolean istPalindromRek( char[] wort ){
	  if( wort.length <= 1 )
		 return true;
	  else{
		 char[] wortNew = new char[wort.length-2];
		 for( int i = 1; i < wort.length-1; i++ )
			wortNew[i-1] = wort[i];
		 return wort[wort.length-1] == wort[0] && istPalindromRek( wortNew );
	  }
   }

   /**
	* Testet, ob Potenzieren mit den eingegebenen Parametern moeglich ist
	* @param basis double Basis des exponentiellen Ausdrucks
	* @param exponent int ganzzahliger Exponent, groesser 0 bei basis gleich 0
	* @return Potenz Methode, die den Wert des Ausdrucks berechnet
	* @throws RuntimeException bei basis gleich 0 und exponent kleiner 1
	*/
   public static double potenz( double basis, int exponent ){
	  if( basis == 0 && exponent < 1 )
		 throw new RuntimeException("Division durch 0!");
	  else
		 return potenzRek(basis, exponent);
   }

   /**
	* Gibt den Wert des exponentiellen Ausdrucks wieder
	* @param basis double Basis des exponentiellen Ausdrucks
	* @param exponent int ganzzahliger Exponent, groesser 0 bei basis gleich 0
	* @return ausgerechneten Wert des Ausdrucks
	*/
   private static double potenzRek(double basis, int exponent){
	  if( exponent == 0 )
		 return 1;
	  else{
		 if( exponent > 0 )
			return basis * potenzRek(basis, exponent -1);
		 else
			return 1/basis * potenzRek(basis, exponent +1);
	  }
   }

   public static void methodenTesten( char weiter ){

	  int n, m, exponent;
	  char[] wort;
	  boolean korrekt;
	  double basis;

	  // Ackermann
	  if( weiter == 'A' || weiter == 'a' ){
		 do{
			n = IO.readInt("Wert des ersten Parameters?\n>");
			// Laesst sich errechnen?
		 }while( n < 0 || n > 4 );

		 do{
			m = IO.readInt("Wert des zweiten Parameters?\n>");
			// Laesst sich errechnen?
		 }while( m < 0 || n == 4 && m > 0 || n == 3 && m > 12
			   || n <= 2 && m > 10000 );

		 IO.println("a("+n+","+m+") = "+ackermann( n, m ));
	  }

	  // Palindrom
	  else if( weiter == 'P' || weiter == 'p' ){
		 do{
			korrekt = true;
			wort = IO.readChars("Buchstaben des Wortes?\n>");
			for( int i = 0; i < wort.length; i++ ){
			   // Ist Buchstabe?
			   if( wort[i] > 64 && wort[i] < 91 || wort[i] > 96 && wort[i] < 123 );
			   else{
				  korrekt = false;
				  break;
			   }
			}
		 }while( !korrekt );

		 if( istPalindrom(wort) )
			IO.println("Das eingegebene Wort ist ein Palindrom");
		 else
			IO.println("Das eingegebene Wort ist kein Palindrom");
	  }

	  // Potenz
	  else{
		 basis = IO.readDouble("Basis?\n>");
		 do{
			exponent = IO.readInt("Exponent?\n>");
			// Divsion durch 0?
		 }while( basis == 0 && exponent < 1 );

		 IO.println(basis+"^"+exponent+" = "+potenz(basis, exponent));
	  }
   }

   public static void main(String[] argv) {

	  char weiter = '0';

	  IO.println("\t+-------------------------------+");
	  IO.println("\t| A :\tAckermann Funktion\t|");
	  IO.println("\t| P :\tPalindrom\t\t|");
	  IO.println("\t| E :\tPotenz Funktion\t\t|");
	  IO.println("\t+-------------------------------+");
	  IO.println("\t| Enter ohne Eingabe : Beenden\t|");
	  IO.println("\t+-------------------------------+");
	  while( true ){
		 do{
			weiter = IO.readChar("\nWelche Methode soll ausgefuehrt werden?\n>");
		 }while( weiter != 'A' && weiter != 'a' && weiter != 'P' && weiter != 'p'
			   && weiter != 'E' && weiter != 'e' && weiter != '\n' );
		 if( weiter == '\n' ){
			IO.println("\nBeenden.");
			break;
		 }
		 else{
			IO.println();
			methodenTesten( weiter );
		 }
	  } 
   }
}

