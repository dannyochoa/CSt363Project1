package disk_store;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An ordered index.  Duplicate search key values are allowed,
 * but not duplicate index table entries.  In DB terminology, a
 * search key is not a superkey.
 *
 * A limitation of this class is that only single integer search
 * keys are supported.
 *
 */

public class OrdIndex implements DBIndex {

	/**
	 * Create an new ordered index.
	 */
	//key    value(num block)
	private ArrayList<Pair<Integer,Integer>> table;

	public OrdIndex() {
		this.table = new ArrayList<>();
	}

	@Override
	public List<Integer> lookup(int key) {
		List<Integer> ans = new ArrayList<>();
		ArrayList<Pair<Integer,Integer>> tableCopy = table;
		boolean found = false;
		int index = 0;
		do{
			found = false;
			index = Bsearch(tableCopy, 0, tableCopy.size()-1,key);
			if(index != -1) {
				ans.add(tableCopy.get(index).getValue());
				tableCopy.remove(tableCopy.get(index));
				found = true;
			}
		}while(found == true);
		Collections.sort(ans);
		return ans;
	}

	@Override
	public void insert(int key, int blockNum) {
		Pair<Integer,Integer> toInsert = new Pair<>(key,blockNum);
		if(table.contains(toInsert)){
			return;
		}
		if(table.isEmpty()){
			table.add(toInsert);
		}else{
			boolean inserted = false;
			int index = 0;
			for(int i = 0; i < table.size();i++){
				if(table.get(i).getKey() == toInsert.getKey()) {
					if(table.get(i).getValue() < toInsert.getValue()) {
						index = i+1;
					}
					if(toInsert.getValue() < table.get(i).getValue()) {
						//we insert
						table.add(index,toInsert);
						inserted = true;
						break;
					}
				}
				if(table.get(i).getKey() < toInsert.getKey()) {
					index = i+1;
				}
				if(toInsert.getKey() < table.get(i).getKey()) {
					//we insert stuff here
					table.add(index,toInsert);
					inserted = true;
					break;
				}
			}
			if(!inserted)
				table.add(index,toInsert);
		}
	}

	@Override
	public void delete(int key, int blockNum) {
		Pair<Integer,Integer> toDelete = new Pair<>(key,blockNum);
		if(table.contains(toDelete))
			table.remove(toDelete);
	}

	/**
	 * Return the number of entries in the index
	 * @return
	 */
	public int size() {
		return table.size();
	}

	@Override
	public String toString() {
		String ans = "";
		for(Pair<Integer,Integer> entry : table){
			ans += ("key: " + entry.getKey() + " value " + entry.getValue() + "\n");
		}
		return ans;
	}

	private int Bsearch(ArrayList<Pair<Integer, Integer>> table, int left,int right, int target){
		if (right >= left)
		{
			int mid = left + (right - left)/2;
			if (table.get(mid).getKey() == target)
				return mid;
			if (table.get(mid).getKey() > target)
				return Bsearch(table, left, mid-1, target);
			return Bsearch(table, mid+1, right, target);
		}
		return -1;
	}
}