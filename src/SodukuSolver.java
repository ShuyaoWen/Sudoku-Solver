import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SodukuSolver {
	public static LinkedGrid lg = new LinkedGrid(9);
	// preparation for reading from a file
	static File infile = new File("sodukuTest.txt");

	// writing the number in the box of the soduku puzzle
	public static void writeNum(int num, Node box) {
		Node pointer = null, rowMarker = null;
		box.setData(num);
		if (num != 0) {
			for (int x = 0; x < 10; x++) {
				box.setNum(x, false);
			}
		}

		pointer = box;
		// move to the top most square in the row
		while (pointer.getUp() != null) {
			pointer = pointer.getUp();
		}
		// set the top node in the column to false
		pointer.setNum(num, false);
		// set the rest to false
		while (pointer.getDown() != null) {
			pointer = pointer.getDown();
			pointer.setNum(num, false);
		}

		pointer = box;
		// move to the most left node in the row
		while (pointer.getLeft() != null) {
			pointer = pointer.getLeft();
		}
		// set the most left node to false
		pointer.setNum(num, false);
		// set the rest to false
		while (pointer.getRight() != null) {
			pointer = pointer.getRight();
			pointer.setNum(num, false);
		}
		// moves pointer to the top right box of the 3x3 square
		pointer = lg.moveToSpecial(box).getUpLeft();
		rowMarker = pointer;
		for (int y = 0; y < 3; y++) {
			// set first box to false
			pointer.setNum(num, false);
			// set next two to false
			for (int x = 0; x < 2; x++) {
				pointer = pointer.getRight();
				pointer.setNum(num, false);
			}
			// move to next line
			if (y < 2) {
				pointer = rowMarker.getDown();
				rowMarker = pointer;
			}
		}

	}

	public static void presentPuzzle() throws IOException {
		Scanner reader = new Scanner(infile);
		// nested for loops runs through all boxes

		for (int x = 1; x < lg.getDimensions() + 1; x++) {
			for (int y = 1; y < lg.getDimensions() + 1; y++) {
				writeNum(reader.nextInt(), lg.findNode(y, x));
			}

		}

	}

	// method used for debugging, will display possibilities for that Node
	public static void showPoss(int num1, int num2) {
		System.out.print(num1 + "," + num2 + "	");
		for (int x = 0; x < 10; x++) {
			if (lg.findNode(num1, num2).getNum(x)) {
				System.out.print(x + "	");
			} else {
				System.out.print("x	");
			}
		}
		System.out.println();
		System.out.println();
	}
	// method used for debugging,will display possibilities for that Node
	public static void showPoss(Node box) {
		for (int x = 0; x < 10; x++) {
			if (box.getNum(x)) {
				System.out.print(x + "	");
			} else {
				System.out.print("x	");
			}
		}
		System.out.println();
		System.out.println();
	}

	public static void ifOnePoss() {
		Node pointer = null, rowMarker = null;
		int counter = 0, temp = 0;

		pointer = lg.getRoot();
		rowMarker = lg.getRoot();
		// nested for loops runs through every Node
		for (int y = 0; y < lg.getDimensions(); y++) {
			for (int x = 0; x < lg.getDimensions(); x++) {
				temp = 0;
				counter = 0;
				for (int q = 0; q < 10; q++) {
					if (pointer.getNum(q) == true) {
						counter++;
						temp = q;
					}
				}
				if (counter == 1) {
					writeNum(temp, pointer);
				}
				if (pointer.getRight() != null)
					pointer = pointer.getRight();
			}
			if (rowMarker.getDown() != null) {
				rowMarker = rowMarker.getDown();
				pointer = rowMarker;
			}
		}
		/*
		 * for(int x = 1; x < lg.getDimensions() + 1; x++){ for(int y = 1; y <
		 * lg.getDimensions() + 1; y ++){ temp = 0; counter = 0; for(int q = 0; q < 10;
		 * q++) { if(lg.findNode(y, x).getNum(q) == true) { counter++; temp = q; } }
		 * if(counter == 1) { writeNum(temp,lg.findNode(y, x) ); }
		 * 
		 * 
		 * }
		 * 
		 * }
		 */
	}

	public static boolean checkIfSolved() {
		boolean solved = true;
		for (int x = 1; x < lg.getDimensions() + 1; x++) {
			for (int y = 1; y < lg.getDimensions() + 1; y++) {
				if (lg.findNode(y, x).getData() == 0)
					solved = false;
			}
		}
		return solved;
	}

	// check if there is only one square possible for a number in a whole
	// column(IfOnlyPoss)
	public static void ifOnlyPossInColumn() {
		Node pointer = lg.getRoot(), columnMarker = pointer, temp = null;
		int counter = 0;
		do {
			// goes through numbers 1 to 10
			for (int x = 1; x < 10; x++) {
				temp = null;
				pointer = columnMarker;
				counter = 0;
				// goes through row
				do {
					if (pointer.getNum(x) == true) {
						counter++;
						// temp Node stores the location of the one possibility, it will be used if there is only one possibility of that number
						temp = pointer;
					}
					pointer = pointer.getDown();
				} while (pointer != null);
				if (counter == 1 && temp.getData() == 0) {
					writeNum(x, temp);
				}

			}
			// shifts to next row
			pointer = columnMarker.getRight();
			columnMarker = pointer;
		} while (pointer != null);

	}

	// check if there is only one square possible for a number in a whole
	// row(IfOnlyPoss)
	public static void ifOnlyPossInRow() {
		Node pointer = lg.getRoot(), rowMarker = pointer, temp = null;
		int counter = 0;
		do {
			// goes through numbers 1 to 10
			for (int x = 1; x < 10; x++) {
				temp = null;
				pointer = rowMarker;
				counter = 0;
				// goes through row
				do {
					if (pointer.getNum(x) == true) {
						counter++;
						// temp Node stores the location of the one possibility, it will be used if there is only one possibility of that number
						temp = pointer;
					}
					pointer = pointer.getRight();
				} while (pointer != null);
				if (counter == 1 && temp.getData() == 0) {
					writeNum(x, temp);
				}

			}
			// shifts to next row
			pointer = rowMarker.getDown();
			rowMarker = pointer;
		} while (pointer != null);

	}

	// check if there is only one square possible for a number in a 3 by 3
	// box(IfOnlyPoss)
	public static void ifOnlyPossInBox() {
		// temp Node stores the location of the one possibility, it will be used if
		// there is only one possibility of that number
		Node pointer = null, rowMarker = null, temp = null;
		// counter counts the amount of boxes with the possibility
		int counter = 0;
		// double for loops go through every 3x3 box
		for (int x = 1; x < 8; x = x + 3) {
			for (int y = 1; y < 8; y = y + 3) {
				// goes through every number possibility
				for (int a = 1; a < 10; a++) {
					// reset every variable
					counter = 0;
					temp = null;
					pointer = lg.findNode(y, x);
					rowMarker = pointer;
					// goes through every box in a 3x3 square
					for (int q = 0; q < 3; q++) {
						for (int w = 0; w < 3; w++) {
							if (pointer.getNum(a) == true) {
								counter++;
								temp = pointer;
							}
							// move right
							if (w < 2 && pointer.getRight() != null) {
								pointer = pointer.getRight();
							}
						}
						// move to next line
						if (q < 2 && rowMarker.getDown() != null) {
							rowMarker = rowMarker.getDown();
							pointer = rowMarker;
						}

					}
					// set a number if it only appears once
					if (counter == 1 && temp.getData() == 0) {
						writeNum(a, temp);
					}
				}

			}
		}

	}
	
	public static void ifOnlyPoss() {
		ifOnlyPossInColumn();
		ifOnlyPossInRow();
		ifOnlyPossInBox();
	}
	
	public static void ifOnlyRowPoss() {
		Node pointer = lg.getRoot(), rowMarker = null;
		final int SIZE = 3;
		//size is number of rows/columns
		boolean[] group= new boolean[SIZE];
		//double for loop goes through all nine 3x3 squares
		for(int x = 1; x < 8; x = x + 3) {
			for(int y = 1; y < 8; y = y + 3) {
				//does for numbers 1 through 9
				for(int q = 1; q < 10; q++) {
					//set all three groups to false
					for(int w = 0; w < SIZE; w++) {
						group[w] = false;
					}
					//checks if it is true for all three groups
					for(int a = 0; a < SIZE; a++) {
						//move to first group
						if(a == 0) {
							pointer = lg.moveToSpecial(lg.findNode(y, x)).getUpLeft();
						}
						//move to second group
						else if(a == 1) {
							pointer = lg.moveToSpecial(lg.findNode(y, x)).getLeft();
						}
						//move to third group
						else if(a == 2) {
							pointer = lg.moveToSpecial(lg.findNode(y, x)).getDownLeft();
						}
						//goes through the group
						for(int s = 0; s < 3; s++) {
							if(pointer.getNum(q) == true) {
								group[a] = true;
							}
							pointer = pointer.getRight();
						}
					}
					//three if statements direct the pointer to the correct location
					if(group[0] == true && group[1] == false && group[2] == false) {
						//set everything to left false(same for next two if statements)
						pointer = lg.moveToSpecial(lg.findNode(y, x)).getUpLeft();
						while(pointer.getLeft() != null) {
							pointer = pointer.getLeft();
							pointer.setNum(q, false);
						}
						//set everything to right false(same for next two if statements)
						pointer = lg.moveToSpecial(lg.findNode(y, x)).getUpRight();
						while(pointer.getRight() != null) {
							pointer = pointer.getRight();
							pointer.setNum(q, false);
						}
					}
					if(group[0] == false && group[1] == true && group[2] == false) {
						pointer = lg.moveToSpecial(lg.findNode(y, x)).getLeft();
						while(pointer.getLeft() != null) {
							pointer = pointer.getLeft();
							pointer.setNum(q, false);
						}
						pointer = lg.moveToSpecial(lg.findNode(y, x)).getRight();
						while(pointer.getRight() != null) {
							pointer = pointer.getRight();
							pointer.setNum(q, false);
						}
					}
					if(group[0] == false && group[1] == false && group[2] == true) {
						pointer = lg.moveToSpecial(lg.findNode(y, x)).getDownLeft();
						while(pointer.getLeft() != null) {
							pointer = pointer.getLeft();
							pointer.setNum(q, false);
						}
						pointer = lg.moveToSpecial(lg.findNode(y, x)).getDownRight();
						while(pointer.getRight() != null) {
							pointer = pointer.getRight();
							pointer.setNum(q, false);
						}
					}
					
				}
				
				
				
				
				
			}
		}
	}
	
	public static void ifOnlyColumePoss() {
		Node pointer = lg.getRoot(), rowMarker = null;
		final int SIZE = 3;
		//size is number of rows/columns
		boolean[] group= new boolean[SIZE];
		//double for loop goes through all nine 3x3 squares
		for(int x = 1; x < 8; x = x + 3) {
			for(int y = 1; y < 8; y = y + 3) {
				//does for numbers 1 through 9
				for(int q = 1; q < 10; q++) {
					//set all three groups to false
					for(int w = 0; w < SIZE; w++) {
						group[w] = false;
					}
					//checks if it is true for all three groups
					for(int a = 0; a < SIZE; a++) {
						//move to first group
						if(a == 0) {
							pointer = lg.moveToSpecial(lg.findNode(y, x)).getUpLeft();
						}
						//move to second group
						else if(a == 1) {
							pointer = lg.moveToSpecial(lg.findNode(y, x)).getUp();
						}
						//move to third group
						else if(a == 2) {
							pointer = lg.moveToSpecial(lg.findNode(y, x)).getUpRight();
						}
						//goes through the group
						for(int s = 0; s < 3; s++) {
							if(pointer.getNum(q) == true) {
								group[a] = true;
							}
							pointer = pointer.getDown();
						}
					}
					//three if statements direct the pointer to the correct location
					if(group[0] == true && group[1] == false && group[2] == false) {
						//set everything to left false(same for next two if statements)
						pointer = lg.moveToSpecial(lg.findNode(y, x)).getUpLeft();
						while(pointer.getUp() != null) {
							pointer = pointer.getUp();
							pointer.setNum(q, false);
						}
						//set everything to right false(same for next two if statements)
						pointer = lg.moveToSpecial(lg.findNode(y, x)).getDownLeft();
						while(pointer.getDown() != null) {
							pointer = pointer.getDown();
							pointer.setNum(q, false);
						}
					}
					if(group[0] == false && group[1] == true && group[2] == false) {
						pointer = lg.moveToSpecial(lg.findNode(y, x)).getUp();
						while(pointer.getUp() != null) {
							pointer = pointer.getUp();
							pointer.setNum(q, false);
						}
						pointer = lg.moveToSpecial(lg.findNode(y, x)).getDown();
						while(pointer.getDown() != null) {
							pointer = pointer.getDown();
							pointer.setNum(q, false);
						}
					}
					if(group[0] == false && group[1] == false && group[2] == true) {
						pointer = lg.moveToSpecial(lg.findNode(y, x)).getUpRight();
						while(pointer.getUp() != null) {
							pointer = pointer.getUp();
							pointer.setNum(q, false);
						}
						pointer = lg.moveToSpecial(lg.findNode(y, x)).getDownRight();
						while(pointer.getDown() != null) {
							pointer = pointer.getDown();
							pointer.setNum(q, false);
						}
					}
					
				}	
			}
		}
	}
	
	public static LinkedGrid copyGrid(LinkedGrid lg) {
		
		LinkedGrid og = new LinkedGrid(9);
		Node pointer = lg.getRoot(), rowMarker = pointer, ogpointer = og.getRoot(), ogrowMarker = ogpointer;
		for (int y = 0; y < lg.getDimensions(); y++) {
			for (int x = 0; x < lg.getDimensions(); x++) {
				//set variables to original value
				
				//whatever happens here happens to every Node in the grid
				//copies number in Node
				ogpointer.setData(pointer.getData());
				//copies possibilities in Node
				for(int q = 1; q < 10; q++) {
					ogpointer.setNum(q, pointer.getNum(q));
				}
				if (pointer.getRight() != null) {
					pointer = pointer.getRight();
					ogpointer = ogpointer.getRight();
				}
					
				
			}
			if (rowMarker.getDown() != null) {
				rowMarker = rowMarker.getDown();
				pointer = rowMarker;
				ogrowMarker = ogrowMarker.getDown();
				ogpointer = ogrowMarker;
			}
		}
		
		return og;
	}
	
	public static void guess() {
		//makes a copy of lg so that when guess is wrong the original grid is intact
		LinkedGrid og = copyGrid(lg);
		LinkedGrid temp = null;
		Node pointer = lg.getRoot(), rowMarker = pointer;
		//goes through numbers 1 to 10
		for(int x = 1; x < 10; x++) {
			pointer = rowMarker = lg.getRoot();
			//finds first Node that is empty
			while(pointer.getData() != 0) {
				pointer = pointer.getRight();
				if(pointer == null) {
					pointer = rowMarker.getDown();
					rowMarker = pointer;
				}
			}
			if(pointer.getNum(x) == true) {
				//writes the guess in the grid
				writeNum(x, pointer);
				//attempts to solve
				do {
					temp = copyGrid(lg);
					ifOnePoss();
					ifOnlyPoss();
					ifOnlyColumePoss();
					ifOnlyRowPoss();
					ifTwoBoxTwoPoss();
					ifOnePoss();
					ifOnlyPoss();
				}while (checkIfSolved() != true && temp.equal(lg) == false && contradiction(lg) == false);
				//if needs to guess again
				if(temp.equal(lg) && contradiction(lg) == false) {
					//guess again
					guess();
					//if guess is wrong, return grid back to previous state
					if(checkIfSolved() != true) {
						lg = copyGrid(og);
					}
				}
				//if solved grid, stop loop
				if(checkIfSolved() == true) {
					break;
				}
				//if there is a square with no possibilities, return grid to previous state
				if(contradiction(lg) == true) {
					lg = copyGrid(og);
				}
			}
		}
	}
	
	//check if a Node has no possibilites
	public static boolean allFalse(Node test) {
		boolean allFalse = true;
		for(int q = 1; q < 10; q++) {
			if(test.getNum(q)) {
				allFalse = false;
			}
		}
		
		return allFalse;
	}
	
	//checks if grid has a square with no possibilities
	public static boolean contradiction(LinkedGrid test) {
		boolean contradiction = false;
		Node pointer = test.getRoot();
		Node rowMarker = pointer;
		//double for loops goes through all squares on the grid
		for(int y = 0; y < test.getDimensions(); y++){
			for(int x = 0; x < test.getDimensions(); x ++){
				//if there are no possibilities for that sqare and there was no number filled in
				if(allFalse(pointer) == true && pointer.getData() == 0) {
					contradiction = true;
				}
				if(pointer.getRight() != null) {
					pointer = pointer.getRight();
				}
				
			}
			if(rowMarker.getDown() != null){
				rowMarker = rowMarker.getDown();
				pointer = rowMarker;
			}
			
		}
		return contradiction;
	}
	
	//if two squares in a 3x3 area have have and only have two of the same possibilities, eliminate those possibilities for all other squares in the area
	public static void ifTwoBoxTwoPoss() {
		Node pointer = lg.getRoot();
		Node rowMarker = pointer;
		Node temp = null, temp2 = null;
		int num1 = 0, num2 = 0;
		boolean foundOne = false, foundTwo = false;
		//double for loops goes through all 9 3x3 squares
		for(int q = 1; q < 8; q = q + 3) {
			for(int w = 1; w < 8; w = w + 3) {
				//set all variables to original state before starting the next loop
				pointer = lg.findNode(w, q);
				rowMarker = pointer;
				temp = null;
				num1 = num2 = 0;
				foundOne = foundTwo = false;
				
				//double for loop goes through every square in 3x3 area
				for(int y = 0; y < 3; y++){
					for(int x = 0; x < 3; x++){
						//if found first square with two possibilities
						if(pointer.getPoss() == 2 && foundOne == false) {
							foundOne = true;
							temp = pointer;
							for(int a = 1; a < 10; a++) {
								if(pointer.getNum(a) == true) {
									if(num1 == 0) {
										num1 = a;
									}
									else if(num2 == 0) {
										num2 = a;
									}
								}
							}
						}
						//if found second square with two possibilities
						else if(pointer.getPoss() == 2 && foundOne == true && foundTwo == false) {
							foundTwo = true;
							for(int a = q; a < 10; a++) {
								if(pointer.getNum(a) == true && a != num1) {
									if(a != num2) {
										foundTwo = false;
									}
								}
							}
							if(foundTwo == true) {
								temp2 = pointer;
							}
						}
						
						if(pointer.getRight() != null && x < 2) {
							pointer = pointer.getRight();
						}
						
					}
					if(rowMarker.getDown() != null && y < 2){
						rowMarker = rowMarker.getDown();
						pointer = rowMarker;
					}
					
				}
				//if two squares with two of the same possibilities are found
				if(foundTwo == true) {
					//double for loops goes through all squares in 3x3 area
					for(int y = 0; y < 3; y++){
						for(int x = 0; x < 3; x++){
							pointer = lg.findNode(w, q);
							rowMarker = pointer;
							
							//remove possibilities if square is not one of the two squares found
							if(pointer != temp && pointer != temp2) {
								pointer.setNum(num1, false);
								pointer.setNum(num2, false);
							}
							
							if(pointer.getRight() != null && x < 2) {
								pointer = pointer.getRight();
							}
							
						}
						if(rowMarker.getDown() != null && y < 2){
							rowMarker = rowMarker.getDown();
							pointer = rowMarker;
						}
						
					}
				}
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException {		
		//used to store grid before solving
		LinkedGrid temp = null;
		//records the amount of times the procedure runs to solve the puzzle
		int times = 0;
		//input the puzzle from the file and set the possibilities of each square
		presentPuzzle();
		lg.display();
		
		//run code until solved
		while (checkIfSolved() == false) {
			times++;
			System.out.println(times);
			//stores grid's original state
			temp = copyGrid(lg);
			ifOnePoss();
			ifOnlyPoss();
			ifOnlyColumePoss();
			ifOnlyRowPoss();
			ifTwoBoxTwoPoss();
			ifOnePoss();
			ifOnlyPoss();
			//if solve prcedure so far makes no progress, use guess method
			if(lg.equal(temp)) {
				guess();
			}
			//display result
			lg.display();
		}
		
	}

}
