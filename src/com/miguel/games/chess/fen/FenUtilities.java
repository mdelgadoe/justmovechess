package com.miguel.games.chess.fen;

import java.util.ArrayList;
import java.util.HashMap;

import com.miguel.games.chess.common.Constants;
import com.miguel.games.chess.engine.Engine;
import com.miguel.games.chess.entities.Bishop;
import com.miguel.games.chess.entities.King;
import com.miguel.games.chess.entities.Knight;
import com.miguel.games.chess.entities.Pawn;
import com.miguel.games.chess.entities.Piece;
import com.miguel.games.chess.entities.Position;
import com.miguel.games.chess.entities.Queen;
import com.miguel.games.chess.entities.Rook;
import com.miguel.games.chess.utils.AlgebraicNotationUtils;

public class FenUtilities {

	public static Position getPosition(String fenString) {
		
		//
		// This method receives a FEN string representing a chess position
		// and returns the associated position in our model
		//
		
		//
		// We get the items in the FEN string
		// 
		String[] itemsFenString =
			fenString.split( " " );
		
		String piecesString =
			itemsFenString[0];
		
		String turnString =
			itemsFenString[1];
		
		String castlePermissionsString =
			itemsFenString[2];
		
		String enPassantTargetString =
			itemsFenString[3];
		
		String halfMoveClockString =
			itemsFenString[4];
		
		String fullMoveClockString =
			itemsFenString[5];
		
		//
		// Then we begin building the position
		//
		Position result =
			new Position();
		
		//
		// Turn
		// 
		boolean turn = ( turnString.equals( "w" ) ) ? true : false;
		result.setTurn( turn );
		
		//
		// Castle permissions
		//
		result.setWhiteShortCastleAllowed(
			castlePermissionsString.indexOf( "K" ) != -1
		);
		result.setWhiteLongCastleAllowed(
			castlePermissionsString.indexOf( "Q" ) != -1
		);
		result.setBlackShortCastleAllowed(
			castlePermissionsString.indexOf( "k" ) != -1
		);
		result.setBlackLongCastleAllowed(
			castlePermissionsString.indexOf( "q" ) != -1
		);
		
		//
		// EnPassant target
		//
		if ( ! enPassantTargetString.equals( "-" ) ) {
			
			result.setEnPassantTargetId(
				AlgebraicNotationUtils.coordinatesToSquareId(
					enPassantTargetString
				)
			);
		}
		
		//
		// Half move clock
		//
		result.setHalfMoveClock(
			Integer.parseInt(
				halfMoveClockString
			)
		);
		
		//
		// Full move counter
		//
		result.setFullMoveCounter(
			Integer.parseInt(
				fullMoveClockString
			)
		);
		
		//
		// Result
		//
		result.setResult( Constants.STILL_PLAYING );
		
		//
		// The most complex: pieces and board
		//
		
		ArrayList<Piece> whitePieces =
			new ArrayList<Piece>();
		
		ArrayList<Piece> blackPieces =
			new ArrayList<Piece>();
		
		HashMap<Integer, Boolean> freeSquares =
			new HashMap<Integer, Boolean>();
		
		String[] rows =
			piecesString.split( "/" );
		
		int rowIndex = 7;
		
		for ( int i = 0; i < rows.length; i++ ) {
			
			String row = rows[i];
			
			int squareId = rowIndex * 8;
			
			for ( int j = 0; j < row.length(); j++ ) {
				
				char c = row.charAt( j );
				
				if ( Character.isDigit( c ) ) {
					
					int numberOfFreeSpaces =
						Character.getNumericValue( c );
					
					for ( int a = 0; a < numberOfFreeSpaces; a++ ) {
						
						freeSquares.put( squareId, true );
						
						squareId++;
					}
				}
				else {
					switch ( c ) {
						case 'K':
							whitePieces.add( 0, new King( true, squareId ) );
							break;
						case 'Q':
							whitePieces.add( new Queen( true, squareId ) );
							break;
						case 'R':
							whitePieces.add( new Rook( true, squareId ) );
							break;
						case 'B':
							whitePieces.add( new Bishop( true, squareId ) );
							break;
						case 'N':
							whitePieces.add( new Knight( true, squareId ) );
							break;
						case 'P':
							whitePieces.add( new Pawn( true, squareId ) );
							break;
						case 'k':
							blackPieces.add( 0, new King( false, squareId ) );
							break;
						case 'q':
							blackPieces.add( new Queen( false, squareId ) );
							break;
						case 'r':
							blackPieces.add( new Rook( false, squareId ) );
							break;
						case 'b':
							blackPieces.add( new Bishop( false, squareId ) );
							break;
						case 'n':
							blackPieces.add( new Knight( false, squareId ) );
							break;
						case 'p':
							blackPieces.add( new Pawn( false, squareId ) );
							break;
					}
					
					squareId++;
				}
			}
			
			rowIndex--;
		}
		
		result.getBoard().setWhitePieces(
			whitePieces 
		);
		
		result.getBoard().setBlackPieces(
			blackPieces
		);
		
		//
		// Static position evaluation
		//
		Engine.evaluatePositionVersionZero(
			result, 
			turn
		);
		
		//
		// King checked or not
		//
		if ( result.getTurn() ) {
			
			result.setKingChecked(
				result.isCheck(	true )
			);
		}
		else {
			result.setKingChecked(
				result.isCheck(	false )
			);
		}
		
		return result;
		
	}

}
