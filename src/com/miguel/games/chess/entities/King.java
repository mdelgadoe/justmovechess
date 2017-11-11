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
	public int getHeuristicValue() {
		return Constants.KING_VALUE;
	}

	@Override
	public boolean isAttackingSquare(Square square, Board board) {
		
		//
		// A king attacks every adjacent square, in every direction
		//
		return board.areAdjacentSquares( 
			this.getSquare(),
			square
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
			this.getSquare().getId();
		
		boolean kingMoved =
			this.isMoved();
		
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
				&& movement.isAMovementDefinedByStartAndEndSquares(
					( Square )( principalVariationMovement.getSquareStart() ),
					( Square )( principalVariationMovement.getSquareEnd() ),
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
				&& movement.isAMovementDefinedByStartAndEndSquares(
					( Square )( principalVariationMovement.getSquareStart() ),
					( Square )( principalVariationMovement.getSquareEnd() ),
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
		ArrayList<Square> kingMovementsEndSquares =
			board.getKingMovementEndSquares(
				this.getSquare(),
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
			Iterator<Square> iter = kingMovementsEndSquares.iterator();
			iter.hasNext();
		) {
			Square square = iter.next();
			
			//
			// We build the movement
			//
			Movement movement = new Movement();
			
			movement.setSquareStart( new Square( kingSquareId ) );
			movement.setSquareEnd( square );
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
			
			if ( ! board.isFreeSquare( square ) ) {
				
				movement.setCapture( true );
				
				movement.setCapturedPiece(
					board.getPieceByColourAndSquare(
						rivalColour,
						square
					)
				);
				
				movement.setOrder(
					Constants.GENERIC_CAPTURE_ORDER - movement.getCapturedPiece().getHeuristicValue()
					+ this.getHeuristicValue()
				);
			}
			else {
				movement.setOrder( Constants.NON_CAPTURE_KING_MOVEMENT_ORDER );
			}
			
			if (
				principalVariationMovement != null
				&& movement.isAMovementDefinedByStartAndEndSquares(
					( Square )( principalVariationMovement.getSquareStart() ),
					( Square )( principalVariationMovement.getSquareEnd() ),
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
