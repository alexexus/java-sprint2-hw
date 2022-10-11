package ru.yandex.praktikum.accounting;

import java.util.ArrayList;
import java.util.HashMap;

public class Reconciliation {

    private final YearlyReport yearlyReport = new YearlyReport();
    private final MonthlyReport monthlyReport = new MonthlyReport();
    private final HashMap<Integer, ArrayList<MonthRecord>> months = monthlyReport.saveDataFromMonthlyReports();
    private final ArrayList<YearRecord> years = yearlyReport.saveDataFromYearlyReport();

    public int checkReports() {
        int month = 0;
        for (YearRecord yearRecord : years) {
            if (yearRecord.getIsExpense()) {
                int spendingPerMonth = monthlyReport.spendingPerMonth(months.get(yearRecord.getMonth()));
                if (yearRecord.getAmount() != spendingPerMonth) {
                    month = yearRecord.getMonth();
                    return month;
                }
            } else {
                int profitPerMonth = monthlyReport.profitPerMonth(months.get(yearRecord.getMonth()));
                if (yearRecord.getAmount() != profitPerMonth) {
                    month = yearRecord.getMonth();
                    return month;
                }
            }
        }
        return month;
    }
}
