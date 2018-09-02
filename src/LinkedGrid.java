
public class LinkedGrid {
	private int dimensions;
	private Node newNode = new Node();
	private Node root = new Node();
	private Node pointer;
	private  Node rowMarker;
	private Node upPointer;
	
	public  int getDimensions() {
		return dimensions;
	}
	//start of getters and setters-----------------------------------------------------------------------------------------------
	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}
	public  Node getNewNode() {
		return newNode;
	}
	public  void setNewNode(Node newNode) {
		this.newNode = newNode;
	}
	public  Node getRoot() {
		return root;
	}
	public  void setRoot(Node root) {
		this.root = root;
	}
	public  Node getPointer() {
		return pointer;
	}
	public  void setPointer(Node pointer) {
		this.pointer = pointer;
	}
	public  Node getRowMarker() {
		return rowMarker;
	}
	public  void setRowMarker(Node rowMarker) {
		this.rowMarker = rowMarker;
	}
	public Node getUpPointer() {
		return upPointer;
	}
	public void setUpPointer(Node upPointer) {
		this.upPointer = upPointer;
	}
	//end of getters and setters-------------------------------------------------------------------------------------------------
	
	//constructor to link the grid
	public LinkedGrid(int dimensions) {
		this.dimensions = dimensions;
		pointer = root;
		rowMarker = root;
		//make first row
		for(int x = 0; x < dimensions - 1; x ++){
			pointer.setRight(new Node());
			pointer.getRight().setLeft(pointer);
			pointer = pointer.getRight();
			
		}
		//make rest of rows
		for(int y = 0; y < dimensions - 1; y++){
			if(y < dimensions - 1){
				rowMarker.setDown(new Node());
				rowMarker.getDown().setUp(rowMarker);
				upPointer = rowMarker;
				rowMarker = rowMarker.getDown();
				pointer = rowMarker;
			}
			for(int x = 0; x < dimensions - 1; x ++){
				pointer.setRight(new Node());
				upPointer = upPointer.getRight();
				pointer.getRight().setLeft(pointer);
				pointer = pointer.getRight();
				pointer.setUp(upPointer);
				upPointer.setDown(pointer);
			}
		}
		//link Nodes sideways
		pointer = root;
		rowMarker = root;
		for(int y = 0; y < dimensions; y++){
			for(int x = 0; x < dimensions; x ++){
				try{
					pointer.setUpLeft(pointer.getUp().getLeft());
					pointer.getUp().getLeft().setDownRight(pointer);
				}
				//only exception would be null pointer, so catch exception e
				catch(Exception e){
					
				}
				try{
					pointer.setUpRight(pointer.getUp().getRight());
					pointer.getUp().getRight().setDownLeft(pointer);
				}
				//only exception would be null pointer, so catch exception e
				catch(Exception e){
					
				}
				try{
					pointer.setDownLeft(pointer.getDown().getLeft());
					pointer.getDown().getLeft().setUpRight(pointer);
				}
				//only exception would be null pointer, so catch exception e
				catch(Exception e){
					
				}
				try{
					pointer.setDownRight(pointer.getDown().getRight());
					pointer.getDown().getRight().setUpLeft(pointer);
				}
				//only exception would be null pointer, so catch exception e
				catch(Exception e){
					
				}
				pointer = pointer.getRight();
			}
			if(rowMarker.getDown() != null){
				rowMarker = rowMarker.getDown();
				pointer = rowMarker;
			}
			
		}
		//setting the center of each 3 by 3 big square to be special, so that identifying the big square a small square is in is easier(!!!ONLY WORKS FOR 9 BY 9 GRID!!!)
		findNode(2,2).setSpecial(true);
		findNode(2,5).setSpecial(true);
		findNode(2,8).setSpecial(true);
		findNode(5,2).setSpecial(true);
		findNode(5,5).setSpecial(true);
		findNode(5,8).setSpecial(true);
		findNode(8,2).setSpecial(true);
		findNode(8,5).setSpecial(true);
		findNode(8,8).setSpecial(true);
		
	}
	
	public boolean equal(LinkedGrid comparer) {
		boolean equal = true;
		setPointer(getRoot());
		setRowMarker(getRoot());
		comparer.setPointer(comparer.getRoot());
		comparer.setRowMarker(comparer.getRoot());
		
		for (int y = 0; y < getDimensions(); y++) {
			for (int x = 0; x < getDimensions(); x++) {
				//set variables to original value
				
				//whatever happens here happens to every Node in the grid
				if(getPointer().equal(comparer.getPointer()) == false) {
					equal = false;
				}
				//move pointer right
				if (pointer.getRight() != null) {
					pointer = pointer.getRight();
					comparer.setPointer(comparer.getPointer().getRight());
				}
					
				
			}
			//move down
			if (rowMarker.getDown() != null) {
				rowMarker = rowMarker.getDown();
				pointer = rowMarker;
				comparer.setRowMarker(comparer.getRowMarker().getDown());
				comparer.setPointer(comparer.getRowMarker());
			}
		}
		return equal;
	}
	
	//moves given Node to closes special Node(one each 3 by 3 square)
	public Node moveToSpecial(Node box){
		if(box.isSpecial() == true){
			pointer = box;
		}
		if(box.getUp() != null && box.getUp().isSpecial() == true ){
			pointer = box.getUp();
		}
		if(box.getDown() != null && box.getDown().isSpecial() == true ){
			pointer = box.getDown();
		}
		if(box.getLeft() != null && box.getLeft().isSpecial() == true ){
			pointer = box.getLeft();
		}
		if(box.getRight() != null && box.getRight().isSpecial() == true ){
			pointer = box.getRight();
		}
		if(box.getUpLeft() != null && box.getUpLeft().isSpecial() == true ){
			pointer = box.getUpLeft();
		}
		if(box.getUpRight() != null && box.getUpRight().isSpecial() == true ){
			pointer = box.getUpRight();
		}
		if(box.getDownLeft() != null && box.getDownLeft().isSpecial() == true ){
			pointer = box.getDownLeft();
		}
		if(box.getDownRight() != null && box.getDownRight().isSpecial() == true ){
			pointer = box.getDownRight();
		}
		return pointer;
	}
	//find Node by position
	public Node findNode(int num1, int num2){
		pointer = root;
		//find horizontal position
		for(int x = 0; x < num1 - 1; x++){
			pointer = pointer.getRight();
		}
		//find vertical position
		for(int y = 0; y < num2 - 1; y++){
			pointer = pointer.getDown();
		}
		return pointer;
	}
	
	public void display(){
		Node pointer = root;
		Node rowMarker = root;
		for(int y = 0; y < dimensions; y++){
			for(int x = 0; x < dimensions; x ++){
				System.out.print(pointer.getData() + "	");
				pointer = pointer.getRight();
			}
			if(rowMarker.getDown() != null){
				System.out.println();
				rowMarker = rowMarker.getDown();
				pointer = rowMarker;
			}
			
		}
		System.out.println();
		System.out.println();
	}
	
	public void clearBoard(){
		pointer =root;
		rowMarker = pointer;
		for(int q = 0; q < dimensions; q++){
			for(int w = 0; w < dimensions; w++){
				 pointer.setData(0);
				 if(pointer.getRight() != null){
					 pointer = pointer.getRight();
				 }
				 
			}
			if(rowMarker.getDown() != null){
				rowMarker = rowMarker.getDown();
				pointer = rowMarker;
			}
			
		}
	}
	
	//method to test if grid is linked properly
	public void testLink(){
		pointer =root;
		rowMarker = pointer;
		int counter = 0;
		for(int y = 0; y < dimensions; y++){
			for(int x = 0; x < dimensions; x ++){
				if(pointer.getUp() != null){
					counter++;
				}
				
				if(pointer.getRight() != null){
					counter++;
				}
				
				if(pointer.getDown() != null){
					counter++;
				}
				
				if(pointer.getLeft() != null){
					counter++;
				}
				if(pointer.getUpLeft() != null){
					counter++;
				}
				if(pointer.getUpRight() != null){
					counter++;
				}
				if(pointer.getDownLeft() != null){
					counter++;
				}
				if(pointer.getDownRight() != null){
					counter++;
				}
				pointer.setData(counter);
				counter = 0;
				
				if(pointer.getRight() != null)
					pointer = pointer.getRight();
				
				
				
			}
			
			if(rowMarker.getDown() != null){
				rowMarker = rowMarker.getDown();
				pointer = rowMarker;
			}
		}
		display();
		//System.out.println(counter);
		clearBoard();
	}
}
	

