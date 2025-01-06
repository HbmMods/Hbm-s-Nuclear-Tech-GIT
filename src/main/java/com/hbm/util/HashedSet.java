package com.hbm.util;

import org.apache.commons.lang3.NotImplementedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A crude implementation of the HashSet with a few key differences:
 * - instead of being stored as the key, the objects are stored as values in the underlying HashMap with the hash being the key
 *   - consequently, things with matching hash are considered the same, skipping the equals check
 * - no equals check means that collisions are possible, so be careful
 * - the underlying HashMap is accessible, which means that the instances can be grabbed out of the HashedSet if a hash is supplied
 *
 * This sack of crap was only intended for the drone request network code
 *
 * @author hbm
 *
 * @param <T>
 */
public class HashedSet<T> implements Set<T> {

	HashMap<Integer, T> map = new HashMap();

	public static class HashedIterator<T> implements Iterator {

		private Iterator<Entry<Integer, T>> iterator;

		public HashedIterator(HashedSet<T> set) {
			this.iterator = set.map.entrySet().iterator();
		}

		@Override
		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		@Override
		public Object next() {
			return this.iterator.next().getValue();
		}

		@Override
		public void remove() {
			this.iterator.remove();
		}
	}

	public HashedSet() { }

	public HashedSet(Set reachableNodes) {
		this.addAll(reachableNodes);
	}

	public HashMap<Integer, T> getMap() {
		return this.map;
	}

	@Override
	public boolean add(Object e) {
		boolean contains = this.contains(e);
		this.map.put(e.hashCode(), (T) e);
		return contains;
	}

	@Override
	public boolean addAll(Collection c) {
		boolean ret = false;
		for(Object o : c) if(add(o)) ret = true;
		return ret;
	}

	@Override
	public void clear() {
		this.map.clear();
	}

	@Override
	public boolean contains(Object o) {
		return this.map.containsKey(o.hashCode());
	}

	@Override
	public boolean containsAll(Collection c) {

		for(Object o : c) {
			if(!this.contains(o)) return false;
		}

		return true;
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public Iterator iterator() {
		return new HashedIterator(this);
	}

	@Override
	public boolean remove(Object o) {
		T obj = this.map.get(o.hashCode());
		boolean rem = false;

		if(obj != null) {
			rem = true;
			this.map.remove(o.hashCode());
		}

		return rem;
	}

	@Override
	public boolean removeAll(Collection c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		throw new NotImplementedException("Fuck you");
	}

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public Object[] toArray() {
		throw new NotImplementedException("Fuck you");
	}

	@Override
	public Object[] toArray(Object[] a) {
		throw new NotImplementedException("Fuck you");
	}
}
