package h2o.common.collections;

import h2o.common.collections.builder.ListBuilder;
import h2o.common.collections.builder.MapBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MarkedList<T> implements java.io.Serializable {


	private static final long serialVersionUID = 1538561787143088580L;
	
	private List<T> list = ListBuilder.newList();
	private List<T> tmpList = ListBuilder.newList();

	private Map<String, Integer> marks = MapBuilder.newMap();
	private Map<String, Integer> tmpMarks = MapBuilder.newMap();

	public boolean add(T e) {
		return tmpList.add(e);
	}

	public boolean addAll(Collection<? extends T> c) {
		return tmpList.addAll(c);
	}

	public boolean directAdd(T e) {
		return list.add(e);
	}

	public boolean directAddAll(Collection<? extends T> c) {
		return list.addAll(c);
	}

	public boolean contains(T e) {	
		if ( tmpList.contains(e) ) {
			return true;
		} else {
			return list.contains(e);
		}
	}

	public int size() {
		return list.size() + tmpList.size();
	}

	public void commit() {

		int size = list.size();
		for (Map.Entry<String, Integer> e : tmpMarks.entrySet()) {
			marks.put(e.getKey(), size + e.getValue());
		}

		list.addAll(tmpList);

		tmpList.clear();
		tmpMarks.clear();

	}

	public void rollback() {

		tmpList.clear();
		tmpMarks.clear();
	}

	public void clear() {

		list.clear();
		marks.clear();

		tmpList.clear();
		tmpMarks.clear();

	}

	public void addMark(String markName) {

		int tmpSize = tmpList.size();

		int size = list.size() + tmpSize;

		if (size > 0) {
			tmpMarks.put(markName, tmpSize - 1);
		} else {
			throw new RuntimeException("No data");
		}

	}

	public void removeMark(String markName) {
		
		if( tmpMarks.containsKey(markName) ) {
			tmpMarks.remove(markName);
		} else {
			marks.remove(markName);
		}
		
		
	}

	public Integer getMark(String markName) {
		Integer mark = tmpMarks.get(markName);
		if (mark == null) {
			return marks.get(markName);
		} else {
			return list.size() + mark;
		}

	}

	public Integer getTmpMark(String markName) {
		return tmpMarks.get(markName);
	}

	public List<T> getList() {

		List<T> newList = ListBuilder.newList();
		newList.addAll(list);
		if (!tmpList.isEmpty()) {
			newList.addAll(tmpList);
		}

		return newList;
	}

	public List<T> getTmpList() {

		List<T> newList = ListBuilder.newList();

		if (!tmpList.isEmpty()) {
			newList.addAll(tmpList);
		}

		return newList;
	}

	public void setTmpList(List<T> tmpList) {
		this.tmpList = tmpList;
	}

	public List<T> subList(int fromIndex, int toIndex) {
		return getList().subList(fromIndex, toIndex);
	}

	@Override
	public String toString() {
		return getList().toString();
	}

}
