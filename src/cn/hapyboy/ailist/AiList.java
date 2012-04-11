// $codepro.audit.disable
package cn.hapyboy.ailist;

import java.util.*;

public class AiList<E> implements Collection<E> {
	/**
	 * 轻风作品，本列表基于一个个小的数组实现，把一个个小的数组用链的方式链接起来。 乱序读取时快于LinkedList，稍慢于ArrayList
	 * 
	 * 乱序存时慢于LinkedList，快于ArrayList
	 * 
	 * 一个个小的数组可以自动调整自己的大小，太少时会合并。 太多时会从中间新建一个新的数组
	 */

	/**
	 * 警告：本列表不能添加空元素，不然会抛出异常！
	 */
	private int size;
	private final int base;
	private final int capacity;// Group Length
	private Group head;
	private Group end;

	public AiList() {
		this(128, 0.1f);
	}

	public AiList(int grouplength) {
		this(grouplength, 0.1f);
	}

	public AiList(int grouplength, float factor) {
		// Factor 为数组冗余数，可以为0，默认是10%
		if (grouplength < 64) {
			grouplength = 64;
		} 
		if(factor<0.1f)
			factor=0.1f;
		base = grouplength;
		capacity = (int) (base + base * factor);
		head = new Group();
		end = head;

	}

	public static void main(String[] args) {
		AiList<Integer> list = new AiList<Integer>(1);
		final int addlength = 500;
		// ArrayList<Integer> consult = new ArrayList<Integer>();
		for (int i = 0; i < addlength; i++) {
			list.add(i);
			// consult.add(i);
		}
		for (int i = 0; i < addlength; i++) {
			Integer element = list.remove(0);
			if (element.equals(i))
				System.out.println(i + "通过！");
			else
				System.out.println(i + "没有通过！" + "element:" + element);

		}

	}

	@Override
	public boolean add(E e) {
		checkNull(e);
		if (end.add(e)) {
			size++;
			return true;
		}
		return false;
	}

	public void add(int index, E element) {
		rangeCheck(index);
		checkNull(element);
		head.add(index, element);
		size++;

	}

	private final void checkNull(Collection<? extends E> c) {
		if (c.contains(null))
			throw new NullPointerException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c.size() < 0) {
			return false;
		}
		checkNull(c);
		Object[] objarray = c.toArray();
		if (end.addAllToEnd(objarray, 0)) {
			size += objarray.length;
			return true;
		}
		return false;
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		rangeCheck(index);
		if (c.size() < 0) {
			return false;
		}
		checkNull(c);
		Object[] objarray = c.toArray();
		SitePackage sp = head.site(index);
		if (sp.group.addAllToIndex(objarray, sp.index)) {
			size += objarray.length;
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		if (o == null)
			return false;
		if (head.remove(o)) {
			size--;
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public E remove(int index) {
		rangeCheck(index);
		size--;
		return (E) head.remove(index); // $codepro.audit.disable unnecessaryCast
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean flag = false;
		for (Object obj : c) {
			if (head.remove(obj)) {
				size--;
				flag = true;
			}
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public E set(int index, E element) {
		checkNull(element);
		return (E) head.set(index, element);// $codepro.audit.disable
											// unnecessaryCast
	}

	private final void checkNull(Object o) {
		if (o == null)
			throw new NullPointerException("此列表不能添加空元素!\t");

	}

	@SuppressWarnings("unchecked")
	public E get(int index) {
		rangeCheck(index);
		return (E) head.get(index); // $codepro.audit.disable unnecessaryCast

	}

	@Override
	public int size() {

		return size;
	}

	public int getBase() {
		return base;
	}

	public String getSkeleton() {
		StringBuilder str = new StringBuilder();
		str.append("小组详细情况：");
		return head.getSkeleton(str).toString();
	}

	public int indexOf(Object obj) {

		return head.find(obj);
	}

	@Override
	public boolean contains(Object obj) {

		if (head.find(obj) != -1)
			return true;
		return false;
	}

	@Override
	public boolean isEmpty() {

		return size == 0 ? true : false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object obj : c) {
			if (head.find(obj) == -1)
				return false;
		}
		return true;
	}

	public int lastIndexOf(Object obj) {
		int rear = end.rearIndexOf(obj);

		return size - rear - 1;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new Iter<E>();
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];

		return head.toArray(array, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < size) {
			return (T[]) toArray();
		}
		a = (T[]) head.toArray(a, 0);
		a[size] = null;
		return a;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		Object[] temp = new Object[c.size()];
		int index = 0;
		for (Object obj : c) {
			if (head.find(obj) != -1) {
				temp[index] = obj;
				index++;
			}
		}
		if (index != 0) {
			temp = Arrays.copyOf(temp, index);
			clear();
			head.addAllToEnd(temp);
		}
		return false;
	}

	@Override
	public void clear() {
		head = new Group();
		end = head;
		size = 0;

	}

	private void rangeCheck(final int index) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException();
	}

	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return new ListIter<E>();
	}

	class Group {
		private int index = 0;

		private Group next;
		private Group previous;

		private Object[] garray;

		public Group() {
			garray = new Object[capacity];
		}

		StringBuilder getSkeleton(StringBuilder s) {
			if (next == null) {
				addStrOfEnd(s, 0);
				return s;
			}
			addStr(s);
			return next.getSkeleton(s, 1);
		}

		final void addStr(StringBuilder s) {
			s.append('\t').append(index);

		}

		final void addStrOfEnd(StringBuilder s, int num) {
			String str = "小组总数：" + (num + 1) + '\r';
			s.insert(0, str);
			addStr(s);

		}

		StringBuilder getSkeleton(StringBuilder s, int num) {
			if (next == null) {
				addStrOfEnd(s, num);

				return s;
			}
			addStr(s);
			return next.getSkeleton(s, num + 1);
		}

		public void add(int num, Object element) {
			if (num < index) {
				if (index != capacity) {// 如果本组未满员
					System.arraycopy(garray, num, garray, num + 1, index - num);
					garray[num] = element;
					index++;
					return;
				}
				
				//如果本组满员就运行到下面
				chageNext();// 看下是否为队尾，如果是新建一组，然后移一个过去
				if (next.free()>0) {
					--index;
					next.addToHead(garray[index]);
					System.arraycopy(garray, num, garray, num + 1, index - num);
					garray[num] = element;
					return;
				}
				if (previous != null && previous.free()>0) {
					previous.add(garray[0]);
					
					System.arraycopy(garray, 1, garray, 0, num-1);
					garray[num] = element;
					return;
				}
				// 如果前后都满员，就新建一个数组
				Group temp = new Group();
				temp.setPrevious(this);
				temp.setNext(next);
				next.setPrevious(temp);
				setNext(temp);

				// 然后移一个到新数组
				next.addToHead(garray[--index]);
				System.arraycopy(garray, num, garray, num + 1, index - num);
				garray[num] = element;
				index++;
				return;

			} else {
				chageNext();
				next.add(num - index, element);
			}

		}

		public boolean add(Object obj) {
			if (index < base) {
				garray[index++] = obj;
				return true;
			}
			chageNext();
			return next.add(obj);

		}

		public boolean addAllToEnd(Object[] src) {
			return addAllToEnd(src, 0);
		}

		public boolean addAllToEnd(Object[] src, int srcpos) {
			int addlength = src.length - srcpos;
			if (addlength < allFree()) {
				System.arraycopy(src, srcpos, garray, index, addlength);
				moveIndex(addlength);
				return true;
			}

			int free = free();
			if (free > 0) {
				System.arraycopy(src, srcpos, garray, index, free);
				srcpos += free;
				index = base;

				chageNext();

				return next.addAllToEnd(src, srcpos);

			}
			chageNext();
			return next.addAllToHead(src, srcpos);
		}

		public boolean addAllToIndex(Object[] src, int destpos) {
			return addAllToIndex(src, 0, destpos);
		}

		private boolean addAllToIndex(Object[] src, int srcpos, int destpos) {
			int copylength = src.length - srcpos;
			int movelength = index - destpos;
			int free = free();
			if (allFree() > copylength) {

				System.arraycopy(garray, destpos, garray, destpos + copylength,
						movelength);
				System.arraycopy(src, srcpos, garray, destpos, copylength);
				moveIndex(copylength);
				return true;
			}

			if (copylength - free < next.allFree()) {
				Object[] copytonext = new Object[copylength - free];
				if (free > 0) {

					System.arraycopy(src, srcpos + free, copytonext, 0,
							copylength - free);
					System.arraycopy(garray, destpos, copytonext, copylength
							- free, movelength);
					System.arraycopy(src, srcpos, garray, destpos, free);

				} else {

					System.arraycopy(src, srcpos, copytonext, 0, copylength);
					System.arraycopy(garray, destpos, copytonext, copylength
							- free, movelength);

				}
				return next.addAllToHead(copytonext);

			}

			if (next.free() > movelength) {
				next.addToHead(Arrays.copyOfRange(garray, destpos, index));
				copylength -= movelength;
				fastAddAll(Arrays.copyOfRange(src, srcpos, src.length));
				return true;
			}
			// 如果不能看填充满N组后，余数能否加入下一组

			Object[] temp = Arrays.copyOfRange(garray, destpos, index);
			System.arraycopy(src, srcpos, garray, destpos, base - destpos);
			srcpos += movelength;
			index = base;
			fastAddAll(Arrays.copyOfRange(src, srcpos, src.length), temp);
			return true;

		}

		private void fastAddAll(Object[] src) {
			Group temp = new Group();
			temp.addAllToEnd(src, 0);
			temp.setPrevious(this);

			end.setNext(next);
			next.setPrevious(end);
			next = temp;
			end = end.getEnd();
		}

		private void fastAddAll(Object[] former, Object[] after) {
			Group temp = new Group();
			temp.addAllToEnd(former);

			temp.setPrevious(this);

			end.setNext(next);
			next.setPrevious(end);
			next = temp;
			end.addAllToEnd(after);
			end = end.getEnd();
		}

		private Group getEnd() {
			if (next != null)
				return next.getEnd();

			return this;
		}

		private boolean addAllToHead(Object[] src) {
			return addAllToHead(src, 0);

		}

		private boolean addAllToHead(Object[] src, int srcpos) {
			int movelength = src.length - srcpos;
			if (movelength < allFree()) {
				System.arraycopy(garray, 0, garray, movelength, index);
				System.arraycopy(src, srcpos, garray, 0, movelength);
				moveIndex(movelength);
				return true;
			}
			int free = free();
			int nextfree = next.free();
			if (movelength > free + nextfree && movelength < capacity) {
				Group temp = new Group();
				temp.addAllToEnd(src, srcpos);
				temp.setPrevious(previous);
				temp.setNext(this);
				previous.setNext(temp);
				setPrevious(temp);
				return true;
			}
			if (srcpos == 0) {
				fastAddAll(src);
				return true;
			}
			fastAddAll(Arrays.copyOfRange(src, srcpos, src.length));
			return true;
		}

		private void addToHead(Object o) {
			Object obj;
			if (index == capacity) {
				obj = garray[index - 1];
				if (next.isFill()) {
					Group newg = new Group();
					newg.setNext(next);
					newg.setPrevious(this);
					next.setPrevious(newg);

					// 把2个数组中的元素转移一些到新数组；
					next = newg;

				}
				next.addToHead(obj);
				System.arraycopy(garray, 0, garray, 1, index - 1);
				garray[0] = o;

				return;

			}

			System.arraycopy(garray, 0, garray, 1, index);
			garray[0] = o;
			index++;

		}

		private final void chageNext() {
			if (next == null) {
				next = new Group();
				next.setPrevious(this);
				end = next;
			}
		}

		public boolean remove(Object obj) {
			for (int i = 0; i < index; i++) {
				if (garray[i] == obj) {
					index--;
					System.arraycopy(garray, i + 1, garray, i, index - i);
					garray[index] = null;
					adjustArray();
					return true;
				}

			}
			if (next == null) {
				return false;
			}
			return next.remove(obj);
		}

		public Object remove(int num) {
			if (num < index) {
				Object obj;
				obj = garray[num];
				index--;
				System.arraycopy(garray, num + 1, garray, num, index - num);
				garray[index] = null;
				adjustArray();
				return obj;
			} else {
				return next.remove(num - index);
			}

		}

		private void deleteGroup() {
			if (previous == null && next == null) {
				return;
			}
			if (previous == null) {
				head = next;
				next.setPrevious(null);
				return;
			}
			if (next == null) {
				previous.setNext(null);
				end = previous;
				return;
			}
			previous.setNext(null);
			next.setPrevious(null);

		}

		public Object set(int num, Object element) {
			if (num < index) {
				Object obj = garray[num];
				garray[num] = element;
				return obj;
			}
			return next.set(num - index, element);
		}

		SitePackage site(int num) {
			// 方法把第N位元素定位到具体的group;

			if (num < index)
				return new SitePackage(this, num);

			return next.site(num - index);
		}

		private int siteList(int idx) {
			// 方法返回本组中的第N个元素在List中的定位
			if (previous != null) {
				return previous.siteList(idx + previous.size());
			}
			return idx;
		}

		private final void adjustArray() {
			if (index > base / 2) {// 如果容量小于一半才调整
				return;
			}
			if (index == 0) {
				deleteGroup();
				return;
			}
			if (next == null || previous == null) {// 暂时不对头和尾调整
				return;
			}

			int nfree = next.free();
			int pfree = previous.free();
			if (nfree < 0) {
				nfree = 0;
			}
			if (pfree < 0) {
				pfree = 0;
			}

			// 如果两边的空余容量不足以容纳就取消
			if (nfree + pfree < index) {
				return;
			}
			// 如果后面可以全部容纳就全加到后面
			if (pfree == 0 || nfree > index) {
				next.addAllToHead(Arrays.copyOfRange(garray, 0, index));
				previous.setNext(next);
				next.setPrevious(previous);
				return;
			}
			// 如果前面可以全部容纳就全加到前面
			if (nfree == 0 || pfree > index) {
				previous.addAllToEnd(Arrays.copyOfRange(garray, 0, index));
				previous.setNext(next);
				next.setPrevious(previous);
				return;
			}
			previous.addAllToEnd(Arrays.copyOfRange(garray, 0, pfree));
			next.addAllToHead(Arrays.copyOfRange(garray, pfree, index));
			previous.setNext(next);
			next.setPrevious(previous);
			return;
		}

		int free() {

			return base - index;
		}

		private int allFree() {

			return capacity - index;
		}

		private final void moveIndex(int i) {
			index += i;

		}

		public Object get(int num) {
			if (num < index)
				return garray[num];
			else
				return next.get(num - index);
		}

		public Object[] toArray(Object[] array, int num) {
			System.arraycopy(garray, 0, array, num, index);
			if (next != null) {
				return next.toArray(array, num + index);
			}
			return array;

		}

		public int find(Object obj) {

			for (int i = 0; i < index; i++) {
				if (garray[i].equals(obj))
					return i;
			}
			if (next != null) {
				int inner = next.find(obj);
				if (inner != -1) {
					return inner + index;
				}

			}
			return -1;

		}

		public int rearIndexOf(Object o) {
			for (int i = 0; i < index; i++) {

				if (garray[index - i - 1].equals(o))
					return i;
			}
			if (previous != null) {
				int rear = previous.rearIndexOf(o);
				if (rear != -1)
					return rear + index;
			}
			return -1;
		}

		public Group getNext() {
			return next;
		}

		// @SuppressWarnings("unused")
		public void setNext(Group next) {
			this.next = next;
		}

		void redd() {
			Arrays.copyOf(garray, capacity);
		}

		public void clear() {
			for (int i = 0; i < index; i++) {
				garray[i] = null;

			}
			index = 0;
			if (next != null)
				next.clear();
		}

		public int size() {
			return index;
		}

		boolean isFill() {
			return index == capacity ? true : false;
		}

		Group getPrevious() {
			return previous;
		}

		void setPrevious(Group previous) {
			this.previous = previous;
		}
	}

	@SuppressWarnings("hiding")
	private class Iter<E> implements Iterator<E> {
		Group group;
		int idx; // index
		boolean removable;

		Iter() {
			group = head;
			idx = 0;
		}

		protected Iter(int index) {

			SitePackage site = head.site(index);
			this.group = site.group;
			this.idx = site.index;
		}

		@Override
		public boolean hasNext() {
			if (idx < group.size())
				return true;
			if (group.getNext() != null) {
				group = group.getNext();
				idx = 0;
				return hasNext();
			}
			return false;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			if (hasNext()) {
				if (removable) {

					return (E) group.get(idx++);
				}
				removable = true;
				return (E) group.get(idx);

			} else {
				throw new NoSuchElementException("迭代器到最后了！");
			}

		}

		@Override
		public void remove() {
			if (removable) {
				group.remove(idx);
				size--;
				removable = false;
				return;
			}
			throw new IllegalStateException(
					"调用next后才能用remove方法，并且每次调用 next后只能调用一次remove方法!");

		}

	}

	@SuppressWarnings("hiding")
	private class ListIter<E> extends Iter<E> implements ListIterator<E> {

		ListIter() {
			super();
		}

		ListIter(int idx) {
			super(idx);
		}

		@Override
		public boolean hasPrevious() {
			if (idx < group.size() && idx >= 0)
				return true;
			if (group.getPrevious() != null) {
				group = group.getPrevious();
				idx = group.size() - 1;
				return hasPrevious();
			}
			return false;

		}

		@SuppressWarnings("unchecked")
		@Override
		public E previous() {
			if (hasPrevious()) {
				removable = true;
				idx--;
				return (E) group.get(idx);
			} else {
				throw new NoSuchElementException("前面没有元素了！");
			}

		}

		@Override
		public int nextIndex() {
			if (hasNext()) {
				return group.siteList(idx) + 1;
			}
			return size;
		}

		@Override
		public int previousIndex() {
			if (hasPrevious()) {
				return group.siteList(idx);
			}
			return -1;
		}

		@Override
		public void set(E e) {
			group.set(idx, e);

		}

		@Override
		public void add(E e) {
			group.add(e);
			idx++;

		}

	}

	private class SitePackage {
		Group group;
		int index;

		public SitePackage(Group group, int index) {
			this.group = group;
			this.index = index;
		}
	}

}
