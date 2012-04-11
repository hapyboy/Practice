package cn.hapyboy.ailist2;

import java.util.Arrays;

public class Group implements IGroup {
	private final int capycity;
	private int limit;
	Object[] container;
	
	public Group(){
		this(32);
	}
	public Group(int initCapacity){
		capycity = initCapacity;
		container = new Object[capycity];
	}
	Group(Object[] array){
		int al = array.length;
		if(al>0){
			capycity = al+al>>1;
			container = Arrays.copyOf(array,capycity);
		}else{
			capycity = 32;
			container = new Object[capycity];
		}
	}
	@Override
	public int size() {
		return limit;
	}
	@Override
	public int capacity(){
		return capycity;
	}

	@Override
	public boolean add(Object obj) {
		if(limit == capycity){
			return false;	
		}
		container[limit++] = obj;
		return true;
	}
	
	@Override
	public boolean add(int index, Object obj) {
		if(limit<capycity){
			System.arraycopy(container, index, container, index+1, index+limit++);
			container[index] = obj;
			return true;
		}
		
		return false;
	}
	@Override
	public boolean addAll(Object[] oa) {
		return addAll(limit,oa);
	}
	@Override
	public boolean addAll(int index, Object[] oa) {
		int free = capycity-limit;
		int length = oa.length;
		if(length>free){
			return false;
		}
		if(index == limit){
			System.arraycopy(oa, 0, container, limit, length);
			limit += length;
			return true;
		}
		System.arraycopy(container, index, container, index+length, limit-index);
		System.arraycopy(oa, 0, container, index, length);
		limit += length;
		return true;
	}
	
	@Override
	public Object remove(int index) {
		Object obj = container[index--];
		System.arraycopy(container, index, container, index+1, limit-index);
		container[index] = null;
		return obj;
	}
	@Override
	public int remove(Object obj) {
		for(int i=0; i<limit; i++){
			if(container[i].equals(obj)){
				System.arraycopy(container, i+1, container, i, --limit -i);
				return limit;
			}
			
		}
		return -1;
	}
	@Override
	public boolean clear() {
		for(int i=0; i<limit; i++){
			container[i]=null;
			limit = 0;
		}
		return true;
	}
	@Override
	public Object set(int index,Object obj) {
		Object o = container[index];
		container[index]= obj;
		return o;
	}
	@Override
	public Object get(int index) {
		return container[index];
	}
	@Override
	public int contains(Object obj) {
		for(int i=0; i<limit; i++){
			if(container[i].equals(obj))
				return i;
		}
		return -1;
	}
	public Object[] toArray() {
		return container;
	}
	@Override
	public void expand(int length) {
		container = Arrays.copyOf(container, length);
	}
	
	@Override
	public Group[] split(int length) {
		
		int temp = limit/length;
		temp += limit%length == 0 ? 0:1;
		Group[] groups = new Group[temp];
		
		temp = 0;
		for(int i=0; i<groups.length-1; i++){
			groups[i] = new Group(Arrays.copyOfRange(container, temp, length));
			temp = length;
			length +=length;
		}
		groups[groups.length-1] = new Group(Arrays.copyOfRange(container, temp, capycity));
		return groups;
	}

	@Override
	public void revise(int length) {
		//如果所容元素过少，就减小容器大小
		container = Arrays.copyOf(container, length);	
	}
	@Override
	public int free() {
		// TODO Auto-generated method stub
		return capycity-limit;
	}





}
