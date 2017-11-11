/**
 * 
 */
package com.miguel.games.chess.common;

/**
 * @author mdelgado
 *
 */
public final class Constants {
	
	//
	// Colours
	//
	public final static int WHITE_COLOUR = 0;
	public final static int BLACK_COLOUR = 1;
	
	// Human player colour
	public final static int HUMAN_PLAYING_WHITE = 0;
	public final static int HUMAN_PLAYING_BLACK = 1;
	
	//
	// Pieces visualizing
	//
	public final static String WHITE_KING_VISUALIZE = "WK";
	public final static String WHITE_QUEEN_VISUALIZE = "WQ";
	public final static String WHITE_ROOK_VISUALIZE = "WR";
	public final static String WHITE_BISHOP_VISUALIZE = "WB";
	public final static String WHITE_KNIGHT_VISUALIZE = "WN";
	public final static String WHITE_PAWN_VISUALIZE = "WP";
	
	public final static String BLACK_KING_VISUALIZE = "BK";
	public final static String BLACK_QUEEN_VISUALIZE = "BQ";
	public final static String BLACK_ROOK_VISUALIZE = "BR";
	public final static String BLACK_BISHOP_VISUALIZE = "BB";
	public final static String BLACK_KNIGHT_VISUALIZE = "BN";
	public final static String BLACK_PAWN_VISUALIZE = "BP";
	
	public final static String EMPTY_SQUARE_VISUALIZE = "--";
	
	//
	// Pieces values, according to Larry Kaufmann's evaluation function (2012)
	// https://chessprogramming.wikispaces.com/Point+Value
	//
	public final static int KING_VALUE = 20000;
	public final static int QUEEN_VALUE = 1000;
	public final static int ROOK_VALUE = 525;
	public final static int BISHOP_VALUE = 350;
	public final static int KNIGHT_VALUE = 350;
	public final static int PAWN_VALUE = 100;
	
	public final static int OPENING_LIMIT_FULL_MOVE_COUNTER = 14;
	public final static int MIDDLE_GAME_LIMIT_FULL_MOVE_COUNTER = 45;
	
	//
	// Special values for special circumstances
	//
	
	// Positive circumstances
	public final static int CASTLED_KING_OPENING_AND_MIDDLE_GAME = 95;
	public final static int CASTLED_KING_ENDINGS = 5;
	public final static int NOT_CASTLED_KING_BUT_STILL_POSSIBLE_TO_CASTLE_OPENING_AND_MIDDLE_GAME = 10;
	public final static int SEVENTH_ROW_ADVANCED_PAWN = 45;
	public final static int SIXTH_ROW_ADVANCED_PAWN = 15;
	public final static int CENTRALIZED_PIECE_FIRST_CLASS = 45;
	public final static int CENTRALIZED_PIECE_SECOND_CLASS = 20;
	public final static int DEVELOPED_MEDIUM_WEIGHT_PIECE_DURING_OPENING = 40;
	public final static int BISHOPS_PAIR_WITH_MANY_PIECES_ON_THE_BOARD = 10;
	public final static int BISHOPS_PAIR_WITH_FEW_PIECES_ON_THE_BOARD = 20;
	public final static int BISHOPS_PAIR_MATERIAL_ON_THE_BOARD_BOUND = 16;
	public final static int KNIGHT_NOT_ON_A_OR_H_COLUMNS_DURING_THE_OPENING_AND_MIDDLE_GAME = 20;
	public final static int KING_NOT_WEIRDLY_AND_SURELY_TOO_EARLY_MOVED = 30;
	public final static int RIVAL_KING_CHECKED_OPENING_AND_MIDDLE_GAME = 15;
	public final static int RIVAL_KING_CHECKED_ENDING = 25;
	public final static int ROOK_ON_ITS_SEVENTH_ROW_AND_RIVAL_KING_ON_THE_EIGHTH_ROW = 30;
	public final static int ROOKS_CONNECTED_ON_THE_FIRST_ROW = 20;
	public static final int KNIGHT_ATTACKING_ANY_OF_THE_CENTRAL_SQUARES_DURING_OPENING = 15;
	public static final int BISHOP_ATTACKING_ANY_OF_THE_CENTRAL_SQUARES_DURING_OPENING = 10;
	public static final int ROOK_ATTACKING_ANY_OF_THE_CENTRAL_SQUARES_DURING_OPENING = 5;
	
	// Negative circumstances
	public final static int NOT_CASTLED_KING_AND_IMPOSSIBLE_TO_CASTLE_OPENING_AND_MIDDLE_GAME = 60;
	public static final int DOUBLED_PAWN = 10;
	public static final int ISOLATED_PAWN = 25;
	public static final int DOUBLED_AND_ISOLATED_PAWN_BONUS = 15;
	
	public final static int KING_MATE = Integer.MAX_VALUE;
	public final static int KING_MATE_IN_ONE = Integer.MAX_VALUE - 1;
	
	//
	// Initial ranges for movement list sorting
	//
	public final static int PRINCIPAL_VARIATION_MOVEMENT_ORDER = 10000;
	public final static int CAPTURE_AND_PAWN_PROMOTION_ORDER = 100000;
	public final static int NON_CAPTURE_AND_PAWN_PROMOTION_ORDER = 200000;
	public final static int CASTLE_ORDER = 300000;
	public final static int GENERIC_CAPTURE_ORDER = 400000;
	public final static int NON_CAPTURE_BISHOP_OR_KNIGHT_MOVEMENT_ORDER = 499995;
	public final static int NON_CAPTURE_QUEEN_MOVEMENT_ORDER = 499996;
	public final static int NON_CAPTURE_ROOK_MOVEMENT_ORDER = 499997;
	public final static int NON_CAPTURE_PAWN_MOVEMENT_ORDER = 499998;
	public final static int NON_CAPTURE_KING_MOVEMENT_ORDER = 499999;
	
	//
	// Result
	//
	public final static int WHITE_WINS = 0;
	public final static int BLACK_WINS = 1;
	public final static int DRAW = 2;
	public final static int STILL_PLAYING = 3;
	
	//
	// Time
	//
	public final static int NORMAL_TIME = 10000000;
	public final static int REDUCED_TIME = 100000;
	
	//
	// Game mode
	//
	public final static int NORMAL_GAME_MODE = 0;
	public final static int POSITION_ANALYSIS_MODE = 1;
	
	//
	// Board
	//
	public final static int BOARD_ROWS_MIN_INDEX = 0;
	public final static int BOARD_COLUMNS_MIN_INDEX = 0;
	public final static int BOARD_ROWS_MAX_INDEX = 7;
	public final static int BOARD_COLUMNS_MAX_INDEX = 7;
	
	public final static int BOARD_MIN_SQUARE_INDEX = 0;
	public final static int BOARD_MAX_SQUARE_INDEX = 63;
	
}
