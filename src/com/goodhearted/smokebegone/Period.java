package com.goodhearted.smokebegone;

public class Period {
	public static final int second = 1000;
	public static final int minute = second * 60;
	public static final int hour = minute * 60;
	public static final int day = hour * 24;

	private int seconds;
	private int minutes;
	private int hours;
	private int days;
	private int miliseconds;

	private long period;

	public Period(long ms) {
		setPeriod(ms);
		calculate();
	}

	public Period(long ms, long ms2) {
		long p = ms2 - ms;
		if (p < 0)
			p *= -1;
		setPeriod(p);
		calculate();
	}

	
	public int getSeconds() { return this.seconds; }
	public int getMinutes() { return this.minutes; }
	public int getHours() { return this.hours; }
	public int getDays() { return this.days; }
	public int getMiliseconds() { return this.miliseconds; }
	
	
	public int[] getAll() {
		int[] returner = new int[5];
		returner[0] = this.miliseconds;
		returner[1] = this.seconds;
		returner[2] = this.minutes;
		returner[3] = this.hours;
		returner[4] = this.days;
		return returner;
	}
	
	
	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
		calculate();
	}

	private void calculate() {
		long rest = period;
		// calculate days
		this.days = (int) ((period - period % day) / day);
		rest = rest - (this.days * day);

		// calculate hours
		this.hours = (int) ((rest - rest % hour) / hour);
		rest = rest - (this.hours * hour);

		// calculate minutes

		this.minutes = (int) ((rest - rest % minute) / minute);
		rest = rest - (this.minutes * minute);

		// calculate seconds
		this.seconds = (int) ((rest - rest % second) / second);
		rest = rest - (this.seconds * second);
		
		//save miliseconds
		this.miliseconds = (int) rest;
	}
	
	public String getString()
	{
		if(this.days == 0 && this.hours == 0)
		{
			return this.minutes + " minuten en " + this.seconds + " seconden";
		}
		else if(this.days == 0) {
			return this.hours + " uur en " + this.minutes + " minuten";
		} else {
			if(this.days > 1) {
				return this.days + " dagen en " + this.hours + " uur";
			}
			else {
				return this.days + " dag en " + this.hours + " uur";
			}
		}
	}
}
