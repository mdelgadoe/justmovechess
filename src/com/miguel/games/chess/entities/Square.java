/**
 * 
 */
package com.miguel.games.chess.entities;

import com.miguel.games.chess.utils.AlgebraicNotationUtils;

/**
 * @author mdelgado
 *
 */
public class Square extends com.miguel.games.entities.Square {

	private int id;
	
	public Square(int id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.miguel.chessbm.entities.Square#draw()
	 */
	@Override
	public String toString() {
		
		return "" + this.id;
	}
	
	public String toUCIString() {
		return AlgebraicNotationUtils.squareIdToCoordinates( this.id );
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
