/**
 * 
 */
package com.miguel.games.chess.engine;

import java.util.ArrayList;

import com.miguel.games.chess.common.Constants;
import com.miguel.games.chess.entities.Bishop;
import com.miguel.games.chess.entities.Board;
import com.miguel.games.chess.entities.King;
import com.miguel.games.chess.entities.Knight;
import com.miguel.games.chess.entities.Movement;
import com.miguel.games.chess.entities.Pawn;
import com.miguel.games.chess.entities.Piece;
import com.miguel.games.chess.entities.Position;
import com.miguel.games.chess.entities.Rook;

/**
 * @author mdelgado
 *
 */
public class Engine extends com.miguel.games.entities.Engine {
	
	public static final String ENGINE_NAME = "JustMoveChess";
	public static final String ENGINE_AUTHOR = "Miguel Delgado Esteban";

	private static int nodesNumber; // This variable was maybe used for testing the move generator
	
	public static final int DEFAULT_NUMBER_OF_PLIES_TO_SEARCH = 6;
	
	public static final boolean QUIESCENCY = true;
	
	public static boolean move(
		Position position,
		Movement movement,
		boolean justMoveChessEngineMovement,
		boolean dontPayAttentionToAnythingElseAndTestEngineVersusItself 
	) {
		
		//
		// We select the best possible movement for the colour in turn 
		//
		boolean result = true;
		
		boolean colourInTurn = position.getTurn();
		
		//
		// Iterative deepening
		// 
		// We search first x plies, then x+1 plies, x+2 plies, and so on. We will get
		// the best movement from the deepest search, unless we find a mate. In
		// this case, we execute the movement that could lead to mate
		//
		
		int pliesToSearch = 5;  // Plies to start the iterative deepening schema
		
		boolean keepExploringDeeper = true;
		
		if ( movement == null ) {
			
			//
			// To achieve a fluid game pace combined with effectiveness, specially for testing, the number of plies to
			// search should depend on the number of pieces still on the board. The less pieces on the board, the deeper
			// the search can be.
			//
			// TODO: mix this concept with available time to move
			//
			int maxPliesToSearch = howManyPliesToSearch( position );
			
			System.out.println( "Number of plies to begin with: " + pliesToSearch );
			System.out.println( "Maximum number of plies to analyze: " + maxPliesToSearch );
			
			//
			// Iterative deepening
			//
			Movement principalVariationMovement = null;  // Just to begin, of course
			
			while ( keepExploringDeeper && pliesToSearch <= maxPliesToSearch ) {
				
				System.out.println( "-- Analyzing " + pliesToSearch + " plies..." );
				
				movement =
					selectBestMovementVersionTwo(
						position,
						pliesToSearch,
						principalVariationMovement
					);
								
				System.out.println( pliesToSearch + " plies analysis finished." );			
				
				if (
					movement == null
					|| movement.getValue() == Constants.KING_MATE
					|| movement.getValue() == Constants.KING_MATE_IN_ONE
				) {
					keepExploringDeeper = false;
				}
				
				if ( movement != null ) {
					principalVariationMovement = movement;
					System.out.println( "Best movement found till now:" );
					movement.visualize();
				}
				
				pliesToSearch = pliesToSearch + 1;
			}
		}
		
		if ( movement != null ) {
			
			System.out.println( "Our BEST MOVEMENT found for this position (or the one chosen by stdin) :" );
			
			movement.visualize();
			
			position.executeMovement(
				movement
			);
			
			//
			// We check if after the movement, there is a check, to inform
			// the user, or the GUI
			//
			if ( position.isCheck( ! colourInTurn ) ) {
				position.setKingChecked( true );
			}
			else {
				position.setKingChecked( false );
			}
			
			if ( justMoveChessEngineMovement ) {
				System.out.println( "bestmove " + movement.toUCIString() );
			}
		}
		else {
			result = false; 
		}
		
		return result;
			
	}

	/**
	 * This method decides how many plies should be searched. In a first version, it will just depend
	 * on the number of pieces still on the board. Later versions might mix this concept with the
	 * available time to move, or any other considerations
	 * 
	 * @param position
	 * @return
	 */
	public static int howManyPliesToSearch( Position position ) {
		int result = DEFAULT_NUMBER_OF_PLIES_TO_SEARCH;
		
		//
		// Instead of using the number of pieces, with our implementation, it is faster to decide depending
		// on the number of free squares. The more free squares, the less pieces on the board
		//
		//		int numberOfFreeSquaresOnTheBoard =
		//			position.getBoard().getFreeSquares().size();
		
		//
		// Searching is still too slow to take it any deeper
		//
		//		if ( numberOfFreeSquaresOnTheBoard > 60 ) {
		//			result = 12;
		//		}
		//		else if ( numberOfFreeSquaresOnTheBoard > 58 ) {
		//			result = 11;
		//		}
		//		else if ( numberOfFreeSquaresOnTheBoard > 56 ) {
		//			result = 10;
		//		}
		//		else if ( numberOfFreeSquaresOnTheBoard > 52 ) {
		//			result = 9;
		//		}
		//		else if ( etc etc etc ) {
		//		}
		
		//		if ( numberOfFreeSquaresOnTheBoard > 48 ) {
		//			result = 8;
		//		}
		//		else if ( numberOfFreeSquaresOnTheBoard > 40 ) {
		//			result = 7;
		//		}
				
		return result;
	}

	private static Movement selectBestMovementVersionZero(
		Position position
	) {
		//
		// This version of selectBestMovement, just selects a random index
		// from 0 to movements size - 1 and return the movement contained in
		// this random index, which is just moving by chance
		//
		ArrayList<Movement> movements =
			position.getLegalMovements( true, null );
		
		return
			( movements.size() > 0 )
			? movements.get( ( int )( Math.random() * ( movements.size() - 1 ) ) )
			: null;
		
	}
	
	public static Movement selectBestMovementVersionOne(
		Position position,
		int originalPlyLevel
	) {
		//
		// This version of selectBestMovement does a complete minimax search,
		// exploring a number of plies
		//
		nodesNumber = 0;
		
		Movement result = null;
		
		boolean colourInTurn = position.getTurn();
		
		ArrayList<Movement> movements =
			position.getLegalMovements( true, null );
		
		int movementsSize =
			movements.size();
		
		int maxNodeValue = 
			Integer.MIN_VALUE;
		
		for ( int i = 0; i < movementsSize; i++ ) {
			
			Movement movement =
				movements.get( i );
			
			//
			// We visualize the movement that is currently being analyzed
			//
			// movement.visualize();
			
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
			int nodeValue =
				minimaxSearchNodeValue(
					position,
					originalPlyLevel - 1,
					colourInTurn,
					false,
					originalPlyLevel
				);
			
			//
			// If it is higher than the best at the moment, we
			// get it as our best choice until now
			//
			if ( nodeValue > maxNodeValue ) {
				
				maxNodeValue = nodeValue;
				
				movement.setValue(
					nodeValue
				);
				
				result = movement;
			}
			
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
		
		// System.out.println( "Nodes number: " + nodesNumber );
		
		return result;
	}

	private static int minimaxSearchNodeValue(
		Position position, 
		int plyLevel,
		boolean colour,
		boolean isMaxNode,
		int originalPlyLevel
	) {
		
		int result;
		
		if ( plyLevel == 0 ) {
			//
			// Base case: we return the static evaluation of the position
			//
			result = 
				evaluatePositionVersionOne(
					position,
					colour,
					plyLevel,
					originalPlyLevel
				);
			
			nodesNumber++;
		}
		else {
			//
			// Recursive case: we have to generate all legal movements and
			// select the maximum or minimum value ( depending on the actual ply ) among them
			//
			ArrayList<Movement> movements =
				position.getLegalMovements( true, null );
			
			int movementsSize =
				movements.size();
			
			if ( movementsSize > 0 ) {
				
				//
				// We set extreme initial values for the result
				//
				result = 
					( isMaxNode )
					? Integer.MIN_VALUE
					: Integer.MAX_VALUE;
				
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
					int nodeValue =
						minimaxSearchNodeValue(
							position,
							plyLevel - 1,
							colour,
							! isMaxNode,
							originalPlyLevel
						);
					
					//
					// If it is better than the best at the moment, we
					// get it as our best choice until now
					//
					if ( 
						(
							isMaxNode 
							&& nodeValue > result
						)
						||
						(
							! isMaxNode
							&& nodeValue < result
						)
					) {
						
						result = nodeValue;
					}
					
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
			else {
				//
				// We are in a not-leaf-node, but there are no legal movements.
				// We have to check if it is a mate or a draw, which is something
				// that our static evaluation method does not perform 
				//
				if ( 
					position.isCheck(
						position.getTurn()
					) 
				) {
					//
					// No possible movements, and the king is checked. This is
					// a victory for the opposite colour. We always return a value
					// from the point of view of the colour moving in the root of the tree
					//
					if ( colour == position.getTurn() ) {
						result = - Constants.KING_MATE + ( originalPlyLevel - plyLevel );
					}
					else {
						result = Constants.KING_MATE - ( originalPlyLevel - plyLevel );
					}
				}
				else {
					//
					// No possible movements, but the king is not checked. This
					// is a draw (Drown king). We return a 0 value (both colours 
					// are tied), though it is not sure that this is the best way
					// of dealing with draws
					//
					result = 0;
				}
			}
		}
		
		return result;
	}

	public static Movement selectBestMovementVersionTwo(
		Position position,
		int originalPlyLevel,
		Movement principalVariationMovement
	) {
		//
		// This version of selectBestMovement does a 
		// minimax search + poda alfa-beta
		//
		Movement result = null;
		
		boolean colourInTurn = position.getTurn();
		
		ArrayList<Movement> movements =
			position.getLegalMovements( true, principalVariationMovement );
		
		int movementsSize =	movements.size();
		
		System.out.println( "Plies to search: " + originalPlyLevel );
		System.out.println( "Number of legal movements to analyze: " + movementsSize );
		
		//
		// alfa is the max value for the previously visited 
		// brother nodes of a min node
		//
		int alfa = Integer.MIN_VALUE;
				
		//
		// beta is the min value for the previously visited 
		// brother nodes of a max node
		//
		int beta = Integer.MAX_VALUE;
		
		boolean keepExploringChildrenNodes = true;
		
		for ( int i = 0; keepExploringChildrenNodes && i < movementsSize; i++ ) {
			
			Movement movement =
				movements.get( i );
			
			//
			// We visualize the movement that is currently being analyzed
			//
			System.out.println( "Analyzing movement " + ( i + 1 ) + "..." );
			movement.visualize();
			
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
			int nodeValue =
				minimaxAlfaBetaSearchNodeValue(
					position,
					originalPlyLevel - 1,
					colourInTurn,
					alfa,
					beta,
					false,
					originalPlyLevel,
					QUIESCENCY
				);
			
			//
			// If it is higher than the best at the moment, we
			// get it as our best choice until now
			//
			
			if ( nodeValue > alfa ) {
				alfa = nodeValue;
				movement.setValue(
					nodeValue
				);
				result = movement;
				
				System.out.println(
					"...which is the best till now. Value: " + nodeValue
				);
				
				if ( nodeValue == Constants.KING_MATE_IN_ONE ) {
					//
					// This is a mate in one. We must execute it, and
					// there is no need to keep exploring children nodes
					//
					keepExploringChildrenNodes = false;
				}
			}
			
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
		
		// System.out.println( "Nodes number: " + nodesNumber );
		
		return result;
	}
	
	private static int minimaxAlfaBetaSearchNodeValue(
		Position position, 
		int plyLevel,
		boolean colour,
		int alfa,
		int beta,
		boolean isMaxNode,
		int originalPlyLevel,
		boolean doQuiescency
	) {
		
		int result;
		
		if ( plyLevel == 0 ) {
			
			//
			// Base case: we just return the static value for the position, or try quiescency search, if
			// we are told to do so
			//
			if ( doQuiescency ) {
				//
				// Quiescency (if we ever make it work properly)
				//
				result =
					minimaxAlfaBetaQuiescencySearchNodeValue(
						position, 
						plyLevel, 
						colour, 
						alfa, 
						beta, 
						isMaxNode,
						originalPlyLevel
					);
			}
			else {
				result = 
					evaluatePositionVersionOne(
						position,
						colour,
						plyLevel,
						originalPlyLevel
					);
			}
		}
		else {
			//
			// Recursive case: we have to generate all legal movements and
			// select the maximum or minimum value ( depending on the actual ply ) among them
			//
			boolean keepExploringChildrenNodes = true;
			
			ArrayList<Movement> movements =
				position.getLegalMovements( true, null );
			
			int movementsSize =
				movements.size();
			
			if ( movementsSize > 0 ) {
				
				//
				// We set extreme initial values for the result
				//
				result = 
					( isMaxNode )
					? alfa
					: beta;
				
				for ( int i = 0; keepExploringChildrenNodes && i < movementsSize; i++ ) {
					
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
					int nodeValue =
						minimaxAlfaBetaSearchNodeValue(
							position,
							plyLevel - 1,
							colour,
							alfa,
							beta,
							! isMaxNode,
							originalPlyLevel,
							doQuiescency
						);
					
					//
					// If it is better than the best at the moment, we
					// get it as our best choice until now
					//
					if ( isMaxNode ) {
						
						if ( nodeValue >= beta ) {
							//
							// Beta cut-off
							//
							result = beta;
							keepExploringChildrenNodes = false;
						}
						
						if ( nodeValue > alfa ) {
							alfa = nodeValue;
							result = alfa;
						}
					}
					else {
						
						if ( nodeValue <= alfa ) {
							//
							// Alfa cut-off
							//
							result = alfa;
							keepExploringChildrenNodes = false;
						}
						
						if ( nodeValue < beta ) {
							beta = nodeValue;
							result = beta;
						}
					}
					
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
			else {
				//
				// We are in a not-leaf-node, but there are no legal movements.
				// We have to check if it is a mate or a draw, which is something
				// that our static evaluation method does not perform 
				//
				if ( position.isCheck( position.getTurn() )	) {
					//
					// No possible movements, and the king is checked. This is
					// a victory for the opposite colour. We always return a value
					// from the point of view of the colour moving in the root of the tree
					//
					if ( colour == position.getTurn() ) {
						
						result = - Constants.KING_MATE + ( originalPlyLevel - plyLevel );
					}
					else {
						result = Constants.KING_MATE - ( originalPlyLevel - plyLevel );
					}
				}
				else {
					//
					// No possible movements, but the king is not checked. This
					// is a draw (Drown king). We return a 0 value (both colours 
					// are tied), though it is not sure that this is the best way
					// of dealing with draws
					//
					result = 0;
				}
			}
		}
		
		return result;
	}
	
	private static int minimaxAlfaBetaQuiescencySearchNodeValue(
		Position position,
		int plyLevel,
		boolean colour,
		int alfa, 
		int beta,
		boolean isMaxNode,
		int originalPlyLevel
	) {
		//
		// This method is used by the minimaxAlfaBeta method, to perform quiescency
		//
		//
		// We will consider a position quiet if it does not allow any of these movements:
		//
		// - Captures, (en-passant included) and queen promotions (other promotions really happen very seldom)
		// 
		int result = 0;  // Just by default. Surely changes below...
		
		int nodeValue =
			evaluatePositionVersionOne(
				position,
				colour,
				plyLevel,
				originalPlyLevel
			);
		
		//
		// If it is better than the best at the moment, we
		// get it as our best choice until now
		//
		if ( isMaxNode ) {
			
			if ( nodeValue >= beta ) {
				//
				// Beta cut-off
				//
				result = beta;
				return result;
			}
			
			if ( nodeValue > alfa ) {
				alfa = nodeValue;
			}
		}
		else {
			
			if ( nodeValue <= alfa ) {
				//
				// Alfa cut-off
				//
				result = alfa;
				return result;
			}
			
			if ( nodeValue < beta ) {
				beta = nodeValue;
			}
		}
		
		// There were no cut-offs. Quiescency search then...
				
		boolean keepExploringChildrenNodes = true;
		
		ArrayList<Movement> movements =
			position.getLegalMovements( true, null );
		
		int movementsSize =
			movements.size();
		
		if ( movementsSize > 0 ) {
			
			//
			// We set extreme initial values for the result
			//
			result = 
				( isMaxNode )
				? alfa
				: beta;
			
			boolean isQuietPosition = true;
			
			for ( int i = 0; keepExploringChildrenNodes && i < movementsSize; i++ ) {
				
				Movement movement = movements.get( i );
				
				if (
					movement.isCapture()
					|| movement.isEnPassant()
					|| movement.isPromotedPawn()
				) {
					isQuietPosition = false;
					
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
					nodeValue =
						minimaxAlfaBetaQuiescencySearchNodeValue(
							position,
							plyLevel + 1,  // With quiescency, ply level goes up increasing its value...
							colour,
							alfa,
							beta,
							! isMaxNode,
							originalPlyLevel
						);
					
					//
					// If it is better than the best at the moment, we
					// get it as our best choice until now
					//
					if ( isMaxNode ) {
						
						if ( nodeValue >= beta ) {
							//
							// Beta cut-off
							//
							result = beta;
							keepExploringChildrenNodes = false;
						}
						
						if ( nodeValue > alfa ) {
							alfa = nodeValue;
							result = alfa;
						}
					}
					else {
						
						if ( nodeValue <= alfa ) {
							//
							// Alfa cut-off
							//
							result = alfa;
							keepExploringChildrenNodes = false;
						}
						
						if ( nodeValue < beta ) {
							beta = nodeValue;
							result = beta;
						}
					}
					
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
			
			if ( isQuietPosition ) {
				result = 
					evaluatePositionVersionOne(
						position,
						colour,
						plyLevel,
						originalPlyLevel
					);
			}
		}
		else {
			//
			// We are in a not-leaf-node, but there are no legal movements.
			// We have to check if it is a mate or a draw, which is something
			// that our static evaluation method does not perform 
			//
			if ( position.isCheck( position.getTurn() ) ) {
				//
				// No possible movements, and the king is checked. This is
				// a victory for the opposite colour. We always return a value
				// from the point of view of the colour moving in the root of the tree
				//
				if ( colour == position.getTurn() ) {
					
					result = - Constants.KING_MATE + ( originalPlyLevel - plyLevel );
				}
				else {
					result = Constants.KING_MATE - ( originalPlyLevel - plyLevel );
				}
			}
			else {
				//
				// No possible movements, but the king is not checked. This
				// is a draw (Drown king). We return a 0 value (both colours 
				// are tied), though it is not sure that this is the best way
				// of dealing with draws
				//
				result = 0;
			}
		}
		
		return result;
	}
	
	public static int evaluatePositionVersionZero(
		Position position,
		boolean colour
	) {
		
		//
		// This method returns a static evaluation for the position, from the
		// point of view of the colour received as parameter.
		//
		// A positive result means some kind of advantage for such colour
		// A zero result means none of the colours has an advantage 
		// A negative result means some kind of disadvantage for such colour
		//

		//
		// Each piece has a value. Just add all those values for remaining pieces
		//
		
		//
		// Maybe the colour in turn cannot move in this position, which would be
		// either a victory for the other colour, or a draw. We check this
		//
		int result;
		
		int fullMoveCounter = position.getFullMoveCounter();
		
		ArrayList<Movement> movements =
			position.getLegalMovements( false, null );
		
		int movementsSize =
			movements.size();
		
		if ( movementsSize > 0 ) {
		
			int whiteHeuristicValue = 0;
			int blackHeuristicValue = 0;
			
			Board board = position.getBoard();
			
			//
			// White pieces
			//
			
			ArrayList<Piece> whitePieces =
				board.getWhitePieces();
			
			int sizeWhitePieces =
				whitePieces.size();
			
			for ( int i = 0; i < sizeWhitePieces; i++ ) {
				
				Piece piece = 
					whitePieces.get( i );
				
				whiteHeuristicValue =
					whiteHeuristicValue + piece.getHeuristicValue( fullMoveCounter );
			}
			
			//
			// Black pieces
			//
			
			ArrayList<Piece> blackPieces =
				board.getBlackPieces();
			
			int sizeBlackPieces =
				blackPieces.size();
			
			for ( int i = 0; i < sizeBlackPieces; i++ ) {
				
				Piece piece =
					blackPieces.get( i );
				
				blackHeuristicValue =
					blackHeuristicValue + piece.getHeuristicValue( fullMoveCounter );
			}
			
			position.setWhiteHeuristicValue( whiteHeuristicValue );
			position.setBlackHeuristicValue( blackHeuristicValue );
			
			if ( colour ) {
				result = whiteHeuristicValue - blackHeuristicValue;
			}
			else {
				result = blackHeuristicValue - whiteHeuristicValue;
			}
		}
		else {
			if ( 
				position.isCheck(
					position.getTurn()
				) 
			) {
				//
				// No possible movements, and the king is checked. This is
				// a victory for the opposite colour. We always return a value
				// from the point of view of the colour moving in the root of the tree
				//
				if ( colour == position.getTurn() ) {
					result = - Constants.KING_MATE;
				}
				else {
					result = Constants.KING_MATE;
				}
			}
			else {
				//
				// No possible movements, but the king is not checked. This
				// is a draw (Drown king). We return a 0 value (both colours 
				// are tied), though it is not sure that this is the best way
				// of dealing with draws
				//
				result = 0;
			}
		}
		
		return result;
		
	}

	public static int evaluatePositionVersionOne(
		Position position,
		boolean colour,
		int plyLevel,
		int originalPlyLevel
	) {
		
		//
		// This method returns a static evaluation for the position, from the
		// point of view of the colour received as parameter.
		//
		// A positive result means some kind of advantage for such colour
		// A zero result means none of the colours has an advantage 
		// A negative result means some kind of disadvantage for such colour
		//

		//
		// Maybe the colour in turn cannot move in this position, which would be
		// either a victory for the other colour, or a draw. We check this
		//
		int result;
		
		ArrayList<Movement> movementsSideInTurn =
			position.getLegalMovements( false, null );
		
		int movementsSideInTurnSize =
			movementsSideInTurn.size();
		
		if ( movementsSideInTurnSize > 0 ) {
		
			int whiteHeuristicValue = 0;
			int blackHeuristicValue = 0;
			
			Board board = position.getBoard();
			
			ArrayList<Piece> whitePieces =
				board.getWhitePieces();
			
			int sizeWhitePieces =
				whitePieces.size();
			
			ArrayList<Piece> blackPieces =
				board.getBlackPieces();
			
			int sizeBlackPieces =
				blackPieces.size();
			
			int fullMoveCounter = position.getFullMoveCounter();
			
			//
			// White pieces
			//
			
			int numberOfWhiteBishops = 0;  // It may change below, of course...
			
			for ( int i = 0; i < sizeWhitePieces; i++ ) {
				
				Piece piece = 
					whitePieces.get( i );
				
				int squareId = piece.getSquareId();
				
				whiteHeuristicValue =
					whiteHeuristicValue + piece.getHeuristicValue( fullMoveCounter );
				
				//
				// A pawn or knight placed in the central squares 
				// receives extra points
				//
				if ( 
					piece instanceof Pawn
					|| piece instanceof Knight
				) {
					int row = squareId / 8;
					
					int column = squareId % 8;
					
					if (
						(
							row == 3
							|| row == 4
						)
						&&
						(
							column == 3
							|| column == 4
						)
					) {
						whiteHeuristicValue =
							whiteHeuristicValue + Constants.CENTRALIZED_PIECE_FIRST_CLASS;
					}
					else if (
						row > 1
						&& row < 6
						&& column > 1
						&& column < 6
					) {
						whiteHeuristicValue =
							whiteHeuristicValue + Constants.CENTRALIZED_PIECE_SECOND_CLASS;
					}
					
					//
					// Middle weight pieces (knights in this case) naturally developed during the opening is usually and advantage.
					//
					if (
						piece instanceof Knight
						&& fullMoveCounter <= Constants.OPENING_LIMIT_FULL_MOVE_COUNTER
						&& squareId != 1
						&& squareId != 6
					) {
						whiteHeuristicValue =
							whiteHeuristicValue + Constants.DEVELOPED_MEDIUM_WEIGHT_PIECE_DURING_OPENING;
						
						if ( board.isPieceAttackingAnyOfTheFourCentralSquares( piece ) ) {
							whiteHeuristicValue =
								whiteHeuristicValue + Constants.KNIGHT_ATTACKING_ANY_OF_THE_CENTRAL_SQUARES_DURING_OPENING;
						}
					}
					
					//
					// Knights are awarded points for not being on the a and h columns during the opening and middle game
					//
					if (
						piece instanceof Knight
						&& fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER
						&& column != 0
						&& column != 7
					) {
						whiteHeuristicValue =
							whiteHeuristicValue + Constants.KNIGHT_NOT_ON_A_OR_H_COLUMNS_DURING_THE_OPENING_AND_MIDDLE_GAME;
					}
					
					if ( piece instanceof Pawn ) {
						//
						// Advanced pawns
						//
						if ( row == 5 ) {
							whiteHeuristicValue = whiteHeuristicValue + Constants.SIXTH_ROW_ADVANCED_PAWN;
						}
						else if ( row == 6 ) {
							whiteHeuristicValue = whiteHeuristicValue + Constants.SEVENTH_ROW_ADVANCED_PAWN;
						}
						
						boolean doubledPawn = false;
						boolean isolatedPawn = false;
						
						//
						// Doubled pawns
						//
						if ( board.thereIsASameColourPawnInTheSameColumn( piece ) ) {
							doubledPawn = true;
							whiteHeuristicValue = whiteHeuristicValue - Constants.DOUBLED_PAWN;
						}
						
						//
						// Isolated pawns
						//
						if ( board.isAnIsolatedPawn( ( Pawn )piece ) ) {
							isolatedPawn = true;
							whiteHeuristicValue = whiteHeuristicValue - Constants.ISOLATED_PAWN;
						}
						
						if ( doubledPawn && isolatedPawn ) {
							whiteHeuristicValue = whiteHeuristicValue - Constants.DOUBLED_AND_ISOLATED_PAWN_BONUS;
						}
						
						//
						// Passed pawns, taking into account that we are already giving points to advanced pawns.
						//
						if (
							fullMoveCounter >= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER
							&& board.isCandidateToBePassedPawn( ( Pawn )piece )
						) {
							whiteHeuristicValue = whiteHeuristicValue + Constants.CANDIDATE_PASSED_PAWN;
						}
					}
				}
				else if (
					piece instanceof Bishop
					&& fullMoveCounter <= Constants.OPENING_LIMIT_FULL_MOVE_COUNTER
					&& squareId != 2
					&& squareId != 5
				) {
					//
					// Middle weight pieces (bishops in this case) naturally developed during the opening is usually and advantage.
					//
					whiteHeuristicValue =
						whiteHeuristicValue + Constants.DEVELOPED_MEDIUM_WEIGHT_PIECE_DURING_OPENING;
					
					if ( board.isPieceAttackingAnyOfTheFourCentralSquares( piece ) ) {
						whiteHeuristicValue =
							whiteHeuristicValue + Constants.BISHOP_ATTACKING_ANY_OF_THE_CENTRAL_SQUARES_DURING_OPENING;
					}
					
					numberOfWhiteBishops++;
				}
				else if ( piece instanceof Bishop ) {
					numberOfWhiteBishops++;
				}
				else if ( piece instanceof Rook ) {
					//
					// Endings: a Rook on its side's 7th row (6th row in our model) should be awarded some points,
					// if the rival king is on the 8th (7th row in our model)
					//
					if (
						fullMoveCounter <= Constants.OPENING_LIMIT_FULL_MOVE_COUNTER
						&& board.isPieceAttackingAnyOfTheFourCentralSquares( piece )
					) {
						whiteHeuristicValue =
							whiteHeuristicValue + Constants.ROOK_ATTACKING_ANY_OF_THE_CENTRAL_SQUARES_DURING_OPENING;
					}
					else if (
						fullMoveCounter >= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER
						&& board.isPieceOnTheIesimRow( piece, 6 )
						&& board.isPieceOnTheIesimRow( board.getKing( false ), 7 )
					) {
						whiteHeuristicValue =
							whiteHeuristicValue + Constants.ROOK_ON_ITS_SEVENTH_ROW_AND_RIVAL_KING_ON_THE_EIGHTH_ROW;
					}
				}
				else if ( piece instanceof King ) {
					if (
						fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER
						|| position.isShortCastlePossible( ( King )piece )
						|| position.isLongCastlePossible( ( King )piece )
					) {
						// If any of both castles are still possible, then it is a good idea not to move the king weirdly
						whiteHeuristicValue =
							whiteHeuristicValue + Constants.KING_NOT_WEIRDLY_AND_SURELY_TOO_EARLY_MOVED;
					}
				}
			}
			
			//
			// White castle situation. Castling is almost crucial in the beginning and middle game, and
			// maybe a bit less important from there on (advanced middle game and endings)
			//
			King whiteKing =
				( King )( whitePieces.get( 0 ) );
			
			if ( 
				whiteKing.isCastled() 
			) {
				if ( fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER ) {
					whiteHeuristicValue = whiteHeuristicValue + Constants.CASTLED_KING_OPENING_AND_MIDDLE_GAME;
				}
				else {
					whiteHeuristicValue = whiteHeuristicValue + Constants.CASTLED_KING_ENDINGS;
				}
			}
			else {
				if ( fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER ) {
					if (
						position.isWhiteShortCastleAllowed()
						|| position.isWhiteLongCastleAllowed()
					) {
						whiteHeuristicValue =
							whiteHeuristicValue
							+ Constants.NOT_CASTLED_KING_BUT_STILL_POSSIBLE_TO_CASTLE_OPENING_AND_MIDDLE_GAME;
					}
					else {
						whiteHeuristicValue =
							whiteHeuristicValue
							- Constants.NOT_CASTLED_KING_AND_IMPOSSIBLE_TO_CASTLE_OPENING_AND_MIDDLE_GAME;
					}
				}
			}
			
			//
			// White king mobility. Only important in the endings
			// This code does not check if all those squares are not under enemy fire and 
			// the king can really move to those free squares
			//
			//			if ( position.getFullMoveCounter() > Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER ) {
			//				ArrayList<Square> whiteKingMovementSquares =
			//					board.getKingMovementEndSquares(
			//						whiteKing.getSquare(), 
			//						Constants.WHITE_COLOUR
			//					);
			//				
			//				whiteHeuristicValue =
			//					whiteHeuristicValue 
			//					+ Constants.KING_MOBILITY_WEIGHT_PER_MOVEMENT * whiteKingMovementSquares.size();
			//			}
			
			//
			// Still enjoying bishops pair is also usually an advantage. The less material on
			// the board, the more valuable is such a bishop pair
			//
			if ( numberOfWhiteBishops == 2 ) {
				if ( sizeWhitePieces + sizeBlackPieces <= Constants.BISHOPS_PAIR_MATERIAL_ON_THE_BOARD_BOUND ) {
					whiteHeuristicValue = whiteHeuristicValue + Constants.BISHOPS_PAIR_WITH_FEW_PIECES_ON_THE_BOARD;
				}
				else {
					whiteHeuristicValue = whiteHeuristicValue + Constants.BISHOPS_PAIR_WITH_MANY_PIECES_ON_THE_BOARD;
				}
			}
			
			//
			// Giving check, just by itself, is not always a good idea, as it can give the king a chance to escape and improve his position.
			// But sometimes it is interesting, for example trying to avoid the rival castling, or even looking for a mate
			// At the moment, we are trying to make the engine play more aggressively in the endings, looking for mate
			//
			// TODO: extra points should be awarded if the king is not castled and this check will avoid it for the whole game
			// because the king would have to be moved
			//
			if (
				position.isCheck( false )
			) {
				if ( fullMoveCounter > Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER ) {
					whiteHeuristicValue = whiteHeuristicValue + Constants.RIVAL_KING_CHECKED_ENDING;
				}
				else {
					whiteHeuristicValue = whiteHeuristicValue + Constants.RIVAL_KING_CHECKED_OPENING_AND_MIDDLE_GAME;
				}
			}
			
			//
			// Having both rooks connected in the first row is usually a positional advantage
			// during the opening and middle game
			//
			if (
				fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER
				&& position.areTwoRooksConnectedOnTheFirstRow( true )
			) {
				whiteHeuristicValue = whiteHeuristicValue + Constants.ROOKS_CONNECTED_ON_THE_FIRST_ROW;
			}
			
			//-------------------------------------------------------------------------------------
			//
			// Black pieces
			//
			
			int numberOfBlackBishops = 0;  // It may change below, of course...
			
			for ( int i = 0; i < sizeBlackPieces; i++ ) {
				
				Piece piece =
					blackPieces.get( i );
				
				int squareId = piece.getSquareId();
				
				blackHeuristicValue =
					blackHeuristicValue + piece.getHeuristicValue( fullMoveCounter );
				
				//
				// A pawn or knight placed in the central squares 
				// receives extra points
				//
				if ( 
					piece instanceof Pawn
					|| piece instanceof Knight
				) {
					int row =
						squareId / 8;
					
					int column =
						squareId % 8;
					
					if (
						(
							row == 3
							|| row == 4
						)
						&&
						(
							column == 3
							|| column == 4
						)
					) {
						blackHeuristicValue =
							blackHeuristicValue + Constants.CENTRALIZED_PIECE_FIRST_CLASS;
					}
					else if (
						row > 1
						&& row < 6
						&& column > 1
						&& column < 6
					) {
						blackHeuristicValue =
							blackHeuristicValue + Constants.CENTRALIZED_PIECE_SECOND_CLASS;
					}
					
					//
					// Middle weight pieces (knights in this case) naturally developed during the opening is usually and advantage.
					//
					if (
						piece instanceof Knight
						&& fullMoveCounter <= Constants.OPENING_LIMIT_FULL_MOVE_COUNTER
						&& squareId != 57
						&& squareId != 62
					) {
						blackHeuristicValue =
							blackHeuristicValue + Constants.DEVELOPED_MEDIUM_WEIGHT_PIECE_DURING_OPENING;
						
						if ( board.isPieceAttackingAnyOfTheFourCentralSquares( piece ) ) {
							blackHeuristicValue =
								blackHeuristicValue + Constants.KNIGHT_ATTACKING_ANY_OF_THE_CENTRAL_SQUARES_DURING_OPENING;
						}
					}
					
					//
					// Knights are awarded points for not being on the a and h columns during the opening and middle game
					//
					if (
						piece instanceof Knight
						&& fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER
						&& column != 0
						&& column != 7
					) {
						blackHeuristicValue =
							blackHeuristicValue + Constants.KNIGHT_NOT_ON_A_OR_H_COLUMNS_DURING_THE_OPENING_AND_MIDDLE_GAME;
					}
					
					if ( piece instanceof Pawn ) {
						//
						// Advanced pawns
						//
						if ( row == 2 ) {
							blackHeuristicValue = blackHeuristicValue + Constants.SIXTH_ROW_ADVANCED_PAWN;
						}
						else if ( row == 1 ) {
							blackHeuristicValue = blackHeuristicValue + Constants.SEVENTH_ROW_ADVANCED_PAWN;
						}
						
						boolean doubledPawn = false;
						boolean isolatedPawn = false;
						
						//
						// Doubled pawns
						//
						if ( board.thereIsASameColourPawnInTheSameColumn( piece ) ) {
							doubledPawn = true;
							blackHeuristicValue = blackHeuristicValue - Constants.DOUBLED_PAWN;
						}
						
						//
						// isolated pawns
						//
						if ( board.isAnIsolatedPawn( ( Pawn )piece ) ) {
							isolatedPawn = true;
							blackHeuristicValue = blackHeuristicValue - Constants.ISOLATED_PAWN;
						}
						
						if ( doubledPawn && isolatedPawn ) {
							blackHeuristicValue = blackHeuristicValue - Constants.DOUBLED_AND_ISOLATED_PAWN_BONUS;
						}
						
						//
						// Passed pawns, taking into account that we are already giving points to advanced pawns.
						//
						if ( 
							fullMoveCounter >= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER
							&& board.isCandidateToBePassedPawn( ( Pawn )piece )
						) {
							blackHeuristicValue = blackHeuristicValue + Constants.CANDIDATE_PASSED_PAWN;
						}
					}
				}
				else if (
					piece instanceof Bishop
					&& fullMoveCounter <= Constants.OPENING_LIMIT_FULL_MOVE_COUNTER
					&& squareId != 58
					&& squareId != 61
				) {
					//
					// Middle weight pieces (bishops in this case) naturally developed during the opening is usually and advantage.
					//
					blackHeuristicValue =
						blackHeuristicValue + Constants.DEVELOPED_MEDIUM_WEIGHT_PIECE_DURING_OPENING;
					
					if ( board.isPieceAttackingAnyOfTheFourCentralSquares( piece ) ) {
						blackHeuristicValue =
							blackHeuristicValue + Constants.BISHOP_ATTACKING_ANY_OF_THE_CENTRAL_SQUARES_DURING_OPENING;
					}
					
					numberOfBlackBishops++;
				}
				else if ( piece instanceof Bishop ) {
					numberOfBlackBishops++;
				}
				else if ( piece instanceof Rook ) {
					//
					// Endings: a Rook on its side's 7th row (6th row in our model) should be awarded some points,
					// if the rival king is on the 8th (7th row in our model)
					//
					if (
						fullMoveCounter <= Constants.OPENING_LIMIT_FULL_MOVE_COUNTER
						&& board.isPieceAttackingAnyOfTheFourCentralSquares( piece )
					) {
						blackHeuristicValue =
							blackHeuristicValue + Constants.ROOK_ATTACKING_ANY_OF_THE_CENTRAL_SQUARES_DURING_OPENING;
					}
					else if (
						fullMoveCounter >= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER
						&& board.isPieceOnTheIesimRow( piece, 1 )
						&& board.isPieceOnTheIesimRow( board.getKing( true ), 0 )
					) {
						blackHeuristicValue =
							blackHeuristicValue + Constants.ROOK_ON_ITS_SEVENTH_ROW_AND_RIVAL_KING_ON_THE_EIGHTH_ROW;
					}
				}
				else if ( piece instanceof King ) {
					if (
						fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER
						|| position.isShortCastlePossible( ( King )piece )
						|| position.isLongCastlePossible( ( King )piece )
					) {
						// If any of both castles are still possible, then it is a good idea not to move the king weirdly
						blackHeuristicValue =
							blackHeuristicValue + Constants.KING_NOT_WEIRDLY_AND_SURELY_TOO_EARLY_MOVED;
					}
				}
			}
			
			//
			// Black castle situation
			//
			King blackKing =
				( King )( blackPieces.get( 0 ) );
			
			if ( 
				blackKing.isCastled() 
			) {
				if ( fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER ) {
					blackHeuristicValue = blackHeuristicValue + Constants.CASTLED_KING_OPENING_AND_MIDDLE_GAME;
				}
				else {
					blackHeuristicValue = blackHeuristicValue + Constants.CASTLED_KING_ENDINGS;
				}
			}
			else {
				if ( fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER ) {
					if (
						position.isBlackShortCastleAllowed()
						|| position.isBlackLongCastleAllowed()
					) {
						blackHeuristicValue =
							blackHeuristicValue
							+ Constants.NOT_CASTLED_KING_BUT_STILL_POSSIBLE_TO_CASTLE_OPENING_AND_MIDDLE_GAME;
					}
					else {
						blackHeuristicValue =
							blackHeuristicValue
							- Constants.NOT_CASTLED_KING_AND_IMPOSSIBLE_TO_CASTLE_OPENING_AND_MIDDLE_GAME;
					}
				}
			}
			
			//
			// Black king mobility. Only important in the endings
			// This code does not check if all those squares are not under enemy fire and 
			// the king can really move to those free squares
			//
			//			if ( position.getFullMoveCounter() > Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER ) {
			//				ArrayList<Square> blackKingMovementSquares =
			//					position.getBoard().getKingMovementEndSquares(
			//						blackKing.getSquare(), 
			//						Constants.BLACK_COLOUR
			//					);
			//				
			//				blackHeuristicValue =
			//					blackHeuristicValue 
			//					+ Constants.KING_MOBILITY_WEIGHT_PER_MOVEMENT * blackKingMovementSquares.size();
			//			}
			
			//
			// Still enjoying bishops pair is also usually an advantage. The less material on
			// the board, the more valuable is such a bishop pair
			//
			if ( numberOfBlackBishops == 2 ) {
				if ( sizeWhitePieces + sizeBlackPieces <= Constants.BISHOPS_PAIR_MATERIAL_ON_THE_BOARD_BOUND ) {
					blackHeuristicValue = blackHeuristicValue + Constants.BISHOPS_PAIR_WITH_FEW_PIECES_ON_THE_BOARD;
				}
				else {
					blackHeuristicValue = blackHeuristicValue + Constants.BISHOPS_PAIR_WITH_MANY_PIECES_ON_THE_BOARD;
				}
			}
			
			//
			// Giving check, just by itself, is not always a good idea, as it can give the king a chance to escape and improve his position.
			// But sometimes it is interesting, for example trying to avoid the rival castling, or even looking for a mate
			// At the moment, we are trying to make the engine play more aggressively in the endings, looking for mate
			//
			// TODO: extra points should be awarded if the king is not castled and this check will avoid it for the whole game
			// because the king would have to be moved
			//
			if (
				position.isCheck( true )
			) {
				if ( fullMoveCounter > Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER ) {
					blackHeuristicValue = blackHeuristicValue + Constants.RIVAL_KING_CHECKED_ENDING;
				}
				else {
					blackHeuristicValue = blackHeuristicValue + Constants.RIVAL_KING_CHECKED_OPENING_AND_MIDDLE_GAME;
				}
			}
			
			//
			// Having both rooks connected in the first row is usually a positional advantage
			// during the opening and middle game
			//
			if (
				fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER
				&& position.areTwoRooksConnectedOnTheFirstRow( false )
			) {
				blackHeuristicValue = blackHeuristicValue + Constants.ROOKS_CONNECTED_ON_THE_FIRST_ROW;
			}
			
			// ----------
			
			//
			// We set the values in the position
			//
			position.setWhiteHeuristicValue( whiteHeuristicValue );
			position.setBlackHeuristicValue( blackHeuristicValue );
			
			//
			// And we calculate a result, depending on the colour point
			// of view
			//
			if ( colour ) {
				result = whiteHeuristicValue - blackHeuristicValue;
			}
			else {
				result = blackHeuristicValue - whiteHeuristicValue;
			}
		}
		else {
			if ( position.isCheck( position.getTurn() ) ) {
				//
				// No possible movements, and the king is checked. This is
				// a victory for the opposite colour. We always return a value
				// from the point of view of the colour moving in the root of the tree
				//
				if ( colour == position.getTurn() ) {
					
					result = - Constants.KING_MATE + ( originalPlyLevel - plyLevel );
				}
				else {
					result = Constants.KING_MATE - ( originalPlyLevel - plyLevel );
				}
			}
			else {
				//
				// No possible movements, but the king is not checked. This
				// is a draw (Drown king). We return a 0 value (both colours 
				// are tied), though it is not sure that this is the best way
				// of dealing with draws
				//
				result = 0;
			}
		}
		
		return result;
		
	}
	
}
