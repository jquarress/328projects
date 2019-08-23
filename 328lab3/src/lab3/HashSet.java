package lab3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Scanner;

// Implements the Set ADT using a hash table with open addressing.
public class HashSet<ValueType> {
	private class Entry {
		public ValueType mValue;
		public boolean mIsNil;
	}

	private Entry[] mTable;
	private int mCount;

	// Constructs a hashtable with the given size.
	public HashSet(int tableSize) {
		tableSize = (int) Math.pow(2, Math.ceil(Math.log(tableSize)/ Math.log(2)));
		//System.out.println(tableSize);
		// The next line is a workaround for Java not liking us making an array
		// of a generic type. (Entry is a generic type because it has generic
		// members.)
		mTable = (Entry[])Array.newInstance(Entry.class, tableSize); 
		mCount = 0;
		// mTable's entries are all null initially.
	}
	public int count() {
		return mCount;
	}
	public double loadFactor() {
		return (double)count()/(double)mTable.length;
	}
	// Inserts the given value into the set.
	public void add(ValueType value) {
		// Every object in Java has a .hashCode() function that computes a h(k)
		// value for that object. Use that function for your hash table index
		// calculations.
		if (!find(value)) {
			if (loadFactor() > 0.8) {
				Entry[] tempTable = (Entry[])Array.newInstance(Entry.class, 2*mTable.length); 
				for(int i = 0; i < mTable.length; i++) {
					Entry entry1 = mTable[i];
					if(mTable[i] != null && !mTable[i].mIsNil) { //don't need to re hash mIsNill
						int entryIndex1 = Math.abs(entry1.mValue.hashCode()) % tempTable.length;
						int j = 1;
						while(tempTable[entryIndex1] != null && mTable[i].mIsNil != true) {
							entryIndex1 = Math.abs(entry1.mValue.hashCode()) + (((int)Math.pow(j, 2) + j)/2);
							entryIndex1 = entryIndex1 % tempTable.length;
							j++;
						}
						tempTable[entryIndex1] = entry1;
					}
				} 
				mTable = tempTable;
			}
			Entry entry = new Entry();
			entry.mValue = value;
			entry.mIsNil = false;
			int entryIndex = Math.abs(value.hashCode()) % mTable.length;
			int i = 1;
			while(mTable[entryIndex] != null && mTable[entryIndex].mIsNil != true) {
				entryIndex = Math.abs(value.hashCode()) + (((int)Math.pow(i, 2) + i)/2);
				entryIndex = entryIndex % mTable.length;
				i++;
			}
			mTable[entryIndex] = entry;
			mCount++;
		}
	}

	// Returns true if the given value is present in the set.
	public boolean find(ValueType value) {
		int entryIndex = value.hashCode() % mTable.length;
		for (int i = 0; i < mTable.length; i++) {
			entryIndex = Math.abs(value.hashCode()) + (((int)Math.pow(i, 2) + i)/2);
			entryIndex = entryIndex % mTable.length;
			if(mTable[entryIndex] == null){
				return false;
			}else {
				if (!mTable[entryIndex].mIsNil) {
					if(mTable[entryIndex].mValue.equals(value)) {
						return true;
					}
				}
			}
		}
		// Replace the following line after you write the find method.
		return false;
	}

	// Removes the given value from the set.
	public void remove(ValueType value) {
		//similar to find but instead of return just make misnil and break
		//also reduce count by 1
		Entry entry1 = new Entry();
		entry1.mValue = value;
		int entryIndex = value.hashCode() % mTable.length;
		for (int i = 0; i < mTable.length; i++) {
			entryIndex = Math.abs(value.hashCode()) + (((int)Math.pow(i, 2) + i)/2);
			entryIndex = entryIndex % mTable.length;
			if (mTable[entryIndex] != null && mTable[entryIndex].mValue.equals(value) && mTable[entryIndex].mIsNil != true) {//and entry index is null is false
				mTable[entryIndex].mValue = null;
				mTable[entryIndex].mIsNil = true;
				break;
			}
		}
		mCount--;
		// Replace the following line after you write the find method.
	}
	public static void main(String[] args) throws IOException {
		HashSet<String> blah = new HashSet<>(50);

		BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\drizz\\OneDrive\\Documents\\Docs\\328\\trump_speech.txt"));
		String line = reader.readLine();
		String[] words = line.split(" ");
		for (int i = 0; i < words.length; i++) {
			String newWord = "";
			for(int j = 0; j < words[i].length(); j++) {
				char character = words[i].charAt(j);
				if (Character.isLetterOrDigit(character)) {
					newWord = newWord + character;
					newWord = newWord.replaceAll("[^\\p{ASCII}]", "");
				}
			}
			if(newWord.length() != 0) {
				blah.add(newWord);
				//System.out.println(newWord);
			}
		}
		System.out.println(blah.mCount);
	}
}