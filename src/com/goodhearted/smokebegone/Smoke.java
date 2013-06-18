package com.goodhearted.smokebegone;

import java.sql.Date;

public class Smoke {
	private long id;
	private Date date;
	private long date_int;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDateInt() {
		return date_int;
	}
	public Date getDateDate() {
		return date;
	}

	public void setDate(long l) {
		this.date_int = l;
		this.date = new Date(this.date_int);
	}

	public String toString() {
		return this.date.toString();
	}
}
