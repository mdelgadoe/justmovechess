/**
 * 
 */
package com.miguel.games.chess.entities;

import java.util.ArrayList;

/**
 * @author mdelgado
 *
 */
public abstract class Piece extends com.miguel.games.entities.Piece {
	
	private boolean colour;
	private int squareId;
	
	public Piece(boolean colour, int squareId) {
		super();
		this.colour = colour;
		this.squareId = squareId;
	}
	
	public abstract int getHeuristicValue( int fullMoveCounter );
	
	public abstract boolean isAttackingSquareId( int squareId, Board board );
	
	public abstract ArrayList<Movement> getLegalMovements( Position position, Movement principalVariationMovement );
	
	public abstract String toUCIString();
	
	public boolean getColour() {
		return colour;
	}
	public void setColour(boolean colour) {
		this.colour = colour;
	}
	public int getSquareId() {
		return squareId;
	}
	public void setSquareId(int squareId) {
		this.squareId = squareId;
	}

}
