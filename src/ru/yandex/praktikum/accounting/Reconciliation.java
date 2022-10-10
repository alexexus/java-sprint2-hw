package ru.yandex.praktikum.accounting;

import java.util.ArrayList;
import java.util.HashMap;

public class Reconciliation {

    YearlyReport yearlyReport = new YearlyReport();
    MonthlyReport monthlyReport = new MonthlyReport();
    HashMap<String, ArrayList<MonthRecord>> months = monthlyReport.saveContentFromFile();
    ArrayList<YearRecord> years = yearlyReport.saveContentFromFile();

    public void reconciliation() {
        if (months.isEmpty() || years.isEmpty()) {
            System.out.println("Отчеты не были считаны!");
            return;
        }
        for (YearRecord yearRecord : yearlyReport.getYears()) {
            if (yearRecord.getIsExpense().equals("false")) {
                if (!(yearRecord.getAmount()
                        .equals(monthlyReport
                                .profitPerMonth(monthlyReport
                                        .getMonths()
                                        .get(yearRecord
                                                .getMonth()))))) {
                    System.out.println("Месяц в котором обнаружено несоответствие - " + yearRecord.getMonth());
                    return;
                }

            } else if (yearRecord.getIsExpense().equals("true")) {
                if (!(yearRecord.getAmount()
                        .equals(monthlyReport
                                .spendingPerMonth(monthlyReport
                                        .getMonths()
                                        .get(yearRecord
                                                .getMonth()))))) {
                    System.out.println("Месяц в котором обнаружено несоответствие - " + yearRecord.getMonth());
                    return;
                }

            }
        }
        System.out.println("Операция завершена успешно!");
    }

}
