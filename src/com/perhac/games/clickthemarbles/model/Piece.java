package com.perhac.games.clickthemarbles.model;

public interface Piece {

    Integer getId();

    boolean isSameAs(Piece piece2);

    public static final Piece NONE = new DefaultPiece();

}