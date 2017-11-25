/**
 * 
 */
package com.miguel.games.entities;

/**
 * @author mdelgado
 *
 */
public abstract class Movement {

	private int squareStartId;
	private int squareEndId;
	private Piece piece;
	
	public Piece getPiece() {
		return piece;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	public int getSquareStartId() {
		return squareStartId;
	}
	public void setSquareStartId(int squareStartId) {
		this.squareStartId = squareStartId;
	}
	public int getSquareEndId() {
		return squareEndId;
	}
	public void setSquareEndId(int squareEndId) {
		this.squareEndId = squareEndId;
	}
	
}
