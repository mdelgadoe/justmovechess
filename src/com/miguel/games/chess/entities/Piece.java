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
	
	private int colour;
	private int squareId;
	
	public Piece(int colour, int squareId) {
		super();
		this.colour = colour;
		this.squareId = squareId;
	}
	
	public abstract int getHeuristicValue( int fullMoveCounter );
	
	public abstract boolean isAttackingSquareId( int squareId, Board board );
	
	public abstract ArrayList<Movement> getLegalMovements( Position position, Movement principalVariationMovement );
	
	public abstract String toUCIString();
	
	public int getColour() {
		return colour;
	}
	public void setColour(int colour) {
		this.colour = colour;
	}
	public int getSquareId() {
		return squareId;
	}
	public void setSquareId(int squareId) {
		this.squareId = squareId;
	}

}
