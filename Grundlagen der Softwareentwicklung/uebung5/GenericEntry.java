
public class GenericEntry<T> implements Cloneable {
	   
		GenericEntry<T> next;
		T o;


	   GenericEntry() {
	      this(null, null);
	   }

	   GenericEntry(T o) {
	      this(o, null);
	   }

	   GenericEntry(T o, GenericEntry<T> e) {
	      this.o = o;
	      this.next = e;
	   }
	   
	   /**
	   
	   Der Aufruf super.clone() stößt die Methode clone() aus Object an und veranlasst so die Laufzeitumgebung,
	   ein neues Objekt zu bilden und die nicht-statischen Attribute zu kopieren. Die Methode kopiert elementweise 
->!!!  die Daten des aktuellen Objekts in das neue. 
	   Die Methode ist in der Oberklasse protected, aber das ist der Trick: Nur Unterklassen können clone() aufrufen,
	   keiner sonst.
	   
	   **/
	   
	   @Override 
	   public GenericEntry<T> clone(){
		   try{
			   GenericEntry<T> copy = (GenericEntry<T>) super.clone();
			   return copy;
		   }catch (CloneNotSupportedException e){	// Wird nicht auftreten
			   throw new InternalError();
		   }
		   
	   }

	    
	   public boolean equals(GenericEntry<T> entry){
		   if(entry == null) return false;
		   else if(this.o == entry.o && this.next== entry.next) return true;
		   else return false;
	   }
	   

}
