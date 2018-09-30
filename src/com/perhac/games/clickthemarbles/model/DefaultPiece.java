package com.perhac.games.clickthemarbles.model;

public class DefaultPiece implements Piece {

    public DefaultPiece() {
    }

    @Override
    public boolean isSameAs(Piece marble2) {
	return false;
    }

    @Override
    public Integer getId() {
	return 0;
    }

}