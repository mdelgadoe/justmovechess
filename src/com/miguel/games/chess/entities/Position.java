/**
 * 
 */
package com.miguel.games.chess.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.miguel.games.chess.common.Constants;
import com.miguel.games.chess.utils.MovementsComparator;

/**
 * @author mdelgado
 *
 */
public class Position extends com.miguel.games.entities.Position {

	//
	// The chess board
	//
	private Board board = null;
	
	//
	// turn is the colour in turn
	//
	private int turn;
	
	//
	// result is the current result of the chess game
	//
	private int result;
	
	//
	// kingChecked will be true if the king in turn to move is currently being checked
	//
	private boolean kingChecked;
	
	//
	// Castle permissions. They define which castles are still allowed for the
	// two colours. They just mean what is described above. They do not mean that
	// it is possible to do any castle in the actual position. This has to be
	// checked before castling
	//
	private boolean whiteShortCastleAllowed;
	private boolean whiteLongCastleAllowed;
	private boolean blackShortCastleAllowed;
	private boolean blackLongCastleAllowed;
	
	//
	// enPassantTarget will contain a board square whenever a pawn is moved 2 squares ahead 
	// in its first movement. In such a case, enPassantTarget will be the end square for any
	// pawn (1 or 2) who is able to capture enPassant, i.e., the square behind the 2 squares ahead pawn
	//
	private int enPassantTargetId;
	
	//
	// halfMoveClock is the number of consecutive movements without a pawn moving or 
	// a capture (to comply with the rule of the 50 movements)
	//
	private int halfMoveClock;
	
	//
	// fullMoveCounter keeps track of the full turn that we are playing, i.e., it
	// starts from 1 and it is increased after each black movement
	//
	private int fullMoveCounter;
	
	//
	// white and black heuristic value from a static position evaluation
	//
	private int whiteHeuristicValue;
	private int blackHeuristicValue;
	
	//
	// A movement comparator. Just one, to save time 
	//
	private static MovementsComparator MOVEMENT_COMPARATOR = new MovementsComparator();
		
	public Position() {
		this.board = new Board();
	}
	
	public void visualize() {
		
		System.out.println( "Board" );
		System.out.println( "-----" );
		
		this.board.visualize();
		
		System.out.println( "turn: " + this.turn );
		System.out.println( "result: " + this.result );
		System.out.println( "kingChecked: " + this.kingChecked );
		System.out.println( "enPassantTargetId: " + this.enPassantTargetId );
		System.out.println( "halfMoveClock: " + this.halfMoveClock );
		System.out.println( "fullMoveCounter: " + this.fullMoveCounter );
		
		System.out.println( "whiteShortCastleAllowed: " + this.whiteShortCastleAllowed );
		System.out.println( "whiteLongCastleAllowed: " + this.whiteLongCastleAllowed );
		System.out.println( "blackShortCastleAllowed: " + this.blackShortCastleAllowed );
		System.out.println( "blackLongCastleAllowed: " + this.blackLongCastleAllowed );
		
		System.out.println( "whiteHeuristicValue: " + this.whiteHeuristicValue );
		System.out.println( "blackHeuristicValue: " + this.blackHeuristicValue );
		
	}
	
	public ArrayList<Movement> getLegalMovements(
		boolean sortMovementList,
		Movement principalVariationMovement
	) {
		
		ArrayList<Movement> result =
			new ArrayList<Movement>();
		
		//
		// We must generate all possible movements for the pieces of the colour
		// in turn, check that all of them are legal, and then return them 
		//
		
		if ( this.turn == Constants.WHITE_COLOUR ) {
			
			result =
				this.getLegalMovements(
					board.getWhitePieces(),
					sortMovementList,
					principalVariationMovement
				);
		}
		else {
			result =
				this.getLegalMovements(
					board.getBlackPieces(),
					sortMovementList,
					principalVariationMovement
				);
		}
		
		return result;
	}
	
	public ArrayList<Movement> getLegalMovements(
		ArrayList<Piece> colourInTurnPieces,
		boolean sortMovementList,
		Movement principalVariationMovement
	) {
		
		ArrayList<Movement> result =
			new ArrayList<Movement>();
		
		int sizeColourInTurnPieces =
			colourInTurnPieces.size();
		
		//
		// Iterate over the pieces to generate and collect together all their legal movements
		//
		for ( int i = 0; i < sizeColourInTurnPieces; i++ ) {
			result.addAll(
				colourInTurnPieces.get( i ).getLegalMovements( this, principalVariationMovement )
			);
		}
		
		if ( sortMovementList ) {
			//
			// If we receive a request to do so, we try to sort this movement list.
			// If we manage to put in the first positions of the ArrayList those movements that are
			// most likely to be better, searching for the best move with minimax-alfa-beta search will be faster
			//
			//
			// Java 8
			//
			// result.sort( new MovementsComparator() );
			//
			// Java 6
			//
			Collections.sort( result, MOVEMENT_COMPARATOR );
		}
		
		return result;
	}

	public Movement getShortCastleMovement(
		Piece kingPiece
	) {
		Movement result =
			new Movement();
		
		result.setCastle( true );
		result.setPiece( kingPiece );
		result.setFormerHalfMoveClock(
			this.halfMoveClock
		);
		result.setFirstMovementForPossiblyInvolvedKingOrRook(
			true
		);
		result.setFormerWhiteShortCastleAllowed(
			this.whiteShortCastleAllowed
		);
		result.setFormerWhiteLongCastleAllowed(
			this.whiteLongCastleAllowed
		);
		result.setFormerBlackShortCastleAllowed(
			this.blackShortCastleAllowed
		);
		result.setFormerBlackLongCastleAllowed(
			this.blackLongCastleAllowed
		);
		
		
		if ( this.turn == Constants.WHITE_COLOUR ) {
			
			result.setSquareStartId( 4 );
			result.setSquareEndId( 6 );
			result.setRookSquareStartId( 7 );
			result.setRookSquareEndId( 5 );
			result.setCastledRook(
				( Rook )
				( 
					this.board.getPieceByColourAndSquareId(
						Constants.WHITE_COLOUR, 
						7
					) 
				)
			);
		}
		else {
			result.setSquareStartId( 60 );
			result.setSquareEndId( 62 );
			result.setRookSquareStartId( 63 );
			result.setRookSquareEndId( 61 );
			result.setCastledRook(
				( Rook )
				(
					this.board.getPieceByColourAndSquareId(
						Constants.BLACK_COLOUR, 
						63
					)
				)
			);
		}
		
		return result;
	}
	
	public Movement getLongCastleMovement(
		Piece kingPiece
	) {
		Movement result =
			new Movement();
		
		result.setCastle( true );
		result.setPiece( kingPiece );
		result.setFormerHalfMoveClock(
			this.halfMoveClock
		);
		result.setFirstMovementForPossiblyInvolvedKingOrRook(
			true
		);
		result.setFormerWhiteShortCastleAllowed(
			this.whiteShortCastleAllowed
		);
		result.setFormerWhiteLongCastleAllowed(
			this.whiteLongCastleAllowed
		);
		result.setFormerBlackShortCastleAllowed(
			this.blackShortCastleAllowed
		);
		result.setFormerBlackLongCastleAllowed(
			this.blackLongCastleAllowed
		);
		
		if ( this.turn == Constants.WHITE_COLOUR ) {
			
			result.setSquareStartId( 4 );
			result.setSquareEndId( 2 );
			result.setRookSquareStartId( 0 );
			result.setRookSquareEndId( 3 );
			result.setCastledRook(
				( Rook )
				(
					this.board.getPieceByColourAndSquareId(
						Constants.WHITE_COLOUR, 
						0
					)
				)
			);
		}
		else {
			result.setSquareStartId( 60 );
			result.setSquareEndId( 58 );
			result.setRookSquareStartId( 56 );
			result.setRookSquareEndId( 59 );
			result.setCastledRook(
				( Rook )
				(
					this.board.getPieceByColourAndSquareId(
						Constants.BLACK_COLOUR, 
						56
					)
				)
			);
		}
		
		return result;
	}

	public boolean isShortCastlePossible(
		King king
	) {
		
		boolean result = false;
		
		if ( this.turn == Constants.WHITE_COLOUR ) {
			
			ArrayList<Piece> blackPieces =
				this.board.getBlackPieces();
			
			if ( 
				this.whiteShortCastleAllowed
				&& ! king.isCastled()
				&& ! king.isMoved() 
			) {
				Piece tentativeRook =
					this.board.getPieceByColourAndSquareId( king.getColour(), 7 );
				
				if (
					tentativeRook != null
					&& tentativeRook instanceof Rook
				) {
					Rook rook =
						( Rook )tentativeRook;
					
					result =
						! rook.isMoved()
						&& this.board.isFreeSquareId( 5 )
						&& this.board.isFreeSquareId( 6 )
						&& ! this.isCheck( this.turn )
						&& ! this.isAtLeastOnePieceAttackingSquareId( blackPieces, 5 )
						&& ! this.isAtLeastOnePieceAttackingSquareId(	blackPieces, 6 );
				}
			}
		}
		else {
			
			ArrayList<Piece> whitePieces =
				this.board.getWhitePieces();
			
			if ( 
				this.blackShortCastleAllowed
				&& ! king.isCastled()
				&& ! king.isMoved() 
			) {
				Piece tentativeRook =
					this.board.getPieceByColourAndSquareId( king.getColour(), 63 );
				
				if (
					tentativeRook != null
					&& tentativeRook instanceof Rook
				) {
					Rook rook =
						( Rook )tentativeRook;
					
					result =
						! rook.isMoved()
						&& this.board.isFreeSquareId( 61 )
						&& this.board.isFreeSquareId( 62 )
						&& ! this.isCheck( this.turn )
						&& ! this.isAtLeastOnePieceAttackingSquareId( whitePieces, 61 ) 
						&& ! this.isAtLeastOnePieceAttackingSquareId( whitePieces, 62 );
				}
			}
		}
		
		return result;
		
	}
	
	public boolean isLongCastlePossible(
		King king
	) {
		
		boolean result = false;
		
		if ( this.turn == Constants.WHITE_COLOUR ) {
			
			ArrayList<Piece> blackPieces =
				this.board.getBlackPieces();
			
			if ( 
				this.whiteLongCastleAllowed
				&& ! king.isCastled()
				&& ! king.isMoved() 
			) {
				Piece tentativeRook =
					this.board.getPieceByColourAndSquareId( king.getColour(), 0 );
				
				if (
					tentativeRook != null
					&& tentativeRook instanceof Rook
				) {
					Rook rook =
						( Rook )tentativeRook;
					
					result =
						! rook.isMoved()
						&& this.board.isFreeSquareId( 1 )
						&& this.board.isFreeSquareId( 2 )
						&& this.board.isFreeSquareId( 3 )
						&& ! this.isCheck( this.turn )
						&& ! this.isAtLeastOnePieceAttackingSquareId( blackPieces, 2 ) 
						&& ! this.isAtLeastOnePieceAttackingSquareId( blackPieces, 3 );
				}
			}
		}
		else {
			
			ArrayList<Piece> whitePieces =
				this.board.getWhitePieces();
			
			if ( 
				this.blackLongCastleAllowed
				&& ! king.isCastled()
				&& ! king.isMoved() 
			) {
				Piece tentativeRook =
					this.board.getPieceByColourAndSquareId( king.getColour(), 56 );
				
				if (
					tentativeRook != null
					&& tentativeRook instanceof Rook
				) {
					Rook rook =
						( Rook )tentativeRook;
					
					result =
						! rook.isMoved()
						&& this.board.isFreeSquareId( 57 )
						&& this.board.isFreeSquareId( 58 )
						&& this.board.isFreeSquareId( 59 )
						&& ! this.isCheck( this.turn )
						&& ! this.isAtLeastOnePieceAttackingSquareId( whitePieces, 58 ) 
						&& ! this.isAtLeastOnePieceAttackingSquareId( whitePieces, 59 );
				}
			}
		}
		
		return result;
		
	}

	public boolean isCheck(
		int kingColour
	) {
		boolean result = false;
		
		Board board = this.getBoard();
		
		if ( kingColour == Constants.WHITE_COLOUR ) {
			//
			// We check if at least one of the black pieces currently attacks
			// the white king square
			//
			result =
				this.isAtLeastOnePieceAttackingSquareId(
					board.getBlackPieces(),
					board.getKing( Constants.WHITE_COLOUR ).getSquareId()
				);
		}
		else {
			//
			// We check if at least one of the white pieces currently attacks
			// the black king square
			//
			result =
				this.isAtLeastOnePieceAttackingSquareId(
					board.getWhitePieces(),
					board.getKing( Constants.BLACK_COLOUR ).getSquareId()
				);
		}
				
		return result;
	}
	
	private boolean isAtLeastOnePieceAttackingSquareId(
		ArrayList<Piece> pieces, 
		int squareId
	) {
		boolean result = false;
		int sizePieces = pieces.size();
		
		for ( int i = 0; ! result && i < sizePieces; i++ ) {
			result = pieces.get( i ).isAttackingSquareId( squareId, board );
		}
		
		return result;
	}
	
	public int changeTurn() {
		
		//
		// Changes turn and return the new colour in turn
		//
		if ( this.turn == Constants.WHITE_COLOUR ) {
			this.turn = Constants.BLACK_COLOUR;
		}
		else {
			this.turn = Constants.WHITE_COLOUR;
		}
		
		return this.turn;
	}
	
	public void executeMovement(
		Movement movement
	) {
		int squareStartId = movement.getSquareStartId();
		
		int squareEndId = movement.getSquareEndId();
		
		HashMap<Integer, Boolean> freeSquares = 
			this.board.getFreeSquares();
		
		Piece piece =
			( Piece )movement.getPiece();
		
		int pieceColour =
			piece.getColour();
		
		//
		// We mark the movement with the present enPassant, to be able to
		// recover it when reversing the movement
		//
		movement.setInEnPassantSituationMovement(
			( this.enPassantTargetId != 0 )
			? true
			: false
		);
		
		movement.setFormerEnPassantTargetId(
			this.enPassantTargetId
		);
		
		//
		// By default, we set enPassantTarget to 0 value. 
		// This can of course change later in the method
		//
		this.enPassantTargetId = 0;
		
		if ( movement.isFirstMovementForPossiblyInvolvedKingOrRook() ) {
			if ( piece instanceof King ) {
				( ( King )piece ).setMoved( true );
			}
			else if ( piece instanceof Rook ) {
				( ( Rook )piece ).setMoved( true );
			}
		}
		
		if ( piece instanceof Pawn ) {
			
			this.halfMoveClock = 0;
			
			//
			// set the enPassant option if that is the case
			//
			if ( Math.abs( squareEndId / 8 - squareStartId / 8 ) > 1 ) {
				
				//
				// A pawn is advancing 2 squares in its first movement. That is
				// enough to set the enPassantTarget square
				//
				this.enPassantTargetId =
					( pieceColour == Constants.WHITE_COLOUR )
					? ( squareEndId - 8 )
					:( squareEndId + 8 );
				
			}
			
			//
			// We work now around the pawn promoted situation
			//
			
			if ( movement.isPromotedPawn() ) {
				
				//
				// We delete the promoted pawn
				//
				this.board.removePiece(
					piece
				);
				
				//
				// And we add the new piece 
				//
				this.board.addPiece(
					movement.getPromotionChoicePiece()
				);
			}
			
		}
		else {
			//
			// By default, if it is not a pawn, we increase our half move counter, 
			// though later in the method, if is a capture, it would be set to 0
			//
			this.halfMoveClock++;
		}
		
		freeSquares.put( squareStartId, true );
		freeSquares.remove( squareEndId );
		
		piece.setSquareId( squareEndId );
		
		if ( movement.isCastle() ) {
			
			int rookSquareStartId =
				movement.getRookSquareStartId();
			
			int rookSquareEndId =
				movement.getRookSquareEndId();
			
			freeSquares.put( rookSquareStartId, true );
			
			Rook castledRook = 
				movement.getCastledRook();
			
			castledRook.setSquareId( rookSquareEndId );
			castledRook.setMoved( true );
			
			freeSquares.remove( rookSquareEndId );
			
			King king =	
				( King )piece;
			
			king.setCastled( true );
			
		}
		else if ( movement.isCapture() ) {
			//
			// We have to delete the captured piece
			//
			this.board.removePiece(
				movement.getCapturedPiece()
			);
			
			this.halfMoveClock = 0;
		}
		else if ( movement.isEnPassant() ) {
			
			//
			// We make the captured pawn square free
			//
			Piece capturedPiece = 
				movement.getCapturedPiece();
			
			freeSquares.put( capturedPiece.getSquareId(), true );
			
			//
			// And we have to delete the captured piece
			//
			this.board.removePiece(
				capturedPiece
			);
		}
		
		//
		// If black colour has moved, we have to increase our full move counter
		//
		if ( pieceColour == Constants.BLACK_COLOUR ) {
			
			this.fullMoveCounter++;
		}
		
		//
		// And finally we check the global situation (both colours, as the movement 
		// could be something like a rook captured, etc. ) after the movement, 
		// regarding the castle permissions.
		//
		if ( this.whiteShortCastleAllowed ) {
			
			King whiteKing =
				this.board.getKing(
					Constants.WHITE_COLOUR
				);
			
			Piece tentativeRook =
				this.board.getPieceByColourAndSquareId( 
					Constants.WHITE_COLOUR, 
					7
				);
			
			if (
				tentativeRook != null
				&& tentativeRook instanceof Rook
			) {
				
				Rook rook =
					( Rook )tentativeRook;
				
				this.whiteShortCastleAllowed =
					! whiteKing.isMoved()
					&& ! rook.isMoved();
			}
			else {
				this.whiteShortCastleAllowed = false;
			}
		}
		
		if ( this.whiteLongCastleAllowed ) {
			
			King whiteKing =
				this.board.getKing(
					Constants.WHITE_COLOUR
				);
			
			Piece tentativeRook =
				this.board.getPieceByColourAndSquareId( 
					Constants.WHITE_COLOUR, 
					0
				);
			
			if (
				tentativeRook != null
				&& tentativeRook instanceof Rook
			) {
				
				Rook rook =
					( Rook )tentativeRook;
				
				this.whiteLongCastleAllowed =
					! whiteKing.isMoved()
					&& ! rook.isMoved();
			}
			else {
				this.whiteLongCastleAllowed = false;
			}
		}
		
		if ( this.blackShortCastleAllowed ) {
			
			King blackKing =
				this.board.getKing(
					Constants.BLACK_COLOUR
				);
			
			Piece tentativeRook =
				this.board.getPieceByColourAndSquareId( 
					Constants.BLACK_COLOUR, 
					63
				);
			
			if (
				tentativeRook != null
				&& tentativeRook instanceof Rook
			) {
				
				Rook rook =
					( Rook )tentativeRook;
				
				this.blackShortCastleAllowed =
					! blackKing.isMoved()
					&& ! rook.isMoved();
			}
			else {
				this.blackShortCastleAllowed = false;
			}
		}
		
		if ( this.blackLongCastleAllowed ) {
			
			King blackKing =
				this.board.getKing(
					Constants.BLACK_COLOUR
				);
			
			Piece tentativeRook =
				this.board.getPieceByColourAndSquareId( 
					Constants.BLACK_COLOUR, 
					56
				);
			
			if (
				tentativeRook != null
				&& tentativeRook instanceof Rook
			) {
				
				Rook rook =
					( Rook )tentativeRook;
				
				this.blackLongCastleAllowed =
					! blackKing.isMoved()
					&& ! rook.isMoved();
			}
			else {
				this.blackLongCastleAllowed = false;
			}
		}
		
	}
	
	public void reverseMovement(
		Movement movement
	) {
		int squareStartId = movement.getSquareStartId();
		
		int squareEndId = movement.getSquareEndId();
		
		HashMap<Integer, Boolean> freeSquares = 
			this.board.getFreeSquares();
		
		Piece piece =
			( Piece )movement.getPiece();
		
		int pieceColour =
			piece.getColour();
		
		//
		// When reversing the movement, we recover the former 
		// enPassantTarget, whatever it was
		//
		this.enPassantTargetId =
			movement.getFormerEnPassantTargetId();
		
		this.halfMoveClock = 
			movement.getFormerHalfMoveClock();
		
		//
		// We always recover the former castle permissions
		//
		this.whiteShortCastleAllowed =
			movement.isFormerWhiteShortCastleAllowed();
		this.whiteLongCastleAllowed =
			movement.isFormerWhiteLongCastleAllowed();
		this.blackShortCastleAllowed =
			movement.isFormerBlackShortCastleAllowed();
		this.blackLongCastleAllowed =
			movement.isFormerBlackLongCastleAllowed();
		
		if ( movement.isFirstMovementForPossiblyInvolvedKingOrRook() ) {
			if ( piece instanceof King ) {
				( ( King )piece ).setMoved( false );
			}
			else if ( piece instanceof Rook ) {
				( ( Rook )piece ).setMoved( false ); 
			}
		}
		
		freeSquares.remove( squareStartId );
		
		piece.setSquareId( squareStartId );
		
		freeSquares.put( squareEndId, true );
		
		if ( movement.isCastle() ) {
			
			int rookStartSquareId = 
				movement.getRookSquareStartId();
			
			freeSquares.remove( rookStartSquareId );
			freeSquares.put( movement.getRookSquareEndId(), true );
			
			Rook castledRook = movement.getCastledRook();
			
			castledRook.setSquareId( rookStartSquareId );
			castledRook.setMoved( false );
			
			( ( King )piece ).setCastled( false );
		}
		else if ( movement.isCapture() ) {
			//
			// We have to add the former captured piece
			//
			this.board.addPiece(
				movement.getCapturedPiece()
			);
			
			freeSquares.remove( squareEndId );
			
		}
		else if ( movement.isEnPassant() ) {
			
			//
			// We mark as not free the former captured pawn square
			//
			Piece capturedPiece = 
				movement.getCapturedPiece();
			
			freeSquares.remove( capturedPiece.getSquareId() );
			
			//
			// And we have to add the former captured piece
			//
			this.board.addPiece( capturedPiece );
		}
		
		//
		// Of course promotion is something possible, and can happen with or without capture 
		//
		if ( movement.isPromotedPawn() ) {
			
			if ( ! movement.isCapture() ) {
				//
				// We mark as free the end square
				//
				freeSquares.put( squareEndId, true );
			}
			
			//
			// We add the former promoted and disappeared pawn. For this,
			// we use the method that lets us choose the place in the ArrayList 
			// to add the piece. 1 is an index thats always avoids this pawn's
			// movements to be generated again as we walk through the pieces
			//
			this.board.addPiece( piece, 1 );
			
			//
			// And we delete the promotion choice piece 
			//
			this.board.removePiece(
				movement.getPromotionChoicePiece()
			);
		}
		
		//
		// If black colour had moved, we have to decrease our full move counter
		//
		if ( pieceColour == Constants.BLACK_COLOUR ) {
			this.fullMoveCounter--;
		}
		
	}
	
	public boolean areTwoRooksConnectedOnTheFirstRow( int colour ) {
		
		boolean result = false; // By default. It may change below, of course
		
		ArrayList<Rook> rooks = this.board.getRooks( colour );
		
		int firstRow = ( colour == Constants.WHITE_COLOUR ) ? 0 : 7; 
		
		// There has to be two rooks, and they have to be connected on the first row
		result = 
			rooks.size() == 2
			&& this.board.isPieceOnTheIesimRow( rooks.get( 0 ), firstRow )
			&& this.board.isPieceOnTheIesimRow( rooks.get( 1 ), firstRow )
			&& this.board.emptyRowOrColumnBetweenSquaresIds(
				rooks.get( 0 ).getSquareId(),
				rooks.get( 1 ).getSquareId()
			);
		
		return result;
	}
	
	public Movement getEnPassantMovement(
		Pawn pawn
	) {
		//
		// This method is only called if the pawn has a real chance to
		// capture enPassant. The only thing to discover is in which
		// capture direction it is
		//
		int pawnSquareId = pawn.getSquareId();
		
		int pawnColour = pawn.getColour();
		
		int enPassantTargetSquareId = this.getEnPassantTargetId();
		
		int capturedPieceSquareId =
			( pawnColour == Constants.WHITE_COLOUR )
			? enPassantTargetSquareId - 8
			: enPassantTargetSquareId + 8;
		
		int rivalPawnsColour =
			( pawnColour == Constants.WHITE_COLOUR )
			? Constants.BLACK_COLOUR
			: Constants.WHITE_COLOUR;
		
		Movement result = 
			new Movement();
		
		result.setEnPassant( true );
		result.setSquareStartId( pawnSquareId );
		result.setSquareEndId( enPassantTargetSquareId );
		result.setPiece( pawn );
		result.setCapturedPiece(
			this.board.getPieceByColourAndSquareId(
				rivalPawnsColour, 
				capturedPieceSquareId
			)
		);
		result.setFormerHalfMoveClock(
			this.halfMoveClock
		);
		result.setInEnPassantSituationMovement(
			true
		);
		result.setFormerEnPassantTargetId(
			this.enPassantTargetId
		);
		result.setFormerWhiteShortCastleAllowed(
			this.whiteShortCastleAllowed
		);
		result.setFormerWhiteLongCastleAllowed(
			this.whiteLongCastleAllowed
		);
		result.setFormerBlackShortCastleAllowed(
			this.blackShortCastleAllowed
		);
		result.setFormerBlackLongCastleAllowed(
			this.blackLongCastleAllowed
		);
			
		return result;
	}
	
	public boolean isADrawCase() {
		
		//
		// There are several situations in a chess game where the result is a draw, maybe
		// because the two kings are alone on the board, or the advantage of one of the colours
		// is not enough to win
		//
		
		//
		// TODO: Add here checks for other technical draw situations (king and bishop vs king, etc)
		//
		
		//
		// TODO: detect here draw due to a position being three times repeated in a game (threefold repetition)
		//
		
		boolean result = false;
		
		//
		//
		// King vs king
		
		ArrayList<Piece> whitePieces =
			this.board.getWhitePieces();
		
		ArrayList<Piece> blackPieces =
			this.board.getBlackPieces();
		
		if ( 
			whitePieces.size() == 1
			&& blackPieces.size() == 1
		) {
			result = true;
		}
		
		return result;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public boolean isKingChecked() {
		return kingChecked;
	}

	public void setKingChecked(boolean kingChecked) {
		this.kingChecked = kingChecked;
	}

	public int getEnPassantTargetId() {
		return enPassantTargetId;
	}

	public void setEnPassantTargetId(int enPassantTargetId) {
		this.enPassantTargetId = enPassantTargetId;
	}

	public int getHalfMoveClock() {
		return halfMoveClock;
	}

	public void setHalfMoveClock(int halfMoveClock) {
		this.halfMoveClock = halfMoveClock;
	}

	public int getFullMoveCounter() {
		return fullMoveCounter;
	}

	public void setFullMoveCounter(int fullMoveCounter) {
		this.fullMoveCounter = fullMoveCounter;
	}

	public int getWhiteHeuristicValue() {
		return whiteHeuristicValue;
	}

	public void setWhiteHeuristicValue(int whiteHeuristicValue) {
		this.whiteHeuristicValue = whiteHeuristicValue;
	}

	public int getBlackHeuristicValue() {
		return blackHeuristicValue;
	}

	public void setBlackHeuristicValue(int blackHeuristicValue) {
		this.blackHeuristicValue = blackHeuristicValue;
	}

	public boolean isWhiteShortCastleAllowed() {
		return whiteShortCastleAllowed;
	}

	public void setWhiteShortCastleAllowed(boolean whiteShortCastleAllowed) {
		this.whiteShortCastleAllowed = whiteShortCastleAllowed;
	}

	public boolean isWhiteLongCastleAllowed() {
		return whiteLongCastleAllowed;
	}

	public void setWhiteLongCastleAllowed(boolean whiteLongCastleAllowed) {
		this.whiteLongCastleAllowed = whiteLongCastleAllowed;
	}

	public boolean isBlackShortCastleAllowed() {
		return blackShortCastleAllowed;
	}

	public void setBlackShortCastleAllowed(boolean blackShortCastleAllowed) {
		this.blackShortCastleAllowed = blackShortCastleAllowed;
	}

	public boolean isBlackLongCastleAllowed() {
		return blackLongCastleAllowed;
	}

	public void setBlackLongCastleAllowed(boolean blackLongCastleAllowed) {
		this.blackLongCastleAllowed = blackLongCastleAllowed;
	}
	
}
