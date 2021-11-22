import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Test52 {

	public static void main(String[] args) {

		GenericEntry<String> one = new GenericEntry<String>();
		boolean equaltest = true;
		
		// Teste clone()
		// clone contract
		// 1. Klon und Original sind verschiedene Objekte, d.h. sie sind an verschiedenen Stellen im Speicher angelegt.
		// 2. Klon und Original sind vom selben Typ.
		// 3. Klon und Original sollten gleich sein im Sinne von equals(), d.h. sie sollten den gleichen Inhalt haben.
		System.out.println("Test clone() für GenericEntry");
		System.out.println("-----------------------------");
		GenericEntry<String> neu = one.clone();
		System.out.println("1/3 tests - Klon und Original liegen an unterschiedlichen Adressen: "+ (one.clone()!=one));
		System.out.println("2/3 tests - Klon und Original sind vom gleichen Typ: "+ (neu.getClass()==one.getClass()) );
		System.out.println("3/3 tests - Klon und Original sollten gleich sein im Sinne von equals(), d.h. sie sollten den gleichen Inhalt haben: "+ one.equals(neu) );
		System.out.println("\n");
		
		System.out.println("teste clone() für GenericList");
		System.out.println("-----------------------------");
		GenericList<String> alteListe = new GenericList<String>();
		
		GenericList<String> neueListe = alteListe.clone();
		System.out.println("1/3 tests - Klon und Original liegen an unterschiedlichen Adressen: "+ (alteListe.clone()!=alteListe));
		System.out.println("2/3 tests - Klon und Original sind vom gleichen Typ: "+ (alteListe.getClass()==neueListe.getClass()) );
		System.out.println("3/3 tests - Klon und Original sollten gleich sein im Sinne von equals(), d.h. sie sollten den gleichen Inhalt haben: "+ alteListe.equals(neueListe));
		System.out.println("\n");
		
		
		GenericList<String> test = new GenericList<String>();
		test.add("eins");
		test.add("zwei");
		test.add("drei");
		test.add("vier");
		
		GenericList<String> cloneTest = new GenericList<String>();
		cloneTest = test.clone();
		
		/*	Ausgabe der beiden Listen
		test.reset();
		cloneTest.reset();
		while(!test.endpos()){
			System.out.println(test.elem());
			test.advance();
		}
		
	
		while(!cloneTest.endpos()){
			System.out.println(cloneTest.elem());
			cloneTest.advance();
		}
		*/
		
		
		test.reset();
		cloneTest.reset();
		while(equaltest && test.endpos() && cloneTest.endpos()){
			if(test.elem().equals(cloneTest.elem())) equaltest = true;
			else equaltest = false;	
			
			if(!test.endpos() && !cloneTest.endpos()){
				cloneTest.advance();
				test.advance();
			}else equaltest = false;	
		}
		
		System.out.println("Beide Listen müssten gleich sein: "+equaltest);
		
	}	

}
