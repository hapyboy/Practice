package cn.hapyboy.queue;

import java.util.NoSuchElementException;

public class client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IQueue<Integer> queue = Factory.creatQueue();
		System.out.println("���Դ������У�");
		System.out.println("����������"+queue.size());
		for (int i = 1; i<101;i++){
			
			
			queue.add(i);
			queue.add(i);
			
			System.out.print("����һ������"+i);
			System.out.println("\t ����������"+queue.size());
			System.out.println("ȡ������"+queue.remove());
		}
		System.out.println("��ʼȡ�ˣ����¶���������"+queue.size());
		for (int i = 0; i<105;i++){
			try {
				
				Integer j = queue.remove();
//				if(j != 0){
					System.out.print("ȡ��������һ������" + j);
					System.out.println("\t ����������"+queue.size());
//				}else{
//					System.out.println("�����ˣ������˳���");
//					break;
//				}
			} catch (NoSuchElementException e) { // $codepro.audit.disable logExceptions
				System.out.println("����Ϊ�գ�ֹͣȡ����");
				break;
				
			}
			
			
			
		}
		
		System.out.println("���������");

	}

}
