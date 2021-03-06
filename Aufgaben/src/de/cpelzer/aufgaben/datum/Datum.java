package de.cpelzer.aufgaben.datum;

/**
 * Datum class
 * 
 * @author Christoph Pelzer on 11.10.17
 *
 */
public class Datum {
	/**
	 * the length of each month
	 */
	public static final int[] monatslaengen = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static final int[] monatslaengenLeap = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * the name of the days of a week
	 */
	public static final String[] wochentage = { "So", "Mo", "Di", "Mi", "Do", "Fr", "Sa" };

	/**
	 * holds the values for "Datum"
	 */
	private int tag, monat, jahr;

	/**
	 * MonatsLaenge (the length of a month)
	 * 
	 * @param monat
	 *            takes one month as parameter
	 * @param jahr
	 *            takes the year as parameter
	 * @return returns the length of the month.
	 */
	public static int getMonatslaenge(int monat, int jahr) {
		if (isSchaltjahr(jahr)) {
			return monatslaengenLeap[monat - 1];
		} else {
			return monatslaengen[monat - 1];
		}
	}

	/**
	 * Checks if a year is leap-year
	 * 
	 * @param jahr
	 *            takes the year as parameter
	 * @return returns true if the year is a leap year/false if it's not
	 */
	public static boolean isSchaltjahr(int jahr) {
		if (jahr % 4 == 0 && jahr % 100 != 0 || jahr % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if a "Datum" a equals b
	 * 
	 * @param a
	 *            takes (called on an instance of Datum e.q. b) a parameter of an
	 *            Object "Datum"
	 * @return returns true if it equals fully/ false if it doesn't
	 */
	public boolean equals(Datum a) {
		if (this.getJahr() == a.getJahr() && this.getMonat() == a.getMonat() && this.getTag() == a.getTag()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the day is the same
	 * 
	 * @param a
	 *            takes (called of an instance of datum e.q b) a parameter object of
	 *            datum
	 * @return true if day and month are equal(the year doesnt matter)/false if not
	 */
	public boolean isGleicherTag(Datum a) {
		if (this.getMonat() == a.getMonat() && this.getTag() == a.getTag()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * returns the String of day/month/year of the object
	 */
	public String toString() {
		return "" + this.getTag() + "/" + this.getMonat() + "/" + this.getJahr();
	}

	/**
	 * Constructor of Datum
	 * 
	 * @param tag
	 *            takes a day as parameter
	 * @param monat
	 *            takes the month as parameter
	 * @param jahr
	 *            takes the year as parameter
	 */
	public Datum(int tag, int monat, int jahr) {

		setJahr(jahr);

		setMonat(monat);

		setTag(tag);
	}

	/**
	 * Constructor of Datum with only Days as param
	 * 
	 * @param tag
	 *            takes a day 1...366 as param.
	 * @param jahr
	 *            takes the year as param
	 */
	public Datum(int tag, int jahr) {
		if (tag <= 1 || tag >= 366) {
			throw new InvalidDateException("The number " + tag + " is more or less then one year!");
		} else {
			setJahr(jahr);
			int i = 0;
			while (tag > getMonatslaenge(i + 1, jahr)) {
				tag -= getMonatslaenge(i + 1, jahr);
				i++;
				setMonat(i + 1);
			}
			setTag(tag);

		}
	}

	/**
	 * 
	 * @return which day is tomorrow according to the Object morgen is called on.
	 */
	public Datum morgen() {
		if (this.getTag() == getMonatslaenge(this.getMonat(), this.getJahr())) {
			if (this.getMonat() == 12) {
				if (this.getJahr() == 2099) {
					throw new DateOutOfRangeException("The day after today is after 2099");
				} else {
					return new Datum(1, 1, this.getJahr() + 1);
				}
			} else {
				return new Datum(1, this.getMonat() + 1, this.getJahr());
			}
		} else {
			return new Datum(this.getTag() + 1, this.getMonat(), this.getJahr());
		}
	}

	/**
	 * 
	 * @return which day was yesterday according to the Object gestern is called on.
	 */
	public Datum gestern() {
		int d = this.tag;
		int m = this.monat;
		int y = this.jahr;
		if (d == 1) {
			if (m == 1) {
				if (y == 1801) {
					throw new DateOutOfRangeException("yesterday was before 1.1.1801");
				} else {
					y -= 1;
					m = 12;
					d = getMonatslaenge(12, y);
				}
			} else {
				m--;
				d = getMonatslaenge(m, this.getJahr());
			}
		} else {
			d--;
		}
		return new Datum(d, m, y);
	}

	/**
	 * setter
	 * 
	 * @param tag
	 */
	public void setTag(int tag) {
		if (tag >= 1 && tag <= getMonatslaenge(this.getMonat(), this.getJahr())) {
			this.tag = tag;
		} else {
			throw new InvalidDateException("There is no day like the " + tag + " of " + monat);
		}
	}

	/**
	 * setter
	 * 
	 * @param monat
	 */
	public void setMonat(int monat) {
		if (monat >= 1 && monat <= 12) {
			this.monat = monat;
		} else {
			throw new InvalidDateException("There is no month like " + monat + ".");
		}
	}

	/**
	 * setter
	 * 
	 * @param jahr
	 */
	public void setJahr(int jahr) {
		if (jahr > 1800 && jahr < 2100) {
			this.jahr = jahr;
		} else {
			throw new DateOutOfRangeException(jahr + "is outside of 1800 and 2100");
		}
	}

	/**
	 * getter
	 * 
	 * @return the day of the object
	 */
	public int getTag() {
		return this.tag;
	}

	/**
	 * getter
	 * 
	 * @return the month of the object
	 */
	public int getMonat() {
		return this.monat;
	}

	/**
	 * getter
	 * 
	 * @return the year of the object
	 */
	public int getJahr() {
		return this.jahr;
	}

	/**
	 * Errechnet den Wochentag eines Datums
	 * 
	 * @return String of the day (Mo, Di, Mi, Do, Fr, Sa, So)
	 */
	public String getWochentag() {

		int day = this.getTag();
		int jahrHundertDoomsday = 2 - ((this.getJahr() / 100) % 4) * 2;
		int JahrEndZahl = this.getJahr() % 100;
		int doomsday = (JahrEndZahl + (JahrEndZahl / 4) + jahrHundertDoomsday) % 7;
		int dayDoomsday = getMonatslaenge(1, this.getJahr()) + getMonatslaenge(2, this.getJahr());

		for (int i = 1; i < this.getMonat(); i++) {
			day += getMonatslaenge(i, this.getJahr());
		}
		if (day >= dayDoomsday) {
			day -= dayDoomsday;
			day += doomsday;
			day %= 7;
		} else {
			while (day < dayDoomsday) {
				day += 7;
			}
			day -= dayDoomsday;
			day += doomsday;
			day %= 7;
		}
		return wochentage[day];
	}
}
