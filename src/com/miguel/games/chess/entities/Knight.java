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
public class Knight extends Piece {

	public Knight(int colour, int squareId) {
		super( colour, squareId );
	}
	
	@Override
	public String toString() {
		
		String result = 
			( this.getColour() == Constants.WHITE_COLOUR ) 
			? Constants.WHITE_KNIGHT_VISUALIZE
			: Constants.BLACK_KNIGHT_VISUALIZE;

		return result;
	}
	
	@Override
	public int getHeuristicValue() {
		return Constants.KNIGHT_VALUE;
	}
	
	@Override
	public boolean isAttackingSquare(Square square, Board board) {
		
		//
		// A knight attacks every square reachable with an L-movement, in 
		// every direction, beginning in itself, jumping over other pieces
		// if necessary
		//
		
		return
			board.existsLMovementBetweenSquares(
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
		
		int knightSquareId =
			this.getSquare().getId();
		
		//
		// Normal knight's movements
		//		
		ArrayList<Square> knightMovementsEndSquares =
			board.getKnightMovementEndSquares(
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
			Iterator<Square> iter = knightMovementsEndSquares.iterator();
			iter.hasNext();
		) {
			Square square = iter.next();
			
			//
			// We build the movement
			//
			Movement movement = new Movement();
			
			movement.setSquareStart( new Square( knightSquareId ) );
			movement.setSquareEnd( square );
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
				movement.setOrder( Constants.NON_CAPTURE_BISHOP_OR_KNIGHT_MOVEMENT_ORDER );
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
	
	@Override
	public String toUCIString() {
		return "n";
	}
}
