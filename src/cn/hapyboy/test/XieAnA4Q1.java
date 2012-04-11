package cn.hapyboy.test;

import java.util.*;

public class XieAnA4Q1 {

	public static void main(String[] args) {

		Scanner kbd = new Scanner(System.in);
		int length, limV, steps;
		double density, pDeV;
//		length = 80;
//		limV=5;
//		steps=10;
//		density=0.3;
//		pDeV=0.1;

		// get input
		System.out.print("Enter the road length:");
		length = kbd.nextInt();
		System.out.print("Enter the road density:");
		density = kbd.nextDouble();
		System.out.print("Enter the deceleration probability:");
		pDeV = kbd.nextDouble();
		System.out.print("Enter the speed limit:");
		limV = kbd.nextInt();
		System.out.print("Enter the number of simulation steps:");
		steps = kbd.nextInt();

		int[] road = new int[length];

		road = creatRoad(length, limV, density);

		printRoad(road);

		// 5
		// printRoad(advanceCars(road));

		for (int i = 0; i < steps; i++) {
			updateV(road, limV);
			deV(road);
			randomV(road, pDeV);
			road = advanceCars(road);
			printRoad(road);
		}

		System.out.println("===========Program By Shane===========");

	}

	/******************************* Creat Road *************************************/
	public static int[] creatRoad(int length, int limV, double density) {

		int[] road = new int[length];

		for (int i = 0; i < road.length; i++) {
			if (Math.random() <= density) {
				road[i] = (int) (Math.random() * (limV + 1));
			} else {
				road[i] = -1;
			}
		}

		return road;
	}

	/******************************** Update V ************************************/
	public static void updateV(int[] road, int limV) {

		for (int i = 0; i < road.length; i++) {
			if (road[i] != -1 && road[i] < limV) {
				road[i]++;
			}
		}
	}

	/******************************** decrease Velocity ************************************/
	public static void deV(int[] road) {
		int speed;
		int length = road.length;
		for (int i = 0; i < length; i++) {
			speed = road[i];
			if (speed == -1)
				continue;
			for (int j = 1; j <= speed; j++) {
				if (road[(i + j)%length]  != -1) {
					road[i] = j - 1;
					break;
				}

			}

		}

	}

	/******************************** Randomlize Velocity ************************************/
	public static void randomV(int[] road, double pDeV) {

		for (int i = 0; i < road.length; i++) {
			if (road[i] > 0 && Math.random() < pDeV) {
				road[i]--;
			}
		}
	}

	/***************************** Advance Cars ***************************************/
	public static int[] advanceCars(int[] road) {

		int length = road.length;
		int[] newRoad = new int[length];

		for (int i = 0; i < length; i++) {
			newRoad[i] = -1;
		}

		for (int i = 0; i < length; i++) {
			if (road[i] == -1)
				continue;

			newRoad[(i + road[i]) % length] = road[i];

		}

		return newRoad;
	

	}

	/******************************* Print Road *************************************/
	public static void printRoad(int[] road) {
		for (int i = 0; i < road.length; i++) {
			if (road[i] < 0) {
				System.out.print('-');
			} else {
				System.out.print(road[i]);
			}
		}
		System.out.println();
	}

}
