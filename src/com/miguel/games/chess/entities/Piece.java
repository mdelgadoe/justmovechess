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
	private Square square;
	
	public Piece(int colour, int squareId) {
		super();
		this.colour = colour;
		this.square = new Square( squareId );
	}
	
	public abstract int getHeuristicValue();
	
	public abstract boolean isAttackingSquare( Square square, Board board );
	
	public abstract ArrayList<Movement> getLegalMovements( Position position, Movement principalVariationMovement );
	
	public abstract String toUCIString();
	
	public int getColour() {
		return colour;
	}
	public void setColour(int colour) {
		this.colour = colour;
	}
	public Square getSquare() {
		return square;
	}
	public void setSquare(Square square) {
		this.square = square;
	}

}
