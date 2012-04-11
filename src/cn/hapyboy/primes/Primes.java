package cn.hapyboy.primes;

import java.util.ArrayList;

//求素数（质数）
public class Primes {
	ArrayList<Integer> a;

	public Primes() {
		a = new ArrayList<Integer>();
		a.add(2);
		a.add(3);
		a.add(5);
	}

	public static void main(String[] args) {
		Primes gxov = new Primes();
		ArrayList<Integer> list = new ArrayList<Integer>();
		long a = System.currentTimeMillis();
		list.addAll(gxov.get());
		long b = System.currentTimeMillis();
		b -= a;
		int c = 0;
		for (int i : list) {
			System.out.print(i + "\t");
			c++;
			if (c > 10) {
				System.out.print('\n');
				c = 0;
			}
		}
		System.out.println();
		System.out.println( "所用时间："+b+"毫秒");
		System.out.println( "找到："+list.size()+"个素数");


	}

	final boolean isPrime(int s) {
		for (int i : a) {
			if (s % i == 0)
				return false;
		}
		return true;

	}

	public ArrayList<Integer> get() {

		for (int i = 6; i < 10000; i++) {

			if (isPrime(i)) {
				// System.out.print(T.interrupted());
				// System.out.println("找到一个素数：" + i);
				a.add(i);
			}

		}
		return a;

	}

}
