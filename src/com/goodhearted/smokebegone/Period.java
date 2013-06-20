package com.goodhearted.smokebegone;

import java.math.BigDecimal;

import android.content.Context;
import android.util.Log;

public class Period {
	public static final int second = 1000;
	public static final int minute = second * 60;
	public static final int hour = minute * 60;
	public static final int day = hour * 24;
	public static final int year = 365 * day;

	// http://whyquit.com/whyquit/a_benefits_time_table.html
	public static final int[] benefits = { 20 * minute, // Your blood pressure,
														// pulse rate and the
														// temperature of your
														// hands and feet have
														// returned to normal.
			8 * hour, // Remaining nicotine in your bloodstream will have fallen
						// to 6.25% of normal peak daily levels, a 93.75%
						// reduction.
			12 * hour, // Your blood oxygen level will have increased to normal
						// and carbon monoxide levels will have dropped to
						// normal.
			24 * hour, // Anxieties have peaked in intensity and within two
						// weeks should return to near pre-cessation levels.
			48 * hour, // Damaged nerve endings have started to regrow and your
						// sense of smell and taste are beginning to return to
						// normal. Cessation anger and irritability will have
						// peaked.
			72 * hour, // Your entire body will test 100% nicotine-free and over
						// 90% of all nicotine metabolites (the chemicals it
						// breaks down into) will now have passed from your body
						// via your urine.
			14 * day, // Recovery has likely progressed to the point where your
						// addiction is no longer doing the talking. Blood
						// circulation in your gums and teeth are now similar to
						// that of a non-user.
			21 * day, // Brain acetylcholine receptor counts that were
						// up-regulated in response to nicotine's presence have
						// now down-regulated and receptor binding has returned
						// to levels seen in the brains of non-smokers.
			56 * day // Your circulation has substantially improved. Walking has
						// become easier. Your chronic cough, if any, has likely
						// disappeared.
	};

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

	public int getSeconds() {
		return this.seconds;
	}

	public int getMinutes() {
		return this.minutes;
	}

	public int getHours() {
		return this.hours;
	}

	public int getDays() {
		return this.days;
	}

	public int getMiliseconds() {
		return this.miliseconds;
	}

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

		// save miliseconds
		this.miliseconds = (int) rest;
	}

	public String getString() {
		if (this.days == 0 && this.hours == 0) {
			return this.minutes + " minuten en " + this.seconds + " seconden";
		} else if (this.days == 0) {
			return this.hours + " uur en " + this.minutes + " minuten";
		} else {
			if (this.days > 1) {
				return this.days + " dagen en " + this.hours + " uur";
			} else {
				return this.days + " dag en " + this.hours + " uur";
			}
		}
	}

	public float getSave(Context c, int smoked) {
		int cpp = (PreferenceProvider.readInteger(c, PreferenceProvider.keyCPP,
				-1));

		int cpd = (PreferenceProvider.readInteger(c, PreferenceProvider.keyCPD,
				-1));
		float ppp = (PreferenceProvider.readFloat(c, PreferenceProvider.keyPPP,
				-1.0f));

		float p = day / cpd;
		float pricepercig = ppp / cpp;
		float numberofcigssaved = this.period / p - smoked;

		return (pricepercig * numberofcigssaved);
	}
	
	public int getHealthBenefitProgress(int option)
	{
		BigDecimal i = new BigDecimal( this.period / benefits[option] * 100).setScale(5, BigDecimal.ROUND_HALF_UP);
		int j = i.intValue();
		if(j > 100) return 100;
		return j;
		
	}
}
