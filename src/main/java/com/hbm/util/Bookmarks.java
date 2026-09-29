package com.hbm.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Bookmarks<T> {

	private static final Object AWAWA = new Object();

	private int capacity;
	private LinkedHashMap<T, Object> map;

	public Bookmarks() {
		this.map = new LinkedHashMap<>();
	}

	public Bookmarks(int capacity) {
		this.capacity = capacity;
		this.map = new LinkedHashMap<T, Object>(capacity, 0.75f, false) {
			@Override
            protected boolean removeEldestEntry(Entry<T, Object> eldest) {
                return this.size() > Bookmarks.this.capacity;
            }
		};
	}

	public int size() {
        return this.map.size();
    }

    public Set<T> set() {
    	return this.map.keySet();
    }

    /**
    * @return <tt>true</tt> if the item was already bookmarked
    */
	public boolean add(T item) {
		return this.map.put(item, AWAWA) != null;
	}

	public void remove(T item) {
		this.map.remove(item);
	}

	public boolean contains(T item) {
		return this.map.containsKey(item);
	}
}
