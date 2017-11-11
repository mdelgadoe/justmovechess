package com.miguel.games.chess.uci;

import java.util.ArrayList;
import java.util.Scanner;

import com.miguel.games.chess.engine.Engine;
import com.miguel.games.chess.entities.Movement;
import com.miguel.games.chess.entities.Position;
import com.miguel.games.chess.fen.FenUtilities;

public class UCI {
	
	/**
	 * Method that takes care of UCI communication tasks
	 */
	public static void uciCommunication( Position position ) {
		
		boolean gameInitialized = false;
		
		while ( true ) {
			Scanner input = new Scanner( System.in );
			
			String inputString = input.nextLine();
			
			System.out.println( "Recibo mensaje: " + inputString );
			
			if (
				inputString != null
				&& inputString.equals( "uci" )
			) {
				uciIdentificationInformation();
			}
			else if (
				inputString != null
				&& inputString.startsWith( "setoption" )
			) {
				//
				// TODO: maybe dealing with options
				//
			}
			else if (
				inputString != null
				&& inputString.equals( "isready" )
			) {
				uciIsReady();
			}
			else if (
				inputString != null
				&& inputString.equals( "ucinewgame" )
			) {
				gameInitialized = false;
			}
			else if (
				inputString != null
				&& inputString.startsWith( "position" )
			) {
				String afterPositionInputString = inputString.substring( 9 );
				if (
					afterPositionInputString.contains( "startpos" )
					&& ( ! gameInitialized )
				) {
					position =
						FenUtilities.getPosition( "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1" );
					
					gameInitialized = true;
				}
				else if (
					inputString.contains( "fen" )
					&& ( ! gameInitialized )
				) {
					position =
						FenUtilities.getPosition( afterPositionInputString.substring( 13 ) );
					
					gameInitialized = true;
				}
				
				if ( inputString.contains( "moves" ) ) {
					
					//
					// Arena sends us the move received from a human user or another engine together with
					// the whole game movement list. At the moment, i think that, to play normal games vs
					// humans or engines, it should be enough to execute the last movement in the list, as
					// we are keeping track of the position before that last movement received
					//
					
					String uciMovementsString =
						afterPositionInputString.substring( afterPositionInputString.indexOf( "moves" ) + 6 );
					
					if (
						uciMovementsString != null
						&& uciMovementsString.length() > 0
					) {
						String[] uciMovements = uciMovementsString.split( " " );
						
						String lastMovementString = uciMovements[uciMovements.length - 1];
						
						ArrayList<Movement> legalMovements = position.getLegalMovements( false, null );
						
						if (
							legalMovements != null
							&& legalMovements.size() > 0
						) {
							// We get the start and end positions
							String startSquareAlgebraicNotation = lastMovementString.substring( 0, 2 );
							String endSquareAlgebraicNotation = lastMovementString.substring( 2, 4 );
							String uciPromotionChoiceString = "";
							
							if ( lastMovementString.length() == 5 ) {
								// Get promotion choice
								uciPromotionChoiceString = lastMovementString.substring( 4 );
							}
							
							boolean uciMovementAmongLegalMovements = false;
							Movement movement = null;
							for ( int i = 0; i < legalMovements.size() && ( ! uciMovementAmongLegalMovements ); i++ ) {
								if (
									legalMovements.get( i ).isAMovementDefinedByAlgebraicNotationStartAndEndSquares(
										startSquareAlgebraicNotation,
										endSquareAlgebraicNotation,
										uciPromotionChoiceString
									)
								) {
									uciMovementAmongLegalMovements = true;
									movement = legalMovements.get( i );
								}
							}
							if ( movement != null ) {
								Engine.move(
									position,
									movement,
									false,
									false
								);
								
								position.changeTurn();
							}
						}
						
//						for ( String uciMovementString : uciMovements ) {
//							
//							ArrayList<Movement> legalMovements = position.getLegalMovements();
//							
//							if (
//								legalMovements != null
//								&& legalMovements.size() > 0
//							) {
//								// We get the start and end positions
//								String startSquareAlgebraicNotation = uciMovementString.substring( 0, 2 );
//								String endSquareAlgebraicNotation = uciMovementString.substring( 2, 4 );
//								String uciPromotionChoiceString = "";
//								
//								if ( uciMovementString.length() == 5 ) {
//									// Get promotion choice
//									uciPromotionChoiceString = uciMovementString.substring( 4 );
//								}
//								
//								boolean uciMovementAmongLegalMovements = false;
//								Movement movement = null;
//								for ( int i = 0; i < legalMovements.size() && ( ! uciMovementAmongLegalMovements ); i++ ) {
//									if (
//										legalMovements.get( i ).isAMovementDefinedByAlgebraicNotationStartAndEndSquares(
//											startSquareAlgebraicNotation,
//											endSquareAlgebraicNotation,
//											uciPromotionChoiceString
//										)
//									) {
//										uciMovementAmongLegalMovements = true;
//										movement = legalMovements.get( i );
//									}
//								}
//								if ( movement != null ) {
//									Engine.move(
//										position,
//										movement,
//										false
//									);
//									
//									position.changeTurn();
//								}
//							}
//						}
					}
				}
			}
			else if (
				inputString != null
				&& inputString.startsWith( "go" )
			) {
				uciGo( position );
			}
		}
	}

	/**
	 * Method that performs a search for the best move the engine can find
	 */
	private static void uciGo( Position position ) {
		Engine.move(
			position,
			null,
			true,
			false
		);
		
		position.changeTurn();
	}

	/**
	 * This method prints the i-am-ready uci command
	 */
	private static void uciIsReady() {
		System.out.println( "readyok" );
	}

	/**
	 * This method prints the engine identification information
	 */
	private static void uciIdentificationInformation() {
//		System.out.println( "id name " + Engine.ENGINE_NAME );
//		System.out.println( "id author " + Engine.ENGINE_AUTHOR );
		
		System.out.println( "id name JustMoveChess" );
		System.out.println( "id author Miguel Delgado Esteban" );
		
		//
		// TODO: maybe implementing options here
		//
		
		System.out.println( "uciok" );
	}
}