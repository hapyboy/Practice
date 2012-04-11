package cn.hapyboy.queue.arrayqueue;

import java.util.NoSuchElementException;

import cn.hapyboy.queue.IQueue;

public class ArrayQueue<E> implements IQueue<E>{


	
	private Object[] queue;
	
	private int head = 0;
	private int end = 0;
	private int count = 0;
	private int reddcount = 0;
	
	public ArrayQueue(){
		queue = new Object[16];
	}
	
	public ArrayQueue(int length){
		if (length<16){
			length = 16;
		}
		queue = new Object[length];
	}
	
	public boolean add(E obj){
		if (count<queue.length){
			queue[end++] = obj;
			
			count++;
			if(end == queue.length){
				end=0;
			}
		}else{
			extend();
			queue[end++] = obj;
			count++;
			
		}
		return true;
		
	}
	Object obj;
	@SuppressWarnings("unchecked")
	public E remove(){
		if(count==0){
			throw new NoSuchElementException("队列为空，不可取！");
		}else{
			reddcount++;
			obj = queue[head];
			queue[head++] = null;
			count--;
			if(head == queue.length){
				head=0;
			}
			if(reddcount>1000)
				redd();
			return (E)obj; // $codepro.audit.disable
			
		}
		
	}
	

	private void redd() {
		if(queue.length / count>1){
			//do something
			extend();
		}
		
	}

	private void extend() {
		Object[]  newqueue = new Object[count+(count>>1)];
		if(head == 0){
			System.arraycopy(queue, 0, newqueue, 0, queue.length);
			end = queue.length;
			queue = newqueue;
			newqueue=null;
		}else{
			System.arraycopy(queue, head, newqueue, 0, queue.length-head);
			head = 0;
			
			System.arraycopy(queue, 0, newqueue, (queue.length-end),end );
			end = queue.length;
			queue = newqueue;
			newqueue=null;
			
		}
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get() {
		
		return (E)queue[head]; // $codepro.audit.disable
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return count;
	}
	

}


