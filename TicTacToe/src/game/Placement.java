package game;

public class Placement {
	public int x;
	public int y;
	public int num;
	
	public Placement() {
		
	}
	
	public Placement(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Placement(int x, int y, int num) {
		this.x = x;
		this.y = y;
		this.num = num;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

//	public boolean equals(Placement other) {
//		return (this.getX() == other.getX() && this.getY() == other.getY());
//	}

	@Override
	public String toString() {
		return num + "[" + x + "," + y + "]";
	}
}
