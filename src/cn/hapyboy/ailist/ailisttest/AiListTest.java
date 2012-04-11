package cn.hapyboy.ailist.ailisttest;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import cn.hapyboy.ailist.AiList;
import cn.hapyboy.simplelinke.SimpleLinke;

public class AiListTest {
	AiList<Integer> list = new AiList<Integer>(0,0);
	final static int addlength = 500;
	
	
	@Before
	public void before(){
		for (int i = 0; i < addlength; i++) {
			list.add(i);
			
			assertTrue(list.size() == i+1);
			
		}
		
	}

	@Test(expected=NullPointerException.class)
	public void testAddNull() {
		Integer o = null;
		list.add(o);
	}
	
	@Test
	public void testAddToIndex() {
		Random random = new Random();
		Integer element;
		int index;
		for (int i=0; i<addlength; i++){
			index = random.nextInt(addlength);
			element = index*2;
			list.add(index,element);
			assertThat("已经通过次数："+i, list.remove(index), equalTo(element));
			
		}
	}

	public void testAddToIndex2() {
		Random random = new Random();
		
		final int index = random.nextInt(addlength);
		final Integer element = index;
		
		int base = list.getBase();
		for (int i=0; i<base*3; i++){
			list.add(index,element);
		}
		for (int i=0; i<base*3; i++){
			assertThat(list.remove(index), equalTo(element));
		}
		
	}
	@Test
	public void testAddToIndex3() {
		Random random = new Random();
		Integer element;
		int site = random.nextInt(addlength);
		for (int i=0; i<addlength; i++){
			element = i*2;
			list.add(site+i,element);	
		}
		for(int i=0; i<site; i++){
			String s = "前面的出了问题，site:"+site+"\t i:"+i;
			assertThat(s,list.remove(0), is(i));
		}
		for(int i=0; i<addlength; i++){
			assertThat("中"+site,list.remove(0), is(i*2));
		}
		for(int i = site; i<addlength; i++){
			assertThat("后"+site,list.remove(0), is(i));
		}
	}

	@Test
	public void testAddAll() {
		ArrayList<Integer> consult = new ArrayList<Integer>();
		for (int i = 0; i < addlength; i++) {
			consult.add(i);
		}
		list.addAll(consult);
		assertThat(list.size(), is(addlength*2));
		for(int i=0; i<addlength;i++){
			assertThat(list.get(i), equalTo(list.get(i+addlength)));
		}
	}
	@Test
	public void testAddAllToIndex() {
		ArrayList<Integer> consult = new ArrayList<Integer>();
		for (int i = 0; i < addlength; i++) {
			consult.add(i);
		}
		int inner = 111;
		list.addAll(inner,consult);
		assertThat(list.size(), is(addlength*2));
		for(int i=0; i<inner;i++){
			assertThat(list.get(i), equalTo(list.get(i+inner)));
		}
	}
	@Test
	public void testRemove() {
		
		assertThat(list.size(),is(addlength));
		for (int i = 0; i < addlength; i++) {
			Integer element = list.remove(0);
			assertThat(element, is(i));
		}
		assertThat(list.size(), is(0));
		
	}

	@Test
	public void testRandomRemove(){
		SimpleLinke<Integer> linke = new SimpleLinke<Integer>();
		for (int i=0; i<addlength; i++){
			linke.add(i);
		}
		int length = addlength;
		Random random = new Random();
		int index;
		for(int i=0; i<addlength; i++){
			index = random.nextInt(length);
			assertThat(list.remove(index), equalTo(linke.remove(index)));
			length--;
		}
	}

	public void testRandomRemoveElement(){
		SimpleLinke<Integer> linke = new SimpleLinke<Integer>();
		for (int i=0; i<addlength; i++){
			linke.add(i);
		}
		int length = addlength;
		Random random = new Random();
		for(int i=0; i<addlength; i++){
			int index = random.nextInt(length);
			Integer element = linke.remove(index);
			assertTrue(list.remove(element));
			length--;
		}
	}

	@Test
	public void testGet(){
		Random random = new Random();
		int index;
		for(int i=0; i<addlength; i++){
			index = random.nextInt(addlength);
			//System.out.println(list.get(index));
			assertThat(list.get(index), equalTo(index));
		}
	}
	@Test
	public void testSet() {
		Random random = new Random();
		Integer element;
		int index;
		for (int i=0; i<addlength; i++){
			index = random.nextInt(addlength);
			element = index;
			list.set(index,element);
			assertThat(list.get(index), equalTo(element));
			
		}
	}
	@Test
	public void testToArray() {
		
		Object[] intarray = list.toArray();
		for(int i =0; i<intarray.length; i++){
			assertTrue(((int)intarray[i])==i);
		}
		
		assertTrue(intarray.length == 500);
		
		assertTrue(list.size() == 500);
		
		System.out.println("toArray测试完成！");
	}
	@Test
	public void testindexOf() {
		Random random = new Random();
		Integer element;
		int index;
		for (int i=0; i<addlength; i++){
			index = random.nextInt(addlength);
			element = index;
			assertThat(""+i,list.indexOf(element), equalTo(index));
			
		}
		
	}
	@Test
	public void testLastIndexOf() {
		
		Integer element = addlength*2;
		
		for (int i=0; i<addlength-1; i++){
			
			list.set(i+1, element);
			assertThat("没有通过的地方："+i,list.lastIndexOf(element), equalTo(i+1));
			
		}
		
	}
	@Test
	public void testIter(){
		Iterator<Integer> iter = list.iterator();
		int i = 0;
		while(iter.hasNext()){
			assertThat(iter.next(), is(i++));
			iter.remove();
			assertThat(list.size(), is(addlength-i));
			
		}
		assertThat(list.size(), is(0));
	}
	@Test
	public void testContains(){
		Integer element;
		for(int i =0; i<addlength; i++){
			element = i;
			assertTrue(list.contains(element));
		}
	}
	@Test
	public void testContainsAll(){
		List<Integer> lst = new ArrayList<Integer>();
		for(int i=0; i<addlength; i++){
			lst.add(i);
		}
		assertTrue(list.containsAll(lst));
		lst.add(addlength*2);
		assertFalse(list.containsAll(lst));
	}
	
	//@Test
	public void testretainAll(){
		int size = 100;
		List<Integer> lst = new ArrayList<Integer>();
		for(int i=0; i<size; i++){
			lst.add(i);
		}
		list.retainAll(lst);
		assertThat(list.size(), is(size));
	}

}
