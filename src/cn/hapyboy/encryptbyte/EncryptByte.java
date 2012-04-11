package cn.hapyboy.encryptbyte;



public class EncryptByte {
	
	private final byte[] encoderset;
	private final byte[] decoderset;
	
	public EncryptByte(String name,String password){
		Ruse ruse = new Ruse();
		encoderset = ruse.getSet(name, password);
		decoderset = Deset.deset;
		name = null;
		password = null;
	}
	public EncryptByte(String name,String password,IRuse ruse){
		
		encoderset = ruse.getSet(name, password);
		
		name = null;
		password = null;
		
		decoderset = new byte[256] ;
		int c;
		for (int i = 0; i<256; i++){
			c = encoderset[i]<0?encoderset[i]+256:encoderset[i];
			decoderset[c] = (byte) i;
		}
	}
	
	int temp;
	public byte[] encoder(String str){
		byte[] original =str.getBytes();
		
		byte[] c = new byte[original.length];
		
		
		for (int i =0; i<original.length; i++){
			temp = original[i] <0 ? original[i] + 256 : original[i];
			c[i] = encoderset[temp];
		}
		
		return c;
	}

	public byte[] decoder(byte[] original) {

		byte[] c = new byte[original.length];

		for (int i = 0; i < original.length; i++) {
			temp = original[i] < 0 ? original[i]+256 : original[i];
			c[i] = decoderset[temp];
		}

		return c;
	}
	static final class Deset{
		static byte[] deset = new byte[256];
	}

	final private class Ruse implements IRuse {

		@Override
		public byte[] getSet(String name, String password) {
			
			long start = System.currentTimeMillis();
			
			
			byte[] narray = name.getBytes();
			byte[] parray = password.getBytes();
			name = null;
			password = null;
			int n = narray.length;
			int p = parray.length;
			
			final int setlength =256;
			
			byte[] encoderset = new byte[setlength];
			byte[] decoderset = Deset.deset;
			
			LinkeList list = new LinkeList();
			for(int  i = 0; i < setlength; i++){
				list.add(new Linke((byte)i));
			}
			
			int count = setlength;
			
			byte tempbyte;
			int c;
			for (int i = 0; i<256; i++){
				c = (parray[(i%p)])*(parray[(i%p)])+(narray[(i%n)]);
				c = c%count;
				tempbyte = list.remove(c);
				encoderset[i] = tempbyte;
				c = tempbyte<0?tempbyte+256:tempbyte;
				
				decoderset[c] = (byte) i;
				
				count--;
				
			}
			long endtime = System.currentTimeMillis();
			
			//测试用代码
			System.out.println("编码所用时间："+(endtime-start));
			
			
			return encoderset;
		}

	}
	final private class LinkeList{
		
		
		private Linke head;
		private Linke end;
		
		private Linke temp;
		private byte result;

		
		void add(Linke linke){
			if(end != null){
				end.next = linke;
				end = linke;
			}else{
				head = linke;
				end = linke;
			}
		}
		byte remove(int index){
			if(index == 0){
				result = head.b;
				head = head.next;
				return result;
			}
			
			temp = head;
			for (int i = 0; i<(index-1); i++){
				temp = temp.next;
			}
			result = temp.next.b;
			temp.next = temp.next.next;
			return  result;
		}
		

	}

	private final class Linke {

		byte b;
		Linke next;

		Linke(byte b) {
			this.b = b;
		}
	
	}
	/**
	public static void main(String[] args) {
		EncryptByte enc = new EncryptByte("hapychina","Lvbinger2");
		String s = "这里是自己写的加密软件1@、||*1";
		byte[] b = s.getBytes();
		byte[] s2;
		s2 = enc.encoder(s);
		//System.out.println(s);
		System.out.println("中间线--------------------------------------------->");
		s = new String(enc.decoder(s2));
		String str = new String(b);
		System.out.println(s);
		System.out.println(str);
	}
*/
}
