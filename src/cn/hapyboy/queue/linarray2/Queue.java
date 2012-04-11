// $codepro.audit.disable
package cn.hapyboy.queue.linarray2;

import java.util.NoSuchElementException;
import cn.hapyboy.queue.*;

public class Queue<E> implements IQueue<E> {



	private int each;
	private int count = 0;
	private Group nonce;
	private Group end;
	private Depot depot;
	
	public Queue() {
		this.each = 16;
		init();
	}
	
	public Queue(int each) {
		if (each<=8){
			this.each=8;
		}else if (each/8 == 0){
			this.each = each;
		}else{
			this.each = (each/8+1)*8;
		}
		init();
		
	}
	private  final void init(){
		nonce = new Group(each);
		end = nonce;
		depot = new Depot();
	}
	

	@Override
	public boolean add(E obj) {
		if (obj ==null)
			throw new NullPointerException("不能向队列里放空元素！");
		
		//如果end.add返回是false,说明这组加满了，要新建一个组
		if(end.isEnd()){
			end.setNext(depot.getGroup());
			end = end.getNext();
			count++;
			end.add(obj);
			return true;
			
			
		}else{
			this.count++;
			end.add(obj);
			return true;		
		}
		
		
		
	}
	Group grouptemp;
	@SuppressWarnings("unchecked")
	@Override
	
	public E remove(){
		
		//如果这组取空了，nonce.size()返回是0
		if(nonce.size() != 0){
			count--;
			return (E)nonce.remove();
		}else{
			//如果队列为空，抛出异常
			if(nonce == end) {
				nonce.clear();
				throw new NoSuchElementException();
			}
			//如果队列不为空，nonce指向下一个组，并从下一组开始取
			grouptemp = nonce;
			nonce = nonce.getNext();
			depot.saveGroup(grouptemp);
			count--;
			return (E)nonce.remove();
			
		}
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return count;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public E get() {
		
		return (E)nonce.get();
	}
	
	class Depot {
		Group[] depot;
		int index;
		Group temp;
		
		Depot(){
			this(5);
			
		}
		
		public Depot(int count) {
			this.depot = new Group[count];
			index = 0;
		}
		
		Group getGroup(){
			if(index>0){
				index--;
				temp= depot[0];
				System.arraycopy(depot, 1, depot, 0, index);
				return temp;
			}
			return new Group(each);
		}
		void saveGroup(Group group){
			if(index < depot.length){
				group.clear();
				depot[index++] = group;
			}
		}
	}

}
