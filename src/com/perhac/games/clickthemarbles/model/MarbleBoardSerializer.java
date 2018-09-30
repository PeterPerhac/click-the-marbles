package com.perhac.games.clickthemarbles.model;

import com.perhac.games.clickthemarbles.game.GameConfig;

public class MarbleBoardSerializer {

    private final GameConfig config = GameConfig.getInstance();

    public String boardToString(MarbleBoard board) {
	StringBuilder sb = new StringBuilder();
	for (int x = 0; x < config.NUMBER_OF_COLUMNS; x++) {
	    for (int y = 0; y < config.NUMBER_OF_ROWS; y++) {
		PositionOnBoard positionOnBoard = new PositionOnBoard(x, y);
		if (!board.isPositionAvailable(positionOnBoard)) {
		    Marble marbleAtPosition = (Marble) board
			    .getPieceAtPosition(positionOnBoard);
		    sb.append(marbleAtPosition.toString()).append(",");
		} else {
		    sb.append("NONE,");
		}
	    }
	    sb.append("\n");
	}
	return sb.toString();
    }

    public MarbleBoard boardFromString(String gameState) {
	MarbleBoard board = new MarbleBoard();
	String[] columns = gameState.split("\\n");
	for (int x = 0; x < config.NUMBER_OF_COLUMNS; x++) {
	    String[] rows = columns[x].split(",");
	    for (int y = 0; y < config.NUMBER_OF_ROWS; y++) {
		String pieceTypeName = rows[y];
		if (!pieceTypeName.equals("NONE")) {
		    board.addPiece(
			    new Marble(MarbleType.valueOf(pieceTypeName)),
			    new PositionOnBoard(x, y));
		}
	    }
	}
	return board;
    }

}
