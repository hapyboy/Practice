package cn.hapyboy.exercise;
/*
 *  һ������ n ��������������ѭ��������
 *  
 *  �� n ���������� n ����һ�����һ��
 *  
 *  �� n ��ż����������Сһ�롣
 *  
 *  ����̵Ĳ���� n ��� 1��
 *
 */

public class example_1 {
	public static int fction(long num){
		if(num<=1){
			System.out.println("���벻����Ҫ�����һ������1������");
			return 0;
		}
		//count ������ʾnumb��Ϊ1���Ҫ�õĲ���
		int count = 0;
		System.out.println("���������ǣ�"+num+"�� ����������:");
		while(num != 1){
			//�����ż��������2��������1
			if((num&1) == 0){
				System.out.println(num+"/"+"2 ="+num/2);
				num >>=1;
				count += 1;
				
			}else{
				//������������ж�ǰһλ��1����0�����ǰ����1λ�����ϵ�1��
				//��1�������,����3ʱ����
				if(((num&2) == 2 )&&(num!=3)){
					System.out.println(num+"+1 = "+(num+1));
					System.out.println((num+1)+"/"+"2 ="+((num+1)/2));
					
					num++;
					//���ǵ�����������ڼ�һʱ������λ�п��ܱ�Ϊ1������������>>>
					num >>>=1;
					count +=2;
					
				//���ǰһλ��0����1��2
				}else{
					System.out.println(num+"-1 = "+ (num-1));
					System.out.println(num+"/"+"2 = "+num/2);
					num >>=1;
					count += 2;
				}
			}
		}
		System.out.println("һ���ǣ�"+count+"��");
		return count;
	}
	public static void main(String[] args) {
		Integer i = 2000;
		Integer k = 2000;
		System.out.println(i==k);

	}
	
}
