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
public class Pawn extends Piece {

	public Pawn(int colour, int squareId) {
		super( colour, squareId );
	}
	
	@Override
	public String toString() {
		
		String result = 
			( this.getColour() == Constants.WHITE_COLOUR ) 
			? Constants.WHITE_PAWN_VISUALIZE
			: Constants.BLACK_PAWN_VISUALIZE;

		return result;
	}
	
	@Override
	public int getHeuristicValue() {
		return Constants.PAWN_VALUE;
	}

	@Override
	public boolean isAttackingSquare(Square square, Board board) {
		
		//
		// A pawn attacks every diagonal adjacent square, in ist natural
		// advance direction (which depends of course on the colour of the pawn)
		//
		return board.areDiagonalAdjacentSquaresWithDirection( 
			this.getSquare(), 
			square,
			this.getColour()
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
		
		int pawnSquareId =
			this.getSquare().getId();
		
		int pawnColour = 
			this.getColour();
		
		int pawnColumn =
			pawnSquareId % 8;
		
		//
		// Generate enPassant capture movement, if it is possible
		//
		
		Square enPassantTarget =
			position.getEnPassantTarget();
		
		if ( enPassantTarget != null ) {
			
			int enPassantTargetSquareId =
				enPassantTarget.getId();
			
			int addValueLeftDiagonalAdvance =
				( pawnColour == Constants.WHITE_COLOUR )
				? 7
				: -9;
			
			int addValueRightDiagonalAdvance =
				( pawnColour == Constants.WHITE_COLOUR )
				? 9
				: -7;
			
			if (
				(
					pawnColumn > 0
					&& enPassantTargetSquareId == pawnSquareId + addValueLeftDiagonalAdvance
				)
				||
				(
					pawnColumn < 7
					&& enPassantTargetSquareId == pawnSquareId + addValueRightDiagonalAdvance
				)
			) {
				
				Movement enPassantMovement =
					position.getEnPassantMovement(
						this
					);
				
				//
				// We execute the movement, as it was possible to find an
				// enPassant
				//
				position.executeMovement(
					enPassantMovement
				);
				
				if ( ! position.isCheck( position.getTurn() ) ) {
					//
					// Ok, the movement is correct
					//
					enPassantMovement.setOrder( Constants.GENERIC_CAPTURE_ORDER ); // No additional operations. 2 pawns concerned
					
					if (
						principalVariationMovement != null
						&& enPassantMovement.isAMovementDefinedByStartAndEndSquares(
							( Square )( principalVariationMovement.getSquareStart() ),
							( Square )( principalVariationMovement.getSquareEnd() ),
							principalVariationMovement.getPromotionChoicePiece()
						)
					) {
						enPassantMovement.setOrder( Constants.PRINCIPAL_VARIATION_MOVEMENT_ORDER );
					}
					
					result.add( enPassantMovement );
				}
				
				//
				// Then we reverse the movement
				//
				position.reverseMovement(
					enPassantMovement
				);
				
			}
			
		}
		
		//
		// Normal pawn's movements
		//		
		ArrayList<Square> pawnMovementsEndSquares =
			board.getPawnMovementEndSquares(
				this.getSquare(),
				this.getColour()
			);
		
		//
		// We build each normal movement, we execute it, check 
		// if it is legal, and if so, we add it to the result.
		// Of course, every movement has to be reversed in the position
		//
		
		int rivalColour =
			( pawnColour == Constants.WHITE_COLOUR )
			? Constants.BLACK_COLOUR
			: Constants.WHITE_COLOUR;
		
		int promotionChoiceIndex = 0;
		
		for ( 
			Iterator<Square> iter = pawnMovementsEndSquares.iterator();
			iter.hasNext();
		) {
			Square square = iter.next();
			int squareId = square.getId();
			
			//
			// We build the movement
			//
			Movement movement = new Movement();
			
			movement.setSquareStart( new Square( pawnSquareId ) );
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
				//
				// Pawn doing a normal capture movement
				//
				movement.setCapture( true );
				
				movement.setCapturedPiece(
					board.getPieceByColourAndSquare(
						rivalColour,
						square
					)
				);
				
				movement.setOrder(
					Constants.GENERIC_CAPTURE_ORDER - movement.getCapturedPiece().getHeuristicValue() + this.getHeuristicValue()
				);
			}
			else {
				movement.setOrder( Constants.NON_CAPTURE_PAWN_MOVEMENT_ORDER );
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
			// Pawn reaching the 8th row, promoting and becoming 
			// a queen, rook, bishop or knight
			//
			
			int rowEndSquare =
				squareId / 8;
			
			if ( rowEndSquare == 0 || rowEndSquare == 7 ) {
				
				movement.setPromotedPawn( true );
				
				boolean isCapture = movement.isCapture();
				
				//
				// 4 choices are possible: 
				// - Pawn becoming a queen
				// - Pawn becoming a rook
				// - Pawn becoming a bishop
				// - Pawn becoming a knight
				//
					
				switch ( promotionChoiceIndex % 4 ) {
					case 0: 
						movement.setPromotionChoicePiece(
							new Queen( 
								pawnColour,
								squareId
							)
						);
						break;
					case 1:
						movement.setPromotionChoicePiece(
							new Rook( 
								pawnColour,
								squareId
							)
						);
						break;
					case 2: 
						movement.setPromotionChoicePiece(
							new Bishop( 
								pawnColour,
								squareId
							)
						);
						break;
					case 3:
						movement.setPromotionChoicePiece(
							new Knight( 
								pawnColour,
								squareId
							)
						);
						break;
				}
				
				if ( isCapture ) {
					movement.setOrder(
						Constants.CAPTURE_AND_PAWN_PROMOTION_ORDER - movement.getCapturedPiece().getHeuristicValue()
						- movement.getPromotionChoicePiece().getHeuristicValue()
					);
				}
				else {
					movement.setOrder(
						Constants.NON_CAPTURE_AND_PAWN_PROMOTION_ORDER - movement.getPromotionChoicePiece().getHeuristicValue()
					);
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
				// We add 1 to our promotionChoiceIndex, so that we choose another
				// promotion piece in next iterations
				//
				promotionChoiceIndex++;
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
		return "p";
	}

}
