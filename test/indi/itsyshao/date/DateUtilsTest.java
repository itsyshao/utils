package indi.itsyshao.date;

import junit.framework.TestCase;

public class DateUtilsTest extends TestCase {
	public void testDate() {
		DateUtils dateHelper = DateUtils.getInstance();
		/* Year */
		for (int i = -5; i <= 5; i++) {
			System.out.println("FirstDayOfYear(" + i + ") = " + dateHelper.getFirstDayOfYear(i));
			System.out.println("LastDayOfYear(" + i + ") = " + dateHelper.getLastDayOfYear(i));
		}
		/* Quarter */
		for (int i = -5; i <= 5; i++) {
			System.out.println("FirstDayOfQuarter(" + i + ") = " + dateHelper.getFirstDayOfQuarter(i));
			System.out.println("LastDayOfQuarter(" + i + ") = " + dateHelper.getLastDayOfQuarter(i));
		}
		/* Month */
		for (int i = -5; i <= 5; i++) {
			System.out.println("FirstDayOfMonth(" + i + ") = " + dateHelper.getFirstDayOfMonth(i));
			System.out.println("LastDayOfMonth(" + i + ") = " + dateHelper.getLastDayOfMonth(i));
		}
		/* Week */
		for (int i = -5; i <= 5; i++) {
			System.out.println("FirstDayOfWeek(" + i + ") = " + dateHelper.getFirstDayOfWeek(i));
			System.out.println("LastDayOfWeek(" + i + ") = " + dateHelper.getLastDayOfWeek(i));
		}
		/* Tendays */
		for (int i = -5; i <= 5; i++) {
			System.out.println("FirstDayOfTendays(" + i + ") = " + dateHelper.getFirstDayOfTendays(i));
			System.out.println("LastDayOfTendays(" + i + ") = " + dateHelper.getLastDayOfTendays(i));
		}
	}
}
