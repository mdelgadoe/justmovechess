/**
 * 
 */
package com.miguel.games.chess.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.miguel.games.chess.common.Constants;
import com.miguel.games.chess.entities.Movement;
import com.miguel.games.chess.entities.Position;
import com.miguel.games.chess.fen.FenUtilities;

/**
 * @author mdelgado
 *
 */
public class Game {
	
	private long whiteTime;
	private long blackTime;
	
	public void initialize() {
		
		//
		// We can play a normal game - 0
		// Or we can play from a special position - 1
		//
		
		playFromPosition(
			Constants.NORMAL_GAME_MODE,
			Constants.HUMAN_PLAYING_WHITE,
			false
		);
		
		//
		//		playFromPosition(
		//			Constants.POSITION_ANALYSIS_MODE,
		//			Constants.BLACK_COLOUR,
		//			false
		//		);
		//
		
	}

	private void playFromPosition(
		int mode,
		boolean humanPlayingColour,
		boolean dontPayAttentionToAnythingElseAndTestEngineVersusItself
	) {
		
		whiteTime = Constants.NORMAL_TIME;
		blackTime = Constants.NORMAL_TIME;
		
		Position position = null;
		
		if ( mode == Constants.NORMAL_GAME_MODE ) {
			position =
				initializePosition();
		}
		else if ( mode == Constants.POSITION_ANALYSIS_MODE ) {
			position = 
				FenUtilities.getPosition(
					"7k/PR6/8/8/8/7p/7K/8 w - - 0 1"
				);
		}
		
		position.visualize();
		
		while ( position.getResult() == Constants.STILL_PLAYING ) {
			
			boolean turnBeforeMoving = position.getTurn();
			
			Movement movement = null;
			if (
				turnBeforeMoving == humanPlayingColour
				&& ( ! dontPayAttentionToAnythingElseAndTestEngineVersusItself ) 
			) {
				movement = collectMovementFromHuman( position );
			}
			
			boolean wasPossibleToMove =
				Engine.move(
					position,
					movement,
					true,
					dontPayAttentionToAnythingElseAndTestEngineVersusItself
				);
			
			boolean turnAfterMoving = position.getTurn();
			
			if ( wasPossibleToMove ) {
				
				//
				// Sometimes it is possible to move, but it is useless, for example, 
				// if the two kings are alone in the board: that is a draw
				//
				
				//
				// We change turn
				//
				position.changeTurn();
				
				boolean isADrawCase =
					position.isADrawCase();
				
				if ( isADrawCase ) {
					
					position.setResult( Constants.DRAW );
				}
				
				Engine.evaluatePositionVersionOne(
					position,
					position.getTurn(),
					4,
					4  // Any ply level and original ply level, but equal, for an evaluation that does not depend on any ply issues
				);
				
				position.visualize();
			}
			else {
				//
				// We check for eventual victories, drown king situations, etc
				//
				//
				// There are no possible movements for the colour in turn.
				// We must check if the opposite colour wins, or if it is
				// a draw.
				//
				if ( position.isCheck( turnAfterMoving ) ) {
					//
					// No possible movements, and the king is checked. This is
					// a victory for the opposite colour
					//
					position.setKingChecked( true );
					
					if ( turnAfterMoving ) {
						
						position.setWhiteHeuristicValue( - Constants.KING_MATE );
						position.setBlackHeuristicValue( Constants.KING_MATE );
						position.setResult( Constants.BLACK_WINS );
					}
					else {
						position.setWhiteHeuristicValue( Constants.KING_MATE );
						position.setBlackHeuristicValue( - Constants.KING_MATE );
						position.setResult( Constants.WHITE_WINS );
					}
				}
				else {
					//
					// No possible movements, but the king is not checked. This
					// is a draw (Drown king).				
					//
					position.setResult( Constants.DRAW );
				}
			}
			
		} 
		
		int result = position.getResult();
		
		if ( result == Constants.WHITE_WINS ) {
			System.out.println( "White colour wins !" );
		}
		else if ( result == Constants.BLACK_WINS ) {
			System.out.println( "Black colour wins !" );
		}
		else {
			System.out.println( "Draw" );
		}
		
	}
	
	
	/**
	 * Method that collects a movement from a human oponent via standard input
	 * The movement needs to be in algebraic notation, e.g. e2e4
	 * 
	 * @param position
	 * @return
	 * @throws IOException 
	 */
	private Movement collectMovementFromHuman( Position position ) {
		
		Movement result = null;
		
		BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
		
		// First we obtain all possible movements for this position, and then we will
		// check if the movement entered by the human is one of those legal movements
		ArrayList<Movement> legalMovements = position.getLegalMovements( false, null );
		
		if (
			legalMovements != null
			&& legalMovements.size() > 0
		) {
		
			while ( result == null ) {
				try {
					System.out.print( "Enter your movement (e.g. e2e4): " );
					String movementString = br.readLine();
					
					// TODO: deal below with short and long castle, and surely other movements. Promotion, enpassant...
					
					String startSquareAlgebraicNotation = null;
					String endSquareAlgebraicNotation = null;
					
					// We get the start and end positions
					startSquareAlgebraicNotation = movementString.substring( 0, 2 );
					endSquareAlgebraicNotation = movementString.substring( 2, 4 );
					String promotionChoice = "";
					
					if ( movementString.length() == 5 ) {
						// Get promotion choice
						promotionChoice = promotionChoice.substring( 4 );
					}
					
					// Check if the movement entered by the human is one of the legal movements
					boolean humanMovementAmongLegalMovements = false;
					for ( int i = 0; i < legalMovements.size() && ( ! humanMovementAmongLegalMovements ); i++ ) {
						if (
							legalMovements.get( i ).isAMovementDefinedByAlgebraicNotationStartAndEndSquares(
								startSquareAlgebraicNotation,
								endSquareAlgebraicNotation,
								promotionChoice
							)
						) {
							humanMovementAmongLegalMovements = true;
							result = legalMovements.get( i );
						}
					}
					
					if ( result == null ) {
						System.out.println( "There was an error entering the movement. Please enter your movement again (e.g. e2e4): " );
					}
				}
				catch ( Exception e ) {
					System.out.println( "There was an error entering the movement. Please enter your movement again (e.g. e2e4): " );
				}
			}
		}
		
		return result;
	}

	public static Position initializePosition() {
		
		Position result = 
			new Position();
		
		result.getBoard().initialize();
		
		result.setTurn( true );
		result.setResult( Constants.STILL_PLAYING );
		
		result.setKingChecked( false );

		result.setHalfMoveClock( 0 );
		result.setFullMoveCounter( 1 );
		
		result.setWhiteShortCastleAllowed( true );
		result.setWhiteLongCastleAllowed( true );
		result.setBlackShortCastleAllowed( true );
		result.setBlackLongCastleAllowed( true );
		
		//
		// Static position evaluation
		//
		Engine.evaluatePositionVersionZero(
			result, 
			true
		);
		
		int initialPositionHeuristicValue =
			Constants.KING_VALUE
			+ Constants.QUEEN_VALUE
			+ 2 * Constants.ROOK_VALUE
			+ 2 * Constants.BISHOP_VALUE
			+ 2 * Constants.KNIGHT_VALUE
			+ 8 * Constants.PAWN_VALUE;
		
		result.setWhiteHeuristicValue( 
			initialPositionHeuristicValue
		);
		
		result.setBlackHeuristicValue( 
			initialPositionHeuristicValue
		);
		
		return result;
	}
	
}
