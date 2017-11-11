/**
 * 
 */
package com.miguel.games.chess.entities;

import com.miguel.games.chess.utils.AlgebraicNotationUtils;

/**
 * @author mdelgado
 *
 */
public class Movement extends com.miguel.games.entities.Movement {

	//
	// capture is true if it is a normal capture 
	// movement, i.e., not included enPassant captures
	//
	private boolean capture;
	
	//
	// capturedPiece is the captured piece in every capture movement,
	// included enPassant captures
	//
	private Piece capturedPiece;
	
	//
	// enPassant is true if it is an enPassant capture movement
	// 
	private boolean enPassant;
	
	//
	// castle is true if it is a castling movement.
	// If castle is true, rookSquareStart and rookSquareEnd define
	// the movement for the implied rook, and castledRook is the
	// implied rook itself
	//
	private boolean castle;
	private Square rookSquareStart;
	private Square rookSquareEnd;
	private Rook castledRook;
	
	//
	// Former castle permissions (as they were before the movement is executed)
	//
	private boolean formerWhiteShortCastleAllowed;
	private boolean formerWhiteLongCastleAllowed;
	private boolean formerBlackShortCastleAllowed;
	private boolean formerBlackLongCastleAllowed;
	
	//
	// firstMovementForPossiblyInvolvedKingOrRook is true if the movement is the
	// first for the king or any or the 2 rooks
	//
	private boolean firstMovementForPossiblyInvolvedKingOrRook;
	
	//
	// inEnPassantSituationMovement is true if the movement (whatever movement) takes
	// place in an enPassant situation. If it is true, formerEnPassantTarget points
	// to the actual enPassantTarget
	//
	private boolean inEnPassantSituationMovement;
	private Square formerEnPassantTarget;
	
	//
	// promotedPawn is true if the movement is a pawn promotion. If that is the case,
	// promotionChoicePiece is the piece chosen to substitute the pawn after promotion
	//
	private boolean promotedPawn;
	private Piece promotionChoicePiece;
	
	//
	// formerHalfMoveClock is the halfMoveClock before the movement is executed
	// 
	private int formerHalfMoveClock;
	
	//
	// Value of the position reached with the movement, from the point of view of
	// the colour that is moving
	//
	private int value;
	
	//
	// Order to use when you want to sort a movement list
	//
	private int order;
	
	
	public void visualize() {
		
		Square squareStart =
			( Square )( this.getSquareStart() );
		
		Square squareEnd =
			( Square )( this.getSquareEnd() );
		
		System.out.println( 
			"Movement: " 
			+ this.getPiece().toString() 
			+ ". " 
			+ squareStart 
			+ " - " 
			+ squareEnd
			+ ". "
			+ AlgebraicNotationUtils.squareIdToCoordinates(
				squareStart.getId()
			)
			+ AlgebraicNotationUtils.squareIdToCoordinates(
				squareEnd.getId()
			)
		);
		
		if ( this.castle ) {
			System.out.println( 
				"0-0 or 0-0-0. Check above and below squares to decide by yourself." 
			);
			System.out.println( 
				"Rook: " + this.rookSquareStart + " - " + this.rookSquareEnd
			);
		}
		else if ( this.promotedPawn ) {
			System.out.println(
				"Promoted pawn ! = " + this.promotionChoicePiece.toString()
			);
		}
		else if ( this.enPassant ) {
			System.out.println( "EnPassant capture !" );
		}
	}
	
	/**
	 * This method returns a String which is the uci representation for the movement
	 * 
	 * @return
	 */
	public String toUCIString() {
		//
		// To represent a movement, uci just needs the start and end position ( algebraic extended notation, e.g. e2e4 )
		// Castles are represented just by the king movement ( e.g. e1g1 , for white short castle ) 
		// Promotions are represented adding the promotion piece choice ( e.g. e7e8q )
		//
		String result =
			AlgebraicNotationUtils.squareIdToCoordinates( ( ( Square )( this.getSquareStart() ) ).getId() )
			+ AlgebraicNotationUtils.squareIdToCoordinates( ( ( Square )( this.getSquareEnd() ) ).getId() );
		
		if ( this.promotedPawn ) {
			result = result + this.promotionChoicePiece.toUCIString();
		}
		
		return result;
	}

	public boolean isCapture() {
		return capture;
	}

	public void setCapture(boolean capture) {
		this.capture = capture;
	}

	public Piece getCapturedPiece() {
		return capturedPiece;
	}

	public void setCapturedPiece(Piece capturedPiece) {
		this.capturedPiece = capturedPiece;
	}

	public boolean isEnPassant() {
		return enPassant;
	}

	public void setEnPassant(boolean enPassant) {
		this.enPassant = enPassant;
	}

	public boolean isCastle() {
		return castle;
	}

	public void setCastle(boolean castle) {
		this.castle = castle;
	}

	public Square getRookSquareStart() {
		return rookSquareStart;
	}

	public void setRookSquareStart(Square rookSquareStart) {
		this.rookSquareStart = rookSquareStart;
	}

	public Square getRookSquareEnd() {
		return rookSquareEnd;
	}

	public void setRookSquareEnd(Square rookSquareEnd) {
		this.rookSquareEnd = rookSquareEnd;
	}

	public Rook getCastledRook() {
		return castledRook;
	}

	public void setCastledRook(Rook castledRook) {
		this.castledRook = castledRook;
	}

	public boolean isFirstMovementForPossiblyInvolvedKingOrRook() {
		return firstMovementForPossiblyInvolvedKingOrRook;
	}

	public void setFirstMovementForPossiblyInvolvedKingOrRook(
			boolean firstMovementForPossiblyInvolvedKingOrRook) {
		this.firstMovementForPossiblyInvolvedKingOrRook = firstMovementForPossiblyInvolvedKingOrRook;
	}

	public boolean isInEnPassantSituationMovement() {
		return inEnPassantSituationMovement;
	}

	public void setInEnPassantSituationMovement(boolean inEnPassantSituationMovement) {
		this.inEnPassantSituationMovement = inEnPassantSituationMovement;
	}

	public Square getFormerEnPassantTarget() {
		return formerEnPassantTarget;
	}

	public void setFormerEnPassantTarget(Square formerEnPassantTarget) {
		this.formerEnPassantTarget = formerEnPassantTarget;
	}

	public boolean isPromotedPawn() {
		return promotedPawn;
	}

	public void setPromotedPawn(boolean promotedPawn) {
		this.promotedPawn = promotedPawn;
	}

	public Piece getPromotionChoicePiece() {
		return promotionChoicePiece;
	}

	public void setPromotionChoicePiece(Piece promotionChoicePiece) {
		this.promotionChoicePiece = promotionChoicePiece;
	}

	public int getFormerHalfMoveClock() {
		return formerHalfMoveClock;
	}

	public void setFormerHalfMoveClock(int formerHalfMoveClock) {
		this.formerHalfMoveClock = formerHalfMoveClock;
	}

	public boolean isFormerWhiteShortCastleAllowed() {
		return formerWhiteShortCastleAllowed;
	}

	public void setFormerWhiteShortCastleAllowed(
			boolean formerWhiteShortCastleAllowed) {
		this.formerWhiteShortCastleAllowed = formerWhiteShortCastleAllowed;
	}

	public boolean isFormerWhiteLongCastleAllowed() {
		return formerWhiteLongCastleAllowed;
	}

	public void setFormerWhiteLongCastleAllowed(boolean formerWhiteLongCastleAllowed) {
		this.formerWhiteLongCastleAllowed = formerWhiteLongCastleAllowed;
	}

	public boolean isFormerBlackShortCastleAllowed() {
		return formerBlackShortCastleAllowed;
	}

	public void setFormerBlackShortCastleAllowed(
			boolean formerBlackShortCastleAllowed) {
		this.formerBlackShortCastleAllowed = formerBlackShortCastleAllowed;
	}

	public boolean isFormerBlackLongCastleAllowed() {
		return formerBlackLongCastleAllowed;
	}

	public void setFormerBlackLongCastleAllowed(boolean formerBlackLongCastleAllowed) {
		this.formerBlackLongCastleAllowed = formerBlackLongCastleAllowed;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isAMovementDefinedByAlgebraicNotationStartAndEndSquares(
		String startSquareAlgebraicNotation,
		String endSquareAlgebraicNotation,
		String promotionChoice
	) {
		boolean result = false;
		
		int startSquareId = AlgebraicNotationUtils.coordinatesToSquareId( startSquareAlgebraicNotation );
		int endSquareId = AlgebraicNotationUtils.coordinatesToSquareId( endSquareAlgebraicNotation );
		
		Square startSquare = ( Square )( this.getSquareStart() );
		Square endSquare = ( Square )( this.getSquareEnd() );
		
		if ( promotionChoice.length() == 0 ) {
			result = (
				startSquare.getId() == startSquareId
				&& endSquare.getId() == endSquareId
			);
		}
		else {
			result = (
				startSquare.getId() == startSquareId
				&& endSquare.getId() == endSquareId
				&& this.promotionChoicePiece.toUCIString().equals( promotionChoice )
			);
		}
		
		return result;
	}
	
	public boolean isAMovementDefinedByStartAndEndSquares(
		Square startSquare,
		Square endSquare,
		Piece promotionChoicePiece
	) {
		boolean result = false;
		
		if ( promotionChoicePiece == null ) {
			result = (
				startSquare.getId() == ( ( Square )( this.getSquareStart() ) ).getId()
				&& endSquare.getId() == ( ( Square )( this.getSquareEnd() ) ).getId()
			);
		}
		else if ( this.promotionChoicePiece != null ) {
			result = (
				startSquare.getId() == ( ( Square )( this.getSquareStart() ) ).getId()
				&& endSquare.getId() == ( ( Square )( this.getSquareEnd() ) ).getId()
				&& promotionChoicePiece.toUCIString().equals( this.promotionChoicePiece.toUCIString() )
			);
		}
		else {
			result = false;
		}
		
		return result;
	}
	
}
