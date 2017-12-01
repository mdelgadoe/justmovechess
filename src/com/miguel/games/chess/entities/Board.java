/**
 * 
 */
package com.miguel.games.chess.entities;

import java.util.ArrayList;

import com.miguel.games.chess.common.Constants;

/**
 * @author mdelgado
 *
 */
public class Board extends com.miguel.games.entities.Board {
	
	private static final int MATRIX_NUMBER_OF_SQUARES = 64;

	// Pieces
	private ArrayList<Piece> whitePieces;
	private ArrayList<Piece> blackPieces;
	
	// Board
	private Piece[] matrix;
	
	//
	// Central squares. It is considered important to occupy or attack them in the opening
	//
	private static final int d4SquareId = 27;
	private static final int e4SquareId = 28;
	private static final int d5SquareId = 35;
	private static final int e5SquareId = 36;
	
	public void initialize() {
		
		//
		// Matrix board
		//
		matrix = new Piece[MATRIX_NUMBER_OF_SQUARES];
		
		//
		// White pieces
		//
		
		whitePieces =
			new ArrayList<Piece>();
		
		King e1King = new King( true, 4 );
		whitePieces.add( 0, e1King );
		matrix[4] = e1King;
		
		Pawn a2Pawn = new Pawn( true, 8 );
		whitePieces.add( 1, a2Pawn );
		matrix[8] = a2Pawn;
		Pawn b2Pawn = new Pawn( true, 9 );
		whitePieces.add( 2, b2Pawn );
		matrix[9] = b2Pawn;
		Pawn c2Pawn = new Pawn( true, 10 );
		whitePieces.add( 2, c2Pawn );
		matrix[10] = c2Pawn;
		Pawn d2Pawn = new Pawn( true, 11 );
		whitePieces.add( 4, d2Pawn );
		matrix[11] = d2Pawn;
		Pawn e2Pawn = new Pawn( true, 12 );
		whitePieces.add( 5, e2Pawn );
		matrix[12] = e2Pawn;
		Pawn f2Pawn = new Pawn( true, 13 );
		whitePieces.add( 6, f2Pawn );
		matrix[13] = f2Pawn;
		Pawn g2Pawn = new Pawn( true, 14 );
		whitePieces.add( 7, g2Pawn );
		matrix[14] = g2Pawn;
		Pawn h2Pawn = new Pawn( true, 15 );
		whitePieces.add( 8, h2Pawn );
		matrix[15] = h2Pawn;
		
		Knight b1Knight = new Knight( true, 1 );
		whitePieces.add( 9, b1Knight );
		matrix[1] = b1Knight;
		Knight g1Pawn = new Knight( true, 6 );
		whitePieces.add( 10, g1Pawn );
		matrix[6] = g1Pawn;
		
		Bishop c1Bishop = new Bishop( true, 2 );
		whitePieces.add( 11, c1Bishop );
		matrix[2] = c1Bishop;
		Bishop f1Bishop = new Bishop( true, 5 );
		whitePieces.add( 12, f1Bishop );
		matrix[5] = f1Bishop;
		
		Rook a1Rook = new Rook( true, 0 );
		whitePieces.add( 13, a1Rook );
		matrix[0] = a1Rook;
		Rook h1Rook = new Rook( true, 7 );
		whitePieces.add( 14, h1Rook );
		matrix[7] = h1Rook;
		
		Queen d1Queen = new Queen( true, 3 );
		whitePieces.add( 15, d1Queen );
		matrix[3] = d1Queen;
		
		//
		// Black pieces
		//
		
		blackPieces =
			new ArrayList<Piece>();
		
		King e8King = new King( false, 60 );
		blackPieces.add( 0, e8King );
		matrix[60] = e8King;
		
		Pawn a7Pawn = new Pawn( false, 48 );
		blackPieces.add( 1, a7Pawn );
		matrix[48] = a7Pawn;
		Pawn b7Pawn = new Pawn( false, 49 );
		blackPieces.add( 2, b7Pawn );
		matrix[49] = b7Pawn;
		Pawn c7Pawn = new Pawn( false, 50 );
		blackPieces.add( 3, c7Pawn );
		matrix[50] = c7Pawn;
		Pawn d7Pawn = new Pawn( false, 51 );
		blackPieces.add( 4, d7Pawn );
		matrix[51] = d7Pawn;
		Pawn e7Pawn = new Pawn( false, 52 );
		blackPieces.add( 5, e7Pawn );
		matrix[52] = e7Pawn;
		Pawn f7Pawn = new Pawn( false, 53 );
		blackPieces.add( 6, f7Pawn );
		matrix[53] = f7Pawn;
		Pawn g7Pawn = new Pawn( false, 54 );
		blackPieces.add( 7, g7Pawn );
		matrix[54] = g7Pawn;
		Pawn h7Pawn = new Pawn( false, 55 );
		blackPieces.add( 8, h7Pawn );
		matrix[55] = h7Pawn;
		
		Knight b8Knight = new Knight( false, 57 );
		blackPieces.add( 9, b8Knight );
		matrix[57] = b8Knight;
		Knight g8Knight = new Knight( false, 62 );
		blackPieces.add( 10, g8Knight );
		matrix[62] = g8Knight;
		
		Bishop c8Bishop = new Bishop( false, 58 );
		blackPieces.add( 11, c8Bishop );
		matrix[58] = c8Bishop;
		Bishop f8Bishop = new Bishop( false, 61 );
		blackPieces.add( 12, f8Bishop );
		matrix[61] = f8Bishop;
		
		Rook a8Rook = new Rook( false, 56 );
		blackPieces.add( 13, a8Rook );
		matrix[56] = a8Rook;
		Rook h8Rook = new Rook( false, 63 );
		blackPieces.add( 14, h8Rook );
		matrix[63] = h8Rook;
		
		Queen d8Queen = new Queen( false, 59 );
		blackPieces.add( 15, d8Queen );
		matrix[59] = d8Queen;
	}
	
	public void setEmptySquareId( int squareId ) {
		matrix[squareId] = null;
	}
	
	public void setPieceOnMatrix( Piece piece ) {
		matrix[piece.getSquareId()] = piece;
	}
	
	@Override
	public void visualize() {
		
		for ( int i = 56; i < 64; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			if ( ! this.isFreeSquareId( i ) ) {
				Piece piece = this.getPieceBySquareId( i );
				whatToPrint = piece.toString() + " ";
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 48; i < 56; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			if ( ! this.isFreeSquareId( i ) ) {
				Piece piece = this.getPieceBySquareId( i );
				whatToPrint = piece.toString() + " ";
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 40; i < 48; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			if ( ! this.isFreeSquareId( i ) ) {
				Piece piece = this.getPieceBySquareId( i );
				whatToPrint = piece.toString() + " ";
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 32; i < 40; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			if ( ! this.isFreeSquareId( i ) ) {
				Piece piece = this.getPieceBySquareId( i );
				whatToPrint = piece.toString() + " ";
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 24; i < 32; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			if ( ! this.isFreeSquareId( i ) ) {
				Piece piece = this.getPieceBySquareId( i );
				whatToPrint = piece.toString() + " ";
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 16; i < 24; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			if ( ! this.isFreeSquareId( i ) ) {
				Piece piece = this.getPieceBySquareId( i );
				whatToPrint = piece.toString() + " ";
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 8; i < 16; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			if ( ! this.isFreeSquareId( i ) ) {
				Piece piece = this.getPieceBySquareId( i );
				whatToPrint = piece.toString() + " ";
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
		
		for ( int i = 0; i < 8; i++ ) {
			
			String whatToPrint = Constants.EMPTY_SQUARE_VISUALIZE + " ";
			
			if ( ! this.isFreeSquareId( i ) ) {
				Piece piece = this.getPieceBySquareId( i );
				whatToPrint = piece.toString() + " ";
			}
			
			System.out.print( whatToPrint );
		}
		
		System.out.println();
	}
	
	public King getKing(
		boolean colour
	) {
		//
		// The King is always the first piece in the pieces array list
		//
		return (
			colour ?
			( King )( this.whitePieces.get( 0 ) ) :
			( King )( this.blackPieces.get( 0 ) )
		);
	}

	public boolean isPieceAttackingAnyOfTheFourCentralSquares(
		Piece piece
	) {
		return (
			piece.isAttackingSquareId( d4SquareId, this )
			|| piece.isAttackingSquareId( d5SquareId, this )
			|| piece.isAttackingSquareId( e4SquareId, this )
			|| piece.isAttackingSquareId( e5SquareId, this )
		);
	}
	
	public boolean areAdjacentSquaresIds(int squareStartId, int squareEndId) {
		return 
			Math.abs( squareEndId / 8 - squareStartId / 8 ) < 2
			&& Math.abs( squareEndId % 8 - squareStartId % 8 ) < 2;
	
	}

	public boolean areDiagonalAdjacentSquaresIdsWithDirection(
		int squareStartId,
		int squareEndId,
		boolean colour
	) {
		boolean result = false;
		
		if ( colour ) {
			
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

	public boolean emptyRowOrColumnBetweenSquaresIds(
		int squareStartId, 
		int squareEndId
	) {
		boolean result = false;
		
		//
		// If it is the same square, we choose to return false. It is a kind of extreme situation, i would say
		//
		if ( squareStartId != squareEndId ) {
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
					result = this.isFreeSquareId( i );
				}
			}
			else if ( squareStartId / 8 == squareEndId / 8 ) {
				//
				// Squares are in the same row. We check then if the straight way is free
				//
				result = true;
				
				int maxSquareId = Math.max( squareStartId, squareEndId );
				
				for ( int i = Math.min( squareStartId, squareEndId ) + 1; result && i != maxSquareId; i = i + 1 ) {
					result = this.isFreeSquareId( i );
				}
			}
		}
		
		return result;
	}

	public boolean emptyDiagonalBetweenSquaresIds(
		int squareStartId,
		int squareEndId
	) {
		boolean result = false;
		
		//
		// If it is the same square, we choose to return false. It is an extreme situation, i would say
		//
		if ( squareStartId != squareEndId ) {
		
			//
			// First, we check that both squares are in a diagonal
			//
			
			int columnDifference =
				squareEndId % 8 - squareStartId % 8;
			
			int rowDifference =
				squareEndId / 8 - squareStartId / 8;
			
			result = Math.abs( columnDifference ) == Math.abs( rowDifference );
			
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
					result = this.isFreeSquareId( i );
				}
			}
		
		}
		
		return result;
		
	}

	public boolean existsLMovementBetweenSquaresIds(
		int squareStartId,
		int squareEndId
	) {
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

	public boolean isFreeSquareId( int squareId ) {
		return ( this.matrix[squareId] == null );
	}
	
	public boolean isSquareIdOccupiedByPieceByColour(
		int squareId,
		boolean colour
	) {
		return (
			this.matrix[squareId] != null
			&& this.matrix[squareId].getColour() == colour
		);
	}
	
	public ArrayList<Integer> getKingMovementEndSquaresIds(
		int squareStartId,
		boolean colour
	) {
		
		ArrayList<Integer> result =
			new ArrayList<Integer>();
		
		int rowStart = squareStartId / 8;
		int columnStart = squareStartId % 8;
		
		int upSquareId = squareStartId + 8;
		int upRightSquareId = squareStartId + 9;
		int rightSquareId = squareStartId + 1;
		int downRightId = squareStartId - 7;
		int downSquareId = squareStartId - 8;
		int downLeftSquareId = squareStartId - 9;
		int leftSquareId = squareStartId - 1;
		int upLeftSquareId = squareStartId + 7;
		
		if ( 
			rowStart < 7 
			&& ! this.isSquareIdOccupiedByPieceByColour( 
				upSquareId, 
				colour 
			) 
		) {
			result.add( upSquareId );
		}
		if ( 
			rowStart < 7
			&& columnStart < 7
			&& ! this.isSquareIdOccupiedByPieceByColour( 
				upRightSquareId, 
				colour 
			) 
		) {
			result.add( upRightSquareId );
		}
		if ( 
			columnStart < 7
			&& ! this.isSquareIdOccupiedByPieceByColour( 
				rightSquareId, 
				colour 
			) 
		) {
			result.add( rightSquareId );
		}
		if ( 
			rowStart > 0
			&& columnStart < 7
			&& ! this.isSquareIdOccupiedByPieceByColour( 
				downRightId, 
				colour 
			) 
		) {
			result.add( downRightId );
		}
		if ( 
			rowStart > 0 
			&& ! this.isSquareIdOccupiedByPieceByColour( 
				downSquareId, 
				colour 
			) 
		) {
			result.add( downSquareId );
		}
		if ( 
			rowStart > 0
			&& columnStart > 0
			&& ! this.isSquareIdOccupiedByPieceByColour( 
				downLeftSquareId, 
				colour 
			) 
		) {
			result.add( downLeftSquareId );
		}
		if ( 
			columnStart > 0
			&& ! this.isSquareIdOccupiedByPieceByColour( 
				leftSquareId, 
				colour 
			) 
		) {
			result.add( leftSquareId );
		}
		if ( 
			rowStart < 7
			&& columnStart > 0
			&& ! this.isSquareIdOccupiedByPieceByColour( 
				upLeftSquareId, 
				colour 
			) 
		) {
			result.add( upLeftSquareId );
		}
		
		return result;
	}

	public ArrayList<Integer> getPawnMovementEndSquaresIds(
		int squareStartId, 
		boolean colour
	) {
		ArrayList<Integer> result =
			new ArrayList<Integer>();
		
		int rowStart = squareStartId / 8;
		int columnStart = squareStartId % 8;
		
		boolean rowStartAppropiateToPromote;
		
		if ( colour ) {
			rowStartAppropiateToPromote =
				rowStart == 6;
		}
		else {
			rowStartAppropiateToPromote =
				rowStart == 1;
		}
		
		boolean rivalColour = ! colour;
		
		int valueToAddLeftSide = colour ? 7	: -9;
		
		int valueToAddRightSide = colour ? 9 : -7;
		
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
			&& this.isSquareIdOccupiedByPieceByColour( squareStartId + valueToAddLeftSide, rivalColour )
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
					result.add( squareStartId + valueToAddLeftSide );					
				}
			}
			else {
				result.add( squareStartId + valueToAddLeftSide );
			}
		}
		
		if ( 
			columnStart < 7 
			&& this.isSquareIdOccupiedByPieceByColour( squareStartId + valueToAddRightSide, rivalColour )
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
					result.add( squareStartId + valueToAddRightSide );					
				}
			}
			else {
				result.add( squareStartId + valueToAddRightSide );
			}
		}
		
		//
		// Then we try the normal one square advance
		//
		int valueToAddNormalAdvance = ( colour ? 8 : -8 );
		
		boolean normalAdvancePossible = false;
		
		if ( this.isFreeSquareId( squareStartId + valueToAddNormalAdvance ) ) {
			//
			// Normal advance
			//
			if ( rowStartAppropiateToPromote ) {
				//
				// We have to add 4 equal squares representing the
				// 4 promotion choices (queen, rook, bishop, knight) 
				//
				for ( int i = 0; i < 4; i++ ) {
					result.add( squareStartId + valueToAddNormalAdvance );					
				}
			}
			else {
				result.add( squareStartId + valueToAddNormalAdvance );
			}
			
			normalAdvancePossible = true;
		}
		
		//
		// Then we try the 2 squares initial advance, if it is possible
		//
		if ( normalAdvancePossible ) {
			
			boolean appropiateRowForInitialDoubleAdvance =
				colour
				? rowStart == 1
				: rowStart == 6;
			if ( appropiateRowForInitialDoubleAdvance ) {
				
				int valueToAddInitialDoubleAdvance = ( colour ? 16 : -16 );
				
				if ( this.isFreeSquareId( squareStartId + valueToAddInitialDoubleAdvance ) ) {
					//
					// 2 squares initial advance. Here it is, of course, impossible to promote this pawn
					//
					result.add( squareStartId + valueToAddInitialDoubleAdvance );
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<Integer> getRookMovementEndSquaresIds(
		int squareStartId, 
		boolean colour
	) {
		ArrayList<Integer> result =
			new ArrayList<Integer>();
		
		//
		// We have to search for every free reachable square in the same row or
		// column, and also for every possible to capture rival piece
		//
		
		boolean keepOnSearchingThisDirection = true;
		
		//
		// Going up...
		//
		for ( int i = squareStartId + 8; keepOnSearchingThisDirection && i / 8 < 8; i = i + 8 ) {
			
			if ( this.isFreeSquareId( i ) ) {
				//
				// A free square. We add it
				//
				result.add( i );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareIdOccupiedByPieceByColour(
						i, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( i );
				}
			}
		}
		
		keepOnSearchingThisDirection = true;
		
		//
		// Going down...
		//
		for ( int i = squareStartId - 8; keepOnSearchingThisDirection && i >= 0; i = i - 8 ) {
			
			if ( this.isFreeSquareId( i ) ) {
				//
				// A free square. We add it
				//
				result.add( i );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareIdOccupiedByPieceByColour(
						i, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( i );
				}
			}
		}
		
		keepOnSearchingThisDirection = true;
		
		//
		// Going left...
		//
		for ( int i = squareStartId - 1; keepOnSearchingThisDirection && i >= 0 && i % 8 < 7; i = i - 1 ) {
			
			if ( this.isFreeSquareId( i ) ) {
				//
				// A free square. We add it
				//
				result.add( i );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareIdOccupiedByPieceByColour(
						i, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( i );
				}
			}
		}
		
		keepOnSearchingThisDirection = true;
		
		//
		// Going right...
		//
		for ( int i = squareStartId + 1; keepOnSearchingThisDirection && i % 8 > 0; i = i + 1 ) {
			
			if ( this.isFreeSquareId( i ) ) {
				//
				// A free square. We add it
				//
				result.add( i );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareIdOccupiedByPieceByColour(
						i, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( i );
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<Integer> getBishopMovementEndSquaresIds(
		int squareStartId, 
		boolean colour
	) {
		ArrayList<Integer> result =
			new ArrayList<Integer>();
		
		//
		// We have to search for every free reachable square in the same diagonals
		// in which this bishop is, and also for every possible to capture rival piece
		//
		
		boolean keepOnSearchingThisDirection = true;
		
		//
		// Going up-right...
		//
		for ( int i = squareStartId + 9; keepOnSearchingThisDirection && i / 8 < 8 && i % 8 > 0; i = i + 9 ) {
			
			if ( this.isFreeSquareId( i ) ) {
				//
				// A free square. We add it
				//
				result.add( i );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( ! this.isSquareIdOccupiedByPieceByColour( i, colour ) ) {
					//
					// A square available to capture. We add it
					//
					result.add( i );
				}
			}
		}
		
		keepOnSearchingThisDirection = true;
		
		//
		// Going down-right...
		//
		for ( int i = squareStartId - 7; keepOnSearchingThisDirection && i > 0 && i % 8 > 0; i = i - 7 ) {
			
			if ( this.isFreeSquareId( i ) ) {
				//
				// A free square. We add it
				//
				result.add( i );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( ! this.isSquareIdOccupiedByPieceByColour( i, colour ) ) {
					//
					// A square available to capture. We add it
					//
					result.add( i );
				}
			}
		}
		
		keepOnSearchingThisDirection = true;
		
		//
		// Going down-left...
		//
		for ( int i = squareStartId - 9; keepOnSearchingThisDirection && i >= 0 && i % 8 < 7; i = i - 9 ) {
			
			if ( this.isFreeSquareId( i ) ) {
				//
				// A free square. We add it
				//
				result.add( i );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareIdOccupiedByPieceByColour(
						i, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( i );
				}
			}
		}
		
		keepOnSearchingThisDirection = true;
		
		//
		// Going up-left...
		//
		for ( int i = squareStartId + 7; keepOnSearchingThisDirection && i / 8 < 8 && i % 8 < 7; i = i + 7 ) {
			
			if ( this.isFreeSquareId( i ) ) {
				//
				// A free square. We add it
				//
				result.add( i );
			}
			else {
				
				keepOnSearchingThisDirection = false;
				
				if ( 
					! this.isSquareIdOccupiedByPieceByColour(
						i, 
						colour
					) 
				) {
					//
					// A square available to capture. We add it
					//
					result.add( i );
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<Integer> getKnightMovementEndSquaresIds(
		int squareStartId, 
		boolean colour
	) {
		ArrayList<Integer> result =
			new ArrayList<Integer>();
		
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
			&& ! this.isSquareIdOccupiedByPieceByColour(
				squareEnd1,
				colour
			)
		) {
			result.add( squareEnd1 );
		}
		
		int squareEnd2 =
			squareStartId + 10;
		
		if (
			squareEnd2 / 8 < 8
			&& squareEnd2 % 8 > 1
			&& ! this.isSquareIdOccupiedByPieceByColour(
				squareEnd2,
				colour
			)
		) {
			result.add( squareEnd2 );
		}
		
		int squareEnd3 =
			squareStartId - 6;
		
		if (
			squareEnd3 > 0
			&& squareEnd3 % 8 > 1
			&& ! this.isSquareIdOccupiedByPieceByColour(
				squareEnd3,
				colour
			)
		) {
			result.add( squareEnd3 );
		}
		
		int squareEnd4 =
			squareStartId - 15;
		
		if (
			squareEnd4 > 0
			&& squareEnd4 % 8 > 0
			&& ! this.isSquareIdOccupiedByPieceByColour(
				squareEnd4,
				colour
			)
		) {
			result.add( squareEnd4 );
		}
		
		int squareEnd5 =
			squareStartId - 17;
		
		if (
			squareEnd5 >= 0
			&& squareEnd5 % 8 < 7
			&& ! this.isSquareIdOccupiedByPieceByColour(
				squareEnd5,
				colour
			)
		) {
			result.add( squareEnd5 );
		}
		
		int squareEnd6 =
			squareStartId - 10;
		
		if (
			squareEnd6 >= 0
			&& squareEnd6 % 8 < 6
			&& ! this.isSquareIdOccupiedByPieceByColour(
				squareEnd6,
				colour
			)
		) {
			result.add( squareEnd6 );
		}
		
		int squareEnd7 =
			squareStartId + 6;
		
		if (
			squareEnd7 / 8 < 8
			&& squareEnd7 % 8 < 6
			&& ! this.isSquareIdOccupiedByPieceByColour(
				squareEnd7,
				colour
			)
		) {
			result.add( squareEnd7 );
		}
		
		int squareEnd8 =
			squareStartId + 15;
		
		if (
			squareEnd8 / 8 < 8
			&& squareEnd8 % 8 < 7
			&& ! this.isSquareIdOccupiedByPieceByColour(
				squareEnd8,
				colour
			)
		) {
			result.add( squareEnd8 );
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
		return ( ( piece.getSquareId() / 8 ) == row );
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
		return ( ( piece.getSquareId() % 8 ) == column );
	}
	
	public boolean thereIsASameColourPawnInTheSameColumn( Piece piece ) {
		boolean result = false;  // By default. It can change below, of course
		
		ArrayList<Piece> pieces = ( piece.getColour() ? this.whitePieces : this.blackPieces );
		
		int sizePieces = pieces.size();
		int pieceColumn = piece.getSquareId() % 8;
		
		for ( int i = 0; ( ! result ) && i < sizePieces; i++ ) {
			Piece iesimPiece = pieces.get( i );
			result =
				iesimPiece instanceof Pawn  // It is a pawn
				&& iesimPiece.getSquareId() != piece.getSquareId()  // It is not the piece we received as argument
				&& isPieceOnTheIesimColumn( iesimPiece, pieceColumn );
		}
		
		return result;
	}
	
	public boolean isAnIsolatedPawn( Pawn pawn ) {
		boolean result = true;  // By default. It can change below, of course
		
		ArrayList<Piece> pieces = ( pawn.getColour() ? this.whitePieces	: this.blackPieces );
		
		int sizePieces = pieces.size();
		int pawnColumn = pawn.getSquareId() % 8;
		
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
	
	/**
	 * Detecting a real fully passed pawn requires too much processing energy (time). We are here trying
	 * to detect feasible candidates to be passed pawns. It is recommended to take this into account
	 * when choosing how many bonus points we give to such pawns in the evaluation function
	 * If one carefully evaluates this code below, it is clear that it is kind of arbitrary. You can
	 * judge by yourself when you try with two pawns on the board if this method suits the chess style
	 * you are looking for
	 * 
	 * @param pawn
	 * @return
	 */
	public boolean isCandidateToBePassedPawn( Pawn pawn ) {
		boolean result = true;  // By default, until we find a rival pawn which could be able to stop our pawn
		
		int pawnColumn = pawn.getSquareId() % 8;
		int pawnRow = pawn.getSquareId() / 8;
		
		if ( pawn.getColour() ) {
			// It is a white pawn, so we will have a look at black pawns
			ArrayList<Piece> pieces = this.blackPieces;
			int sizePieces = pieces.size();
			
			for ( int i = 0; result && i < sizePieces; i++ ) {
				Piece iesimPiece = pieces.get( i );
				if ( iesimPiece instanceof Pawn ) {
					int iesimPieceRow = iesimPiece.getSquareId() / 8;
					if ( iesimPieceRow > pawnRow ) {
						int iesimPieceColumn = iesimPiece.getSquareId() % 8;
						if ( iesimPieceColumn == pawnColumn ) {
							result = false;
						}
						else {
							result =
								(
									Math.abs( iesimPieceRow - pawnRow )
									- Math.abs( iesimPieceColumn - pawnColumn )
								) < 1;
						}
					}
				}
			}
		}
		else {
			// It is a black pawn, so we will have a look at white pawns
			ArrayList<Piece> pieces = this.whitePieces;
			int sizePieces = pieces.size();
			
			for ( int i = 0; result && i < sizePieces; i++ ) {
				Piece iesimPiece = pieces.get( i );
				if ( iesimPiece instanceof Pawn ) {
					int iesimPieceRow = iesimPiece.getSquareId() / 8;
					if ( iesimPieceRow < pawnRow ) {
						int iesimPieceColumn = iesimPiece.getSquareId() % 8;
						if ( iesimPieceColumn == pawnColumn ) {
							result = false;
						}
						else {
							result =
								( 
									Math.abs( iesimPieceRow - pawnRow )
									- Math.abs( iesimPieceColumn - pawnColumn )
								) < 1;
						}
					}
				}
			}
		}
		
		return result;
	}
	
	public Piece getPieceBySquareId( int squareId ) {
		return this.matrix[squareId];
	}
	
	public void removePiece(
		Piece piece
	) {
		if ( piece.getColour() ) {
			this.whitePieces.remove( piece );
		}
		else {
			this.blackPieces.remove( piece );
		}
	}

	public void addPiece( Piece piece ) {
		if ( piece.getColour() ) {
			this.whitePieces.add( piece );
		}
		else {
			this.blackPieces.add( piece	);
		}
	}
	
	public void addPiece( Piece piece, int index ) {
		if ( piece.getColour() ) {
			this.whitePieces.add( index, piece );
		}
		else {
			this.blackPieces.add( index, piece );
		}
	}
	
	public ArrayList<Rook> getRooks( boolean colour ) {
		
		ArrayList<Rook> result = new ArrayList<Rook>();
		
		ArrayList<Piece> colourPieces = ( colour ? this.getWhitePieces(): this.getBlackPieces() );
		
		// There might be one, two, three or whaveter number of rooks on the board, because of promotions...	
			
		for ( int i = 0; i < colourPieces.size(); i++ ) {
			if ( colourPieces.get( i ) instanceof Rook ) {
				result.add( ( Rook )( colourPieces.get( i ) ) );
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
}
