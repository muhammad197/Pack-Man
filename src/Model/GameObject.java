package Model;

import java.util.Objects;

public abstract class GameObject {

		protected int X, Y;
		
		public GameObject(int x, int y){
			
			this.X = x;
			this.Y = y;
		}

		public int getX() {
			return X;
		}

		public int getY() {
			return Y;
		}

		public void setX(int x) {
			X = x;
		}

		public void setY(int y) {
			Y = y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(X, Y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GameObject other = (GameObject) obj;
			return X == other.X && Y == other.Y;
		}
		
		
		
		
		
	
}
