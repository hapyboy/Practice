package cn.hapyboy.queue;

public interface IQueue<E>  {
	public boolean add(E obj);
	public E remove();
	public E get();
	public int size();

}
