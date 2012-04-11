package cn.hapyboy.queue;

import cn.hapyboy.queue.arrayqueue.*;
public class Factory <E>{
	public static <E> IQueue<E> creatQueue(){
		return new ArrayQueue<E>();
	}
	public static <E> IQueue<E> creatQueue(int i){
		return new ArrayQueue<E>(i);
	}
}
