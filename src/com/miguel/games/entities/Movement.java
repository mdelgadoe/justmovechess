/**
 * 
 */
package com.miguel.games.entities;

/**
 * @author mdelgado
 *
 */
public abstract class Movement {

	private Square squareStart;
	private Square squareEnd;
	private Piece piece;
	
	public Piece getPiece() {
		return piece;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	public Square getSquareStart() {
		return squareStart;
	}
	public void setSquareStart(Square squareStart) {
		this.squareStart = squareStart;
	}
	public Square getSquareEnd() {
		return squareEnd;
	}
	public void setSquareEnd(Square squareEnd) {
		this.squareEnd = squareEnd;
	}
	
}
