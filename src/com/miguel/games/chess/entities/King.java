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
public class King extends Piece {

	private boolean moved;
	private boolean castled;
	
	public King(int colour, int squareId) {
		super( colour, squareId );
	}

	@Override
	public String toString() {
		
		String result = 
			( this.getColour() == Constants.WHITE_COLOUR ) 
			? Constants.WHITE_KING_VISUALIZE
			: Constants.BLACK_KING_VISUALIZE;

		return result;
	}
	
	@Override
	public int getHeuristicValue( int fullMoveCounter ) {
		return Constants.KING_VALUE;  // King value does not change during the game
	}

	@Override
	public boolean isAttackingSquareId(int squareId, Board board) {
		
		//
		// A king attacks every adjacent square, in every direction
		//
		return board.areAdjacentSquaresIds( 
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
		
		int kingSquareId =
			this.getSquareId();
		
		boolean kingMoved =
			this.isMoved();
		
		int fullMoveCounter = position.getFullMoveCounter();
		
		//
		// Short castle movement
		//
		if ( position.isShortCastlePossible( this ) ) {
			Movement movement =
				position.getShortCastleMovement(
					this
				);
			 
			movement.setOrder( Constants.CASTLE_ORDER );
			 
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
			// We can just add the movement, without checking it for
			// check conditions, because the castling process already checked this
			//
			result.add( movement );
		}
		
		//
		// Long castle movement
		//
		if ( position.isLongCastlePossible( this ) ) {
			Movement movement =
				position.getLongCastleMovement(
					this
				);
			
			movement.setOrder( Constants.CASTLE_ORDER );
			
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
			// We can just add the movement, without checking it for
			// check conditions, because the castling process already checked
			// this
			//
			result.add( movement );
		}
		
		//
		// Normal king's movements
		//		
		ArrayList<Integer> kingMovementsEndSquares =
			board.getKingMovementEndSquaresIds(
				this.getSquareId(),
				this.getColour()
			);
		
		//
		// We build each normal movement, we execute it, check 
		// if it is legal, and if so, we add it to the result.
		// Of course, every movement has to be reversed in the position
		//
		
		for ( 
			Iterator<Integer> iter = kingMovementsEndSquares.iterator();
			iter.hasNext();
		) {
			int squareId = iter.next();
			
			//
			// We build the movement
			//
			Movement movement = new Movement();
			
			movement.setSquareStartId( kingSquareId );
			movement.setSquareEndId( squareId );
			movement.setPiece( this );
			movement.setFormerHalfMoveClock(
				position.getHalfMoveClock()
			);
			movement.setFirstMovementForPossiblyInvolvedKingOrRook(
				kingMoved ? false : true
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
				movement.setOrder( Constants.NON_CAPTURE_KING_MOVEMENT_ORDER );
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

	public boolean isCastled() {
		return castled;
	}

	public void setCastled(boolean castled) {
		this.castled = castled;
	}
	
	@Override
	public String toUCIString() {
		return "k";
	}
}
