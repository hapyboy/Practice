package cn.hapyboy.ailist2;

public interface IGroup {
	
	public int size();
	public int capacity();
	public boolean add(Object obj);
	public boolean add(int index,Object obj);
	
	public boolean addAll(Object[] oa);
	public boolean addAll(int index,Object[] oa);
	
	public Object remove(int index);
	public int remove(Object obj);
	public boolean clear();
	
	public Object set(int index,Object obj);
	public Object get(int index);
	
	public int free();
	public int contains(Object obj);//如果包含指定元素返回其所在索引，不包含返回-1
	
	public void expand(int length);
	public Group[] split(int length);
	void revise(int length);

}
