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
public class Bishop extends Piece {

	public Bishop(boolean colour, int squareId) {
		super( colour, squareId );
	}
	
	@Override
	public String toString() {
		
		String result = 
			this.getColour() 
			? Constants.WHITE_BISHOP_VISUALIZE
			: Constants.BLACK_BISHOP_VISUALIZE;

		return result;
	}
	
	@Override
	public int getHeuristicValue( int fullMoveCounter ) {
		return
			( fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER ) ?
			Constants.BISHOP_VALUE : Constants.ENDINGS_BISHOP_VALUE;
	}
	
	@Override
	public boolean isAttackingSquareId(int squareId, Board board) {
		
		//
		// A bishop attacks every square in its diagonal, in 
		// every direction, beginning in itself, until it finds another piece. 
		// The bishop also attacks this piece square, but not others beyond it
		//
		
		//
		// So, the bishop attacks the square if both are in the same diagonal,
		// and the squares between them are empty
		//
		return
			board.emptyDiagonalBetweenSquaresIds(
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
		
		int bishopSquareId =
			this.getSquareId();
		
		int fullMoveCounter = position.getFullMoveCounter();
		
		//
		// Normal bishop's movements
		//		
		ArrayList<Integer> bishopMovementsEndSquares =
			board.getBishopMovementEndSquaresIds(
				this.getSquareId(),
				this.getColour()
			);
		
		//
		// We build each normal movement, we execute it, check 
		// if it is legal, and if so, we add it to the result.
		// Of course, every movement has to be reversed in the position
		//
		
		for ( 
			Iterator<Integer> iter = bishopMovementsEndSquares.iterator();
			iter.hasNext();
		) {
			int squareId = iter.next();
			
			//
			// We build the movement
			//
			Movement movement = new Movement();
			
			movement.setSquareStartId( bishopSquareId );
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
					board.getPieceBySquareId( squareId )
				);
				
				movement.setOrder(
					Constants.GENERIC_CAPTURE_ORDER - movement.getCapturedPiece().getHeuristicValue( fullMoveCounter )
					+ this.getHeuristicValue( fullMoveCounter )
				);
			}
			else {
				movement.setOrder( Constants.NON_CAPTURE_BISHOP_OR_KNIGHT_MOVEMENT_ORDER );
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
		return "b";
	}
}
