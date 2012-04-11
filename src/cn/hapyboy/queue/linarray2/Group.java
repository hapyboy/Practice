package cn.hapyboy.queue.linarray2;

class Group  {
	private int index= 0;
	private int end = 0;
	private Group next;
	
	private Object[] group;
	
	
	public Group(int c) {
		group = new Object[c];
	}
	
	public boolean  add(Object obj) {
			if(end < group.length){
				group[end++]=obj;
				
				return true;
			}else
				return false;
				
		
	}
	

	public Group getNext() {
		return next;
	}
	//@SuppressWarnings("unused")
	public void setNext(Group next) {
		this.next = next;
	}
	Object obj;
	public Object remove() {
		if (end > index){				
				obj = group[index];
				group[index++] = null;
				return obj;
		}
		else 
			return null;
	
	}

	
	public int size() {
		//index == end有两种情况，一种是小组为空，一种是小组为满
		if (index != end)
			return end - index;
		else {
			//group[group.length-1]!= null说明小组加满了。
			if(group[end-1]!= null) 
				return group.length;
			else
				return 0;
		}
		
		
	}
	boolean isEnd(){
		return end == group.length? true:false;
	}
	void redd(){
		System.arraycopy(group, index, group, 0, this.size());
	}
	
	public Object get() {
		if(this.size()!=0)
		 return group[index];
		else
			return null;
	}
	public void clear(){
		index =0;
		end = 0;
		next = null;
	}

}
	

