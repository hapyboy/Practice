package cn.hapyboy.monkeygame;
/*
 * 	��1000ֻ����Χ��һ��Ȧ�����θ�ÿֻ���ӱ�ţ�Ϊ1��1000��
 * Ȼ���1��ʼ����1,2,1,2�Ĵ�����������1��ԭ�ز���������2���˳�Ȧ��
 * ֱ��ֻ��1ֻ����Ϊֹ�������ʣ�µ���ֻ���ӵı���Ƕ���
 * 
 */

public class MonkeyGame {
	static final int N =6;
	public static void main(String[] args) {
		int[] mankey = new int[N];
		for (int i = 0; i < N; ) {
			mankey[i] = ++i;
		}
		
		int index=0;
		int count =1;
		int limit=N;
		while(limit != 1){
			if(count%5==0){
				//System.out.println(Monkey[index]+ "�ź����˳�");
				System.arraycopy(mankey, index+1, mankey, index, --limit-index);
				
			} else {
				index++;
			}

			count++;
			
			if(index >= limit){
				index = 0;
			}
		}
		
		System.out.println("���º��ӱ�ţ�"+mankey[0]);
	}
}
