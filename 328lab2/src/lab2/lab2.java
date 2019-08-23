package lab2;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class lab2 {
	
	
	public static boolean isOrdered(ArrayList<Integer> a, int size) {
		if(size > 1) {
			for ( int i = 0; i < size; i++) {
				if(a.get(i)> a.get(i+1)) {
					return false;
				}
			}
		}
		return true;		
	}
	
	public static void insertionSort(ArrayList<Integer> a, int start, int end) {
		for(int i = start; i <= end; i++) {
			int j;
			int temp = a.get(i);
			for(j = i-1; j>= start; j--) {
				if(a.get(j) > temp) {
					a.set(j + 1, a.get(j));
				}else {
					break;
				}
			}
			a.set(j + 1, temp);
		}
	}
	public static int median(ArrayList<Integer> a, int start, int end) {
		int left = a.get(start);
		int right = a.get(end);
		int middle = a.get((end+start)/2);
		int middleIndex = (end+start)/2;
		
		if (left<=middle) {
			if(middle<=right) {
				return middleIndex;
			}
			if(left<=right) {
				return end;
			}
			return start;
		}
		if (left <= right) {
			return start;
		}
		if (middle <= right) {
			return end;
		}
		return middleIndex;
	}
	public static int partition(ArrayList<Integer> a, int left, int right, int pivotIndex) {
		int pivotValue = a.get(pivotIndex);
		//swap
		a.set(pivotIndex, a.get(right));
		a.set(right, pivotValue);
		int store = left;
		for(int i = left; i < right; i++) {
			if( a.get(i) <= pivotValue) {
				int temp = a.get(store);
				a.set(store, a.get(i));
				a.set(i, temp);
				store++;
			}
		}
		a.set(right, a.get(store));
		a.set(store, pivotValue);
		return store;
	}
	public static void quickSort(ArrayList<Integer> a, int start, int end) {
		if (end-start <= 9) {
			lab2.insertionSort(a, start, end);
			return;
		}
		int p = partition(a, start, end, lab2.median(a, start, end));
		quickSort(a, start, p-1);
		quickSort(a, p+1, end);
	}
	
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);	
		String filename;
		do {
		System.out.print("input file name: ");
		filename = "C:\\Users\\drizz\\OneDrive\\Documents\\Docs\\328\\" + input.nextLine();
//		FileOutputStream out = new FileOutputStream(filename);
//		DataOutputStream file = new DataOutputStream(out); 
		//file.close();
		
		FileInputStream fileIn = new FileInputStream(filename);
		DataInputStream dataIn = new DataInputStream(fileIn);
		int intList = dataIn.readInt();
		ArrayList<Integer> nums = new ArrayList<Integer>();
		ArrayList<Integer> numsDuplicate1 = new ArrayList<Integer>();
		ArrayList<Integer> numsDuplicate2 = new ArrayList<Integer>();
		for (int i = 0; i < intList; i++) {
			nums.add(dataIn.readInt());
		}
		for (int i = 0; i < nums.size(); i++) {
			numsDuplicate1.add(nums.get(i));
			numsDuplicate2.add(nums.get(i));
		}
		
		dataIn.close();

		long start = System.currentTimeMillis();
		lab2.insertionSort(numsDuplicate1, 0, numsDuplicate1.size()-1);
		long finish = System.currentTimeMillis();
		long time = finish - start;
		System.out.println(time);
		
		lab2.isOrdered(numsDuplicate1, numsDuplicate1.size()-1);
		
		if (lab2.isOrdered(numsDuplicate1, numsDuplicate1.size()-1)){
			System.out.println("its sorted(insertion)");
		}
		
		long start2 = System.currentTimeMillis();
		lab2.quickSort(numsDuplicate2, 0, numsDuplicate2.size()-1);
		long finish2 = System.currentTimeMillis();
		long time2 = finish2 - start2;
		System.out.println(time2);
		
		lab2.isOrdered(numsDuplicate2, numsDuplicate2.size()-1);
		
		if (lab2.isOrdered(numsDuplicate2, numsDuplicate2.size()-1)){
			System.out.println("its sorted(quick)\n");
		}
		}while(filename != "C:\\Users\\drizz\\OneDrive\\Documents\\Docs\\328\\exit");

	}
}
