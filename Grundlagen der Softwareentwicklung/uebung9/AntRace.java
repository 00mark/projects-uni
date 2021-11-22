package antRace;

public class AntRace implements AntFields {

	public static void main(String[] args) {

		AntField field1 = new AntField(FIELD1);
		AntField field2 = new AntField(FIELD2);
		AntField field3 = new AntField(FIELD3);
		AntField field4 = new AntField(FIELD4);
		AntField field5 = new AntField(FIELD5);
		AntField field6 = new AntField(FIELD6);

		Ant ant = new Ant(field4, 2, 7, 1);

		Thread t = new Thread(ant);

		t.start();
		try{
		    t.join();
        }catch(InterruptedException e){
        }

		System.out.println(field4.toString());
	}
}
