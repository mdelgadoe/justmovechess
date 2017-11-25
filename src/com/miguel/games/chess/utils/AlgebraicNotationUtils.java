package com.miguel.games.chess.utils;

public class AlgebraicNotationUtils {

	public static int coordinatesToSquareId(
		String algebraicCoordinate
	) {
		//
		// This method returns the 0 - 63 number corresponding to a 
		// correct algebraic chess coordinate received as parameter
		//
		
		int result = 0; 
		
		String columnString =
			algebraicCoordinate.substring(
				0,
				1
			);
		
		int column;
		
		if ( columnString.equals( "a" ) ) {
			column = 0;
		}
		else if ( columnString.equals( "b" ) ) {
			column = 1;
		}
		else if ( columnString.equals( "c" ) ) {
			column = 2;
		}
		else if ( columnString.equals( "d" ) ) {
			column = 3;
		}
		else if ( columnString.equals( "e" ) ) {
			column = 4;
		}
		else if ( columnString.equals( "f" ) ) {
			column = 5;
		}
		else if ( columnString.equals( "g" ) ) {
			column = 6;
		}
		else if ( columnString.equals( "h" ) ) {
			column = 7;
		}
		else {
			column = 0; // Error situation
		}
		
		int row =
			Integer.parseInt(
				algebraicCoordinate.substring(
					1,
					2
				)
			) - 1;
		
		result =
			8 * row + column;
		
		return result;
	}
	
	public static String squareIdToCoordinates(
		int squareId
	) {
		//
		// This method returns the algebraic chess coordinate that corresponds
		// to the squareId received
		//
		int row = ( squareId / 8 ) + 1;
		
		int column = squareId % 8;
		
		String columnString = "";
		
		if ( column == 0 ) {
			columnString = "a";
		}
		else if ( column == 1 ) {
			columnString = "b";
		}
		else if ( column == 2 ) {
			columnString = "c";
		}
		else if ( column == 3 ) {
			columnString = "d";
		}
		else if ( column == 4 ) {
			columnString = "e";
		}
		else if ( column == 5 ) {
			columnString = "f";
		}
		else if ( column == 6 ) {
			columnString = "g";
		}
		else if ( column == 7 ) {
			columnString = "h";
		}
		else {
			columnString = "Error"; // Error situation
		}
		
		return
			columnString + row;
	}

}
