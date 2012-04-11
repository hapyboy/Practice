package cn.hapyboy.queue.linarray;



public class Temp {
	static int[] ia = new int[2];
	static boolean add(int i){
		ia[0]= i;
		return true;
	}
	public static void main(String[] args) {
		 
		//java.lang.ArrayIndexOutOfBoundsException
		
		System.out.println("c=-------->"+add(7));
		System.out.println("7=-------->"+ia[0]);
	}

}
