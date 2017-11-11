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
	public int getHeuristicValue() {
		return Constants.ROOK_VALUE;
	}
	
	@Override
	public boolean isAttackingSquare(Square square, Board board) {
		
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
			board.emptyRowOrColumnBetweenSquares(
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
		
		int rookSquareId =
			this.getSquare().getId();
		
		boolean rookMoved =
			this.isMoved();
		
		//
		// Normal rook's movements
		//		
		ArrayList<Square> rookMovementsEndSquares =
			board.getRookMovementEndSquares(
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
			Iterator<Square> iter = rookMovementsEndSquares.iterator();
			iter.hasNext();
		) {
			Square square = iter.next();
			
			//
			// We build the movement
			//
			Movement movement = new Movement();
			
			movement.setSquareStart( new Square( rookSquareId ) );
			movement.setSquareEnd( square );
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
				movement.setOrder( Constants.NON_CAPTURE_ROOK_MOVEMENT_ORDER );
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

	@Override
	public String toUCIString() {
		return "r";
	}
	
}
