package cn.hapyboy.encrypt;



public class Encrypt {
	
	private final char[] encoderset;
	private final char[] decoderset;
	private  char[] tempset;
	
	public Encrypt(String name,String password){
		IRuse ruse = new Ruse();
		encoderset = ruse.getCharset(name, password);
		decoderset = tempset;
		tempset = null;
		name = null;
		password = null;
	}
	public Encrypt(String name,String password,IRuse ruse){
		
		encoderset = ruse.getCharset(name, password);
		
		name = null;
		password = null;
		
		//����ʡ��һ�¶����������ñ��뼯���Ƶ������뼯
		
		//����
		decoderset = new char[256*256] ;
		char c;
		for (int i = 0; i<(256*256); i++){
			c = encoderset[i];
			decoderset[c] = (char) i;
		}
	}
	

	public String encoder(String str){
		char[] original = new char[str.length()];
		str.getChars(0, str.length(), original, 0);
		
		char[] c = new char[original.length];
		
		for (int i =0; i<original.length; i++){
			c[i] = encoderset[original[i]];
		}
		
		return new String(c);
	}
	public String decoder(String str){
		char[] secret = new char[str.length()];
		str.getChars(0, str.length(), secret, 0);
		
		char[] c = new char[secret.length];
		
		for (int i =0; i<secret.length; i++){
			c[i] = decoderset[secret[i]];
		}
		
		return new String(c);
	}

	final private class Ruse implements IRuse {

		@Override
		public char[] getCharset(String name, String password) {
			
			char[] encoderset = new char[256*256];
			char[] decoderset = new char[256*256];
			Encrypt.this.tempset = decoderset;
			
			long start = System.currentTimeMillis();
			
			
			LinkeList list = new LinkeList();
			for(int i = 0; i < (256*256); i++){
				list.add(new Linke(i));
			}
			int n = name.length();
			int p = password.length();
			int count = 256*256;
			char[] narray = new char[n];
			name.getChars(0, n, narray, 0);
			char[] parray = new char[p];
			password.getChars(0, p, parray, 0);
			name = null;
			password = null;
			int tempint;
			for (int i = 0; i<(256*256); i++){
				int c = (parray[(i%p)])*(narray[(i%n)]);
				c = c%count;
				tempint = list.remove(c);
				encoderset[i] = (char)tempint;
				decoderset[tempint] = (char) i;
				
				count--;
				
			}
			long endtime = System.currentTimeMillis();
			
			//�����ô���
			System.out.println("��������ʱ�䣺"+(endtime-start));
			
			
			return encoderset;
		}

	}
	final private class LinkeList{
		
		
		private Linke head;
		private Linke end;
		
		private Linke temp;
		private int result;

		
		void add(Linke linke){
			if(end != null){
				end.next = linke;
				end = linke;
			}else{
				head = linke;
				end = linke;
			}
		}
		int remove(int index){
			if(index == 0){
				result = head.i;
				head = head.next;
				return result;
			}
			
			temp = head;
			for (int i = 0; i<(index-1); i++){
				temp = temp.next;
			}
			result = temp.next.i;
			temp.next = temp.next.next;
			return  result;
		}
		

	}

	private final class Linke {

		int i;
		Linke next;

		Linke(int i) {
			this.i = i;
		}
	
	}
	class SetPackage{
		char [] encoderset;
		char[] decoderset;
		SetPackage(char[] encoder,char[] decoder){
			this.encoderset =encoder;
			this.decoderset = decoder;
		}
	}
	public static void main(String[] args) {
		Encrypt enc = new Encrypt("�������","vbiNg#er2");
		String s = "�������Լ�дm=,.*[�ļ���������ҿ� ���ǲ������������У����Ǻǡ���д�㶫������";
		s = enc.encoder(s);
		System.out.println("���ģ� "+s);
		System.out.println("�м���--------------------------------------------->");
		System.out.println("���ģ�"+enc.decoder(s));
	}

}
