package cn.hapyboy.queue.linarray;

import java.util.NoSuchElementException;

import cn.hapyboy.queue.IQueue;

public class Group <E> implements IQueue<E> {
	private int index= 0;
	private int end = 0;
	private Group<E> next = null;
	
	private Object[] group;
	
	
	
	public Group(int c){
		group = new Object[c];
			}
	@Override
	public boolean add (E obj) {
		
		try {
			group[end++] = obj;
			
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			end++;
			throw e;
		}
	}
	

	public Group<E> getNext() {
		return next;
	}
	public void setNext(Group<E> next) {
		this.next = next;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public E remove() throws NoSuchElementException{
		if (index < end){
			try {
				E element = (E)group[index]; // $codepro.audit.disable
				index++;
				return element ;
			} catch (ArrayIndexOutOfBoundsException e) {
				throw e;
			}
			
		}else{
			throw new NoSuchElementException();
		}
		
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return (end - index);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E get() {
		
		return (E)group[index]; // $codepro.audit.disable
	}

}
