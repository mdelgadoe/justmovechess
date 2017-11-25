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
public class Rook extends Piece {

	private boolean moved;
	
	public Rook(int colour, int squareId) {
		super( colour, squareId );
	}
	
	@Override
	public String toString() {
		
		String result = 
			( this.getColour() == Constants.WHITE_COLOUR ) 
			? Constants.WHITE_ROOK_VISUALIZE
			: Constants.BLACK_ROOK_VISUALIZE;

		return result;
	}
	
	@Override
	public int getHeuristicValue( int fullMoveCounter ) {
		return
			( fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER ) ?
			Constants.ROOK_VALUE : Constants.ENDINGS_ROOK_VALUE;
	}
	
	@Override
	public boolean isAttackingSquareId(int squareId, Board board) {
		
		//
		// A rook attacks every square in its same row and column, in 
		// every direction, beginning in itself, until it finds another piece. 
		// The rook also attacks this piece square, but not others beyond it
		//
		
		//
		// So, the rook attacks the square if both are in the same row or column,
		// and the squares between them are empty
		//
		return
			board.emptyRowOrColumnBetweenSquaresIds(
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
		
		int rookSquareId =
			this.getSquareId();
		
		boolean rookMoved =
			this.isMoved();
		
		int fullMoveCounter = position.getFullMoveCounter();
		
		//
		// Normal rook's movements
		//		
		ArrayList<Integer> rookMovementsEndSquares =
			board.getRookMovementEndSquaresIds(
				this.getSquareId(),
				this.getColour()
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
			Iterator<Integer> iter = rookMovementsEndSquares.iterator();
			iter.hasNext();
		) {
			int squareId = iter.next();
			
			//
			// We build the movement
			//
			Movement movement = new Movement();
			
			movement.setSquareStartId( rookSquareId );
			movement.setSquareEndId( squareId );
			movement.setPiece( this );
			movement.setFormerHalfMoveClock(
				position.getHalfMoveClock()
			);
			movement.setFirstMovementForPossiblyInvolvedKingOrRook(
				rookMoved ? false : true
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
				movement.setOrder( Constants.NON_CAPTURE_ROOK_MOVEMENT_ORDER );
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

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	@Override
	public String toUCIString() {
		return "r";
	}
	
}
