/******************************  Zahlen.java  *********************************/

import AlgoTools.IO;

/**
 * @version 03.11.16
 *
 * Gibt die eingelesene Zahl als englisches Wort aus (10 = ten)
 *
 * @author  mhiltenkamp
 */

public class Zahlen {

  public static void main(String[] argv) {
     
    // Anlegung von Variablen für die gesamte Zahl sowie Einer-, Zehner- und+
    // Hunderterstellen ( =0, um zu sehen ob die Variable beschrieben wurde
    int zahl, einer = 0, zehner = 0, hunderter = 0;
     
    // Einlesen einer korrekten Zahl
    do{
      zahl = IO.readInt("Bitte eine Zahl eingeben ([10,999]) >");
    }while( 10 > zahl || zahl > 999 );
    
	// Berechnung Einer-, Zehner- und Hunderterstelle
	// Sonderfall Zehnerstelle < 20 
	if ( (zahl % 100) < 20){
	  zehner = zahl % 100;
	  hunderter = zahl - zehner;
    }
	else{
	  einer = (zahl % 100) % 10;
	  zehner = (zahl % 100) - einer;
	  hunderter = zahl - (einer + zehner);
	}
    // Ausgabe der Hunderterstelle (falls vorhanden). Dabei wird dazwischen+
    // unterschieden, ob noch eine Zehner- oder Einerstelle folgt ("and") oder
    // nicht ("")
    if ( hunderter > 0 && (zehner > 0 || einer > 0)){
     switch( hunderter ){
       case 100 : IO.print("one hundred and "); break;
       case 200 : IO.print("two hundred and "); break;
       case 300 : IO.print("three hundred and "); break;
       case 400 : IO.print("four hundred and "); break;
       case 500 : IO.print("five hundred and "); break;
       case 600 : IO.print("six hundred and "); break;
       case 700 : IO.print("seven hundred and "); break;
       case 800 : IO.print("eight hundred and "); break;
       case 900 : IO.print("nine hundred and "); break;
     }
    }
    else{
      if ( hunderter > 0 ){
       switch( hunderter ){
         case 100 : IO.print("one hundred"); break;
         case 200 : IO.print("two hundred"); break;
         case 300 : IO.print("three hundred"); break;
         case 400 : IO.print("four hundred"); break;
         case 500 : IO.print("five hundred"); break;
         case 600 : IO.print("six hundred"); break;
         case 700 : IO.print("seven hundred"); break;
         case 800 : IO.print("eight hundred"); break;
         case 900 : IO.print("nine hundred"); break;
       }
      }
    }
    
    // Ausgabe der Zehnerstelle inklusive des Sonderfalls 10 < Zehner < 20
    if ( zehner > 0 ){
      switch( zehner ){
        case 10 : IO.print("ten"); break;
        case 11 : IO.print("eleven"); break;
        case 12 : IO.print("twelve"); break;
        case 13 : IO.print("thirteen"); break;
        case 14 : IO.print("fourteen"); break;
        case 15 : IO.print("fifteen"); break;
        case 16 : IO.print("sixteen"); break;
        case 17 : IO.print("seventeen"); break;
        case 18 : IO.print("eighteen"); break;
        case 19 : IO.print("nineteen"); break;
        case 20 : IO.print("twenty"); break;
        case 30 : IO.print("thirty"); break;
        case 40 : IO.print("forty"); break;
        case 50 : IO.print("fifty"); break;
        case 60 : IO.print("sixty"); break;
        case 70 : IO.print("seventy"); break;
        case 80 : IO.print("eighty"); break;
        case 90 : IO.print("ninety"); break;
      }
    }
    
    // Ausgabe der Einerstelle
    if ( einer > 0 ){
      switch( einer ){
        case 1 : IO.print("-one"); break;
        case 2 : IO.print("-two"); break;
        case 3 : IO.print("-three"); break;
        case 4 : IO.print("-four"); break;
        case 5 : IO.print("-five"); break;
        case 6 : IO.print("-six"); break;
        case 7 : IO.print("-seven"); break;
        case 8 : IO.print("-eight"); break;
        case 9 : IO.print("-nine"); break;
      }
    } 

    IO.println();
  }
}

