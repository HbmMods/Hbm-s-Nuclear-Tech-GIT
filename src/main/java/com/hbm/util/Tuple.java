package com.hbm.util;

import com.hbm.interfaces.Spaghetti;

@Spaghetti("alreay??") //i made this like a week ago and it's already eye-bleeding, what the fuck happened?!
public class Tuple {
	
	/*
	 * We endure this horribleness in order to provide a way to create classes that hold values of definite types (no more nasty casting)
	 * that may also be used in hashmaps, should the need arrive. I'm kinda tired of making new classes just to hold values for one single list.
	 */

	public static class Pair<X,Y> {

		public X key;
		public Y value; //because fuck you
		
		public Pair(X x, Y y) {
			this.key = x;
			this.value = y;
		}
		
		public X getKey() {
			return this.key;
		}
		
		public Y getValue() {
			return this.value;
		}
		
		@Override
		public int hashCode() {
			final int prime = 27644437;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if(key == null) {
				if(other.key != null)
					return false;
			} else if(!key.equals(other.key))
				return false;
			if(value == null) {
				if(other.value != null)
					return false;
			} else if(!value.equals(other.value))
				return false;
			return true;
		}
	}

	public static class Triplet<X,Y,Z> {

		X x;
		Y y;
		Z z;
		
		public Triplet(X x, Y y, Z z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public X getX() {
			return this.x;
		}
		
		public Y getY() {
			return this.y;
		}
		
		public Z getZ() {
			return this.z;
		}
		
		@Override
		public int hashCode() {
			final int prime = 27644437;
			int result = 1;
			result = prime * result + ((x == null) ? 0 : x.hashCode());
			result = prime * result + ((y == null) ? 0 : y.hashCode());
			result = prime * result + ((z == null) ? 0 : z.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			Triplet other = (Triplet) obj;
			if(x == null) {
				if(other.x != null)
					return false;
			} else if(!x.equals(other.x))
				return false;
			if(y == null) {
				if(other.y != null)
					return false;
			} else if(!y.equals(other.y))
				return false;
			if(z == null) {
				if(other.z != null)
					return false;
			} else if(!z.equals(other.z))
				return false;
			return true;
		}
	}

	public static class Quartet<W,X,Y,Z> {

		@Override
		public int hashCode() {
			final int prime = 27644437;
			int result = 1;
			result = prime * result + ((w == null) ? 0 : w.hashCode());
			result = prime * result + ((x == null) ? 0 : x.hashCode());
			result = prime * result + ((y == null) ? 0 : y.hashCode());
			result = prime * result + ((z == null) ? 0 : z.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			Quartet other = (Quartet) obj;
			if(w == null) {
				if(other.w != null)
					return false;
			} else if(!w.equals(other.w))
				return false;
			if(x == null) {
				if(other.x != null)
					return false;
			} else if(!x.equals(other.x))
				return false;
			if(y == null) {
				if(other.y != null)
					return false;
			} else if(!y.equals(other.y))
				return false;
			if(z == null) {
				if(other.z != null)
					return false;
			} else if(!z.equals(other.z))
				return false;
			return true;
		}

		W w;
		X x;
		Y y;
		Z z;
		
		public Quartet(W w, X x, Y y, Z z) {
			this.w = w;
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public W getW() {
			return this.w;
		}
		
		public X getX() {
			return this.x;
		}
		
		public Y getY() {
			return this.y;
		}
		
		public Z getZ() {
			return this.z;
		}
	}

	public static class Quintet<V,W,X,Y,Z> {

		@Override
		public int hashCode() {
			final int prime = 27644437;
			int result = 1;
			result = prime * result + ((v == null) ? 0 : v.hashCode());
			result = prime * result + ((w == null) ? 0 : w.hashCode());
			result = prime * result + ((x == null) ? 0 : x.hashCode());
			result = prime * result + ((y == null) ? 0 : y.hashCode());
			result = prime * result + ((z == null) ? 0 : z.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			Quintet other = (Quintet) obj;
			if(v == null) {
				if(other.v != null)
					return false;
			} else if(!v.equals(other.w))
				return false;
			if(w == null) {
				if(other.w != null)
					return false;
			} else if(!w.equals(other.w))
				return false;
			if(x == null) {
				if(other.x != null)
					return false;
			} else if(!x.equals(other.x))
				return false;
			if(y == null) {
				if(other.y != null)
					return false;
			} else if(!y.equals(other.y))
				return false;
			if(z == null) {
				if(other.z != null)
					return false;
			} else if(!z.equals(other.z))
				return false;
			return true;
		}

		V v;
		W w;
		X x;
		Y y;
		Z z;
		
		public Quintet(V v, W w, X x, Y y, Z z) {
			this.v = v;
			this.w = w;
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public V getV() {
			return this.v;
		}
		
		public W getW() {
			return this.w;
		}
		
		public X getX() {
			return this.x;
		}
		
		public Y getY() {
			return this.y;
		}
		
		public Z getZ() {
			return this.z;
		}
	}
}
