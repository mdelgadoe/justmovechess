package com.miguel.games.chess.uci;

import com.miguel.games.chess.entities.Position;

public class GameUCI {
	
	public void initialize() {
		
		Position position = new Position();
		
		UCI.uciCommunication( position );
		
	}
}
