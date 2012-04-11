package cn.hapyboy.ailist2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AiList<E> implements Collection<E>{
	private int size;
	private int limit;
	private int ridex;
	private int next;
	private final int initCapacity;

	private Group[] groups;
	
	AiList(){
		this(64);
	}
	AiList(int initCapacity){
		if(initCapacity<32)
			initCapacity = 32;
		ridex = initCapacity;
		groups = new Group[8];
		groups[0] = new Group(initCapacity);
		limit = 1;
		next = initCapacity*initCapacity;
		this.initCapacity = initCapacity;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size==0?true:false;
	}

	@Override
	public boolean contains(Object obj) {
		for(int i=0; i<limit; i++){
			if(groups[i].contains(obj) != -1){
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		Object[] arrays = new Object[size];
		int index = 0;
		int length;
		for(int i=0; i<limit; i++){
			length = groups[i].size();
			System.arraycopy(groups[i].toArray(), 0, arrays, index, length);
			index += length;
		}
		return arrays;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		final int length = a.length;
		if (length<size)
			return (T[]) toArray();
		System.arraycopy(toArray(), 0, a, 0, size);
		for(int i= size; i<a.length; i++){
			a[i] = null;
		}
		return a;
	}

	@Override
	public boolean add(E e) {
		int flag = limit;
		if(groups[limit-1].add(e)){
			chaged(1);
			return true;
		}
		
		if(flag != limit)
			return add(e);
		return false;
	}

	private void chaged(int i) {
		size += i;
		next -= i;
		if(next<0){
			ridex ++;
			next = ridex*ridex -size;
		}
	}
	@Override
	public boolean remove(Object obj) {
		int temp;
		for(int i =0; i<limit; i++){
			temp = groups[i].remove(obj);
			chaged(-1);
			isSmall(i,temp);
			if(temp != -1)
				return true;
		}
		return false;
	}

	private void isSmall(int i, int temp) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean containsAll(Collection<?> c) {
		
		for(Object obj:c){
			for(int i =0; i<limit; i++){
				if(groups[i].contains(obj) == -1){
					return false;
				}
			}
					
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if(c.size()>ridex){
			
			addGroup(split(c));
		}
		Object[] array =  c.toArray();
		if(groups[limit-1].addAll(array))
			return true;
		return false;
	}
	@SuppressWarnings("unused")
	private void addGroup(Group group) {
		addGroup(limit,group);
	}
	private void addGroup(int index, Group group) {
		if(limit == groups.length){
			expand();
		}
		System.arraycopy(groups, index, groups, index+1, limit-index);
		groups[index] = group;
		limit++;
		
	}
	private final void expand() {
		int capacity = groups.length;
		Arrays.copyOf(groups, capacity+capacity>>1);
		
	}
	private final void expand(int min) {
		int capacity = groups.length;
		
		capacity += (capacity>>1) > min? (capacity>>1):min;
		Arrays.copyOf(groups, capacity);
		
	}
	private final void addGroup(Group[] array) {
		addGroup(limit,array);
	}
	private final void addGroup(int index, Group[] array) {
		int length = array.length;
		if(groups.length < length){
			expand(length);
		}
		if(index == limit){
			System.arraycopy(array, 0, groups, limit, length);
			return;
		}
		System.arraycopy(groups, index, groups, index+length, limit-index);
		System.arraycopy(array, 0, groups, index, length);
		
	}
	
	private final Group[] split(Collection<? extends E> c) {
		return null;
		
	}
	@SuppressWarnings("unused")
	private final Group[] split(Object[] array) {
		int al = array.length;
		int length = al/ridex;
		length += al%ridex == 0 ? 0:1;
		Group[] gs = new Group[length];
		
		al =0;
		length = ridex;
		for(int i=0; i<length-1; i++){
			gs[i]=new Group(Arrays.copyOfRange(array, al, length));
			al = length;
			length += length;
		}
		gs[gs.length-1] = new Group(Arrays.copyOfRange(array, al, array.length));
		return gs;
		
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean flag=false;
		for(Object obj:c){
			if(remove(obj))
				flag = true;
		}
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		limit =1;
		size = 0;
		ridex = initCapacity;
		groups[0] = new Group(initCapacity);
		next = initCapacity*initCapacity;
	}
	
	@SuppressWarnings("hiding")
	class Iter<E> implements Iterator<E>{
		private Group group;
		private int index;
		private int gindex;//Group index
		private int capycity;
		private boolean delable; //删除标记，可删除时标记为true,直到再一次调用next才变成false
		
		Iter(){
			group =  groups[0];
			capycity = group.size();
		}
		@Override
		public boolean hasNext() {
			if(index<capycity)
				return true;
			if(index==capycity && gindex<limit){
				
				group = groups[++gindex];
				capycity = group.size();
				index = 0;
				return hasNext();
			}
			return false;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			if(hasNext()){
				if(delable){
					index++;
					delable = true;
				}
					
				return (E) group.get(index);
			}
			throw new NoSuchElementException("已经到最后，没有下一个了！");
			
		}

		@Override
		public void remove() {
			delable = true;
			group.remove(index);
			capycity--;
			
		}
		
	}

	

}
