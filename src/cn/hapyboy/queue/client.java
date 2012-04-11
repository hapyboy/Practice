package cn.hapyboy.queue;

import java.util.NoSuchElementException;

public class client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IQueue<Integer> queue = Factory.creatQueue();
		System.out.println("可以创建队列！");
		System.out.println("队列总数："+queue.size());
		for (int i = 1; i<101;i++){
			
			
			queue.add(i);
			queue.add(i);
			
			System.out.print("加入一个对象："+i);
			System.out.println("\t 队列总数："+queue.size());
			System.out.println("取出对象："+queue.remove());
		}
		System.out.println("开始取了，看下队列总数："+queue.size());
		for (int i = 0; i<105;i++){
			try {
				
				Integer j = queue.remove();
//				if(j != 0){
					System.out.print("取出队列中一个对象：" + j);
					System.out.println("\t 队列总数："+queue.size());
//				}else{
//					System.out.println("出错了，程序退出！");
//					break;
//				}
			} catch (NoSuchElementException e) { // $codepro.audit.disable logExceptions
				System.out.println("队列为空，停止取出！");
				break;
				
			}
			
			
			
		}
		
		System.out.println("程序结束！");

	}

}
