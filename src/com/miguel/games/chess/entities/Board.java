/**
 * 
 */
package com.miguel.games.chess.entities;

import java.util.ArrayList;
import java.util.HashMap;

import com.miguel.games.chess.common.Constants;

/**
 * @author mdelgado
 *
 */
public class Board extends com.miguel.games.entities.Board {

	private ArrayList<Piece> whitePieces;
	private ArrayList<Piece> blackPieces;
	
	private HashMap<Integer, Boolean> freeSquares;
	
	//
	// Central squares. It is considered important to occupy or attack them in the opening
	//
	private static final Square squareD4 = new Square( 27 );
	private static final Square squareE4 = new Square( 28 );
	private static final Square squareD5 = new Square( 35 );
	private static final Square squareE5 = new Square( 36 );
	
	public void initialize() {
		
		//
		// Free squares
		// 
		
		freeSquares =
			new HashMap<Integer, Boolean>();
		
		freeSquares.put( 16, true );
		freeSquares.put( 17, true );
		freeSquares.put( 18, true );
		freeSquares.put( 19, true );
		freeSquares.put( 20, true );
		freeSquares.put( 21, true );
		freeSquares.put( 22, true );
		freeSquares.put( 23, true );
		
		freeSquares.put( 24, true );
		freeSquares.put( 25, true );
		freeSquares.put( 26, true );
		freeSquares.put( 27, true );
		freeSquares.put( 28, true );
		freeSquares.put( 29, true );
		freeSquares.put( 30, true );
		freeSquares.put( 31, true );
		
		freeSquares.put( 32, true );
		freeSquares.put( 33, true );
		freeSquares.put( 34, true );
		freeSquares.put( 35, true );
		freeSquares.put( 36, true );
		freeSquares.put( 37, true );
		freeSquares.put( 38, true );
		freeSquares.put( 39, true );
		
		freeSquares.put( 40, true );
		freeSquares.put( 41, true );
		freeSquares.put( 42, true );
		freeSquares.put( 43, true );
		freeSquares.put( 44, true );
		freeSquares.put( 45, true );
		freeSquares.put( 46, true );
		freeSquares.put( 47, true );
		
		//
		// White pieces
		//
		
		whitePieces =
			new ArrayList<Piece>();
		
		whitePieces.add( 0, new King( Constants.WHITE_COLOUR, 4 ) );
		
		whitePieces.add( 1, new Pawn( Constants.WHITE_COLOUR, 8 ) );
		whitePieces.add( 2, new Pawn( Constants.WHITE_COLOUR, 9 ) );
		whitePieces.add( 3, new Pawn( Constants.WHITE_COLOUR, 10 ) );
		whitePieces.add( 4, new Pawn( Constants.WHITE_COLOUR, 11 ) );
		whitePieces.add( 5, new Pawn( Constants.WHITE_COLOUR, 12 ) );
		whitePieces.add( 6, new Pawn( Constants.WHITE_COLOUR, 13 ) );
		whitePieces.add( 7, new Pawn( Constants.WHITE_COLOUR, 14 ) );
		whitePieces.add( 8, new Pawn( Constants.WHITE_COLOUR, 15 ) );
		
		whitePieces.add( 9, new Knight( Constants.WHITE_COLOUR, 1 ) );
		whitePieces.add( 10, new Knight( Constants.WHITE_COLOUR, 6 ) );
		
		whitePieces.add( 11, new Bishop( Constants.WHITE_COLOUR, 2 ) );
		whitePieces.add( 12, new Bishop( Constants.WHITE_COLOUR, 5 ) );
		
		whitePieces.add( 13, new Rook( Constants.WHITE_COLOUR, 0 ) );
		whitePieces.add( 14, new Rook( Constants.WHITE_COLOUR, 7 ) );
		
		whitePieces.add( 15, new Queen( Constants.WHITE_COLOUR, 3 ) );
		
		//
		// Black pieces
		//
		
		blackPieces =
			new ArrayList<Piece>();
		
		blackPieces.add( 0, new King( Constants.BLACK_COLOUR, 60 ) );
		
		blackPieces.add( 1, new Pawn( Constants.BLACK_COLOUR, 48 ) );
		blackPieces.add( 2, new Pawn( Constants.BLACK_COLOUR, 49 ) );
		blackPieces.add( 3, new Pawn( Constants.BLACK_COLOUR, 50 ) );
		blackPieces.add( 4, new Pawn( Constants.BLACK_COLOUR, 51 ) );
		blackPieces.add( 5, new Pawn( Constants.BLACK_COLOUR, 52 ) );
		blackPieces.add( 6, new Pawn( Constants.BLACK_COLOUR, 53 ) );
		blackPieces.add( 7, new Pawn( Constants.BLACK_COLOUR, 54 ) );
		blackPieces.add( 8, new Pawn( Constants.BLACK_COLOUR, 55 ) );
		
		blackPieces.add( 9, new Knight( Constants.BLACK_COLOUR, 57 ) );
		blackPieces.add( 10, new Knight( Constants.BLACK_COLOUR, 62 ) );
		
		blackPieces.add( 11, new Bishop( Constants.BLACK_COLOUR, 58 ) );
		blackPieces.add( 12, new Bishop( Constants.BLACK_COLOUR, 61 ) );
		
		blackPieces.add( 13, new Rook( Constants.BLACK_COLOUR, 56 ) );
		blackPieces.add( 14, new Rook( Constants.BLACK_COLOUR, 63 ) );
		
		blackPieces.add( 15, new Queen( Constants.BLACK_COLOUR, 59 ) );
		
	}
	
	@Override
	public void visualize() {
		
		for ( int i = 56; i < 64; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			Square square =
				new Square( i );
			
			if ( ! this.isFreeSquare( square ) ) {
				
				Piece piece =
					this.getPieceByColourAndSquare(
						Constants.WHITE_COLOUR,
						square
					);
				
				if ( piece != null ) {
					whatToPrint = piece.toString() + " ";
				}
				else {
					piece =
						this.getPieceByColourAndSquare(
							Constants.BLACK_COLOUR,
							square
						);

					whatToPrint = piece.toString() + " ";
				}
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 48; i < 56; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			Square square =
				new Square( i );
			
			if ( ! this.isFreeSquare( square ) ) {
				
				Piece piece =
					this.getPieceByColourAndSquare(
						Constants.WHITE_COLOUR,
						square
					);
				
				if ( piece != null ) {
					whatToPrint = piece.toString() + " ";
				}
				else {
					piece =
						this.getPieceByColourAndSquare(
							Constants.BLACK_COLOUR,
							square
						);
					
					whatToPrint = piece.toString() + " ";
				}
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 40; i < 48; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			Square square =
				new Square( i );
			
			if ( ! this.isFreeSquare( square ) ) {
				
				Piece piece =
					this.getPieceByColourAndSquare(
						Constants.WHITE_COLOUR,
						square
					);
				
				if ( piece != null ) {
					whatToPrint = piece.toString() + " ";
				}
				else {
					piece =
						this.getPieceByColourAndSquare(
							Constants.BLACK_COLOUR,
							square
						);
					
					whatToPrint = piece.toString() + " ";
				}
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 32; i < 40; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			Square square =
				new Square( i );
			
			if ( ! this.isFreeSquare( square ) ) {
				
				Piece piece =
					this.getPieceByColourAndSquare(
						Constants.WHITE_COLOUR,
						square
					);
				
				if ( piece != null ) {
					whatToPrint = piece.toString() + " ";
				}
				else {
					piece =
						this.getPieceByColourAndSquare(
							Constants.BLACK_COLOUR,
							square
						);
					
					whatToPrint = piece.toString() + " ";
				}
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 24; i < 32; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			Square square =
				new Square( i );
			
			if ( ! this.isFreeSquare( square ) ) {
				
				Piece piece =
					this.getPieceByColourAndSquare(
						Constants.WHITE_COLOUR,
						square
					);
				
				if ( piece != null ) {
					whatToPrint = piece.toString() + " ";
				}
				else {
					piece =
						this.getPieceByColourAndSquare(
							Constants.BLACK_COLOUR,
							square
						);
					
					whatToPrint = piece.toString() + " ";
				}
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 16; i < 24; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			Square square =
				new Square( i );
			
			if ( ! this.isFreeSquare( square ) ) {
				
				Piece piece =
					this.getPieceByColourAndSquare(
						Constants.WHITE_COLOUR,
						square
					);
				
				if ( piece != null ) {
					whatToPrint = piece.toString() + " ";
				}
				else {
					piece =
						this.getPieceByColourAndSquare(
							Constants.BLACK_COLOUR,
							square
						);
					
					whatToPrint = piece.toString() + " ";
				}
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 8; i < 16; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			Square square =
				new Square( i );
			
			if ( ! this.isFreeSquare( square ) ) {
				
				Piece piece =
					this.getPieceByColourAndSquare(
						Constants.WHITE_COLOUR,
						square
					);
				
				if ( piece != null ) {
					whatToPrint = piece.toString() + " ";
				}
				else {
					piece =
						this.getPieceByColourAndSquare(
							Constants.BLACK_COLOUR,
							square
						);
					
					whatToPrint = piece.toString() + " ";
				}
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 0; i < 8; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			Square square =
				new Square( i );
			
			if ( ! this.isFreeSquare( square ) ) {
				
				Piece piece =
					this.getPieceByColourAndSquare(
						Constants.WHITE_COLOUR,
						square
					);
				
				if ( piece != null ) {
					whatToPrint = piece.toString() + " ";
				}
				else {
					piece =
						this.getPieceByColourAndSquare(
							Constants.BLACK_COLOUR,
							square
						);
					
					whatToPrint = piece.toString() + " ";
				}
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
	}
	
	public King getKing(
		int colour
	) {
		//
		// The King is always the first piece in the pieces array list
		//
		King result;
		
		if ( colour == Constants.WHITE_COLOUR ) {
			result =
				( King )( this.whitePieces.get( 0 ) );
		}
		else {
			result =
				( King )( this.blackPieces.get( 0 ) );
		}
		
		return result;
	}

	public boolean isPieceAttackingSquare(
		Piece piece, 
		Square square
	) {
		return 
			piece.isAttackingSquare(
				square,
				this
			);
	}
	
	public boolean isPieceAttackingAnyOfTheFourCentralSquares(
		Piece piece
	) {
		return (
			this.isPieceAttackingSquare( piece, squareD4 )
			|| this.isPieceAttackingSquare( piece, squareD5 )
			|| this.isPieceAttackingSquare( piece, squareE4 )
			|| this.isPieceAttackingSquare( piece, squareE5 )
		);
	}
	
	public boolean areAdjacentSquares(Square squareStart, Square squareEnd) {
		
		int squareStartId = squareStart.getId();
		int squareEndId = squareEnd.getId();
		
		return 
			Math.abs( squareEndId / 8 - squareStartId / 8 ) < 2
			&& Math.abs( squareEndId % 8 - squareStartId % 8 ) < 2;
	
	}

	public boolean areDiagonalAdjacentSquaresWithDirection(
		Square squareStart,
		Square squareEnd,
		int colour
	) {
		boolean result = false;
		
		int squareStartId = squareStart.getId();
		int squareEndId = squareEnd.getId();
		
		if ( colour == Constants.WHITE_COLOUR ) {
			
			result = 
				squareEndId / 8 - squareStartId / 8 == 1
				&& Math.abs( squareEndId % 8 - squareStartId % 8 ) == 1; 
		}
		else {
			result = 
				squareEndId / 8 - squareStartId / 8 == -1
				&& Math.abs( squareEndId % 8 - squareStartId % 8 ) == 1;
		}
		
		return result;
	}

	public boolean emptyRowOrColumnBetweenSquares(
		Square squareStart, 
		Square squareEnd
	) {

		boolean result = false;
		
		int squareStartId = squareStart.getId();
		int squareEndId = squareEnd.getId();
		
		//
		// First, we need to know if the squares are in the same row
		// or in the same column, to perform further operations
		//
		
		if ( squareStartId % 8 == squareEndId % 8 ) {
			//
			// Squares are in the same column. We check then if the straight way is free
			//
			result = true;
			
			int maxSquareId = Math.max( squareStartId, squareEndId );
			
			for ( int i = Math.min( squareStartId, squareEndId ) + 8; result && i != maxSquareId; i = i + 8 ) {
				result = this.isFreeSquare( new Square( i ) );
			}
		}
		else if ( squareStartId / 8 == squareEndId / 8 ) {
			//
			// Squares are in the same row. We check then if the straight way is free
			//
			result = true;
			
			int maxSquareId = Math.max( squareStartId, squareEndId );
			
			for ( int i = Math.min( squareStartId, squareEndId ) + 1; result && i != maxSquareId; i = i + 1 ) {
				result = this.isFreeSquare( new Square( i ) );
			}
		}
		
		return result;
	}

	public boolean emptyDiagonalBetweenSquares(
		Square squareStart,
		Square squareEnd
	) {
		
		boolean result = false;
		
		int squareStartId = squareStart.getId();
		int squareEndId = squareEnd.getId();
		
		//
		// First, we check that both squares are in a diagonal
		//
		
		int columnDifference =
			squareEndId % 8 - squareStartId % 8;
		
		int rowDifference =
			squareEndId / 8 - squareStartId / 8;
		
		result = 
			Math.abs( columnDifference ) == Math.abs( rowDifference );
		
		if ( result ) {
			
			//
			// If so, we perform further operations to check if the squares
			// that are between our two squares are free
			//
			
			int addValue = 9;  // By default, one of the possible values
			
			if ( columnDifference > 0 && rowDifference < 0 ) {
				addValue = -7;
			}
			else if ( columnDifference < 0 && rowDifference > 0 ) {
				addValue = 7;
			}
			else if ( columnDifference < 0 && rowDifference < 0 ) {
				addValue = -9;
			}
			
			for ( int i = squareStartId + addValue; result && i != squareEndId; i = i + addValue ) {
				
				result = 
					this.isFreeSquare( new Square( i ) );
			}
		}
		
		return result;
		
	}

	public boolean existsLMovementBetweenSquares(
		Square squareStart,
		Square squareEnd
	) {
		
		int squareStartId = squareStart.getId();
		int squareEndId = squareEnd.getId();
		
		int columnDifference =
			Math.abs( squareStartId % 8 - squareEndId % 8 );
		
		int rowDifference =
			Math.abs( squareStartId / 8 - squareEndId / 8 );
		
		return 
			( 
				rowDifference == 2
				&& columnDifference == 1
			)
			|| 
			( 
				rowDifference == 1
				&& columnDifference == 2
			);
			
	}

	public boolean isFreeSquare( Square square ) {
		
		return 
			this.freeSquares.containsKey(
				square.getId()
			);
	}
	
	public boolean isSquareOccupiedByPieceByColour(
		Square square,
		int colour
	) {
		boolean result = false;
		
		ArrayList<Piece> pieces =
			( colour == Constants.WHITE_COLOUR ) 
			? this.whitePieces 
			: this.blackPieces;
		
		int sizePieces =
			pieces.size();
		
		for ( int i = 0; ! result && i < sizePieces; i++ ) {
			result =
				pieces.get( i ).getSquare().getId() == square.getId();
		}
		
		return result;
	}
	
	public ArrayList<Square> getKingMovementEndSquares(
		Square squareStart,
		int colour
	) {
		
		ArrayList<Square> result =
			new ArrayList<Square>();
		
		int squareStartId =
			squareStart.getId();
		
		int rowStart = squareStartId / 8;
		int columnStart = squareStartId % 8;
		
		int upSquareId = squareStartId + 8;
		int upRightSquareId = squareStartId + 9;
		int rightSquareId = squareStartId + 1;
		int downRight = squareStartId - 7;
		int downSquareId = squareStartId - 8;
		int downLeftSquareId = squareStartId - 9;
		int leftSquareId = squareStartId - 1;
		int upLeftSquareId = squareStartId + 7;
		
		if ( 
			rowStart < 7 
			&& ! this.isSquareOccupiedByPieceByColour( 
				new Square( upSquareId ), 
				colour 
			) 
		) {
			result.add( new Square( upSquareId ) );
		}
		if ( 
			rowStart < 7
			&& columnStart < 7
			&& ! this.isSquareOccupiedByPieceByColour( 
				new Square( upRightSquareId ), 
				colour 
			) 
		) {
			result.add( new Square( upRightSquareId ) );
		}
		if ( 
			columnStart < 7
			&& ! this.isSquareOccupiedByPieceByColour( 
				new Square( rightSquareId ), 
				colour 
			) 
		) {
			result.add( new Square( rightSquareId ) );
		}
		if ( 
			rowStart > 0
			&& columnStart < 7
			&& ! this.isSquareOccupiedByPieceByColour( 
				new Square( downRight ), 
				colour 
			) 
		) {
			result.add( new Square( downRight ) );
		}
		if ( 
			rowStart > 0 
			&& ! this.isSquareOccupiedByPieceByColour( 
				new Square( downSquareId ), 
				colour 
			) 
		) {
			result.add( new Square( downSquareId ) );
		}
		if ( 
			rowStart > 0
			&& columnStart > 0
			&& ! this.isSquareOccupiedByPieceByColour( 
				new Square( downLeftSquareId ), 
				colour 
			) 
		) {
			result.add( new Square( downLeftSquareId ) );
		}
		if ( 
			columnStart > 0
			&& ! this.isSquareOccupiedByPieceByColour( 
				new Square( leftSquareId ), 
				colour 
			) 
		) {
			result.add( new Square( leftSquareId ) );
		}
		if ( 
			rowStart < 7
			&& columnStart > 0
			&& ! this.isSquareOccupiedByPieceByColour( 
				new Square( upLeftSquareId ), 
				colour 
			) 
		) {
			result.add( new Square( upLeftSquareId ) );
		}
		
		return result;
	}

	public ArrayList<Square> getPawnMovementEndSquares(
		Square squareStart, 
		int colour
	) {
		ArrayList<Square> result =
			new ArrayList<Square>();
		
		int squareStartId =
			squareStart.getId();
		
		int rowStart = squareStartId / 8;
		int columnStart = squareStartId % 8;
		
		boolean rowStartAppropiateToPromote;
		
		if ( colour == Constants.WHITE_COLOUR ) {
			rowStartAppropiateToPromote =
				rowStart == 6;
		}
		else {
			rowStartAppropiateToPromote =
				rowStart == 1;
		}
		
		int rivalColour =
			( colour == Constants.WHITE_COLOUR )
			? Constants.BLACK_COLOUR
			: Constants.WHITE_COLOUR;
		
		int valueToAddLeftSide =
			( colour == Constants.WHITE_COLOUR )
			? 7
			: -9;
		
		int valueToAddRightSide =
			( colour == Constants.WHITE_COLOUR )
			? 9
			: -7;
		
		//
		// A pawn can capture another piece in the two diagonal 
		// adjacent squares, that are in front of it.
		// A pawn can also move one square ahead, if that square is free.
		// A pawn that has not been played before, can also jump two
		// squares ahead in its first movement.
		//
		
		//
		// First we try with capture movements, if they are possible
		//
			
		if ( 
			columnStart > 0 
			&& this.getPieceByColourAndSquare( rivalColour, new Square( squareStartId + valueToAddLeftSide ) ) != null 
		) {
			//
			// Diagonal-left
			//
			if ( rowStartAppropiateToPromote ) {
				//
				// We have to add 4 equal squares representing the
				// 4 promotion choices (queen, rook, bishop, knight) 
				//
				for ( int i = 0; i < 4; i++ ) {
					result.add( new Square( squareStartId + valueToAddLeftSide ) );					
				}
			}
			else {
				result.add( new Square( squareStartId + valueToAddLeftSide ) );
			}
		}
		
		if ( 
			columnStart < 7 
			&& this.getPieceByColourAndSquare( rivalColour, new Square( squareStartId + valueToAddRightSide ) ) != null
		) {
			//
			// Diagonal-right
			//
			if ( rowStartAppropiateToPromote ) {
				//
				// We have to add 4 equal squares representing the
				// 4 promotion choices (queen, rook, bishop, knight) 
				//
				for ( int i = 0; i < 4; i++ ) {
					result.add( new Square( squareStartId + valueToAddRightSide ) );					
				}
			}
			else {
				result.add( new Square( squareStartId + valueToAddRightSide ) );
			}
		}
		
		//
		// Then we try the normal one square advance
		//
		int valueToAddNormalAdvance =
			( colour == Constants.WHITE_COLOUR )
			? 8
			: -8;
		
		boolean normalAdvancePossible = false;
		
		if ( this.isFreeSquare( new Square( squareStartId + valueToAddNormalAdvance ) ) ) {
			//
			// Normal advance
			//
			if ( rowStartAppropiateToPromote ) {
				//
				// We have to add 4 equal squares representing the
				// 4 promotion choices (queen, rook, bishop, knight) 
				//
				for ( int i = 0; i < 4; i++ ) {
					result.add( new Square( squareStartId + valueToAddNormalAdvance ) );					
				}
			}
			else {
				result.add( new Square( squareStartId + valueToAddNormalAdvance ) );
			}
			
			normalAdvancePossible = true;
		}
		
		//
		// Then we try the 2 squares initial advance, if it is possible
		//
		int valueToAddInitialDoubleAdvance =
			( colour == Constants.WHITE_COLOUR )
			? 16
			: -16;
		
		boolean appropiateRowForInitialDoubleAdvance =
			( colour == Constants.WHITE_COLOUR )
			? rowStart == 1
			: rowStart == 6;
		
		if ( 
			appropiateRowForInitialDoubleAdvance
			&& normalAdvancePossible
			&& this.isFreeSquare( new Square( squareStartId + valueToAddInitialDoubleAdvance ) )
		) {
			//
			// 2 squares initial advance. Here is, of course, impossible to promote this pawn
			//
			result.add( new Square( squareStartId + valueToAddInitialDoubleAdvance ) );
		}
		
		return result;
	}
	
	public ArrayList<Square> getRookMovementEndSquares(
		Square squareStart, 
		int colour
	) {
		ArrayList<Square> result =
			new ArrayList<Square>();
		
		int squareStartId =
			squareStart.getId();
		
		//
		// We have to search for every free reachable square in the same row or
		// column, and also for every possible to capture rival piece
		//
		
		boolean keepOnSearchingThisDirection = true;
		
		//
		// Going up...
		//
		for ( int i = squareStartId + 8; keepOnSearchingThisDirection && i / 8 < 8; i = i + 8 ) {
			
			Square square =
				new Square( i );
			
			if ( this.isFreeSquare( new Square( i ) ) ) {
				//
				// A free square. We add it
				//
				result.add( square );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareOccupiedByPieceByColour(
						square, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( square );
				}
			}
		}
		
		keepOnSearchingThisDirection = true;
		
		//
		// Going down...
		//
		for ( int i = squareStartId - 8; keepOnSearchingThisDirection && i >= 0; i = i - 8 ) {
			
			Square square =
				new Square( i );
			
			if ( this.isFreeSquare( new Square( i ) ) ) {
				//
				// A free square. We add it
				//
				result.add( square );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareOccupiedByPieceByColour(
						square, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( square );
				}
			}
		}
		
		keepOnSearchingThisDirection = true;
		
		//
		// Going left...
		//
		for ( int i = squareStartId - 1; keepOnSearchingThisDirection && i >= 0 && i % 8 < 7; i = i - 1 ) {
			
			Square square =
				new Square( i );
			
			if ( this.isFreeSquare( new Square( i ) ) ) {
				//
				// A free square. We add it
				//
				result.add( square );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareOccupiedByPieceByColour(
						square, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( square );
				}
			}
		}
		
		keepOnSearchingThisDirection = true;
		
		//
		// Going right...
		//
		for ( int i = squareStartId + 1; keepOnSearchingThisDirection && i % 8 > 0; i = i + 1 ) {
			
			Square square =
				new Square( i );
			
			if ( this.isFreeSquare( new Square( i ) ) ) {
				//
				// A free square. We add it
				//
				result.add( square );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareOccupiedByPieceByColour(
						square, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( square );
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<Square> getBishopMovementEndSquares(
		Square squareStart, 
		int colour
	) {
		ArrayList<Square> result =
			new ArrayList<Square>();
		
		int squareStartId =
			squareStart.getId();
		
		//
		// We have to search for every free reachable square in the same diagonals
		// in which this bishop is, and also for every possible to capture rival piece
		//
		
		boolean keepOnSearchingThisDirection = true;
		
		//
		// Going up-right...
		//
		for ( int i = squareStartId + 9; keepOnSearchingThisDirection && i / 8 < 8 && i % 8 > 0; i = i + 9 ) {
			
			Square square =
				new Square( i );
			
			if ( this.isFreeSquare( new Square( i ) ) ) {
				//
				// A free square. We add it
				//
				result.add( square );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareOccupiedByPieceByColour(
						square, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( square );
				}
			}
		}
		
		keepOnSearchingThisDirection = true;
		
		//
		// Going down-right...
		//
		for ( int i = squareStartId - 7; keepOnSearchingThisDirection && i > 0 && i % 8 > 0; i = i - 7 ) {
			
			Square square =
				new Square( i );
			
			if ( this.isFreeSquare( new Square( i ) ) ) {
				//
				// A free square. We add it
				//
				result.add( square );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareOccupiedByPieceByColour(
						square, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( square );
				}
			}
		}
		
		keepOnSearchingThisDirection = true;
		
		//
		// Going down-left...
		//
		for ( int i = squareStartId - 9; keepOnSearchingThisDirection && i >= 0 && i % 8 < 7; i = i - 9 ) {
			
			Square square =
				new Square( i );
			
			if ( this.isFreeSquare( new Square( i ) ) ) {
				//
				// A free square. We add it
				//
				result.add( square );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareOccupiedByPieceByColour(
						square, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( square );
				}
			}
		}
		
		keepOnSearchingThisDirection = true;
		
		//
		// Going up-left...
		//
		for ( int i = squareStartId + 7; keepOnSearchingThisDirection && i / 8 < 8 && i % 8 < 7; i = i + 7 ) {
			
			Square square =
				new Square( i );
			
			if ( this.isFreeSquare( new Square( i ) ) ) {
				//
				// A free square. We add it
				//
				result.add( square );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareOccupiedByPieceByColour(
						square, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( square );
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<Square> getKnightMovementEndSquares(
		Square squareStart, 
		int colour
	) {
		ArrayList<Square> result =
			new ArrayList<Square>();
		
		int squareStartId =
			squareStart.getId();
		
		//
		// We have to search for every reachable square doing a typical knight L-movement.
		// The squares must be free or occupied by a rival piece.
		//
		
		//
		// We do a clockwise search
		//
		int squareEnd1 =
			squareStartId + 17;
		
		if (
			squareEnd1 / 8 < 8
			&& squareEnd1 % 8 > 0
			&& ! this.isSquareOccupiedByPieceByColour(
				new Square( squareEnd1 ),
				colour
			)
		) {
			result.add( new Square( squareEnd1 ) );
		}
		
		int squareEnd2 =
			squareStartId + 10;
		
		if (
			squareEnd2 / 8 < 8
			&& squareEnd2 % 8 > 1
			&& ! this.isSquareOccupiedByPieceByColour(
				new Square( squareEnd2 ),
				colour
			)
		) {
			result.add( new Square( squareEnd2 ) );
		}
		
		int squareEnd3 =
			squareStartId - 6;
		
		if (
			squareEnd3 > 0
			&& squareEnd3 % 8 > 1
			&& ! this.isSquareOccupiedByPieceByColour(
				new Square( squareEnd3 ),
				colour
			)
		) {
			result.add( new Square( squareEnd3 ) );
		}
		
		int squareEnd4 =
			squareStartId - 15;
		
		if (
			squareEnd4 > 0
			&& squareEnd4 % 8 > 0
			&& ! this.isSquareOccupiedByPieceByColour(
				new Square( squareEnd4 ),
				colour
			)
		) {
			result.add( new Square( squareEnd4 ) );
		}
		
		int squareEnd5 =
			squareStartId - 17;
		
		if (
			squareEnd5 >= 0
			&& squareEnd5 % 8 < 7
			&& ! this.isSquareOccupiedByPieceByColour(
				new Square( squareEnd5 ),
				colour
			)
		) {
			result.add( new Square( squareEnd5 ) );
		}
		
		int squareEnd6 =
			squareStartId - 10;
		
		if (
			squareEnd6 >= 0
			&& squareEnd6 % 8 < 6
			&& ! this.isSquareOccupiedByPieceByColour(
				new Square( squareEnd6 ),
				colour
			)
		) {
			result.add( new Square( squareEnd6 ) );
		}
		
		int squareEnd7 =
			squareStartId + 6;
		
		if (
			squareEnd7 / 8 < 8
			&& squareEnd7 % 8 < 6
			&& ! this.isSquareOccupiedByPieceByColour(
				new Square( squareEnd7 ),
				colour
			)
		) {
			result.add( new Square( squareEnd7 ) );
		}
		
		int squareEnd8 =
			squareStartId + 15;
		
		if (
			squareEnd8 / 8 < 8
			&& squareEnd8 % 8 < 7
			&& ! this.isSquareOccupiedByPieceByColour(
				new Square( squareEnd8 ),
				colour
			)
		) {
			result.add( new Square( squareEnd8 ) );
		}
		
		return result;
	}
	
	/**
	 * Method that indicates if a piece is placed on the iesim row (from white pieces perspective, from
	 * the 0st row to the 7th row)
	 * 
	 * @param piece the piece
	 * @param row the row
	 * @return
	 */
	public boolean isPieceOnTheIesimRow( Piece piece, int row ) {
		return ( ( piece.getSquare().getId() / 8 ) == row );
	}
	
	/**
	 * Method that indicates if a piece is placed on the iesim column (from white pieces perspective, from
	 * the 0st column to the 7th column)
	 * 
	 * @param piece the piece
	 * @param column the column
	 * @return
	 */
	public boolean isPieceOnTheIesimColumn( Piece piece, int column ) {
		return ( ( piece.getSquare().getId() % 8 ) == column );
	}
	
	public boolean thereIsASameColourPawnInTheSameColumn( Piece piece ) {
		boolean result = false;  // By default. It can change below, of course
		
		int colour = piece.getColour();
		ArrayList<Piece> pieces =
			( colour == Constants.WHITE_COLOUR ) 
			? this.whitePieces 
			: this.blackPieces;
		
		int sizePieces = pieces.size();
		int pieceColumn = piece.getSquare().getId() % 8;
		
		for ( int i = 0; ( ! result ) && i < sizePieces; i++ ) {
			Piece iesimPiece = pieces.get( i );
			result =
				iesimPiece instanceof Pawn  // It is a pawn
				&& iesimPiece.getSquare().getId() != piece.getSquare().getId()  // It is not the piece we received as argument
				&& isPieceOnTheIesimColumn( iesimPiece, pieceColumn );
		}
		
		return result;
	}
	
	public boolean isAnIsolatedPawn( Pawn pawn ) {
		boolean result = true;  // By default. It can change below, of course
		
		int colour = pawn.getColour();
		ArrayList<Piece> pieces =
			( colour == Constants.WHITE_COLOUR ) 
			? this.whitePieces 
			: this.blackPieces;
		
		int sizePieces = pieces.size();
		int pawnColumn = pawn.getSquare().getId() % 8;
		
		for ( int i = 0; result && i < sizePieces; i++ ) {
			Piece iesimPiece = pieces.get( i );
			if ( iesimPiece instanceof Pawn ) {
				if ( pawnColumn > 0 && pawnColumn < 7 ) {  // Columns 1 to 6
					result =
						! (
							this.isPieceOnTheIesimColumn( iesimPiece, pawnColumn + 1 )
							|| this.isPieceOnTheIesimColumn( iesimPiece, pawnColumn - 1 )
						);
				}
				else if ( pawnColumn == 0 ) {  // Column 0
					result =
						! this.isPieceOnTheIesimColumn( iesimPiece, pawnColumn + 1 );
				}
				else if ( pawnColumn == 7 ) {  // Column 7
					result =
						! this.isPieceOnTheIesimColumn( iesimPiece, pawnColumn - 1 );
				}
			}
		}
		
		return result;
	}
	
	public Piece getPieceByColourAndSquare(
		int colour,
		Square square
	) {
		
		Piece result = null;
		boolean found = false;
		
		ArrayList<Piece> pieces =
			( colour == Constants.WHITE_COLOUR ) 
			? this.whitePieces 
			: this.blackPieces;
		
		int sizePieces =
			pieces.size();
		
		for ( int i = 0; ( ! found ) && i < sizePieces; i++ ) {
			result = pieces.get( i );
			found = ( result.getSquare().getId() == square.getId() );
		}
		
		if ( ! found ) {
			result = null;
		}
		
		return result;
		
	}

	public void removePiece(
		Piece piece
	) {
		
		if ( piece.getColour() == Constants.WHITE_COLOUR ) {
			this.whitePieces.remove( piece );
		}
		else {
			this.blackPieces.remove( piece );
		}
		
	}

	public void addPiece(
		Piece piece
	) {
		
		if ( piece.getColour() == Constants.WHITE_COLOUR ) {
			this.whitePieces.add( piece );
		}
		else {
			this.blackPieces.add( piece	);
		}
	}
	
	public void addPiece(
		Piece piece,
		int index
	) {
		
		if ( piece.getColour() == Constants.WHITE_COLOUR ) {
			this.whitePieces.add(
				index,
				piece
			);
		}
		else {
			this.blackPieces.add(
				index,
				piece
			);
		}
	}
	
	public ArrayList<Rook> getRooks( int colour ) {
		
		ArrayList<Rook> result = new ArrayList<Rook>();
		
		ArrayList<Piece> colourPieces =
			( colour == Constants.WHITE_COLOUR )
			? this.getWhitePieces()
			: this.getBlackPieces() ;
		
		// There might be one, two, three or whaveter number of rooks on the board, because of promotions...	
			
		for ( int i = 0; i < colourPieces.size(); i++ ) {
			if ( colourPieces.get( i ) instanceof Rook ) {
				result.add( ( Rook ) ( colourPieces.get( i ) ) );
			}
		}
			
		return result;
	}

	public ArrayList<Piece> getWhitePieces() {
		return whitePieces;
	}

	public void setWhitePieces(ArrayList<Piece> whitePieces) {
		this.whitePieces = whitePieces;
	}

	public ArrayList<Piece> getBlackPieces() {
		return blackPieces;
	}

	public void setBlackPieces(ArrayList<Piece> blackPieces) {
		this.blackPieces = blackPieces;
	}

	public HashMap<Integer, Boolean> getFreeSquares() {
		return freeSquares;
	}

	public void setFreeSquares(HashMap<Integer, Boolean> freeSquares) {
		this.freeSquares = freeSquares;
	}
}
