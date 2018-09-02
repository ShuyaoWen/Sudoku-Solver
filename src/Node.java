

public class Node {
	//array that stores possibilities of square
	private final int SIZE = 10;
	private boolean num[] = new boolean[SIZE];
	private boolean special = false;
	private int data = 0;
	private int poss = 0;
	
	//pointers to the boxes around
	Node up, down, left, right,upLeft,upRight,downLeft,DownRight;
	//set all possibilities to true
	public Node() {
		for(int x = 0; x < 10; x++){
			num[x] = true;
		}
		num[0] = false;
		special = false;
		poss = 9;
	}
	
	public boolean equal(Node comparer) {
		boolean isEqual = true;
		if(getData() != comparer.getData()) {
			isEqual = false;
		}
		
		for(int x = 1; x < 10; x++) {
			if(getNum(x) != comparer.getNum(x)) {
				isEqual = false;
			}
		}
		return isEqual;
	}
	
	//Getters and Setters------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public int getPoss() {
		return poss;
	}
	public void setPoss(int poss) {
		this.poss = poss;
	}
	public boolean isSpecial() {
		return special;
	}
	public void setSpecial(boolean special) {
		this.special = special;
	}
	public Node getUpLeft() {
		return upLeft;
	}
	public void setUpLeft(Node upLeft) {
		this.upLeft = upLeft;
	}
	public Node getUpRight() {
		return upRight;
	}
	public void setUpRight(Node upRight) {
		this.upRight = upRight;
	}
	public Node getDownLeft() {
		return downLeft;
	}
	public void setDownLeft(Node downLeft) {
		this.downLeft = downLeft;
	}
	public Node getDownRight() {
		return DownRight;
	}
	public void setDownRight(Node downRight) {
		DownRight = downRight;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public boolean getNum(int num) {
		return this.num[num];
	}
	public void setNum(int num, boolean tod) {
		boolean temp = this.num[num];
		int newPoss = -1;
		this.num[num] = tod;
		if(tod == false && temp != tod) {
			newPoss = getPoss() - 1;
			setPoss(newPoss);
		}
		if(tod == true && temp != tod) {
			newPoss = getPoss() + 1;
			setPoss(newPoss);
		}
	}
	public Node getUp() {
		return up;
	}
	public void setUp(Node up) {
		this.up = up;
	}
	public Node getDown() {
		return down;
	}
	public void setDown(Node down) {
		this.down = down;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public int getSIZE() {
		return SIZE;
	}
	
}
