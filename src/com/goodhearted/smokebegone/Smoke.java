package com.goodhearted.smokebegone;

import java.sql.Date;

public class Smoke {
	private long id;
	private Date date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String toString() {
		return this.date.toString();
	}
}
