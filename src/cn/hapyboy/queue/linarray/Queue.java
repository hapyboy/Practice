// $codepro.audit.disable
package cn.hapyboy.queue.linarray;

import java.util.NoSuchElementException;
import cn.hapyboy.queue.*;

public class Queue<E> implements IQueue<E> {



	private int each;
	private int count = 0;
	private Group<E> nonce;
	private Group<E> end;
	
	
	public Queue() {
		this.each = 16;
	}
	
	public Queue(int each) {
		if (each<=8){
			this.each=8;
		}else if (each/8 == 0){
			this.each = each;
		}else{
			this.each = (each/8+1)*8;
		}
		
	}
	

	@Override
	public boolean add(E obj) {
		try {
			end.add(obj);	
		} catch (NullPointerException e) { // $codepro.audit.disable
			nonce = new Group<E>(each);
			end = nonce;
			end.add(obj);
			
		}catch (ArrayIndexOutOfBoundsException e) {
			Group<E> temp = new Group<E>(each);
			end.setNext(temp);
			end = temp;	
			end.add(obj);
		}
		this.count++;
		return true;
		
	}

	@Override
	public E remove(){
		try {
			this.count--;
			return nonce.remove();
		} catch (NullPointerException e) {
			count++;
			throw new  NoSuchElementException();
		}catch (ArrayIndexOutOfBoundsException e) {
			nonce = nonce.getNext();
			if (nonce == null){
				end = null;
				count++;
				throw new  NoSuchElementException();
			}else{
				count++;
				return this.remove();
			}
			
		}catch (NoSuchElementException e) {
			count = 0;
			throw e;
		}
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public E get() {
		// TODO Auto-generated method stub
		return nonce.get();
	}

}
