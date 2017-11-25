/**
 * 
 */
package com.miguel.games.chess.entities;

import java.util.ArrayList;
import java.util.Iterator;

import com.miguel.games.chess.common.Constants;


/**
 * @author mdelgado
 *
 */
public class Queen extends Piece {

	public Queen(int colour, int squareId) {
		super( colour, squareId );
	}
	
	@Override
	public String toString() {
		
		String result = 
			( this.getColour() == Constants.WHITE_COLOUR ) 
			? Constants.WHITE_QUEEN_VISUALIZE
			: Constants.BLACK_QUEEN_VISUALIZE;

		return result;
	}
	
	@Override
	public int getHeuristicValue( int fullMoveCounter ) {
		return
			( fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER ) ?
			Constants.QUEEN_VALUE : Constants.ENDINGS_QUEEN_VALUE;
	}
	
	@Override
	public boolean isAttackingSquareId(int squareId, Board board) {
		
		//
		// A queen attacks every square in its row or column, and also in its diagonals, in 
		// every direction, beginning in itself, until it finds another piece. 
		// The queen also attacks this piece square, but not others beyond it
		//
		
		//
		// So, the queen attacks the square if both are in the same row, column or diagonal,
		// and the squares between them are empty
		//
		return
			board.emptyRowOrColumnBetweenSquaresIds(
				this.getSquareId(), 
				squareId
			)
			|| board.emptyDiagonalBetweenSquaresIds(
				this.getSquareId(),
				squareId
			);
		
	}

	@Override
	public ArrayList<Movement> getLegalMovements(
		Position position,
		Movement principalVariationMovement
	) {
		
		ArrayList<Movement> result =
			new ArrayList<Movement>();
		
		Board board =
			position.getBoard();
		
		int queenSquareId = this.getSquareId();
		
		int fullMoveCounter = position.getFullMoveCounter();
		
		//
		// Normal queen's movements: rook + bishop possible squares
		//	
		ArrayList<Integer> queenMovementsEndSquares =
			board.getRookMovementEndSquaresIds(
				this.getSquareId(),
				this.getColour()
			);
		
		ArrayList<Integer> bishopMovementsEndSquares =
			board.getBishopMovementEndSquaresIds(
				this.getSquareId(),
				this.getColour()
			);
		
		queenMovementsEndSquares.addAll(
			bishopMovementsEndSquares
		);
		
		int rivalColour =
			( this.getColour() == Constants.WHITE_COLOUR )
			? Constants.BLACK_COLOUR
			: Constants.WHITE_COLOUR;
		
		//
		// We build each normal movement, we execute it, check 
		// if it is legal, and if so, we add it to the result.
		// Of course, every movement has to be reversed in the position
		//
		
		for ( 
			Iterator<Integer> iter = queenMovementsEndSquares.iterator();
			iter.hasNext();
		) {
			int squareId = iter.next();
			
			//
			// We build the movement
			//
			Movement movement = new Movement();
			
			movement.setSquareStartId( queenSquareId );
			movement.setSquareEndId( squareId );
			movement.setPiece( this );
			movement.setFormerHalfMoveClock(
				position.getHalfMoveClock()
			);
			movement.setFormerWhiteShortCastleAllowed(
				position.isWhiteShortCastleAllowed()
			);
			movement.setFormerWhiteLongCastleAllowed(
				position.isWhiteLongCastleAllowed()
			);
			movement.setFormerBlackShortCastleAllowed(
				position.isBlackShortCastleAllowed()
			);
			movement.setFormerBlackLongCastleAllowed(
				position.isBlackLongCastleAllowed()
			);
			
			if ( ! board.isFreeSquareId( squareId ) ) {
				
				movement.setCapture( true );
				
				movement.setCapturedPiece(
					board.getPieceByColourAndSquareId(
						rivalColour,
						squareId
					)
				);
				
				movement.setOrder(
					Constants.GENERIC_CAPTURE_ORDER - movement.getCapturedPiece().getHeuristicValue( fullMoveCounter )
					+ this.getHeuristicValue( fullMoveCounter )
				);
			}
			else {
				movement.setOrder( Constants.NON_CAPTURE_QUEEN_MOVEMENT_ORDER );
			}
			
			if (
				principalVariationMovement != null
				&& movement.isAMovementDefinedByStartAndEndSquaresIds(
					principalVariationMovement.getSquareStartId(),
					principalVariationMovement.getSquareEndId(),
					principalVariationMovement.getPromotionChoicePiece()
				)
			) {
				movement.setOrder( Constants.PRINCIPAL_VARIATION_MOVEMENT_ORDER );
			}
			
			//
			// We execute the movement
			//
			position.executeMovement(
				movement
			);
			
			if ( ! position.isCheck( position.getTurn() ) ) {
				//
				// Ok, the movement is correct
				//
				result.add( movement );
			}
			
			//
			// Then we reverse the movement
			//
			position.reverseMovement(
				movement
			);
		}
		
		return result;
	}

	@Override
	public String toUCIString() {
		return "q";
	}
}
