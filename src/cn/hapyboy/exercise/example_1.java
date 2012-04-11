package cn.hapyboy.exercise;
/*
 *  一个整数 n ，对它进行如下循环操作：
 *  
 *  若 n 是奇数，将 n 增加一或减少一；
 *  
 *  若 n 是偶数，将它减小一半。
 *  
 *  求最短的步骤把 n 变成 1。
 *
 */

public class example_1 {
	public static int fction(long num){
		if(num<=1){
			System.out.println("输入不符合要求，请给一个大于1的数！");
			return 0;
		}
		//count 用来表示numb变为1最短要用的步骤
		int count = 0;
		System.out.println("给定的数是："+num+"， 求解过程如下:");
		while(num != 1){
			//如果是偶数，除以2，步数加1
			if((num&1) == 0){
				System.out.println(num+"/"+"2 ="+num/2);
				num >>=1;
				count += 1;
				
			}else{
				//如果是奇数，判断前一位是1还是0，如果前面有1位及以上的1，
				//加1步骤更短,等于3时例外
				if(((num&2) == 2 )&&(num!=3)){
					System.out.println(num+"+1 = "+(num+1));
					System.out.println((num+1)+"/"+"2 ="+((num+1)/2));
					
					num++;
					//考虑到极端情况，在加一时，符号位有可能变为1，所以这里用>>>
					num >>>=1;
					count +=2;
					
				//如果前一位是0，减1除2
				}else{
					System.out.println(num+"-1 = "+ (num-1));
					System.out.println(num+"/"+"2 = "+num/2);
					num >>=1;
					count += 2;
				}
			}
		}
		System.out.println("一共是："+count+"步");
		return count;
	}
	public static void main(String[] args) {
		Integer i = 2000;
		Integer k = 2000;
		System.out.println(i==k);

	}
	
}
