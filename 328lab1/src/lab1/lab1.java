package lab1;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class lab1 {
	private static int menuChoice;
	private static int seedChoice;
	private static int sizeChoice;
	
	private static int seed;
	private static int size;
	// private static ArrayList<Integer> numsArray = new ArrayList<>();
	//random number array
	public static ArrayList<Integer> randomArray(int s, int n) {
		ArrayList<Integer> numsArray = new ArrayList<>();
		numsArray.clear();
		Random nums = new Random(s);
		for(int i=0; i<n; i++) {
			int max = 100;
			int min = -100;
			int r = nums.nextInt(max - min) + min;
			numsArray.add(r);
		}
		//System.out.println(numsArray);
		return numsArray;
	}
	//freddys alg
	public static int maxArraySum1(ArrayList<Integer> list) {
		int max = 0;
		for(int i = 0; i <= list.size()-1; i++) {
			for(int j = i; j <= list.size()-1; j++) {
				int thisSum = 0;
				for(int k = i; k <= j; k++) {
					thisSum = thisSum + list.get(k);
				}
				if (thisSum > max) {
					max = thisSum;
				}
			}
		}
		//System.out.println(max);
		return max;
	}
	public static int maxArraySum2(ArrayList<Integer> list) {
		int max = 0;
		for(int i = 0; i <= list.size()-1; i++) {
			int thisSum = 0;
			for(int j = i; j <= list.size()-1; j++) {
				thisSum = thisSum + list.get(j);
				if (thisSum > max) {
					max = thisSum;
				}
			}
		}
		//System.out.println(max);
		return max;
	}
	public static int maxArraySum3(ArrayList<Integer> list, int left, int right) {
		if (list.size() == 0) {
			return 0;
		}
		if (left == right) {
			if (list.get(left) > 0) {
				return list.get(left);
			}
			return 0;
		}
		int center = ((left + right)/2);
		int maxLeftSum = maxArraySum3(list, left, center);
		int maxRightSum = maxArraySum3(list, center+1, right);
		
		int maxLeftBorder = 0;
		int leftBorder = 0;
		for(int i = center; i == left; i--) {
			leftBorder = leftBorder + list.get(i);
			if(leftBorder>maxLeftBorder) {
				maxLeftBorder=leftBorder;
			}
		}
		int maxRightBorder = 0;
		int rightBorder = 0;
		for(int i = center; i == right; i++) {
			rightBorder = rightBorder + list.get(i);
			if(rightBorder>maxRightBorder) {
				maxRightBorder=rightBorder;
			}
		}
		lab1.max3(maxLeftSum, maxRightSum, maxLeftBorder + maxRightBorder);
		return max3(maxLeftSum, maxRightSum, maxLeftBorder + maxRightBorder);
	}
	public static int maxArraySum4(ArrayList<Integer> list) {
		int max = 0;
		int thisSum = 0;
		for(int i = 0; i < list.size(); i++) {
			thisSum = thisSum + list.get(i);
			if(thisSum > max){
				max = thisSum;
			}else if(thisSum < 0) {
				thisSum = 0;
			}
		}
		return max;
	}
	public static int max3(int maxLeftSum, int maxRightSum, int maxBorders) {
		int currentMax = 0;
		if (maxLeftSum > currentMax)
			currentMax=maxLeftSum;
		if (maxBorders > currentMax)
			currentMax=maxBorders;
		if (maxRightSum > currentMax)
			currentMax=maxRightSum;
		return currentMax;
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Scanner in1 = new Scanner(System.in);
		Scanner in2 = new Scanner(System.in);
		do {
			System.out.println("\n1)quit program\n2)time freddys algorithm\n3)time susies algorithm\n4)time johnnys algorithm\n5)time sallys algorithm\n");
			menuChoice = in.nextInt();
			switch (menuChoice) {
			case 2:
				System.out.println("set the seed value:");
				seedChoice = in1.nextInt();
				System.out.println("\nset input size:");
				sizeChoice = in2.nextInt();
				System.out.println();
				seed = seedChoice;
				size = sizeChoice;
				ArrayList<Integer> numsArray = randomArray(seed, size);
				// freddys alg
				long start = System.currentTimeMillis();
				lab1.maxArraySum1(numsArray);
				long finish = System.currentTimeMillis();
				long time = finish - start;
				System.out.println(time);
				break;
			case 3:
				System.out.println("set the seed value:");
				seedChoice = in1.nextInt();
				System.out.println("\nset input size:");
				sizeChoice = in2.nextInt();
				System.out.println();
				seed = seedChoice;
				size = sizeChoice;
				ArrayList<Integer> numsArray3 = randomArray(seed, size);
				// susies alg
				long start2 = System.currentTimeMillis();
				lab1.maxArraySum2(numsArray3);
				long finish2 = System.currentTimeMillis();
				long time2 = finish2 - start2;
				System.out.println(time2);
				break;
			case 4:
				System.out.println("set the seed value:");
				seedChoice = in1.nextInt();
				System.out.println("\nset input size:");
				sizeChoice = in2.nextInt();
				System.out.println();
				seed = seedChoice;
				size = sizeChoice;
				ArrayList<Integer> numsArray4 = randomArray(seed, size);
				// johnnys alg
				long start3 = System.currentTimeMillis();
				lab1.maxArraySum3(numsArray4, 0, numsArray4.size()-1);
				long finish3 = System.currentTimeMillis();
				//System.out.println(lab1.maxArraySum3(numsArray4, 0, numsArray4.size()-1));
				long time3 = finish3 - start3;
				System.out.println(time3);
				break;
			case 5:
				System.out.println("set the seed value:");
				seedChoice = in1.nextInt();
				System.out.println("\nset input size:");
				sizeChoice = in2.nextInt();
				System.out.println();
				seed = seedChoice;
				size = sizeChoice;
				ArrayList<Integer> numsArray5 = randomArray(seed, size);
				//Sallys alg
				long start4 = System.currentTimeMillis();
				lab1.maxArraySum4(numsArray5);
				long finish4 = System.currentTimeMillis();
				long time4 = finish4 - start4;
				System.out.println(time4);
				//System.out.println(lab1.maxArraySum4(numsArray5));
				break;
			}
		}while (menuChoice != 1);
	}
}

