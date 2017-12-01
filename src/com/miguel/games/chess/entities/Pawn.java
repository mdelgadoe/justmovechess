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

	public Pawn(boolean colour, int squareId) {
		super( colour, squareId );
	}
	
	@Override
	public String toString() {
		
		String result = 
			this.getColour() 
			? Constants.WHITE_PAWN_VISUALIZE
			: Constants.BLACK_PAWN_VISUALIZE;

		return result;
	}
	
	@Override
	public int getHeuristicValue( int fullMoveCounter ) {
		return
			( fullMoveCounter <= Constants.MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER ) ?
			Constants.PAWN_VALUE : Constants.ENDINGS_PAWN_VALUE;
	}

	@Override
	public boolean isAttackingSquareId(int squareId, Board board) {
		
		//
		// A pawn attacks every diagonal adjacent square, in ist natural
		// advance direction (which depends of course on the colour of the pawn)
		//
		return board.areDiagonalAdjacentSquaresIdsWithDirection( 
			this.getSquareId(), 
			squareId,
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
			this.getSquareId();
		
		boolean pawnColour = 
			this.getColour();
		
		int pawnColumn =
			pawnSquareId % 8;
		
		int fullMoveCounter = position.getFullMoveCounter();
		
		//
		// Generate enPassant capture movement, if it is possible
		//
		
		int enPassantTargetId =
			position.getEnPassantTargetId();
		
		if ( enPassantTargetId != 0 ) {
			
			int addValueLeftDiagonalAdvance = ( pawnColour ? 7 : -9 );
			
			int addValueRightDiagonalAdvance = ( pawnColour ? 9	: -7 );
			
			if (
				(
					pawnColumn > 0
					&& enPassantTargetId == pawnSquareId + addValueLeftDiagonalAdvance
				)
				||
				(
					pawnColumn < 7
					&& enPassantTargetId == pawnSquareId + addValueRightDiagonalAdvance
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
						&& enPassantMovement.isAMovementDefinedByStartAndEndSquaresIds(
							principalVariationMovement.getSquareStartId(),
							principalVariationMovement.getSquareEndId(),
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
		ArrayList<Integer> pawnMovementsEndSquares =
			board.getPawnMovementEndSquaresIds(
				this.getSquareId(),
				this.getColour()
			);
		
		//
		// We build each normal movement, we execute it, check 
		// if it is legal, and if so, we add it to the result.
		// Of course, every movement has to be reversed in the position
		//
		
		int promotionChoiceIndex = 0;
		
		for ( 
			Iterator<Integer> iter = pawnMovementsEndSquares.iterator();
			iter.hasNext();
		) {
			int squareId = iter.next();
			
			//
			// We build the movement
			//
			Movement movement = new Movement();
			
			movement.setSquareStartId( pawnSquareId );
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
				//
				// Pawn doing a normal capture movement
				//
				movement.setCapture( true );
				
				movement.setCapturedPiece(
					board.getPieceBySquareId( squareId )
				);
				
				movement.setOrder(
					Constants.GENERIC_CAPTURE_ORDER 
					- movement.getCapturedPiece().getHeuristicValue( fullMoveCounter ) 
					+ this.getHeuristicValue( fullMoveCounter )
				);
			}
			else {
				movement.setOrder( Constants.NON_CAPTURE_PAWN_MOVEMENT_ORDER );
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
						Constants.CAPTURE_AND_PAWN_PROMOTION_ORDER - movement.getCapturedPiece().getHeuristicValue( fullMoveCounter )
						- movement.getPromotionChoicePiece().getHeuristicValue( fullMoveCounter )
					);
				}
				else {
					movement.setOrder(
						Constants.NON_CAPTURE_AND_PAWN_PROMOTION_ORDER 
						- movement.getPromotionChoicePiece().getHeuristicValue( fullMoveCounter )
					);
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
