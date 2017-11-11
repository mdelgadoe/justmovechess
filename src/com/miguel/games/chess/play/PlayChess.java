/**
 * 
 */
package com.miguel.games.chess.play;

import com.miguel.games.chess.engine.Game;
import com.miguel.games.chess.uci.GameUCI;

/**
 * @author mdelgado
 *
 */
public class PlayChess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println( "Play chess: START" );
		
		boolean uci = false;
		
		long currentTimeMillisecondsStart = System.currentTimeMillis();
		
		if ( uci ) {
			GameUCI gameUCI = new GameUCI();
			gameUCI.initialize();
		}
		else {
			Game game = new Game();
			game.initialize();
		}
		
		long currentTimeMillisecondsEnd = System.currentTimeMillis();
		
		System.out.println(
			"Total time: " +
			( ( currentTimeMillisecondsEnd - currentTimeMillisecondsStart ) / 1000 )
			+ " s."
		);
		
		System.out.println( "Play chess: END" );
	}

}
