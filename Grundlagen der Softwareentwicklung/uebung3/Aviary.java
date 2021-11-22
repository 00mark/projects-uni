/**
 * A Class, showing the different abilities of birds in the aviary
 *
 * @author Kevin Trebing
 */
public class Aviary {

   public static void main(String[] args) {
      Dodo dodo = new Dodo();
      // Die Referenz dodo vom Typ Dodo verweist auf eine Instanz vom Typ Dodo

      System.out.println("1: ((Bird)dodo).ability           : " + ((Bird) dodo).ability);
      // Die Instanz dodo des Typs Dodo wird durch eine Typumwandlung als Klasse
      // des Typs Bird aufgefasst. Das ist moeglich, da Bird die Superklasse von
      // Dodo ist. Da es sich bei ability um eine Variable handelt tritt
      // das statische Binden in Kraft und es wird das Attribut ability von
      // Bird ausgegeben
      System.out.println("2: dodo.ability                   : " + dodo.ability);
      // Die Instanzvariable ability der Instanz dodo, vom Typ Dodo, wird 
      // ausgegeben. Das Attribut ability der Klasse Dodo ueberlagert dabei
      // das Attribut ability der Klasse Bird
      System.out.println("3: dodo.getAbility()              : " + dodo.getAbility());
      // Die Instanzmethode getAbility() der Klasse Dodo wird aufgerufen. Diese
      // ruft wiederum die Instanzmethode getAbility() der SuperKlasse(Bird)
      // auf, welche die Instanzvariable ability wiedergibt. Da die Methode in
      // der Klasse Bird ist, wird das Attribut ability der Klasse Bird wiedergegeben
      // Die Methode getAbility() der Klasse Dodo ueberschreibt die Methode 
      // getAbility() der Klasse Bird

      Parrot parrot = new Parrot();
      // Die Referenz parrot vom Typ Parrot verweist auf eine Instanz vom Typ Parrot
      
      System.out.println("4: parrot.allAbilities()          : " + parrot.allAbilities());
      // Die Instanzmethode allAbilites() der Klasse Parrot wird aufgerufen. Diese
      // ruft die Instanzmethode allAbilities() der Klasse Bird auf, welch die
      // Instanzmethode getAbility() der Klasse Bird aufruft und schliesslich
      // die Insanzvariable ability der Klasse Bird wiedergibt. Die Instanzmethode
      // allAbilities() der Klasse Parrot gibt schliesslich die Klassenvariable
      // ability der Klassen Parrot und Bird wieder. Sie ueberschreibt die 
      // Instanzmethode allAbilities() der Klasse Bird.
      System.out.println("5: parrot.ability                 : " + parrot.ability);
      // Die Instanzvariable ability der Klasse parrot wird wiedergegeben. 
      // ability ueberlagert die Insanzvariable ability der Superklasse Bird

      Bird carsten = new Dodo();
      // Die Referenz carsten vom Typ Bird verweist auf eine Instanz vom Typ Dodo
      
      System.out.println("6: carsten.ability                : " + carsten.ability);
      // Da Instanzvariablen statisch gebunden werden, wird das Attribut ability
      // der Klasse Bird wiedergegeben
      System.out.println("7: ((Bird)carsten).allAbilities() : " + ((Bird) carsten).allAbilities());
      // Da Methoden in java dynamisch gebunden werden, wird zur Laufzeit die 
      // Methode allAbilities() der Klasse Dodo ausgefuehrt. Dieser Ausdruck
      // wuerde zu einem Fehler zur Compilezeit fuehren, haette die Klasse
      // Bird keine Methode allAbilities()

      Bird einstein = parrot;
      // Die Referenz einstein vom Typ Bird verweist auf die Referenz parrot,
      // welche auf eine Instanz vom Typ Parrot verweist
      
      System.out.println("8: einstein.allAbilities()        : " + einstein.allAbilities());
      // Da einstein und parrot auf die Selbe Instanz verweisen, verhaelt sich 
      // dieser Ausdruck wie 4
      System.out.println("9: einstein.getAbility()          : " + einstein.getAbility());
      // Die Instanzmethode getAbility() aus der Klasse Bird wird ausgefuehrt.
      // Aufgrund des dynamischen Bindens wird zur Laufzeit zuerst in der Klasse
      // Parrot nach einer Methode mit passender Signatur gesucht. Nachdem
      // dieser Versuch fehlschlaegt, wird in der SuperKlasse nach einer passenden
      // Methode gesucht, welche dann schliesslich ausgefuehrt wird. Da einstein
      // vom Typ Bird ist, kommt es zu keinem Fehler zur Compilezeit
      System.out.println("10: ((Parrot)einstein).ability    : " + ((Parrot) einstein).ability);
      // Da java bei Klassenattributen statisch bindet, wird die Instanzvariable
      // ability der Klasse Parrot wiedergegeben. Ohne die Typumwandlung waere
      // die Instanzvariable ability der Klasse Bird wiedergegeben worden
   }
}
