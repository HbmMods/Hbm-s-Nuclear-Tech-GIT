package com.hbm.util;

public class Tuple {

	public static class Pair<X,Y> {

		X key;
		Y value;
		
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
			final int prime = 31;
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
			final int prime = 31;
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
}
