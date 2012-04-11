package cn.hapyboy.simplelinke;



public class SimpleLinke<E>{

		
		
		private Linke<E> head;
		private Linke<E> end;
		
		private Linke<E> temp;
		private E result;
		int size;
		public int size(){
			return size;
		}
		public boolean add(E e){
			temp = new Linke<E>(e);
			add(temp);
			return true;
		}
		private final void add(Linke<E> linke){
			if(end != null){
				end.next = linke;
				end = linke;
				size++;
			}else{
				head = linke;
				end = linke;
			}
		}
		public E remove(){
			return remove(0);
		}
		public E remove(int index){
			if(index<0 || index >size)
				throw new IndexOutOfBoundsException();
			if(index == 0){
				result = head.element;
				head = head.next;
				size--;
				return result;
			}
			
			temp = head;
			for (int i = 0; i<(index-1); i++){
				temp = temp.next;
			}
			result = temp.next.element;
			temp.next = temp.next.next;
			size--;
			return  result;
		}
		

	}

	final class Linke <E>{

		final E element;
		Linke<E> next;

		Linke(E element) {
			this.element = element;
		}
		final E getElement(){
			return element;
		}
	
	
}
