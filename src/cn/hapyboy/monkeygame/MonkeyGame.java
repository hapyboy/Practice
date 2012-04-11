package cn.hapyboy.monkeygame;
/*
 * 	有1000只猴子围成一个圈，依次给每只猴子编号，为1到1000，
 * 然后从1开始按照1,2,1,2的次序报数，报到1的原地不动，报到2的退出圈，
 * 直到只有1只猴子为止，问最后剩下的那只猴子的编号是多少
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
				//System.out.println(Monkey[index]+ "号猴子退出");
				System.arraycopy(mankey, index+1, mankey, index, --limit-index);
				
			} else {
				index++;
			}

			count++;
			
			if(index >= limit){
				index = 0;
			}
		}
		
		System.out.println("留下猴子编号："+mankey[0]);
	}
}
