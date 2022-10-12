package ru.yandex.praktikum.accounting;

public class Reconciliation {

    private final YearlyReport yearlyReport;
    private final MonthlyReport monthlyReport;

    public Reconciliation(YearlyReport yearlyReport, MonthlyReport monthlyReport) {
        this.yearlyReport = yearlyReport;
        this.monthlyReport = monthlyReport;
    }

    public int checkReports() {
        int month = 0;
        for (YearRecord yearRecord : yearlyReport.getYears()) {
            if (yearRecord.getIsExpense()) {
                int spendingPerMonth = monthlyReport.spendingPerMonth(monthlyReport.getMonths().get(yearRecord.getMonth()));
                if (yearRecord.getAmount() != spendingPerMonth) {
                    month = yearRecord.getMonth();
                    return month;
                }
            } else {
                int profitPerMonth = monthlyReport.profitPerMonth(monthlyReport.getMonths().get(yearRecord.getMonth()));
                if (yearRecord.getAmount() != profitPerMonth) {
                    month = yearRecord.getMonth();
                    return month;
                }
            }
        }
        return month;
    }
}
