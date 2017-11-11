package com.miguel.games.chess.utils;

import java.util.Comparator;

import com.miguel.games.chess.entities.Movement;

public class MovementsComparator implements Comparator<Movement> {

	/* (non-Javadoc)
	 * This method compares two movements in the sense of trying to place capture movements
	 * first, and specially if capturing piece has a low value and captured piece has
	 * a high value
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare( Movement m1, Movement m2 ) {
		
		int result = 0;  // By default, all movements are created equal :)
		
		if ( m1.getOrder() < m2.getOrder() ) {
			result = -1;
		}
		else if ( m1.getOrder() > m2.getOrder() ) {
			result = 1;
		}
		
		return result;
	}

}
