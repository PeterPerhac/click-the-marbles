package com.perhac.games.clickthemarbles.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Marble implements Piece {

    private static AtomicInteger idGenerator = new AtomicInteger(0);

    private final MarbleType type;
    private final Integer id;

    public Marble(MarbleType type) {
	this.type = type;
	id = idGenerator.incrementAndGet();
    }

    @Override
    public boolean isSameAs(Piece piece2) {
	if (piece2 == null || !(piece2 instanceof Marble)) {
	    return false;
	}
	return type.equals(((Marble) piece2).getType());
    }

    @Override
    public Integer getId() {
	return id;
    }

    public MarbleType getType() {
	return type;
    }

    @Override
    public String toString() {
	return type.name();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null || getClass() != obj.getClass())
	    return false;
	Marble other = (Marble) obj;
	return other.id.equals(this.id);
    }

}
