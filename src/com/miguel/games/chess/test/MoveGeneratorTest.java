package com.miguel.games.chess.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.miguel.games.chess.entities.Movement;
import com.miguel.games.chess.entities.Position;
import com.miguel.games.chess.fen.FenUtilities;

public class MoveGeneratorTest {

	private static int rootMovementsNumber;	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//
		// Move generator test
		//
		
		System.out.println( "Move generator test: START" );
		
		moveGeneratorTestSuite();

		System.out.println( "Move generator test: END" );
		
	}

	private static int moveGeneratorTest(
		Position position, 
		int plyLevel,
		int selectedDepth
	) {
		
		int result;
		
		if ( plyLevel == 0 ) {
			//
			// Base case: we return how many leaves we visited, i.e., 1
			//
			result = 1;
			
		}
		else {
			//
			// Recursive case: we have to generate all legal movements, get how
			// many leaf nodes we visited on each of them, and return the addition
			// of this quantities
			//
			ArrayList<Movement> movements =
				position.getLegalMovements( false, null );
			
			int movementsSize =
				movements.size();
			
			result = 0;
			
			for ( int i = 0; i < movementsSize; i++ ) {
				
				Movement movement =
					movements.get( i );
				
				//
				// We execute the movement
				//
				position.executeMovement(
					movement
				);
				
				//
				// Then we change turn
				//
				position.changeTurn();
				
				//
				// We get the minimax value for this node
				//
				int movementLeafNodesVisited =
					moveGeneratorTest(
						position,
						plyLevel - 1,
						selectedDepth
					);
				
				if ( plyLevel == selectedDepth ) {
					
					rootMovementsNumber++;
					
					movement.visualize();
					System.out.println( " " + movementLeafNodesVisited );
				}
				
				result = 
					result
					+ movementLeafNodesVisited;
				
				//
				// We reverse the movement
				//
				position.reverseMovement(
					movement
				);
				
				//
				// And we change turn, to keep everything as it was,
				// for the next branch of our tree
				//
				position.changeTurn();
				
			}
			
		}
		
		return result;
	}
	
	private static void moveGeneratorTestSuite() {
		
		//
		// We have to read our FEN-Perft file and test all of its items, to ensure
		// that our move generator is correct enough
		//
		boolean error = false;
		
		try {
			
			File epdFile =
				new File( "perftsuite.epd" );
			
			FileReader fr =
				new FileReader(
					epdFile
				);
			
			BufferedReader br =
				new BufferedReader( fr );
			
			String fileLine =
				br.readLine();
			
			while ( fileLine != null ) {
				
				System.out.println( fileLine );
				
				String[] fileLineItems =
					fileLine.split( ";" );
				
				//
				// We get the FEN string defining a chess position
				//
				String fenString =
					fileLineItems[0];
				
				Position position =
					FenUtilities.getPosition(
						fenString
					);
				
				//
				// DEBUGGING. Quitar esto de abajoooooooooooooooooooooo
				//
				
//				ArrayList<Movement> movements =
//					position.getLegalMovements();
//			
//				position.executeMovement(
//					movements.get( 0 )
//				);
//				
//				position.changeTurn();
//				
//				movements =
//					position.getLegalMovements();
//			
//				position.executeMovement(
//					movements.get( 42 )
//				);
//				
//				position.changeTurn();
//				
//				movements =
//					position.getLegalMovements();
//			
//				position.executeMovement(
//					movements.get( 2 )
//				);
//				
//				position.changeTurn();
				
				//
				// DEBUGGING. Quitar esto de arribaaaaaaaaaaaaaaaaaaaaa
				//
				
				//
				// And now, we get as many Dx y items as present. Each item
				// contains a deepness level (number of plies) and the number of leaves
				// that there should be in the complete minimax search for this level
				//
				for ( int i = 1; ! error && i < fileLineItems.length; i++  ) {
					
					String deepnessAndLeaves =
						fileLineItems[i];
					
					int firstBlankIndex =
						deepnessAndLeaves.indexOf( " " );
					
					int secondBlankIndex =
						deepnessAndLeaves.indexOf(
							" ",
							firstBlankIndex + 1
						);
					
					if ( secondBlankIndex == -1 ) {
						//
						// This happens in the last item...
						//
						secondBlankIndex = 
							deepnessAndLeaves.length();
					}
					
					String pliesLevelString =
						deepnessAndLeaves.substring(
							1,
							firstBlankIndex
						);
					
					String numberOfLeavesString =
						deepnessAndLeaves.substring(
							firstBlankIndex + 1,
							secondBlankIndex
						);
					
					int pliesLevel =
						Integer.parseInt(
							pliesLevelString
						);
					
					int numberOfLeavesExpected =
						Integer.parseInt(
							numberOfLeavesString
						);
					
					int numberOfVisitedLeaves =
						moveGeneratorTest(
							position,
							pliesLevel,
							pliesLevel
						);
					
					if ( numberOfVisitedLeaves == numberOfLeavesExpected ) {
						
						System.out.println( 
							"PliesLevel: "
							+ pliesLevel
							+ ". Correct. Visited leaves: "
							+ numberOfVisitedLeaves
						);
						System.out.println( "---------------------------------------------------------" );
					}
					else {
						//
						// There is an error in the move generator. We print some useful
						// information and, at the moment, we stop the process
						//
						System.out.println( "Unexpected result from move generator" );
						System.out.println( "-------------------------------------" );
						System.out.println( "pliesLevel: " + pliesLevel );
						System.out.println( "numberOfVisitedLeaves: " + numberOfVisitedLeaves );
						System.out.println( "numberOfLeavesExpected: " + numberOfLeavesExpected );
						System.out.println( "-------------------------------------" );
						
						error = true;
					}
					
				}
				
				if ( ! error ) {
					fileLine = br.readLine();
				}
				else {
					fileLine = null;
				}
				
			}
			
			fr.close();
			
		} catch ( FileNotFoundException fnfe ) {

			fnfe.printStackTrace();
			
			error = true;
			
		} catch ( IOException ioe ) {
			
			ioe.printStackTrace();
			
			error = true;
		}
		
		if ( error ) {
			System.out.println( "--------------------------------------" );
			System.out.println( "--------------------------------------" );
			System.out.println( "THERE ARE ERRORS IN THE MOVE GENERATOR" );
		}
		else {
			System.out.println( "--------------------------------------" );
			System.out.println( "--------------------------------------" );
			System.out.println( "TEST MOVE GENERATOR OK !" );
		}
	}

}
