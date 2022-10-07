package ru.yandex.praktikum.accounting;

import java.util.ArrayList;
import java.util.HashMap;

public class Reconciliation {

    YearlyReport yearlyReport = new YearlyReport();
    MonthlyReport monthlyReport = new MonthlyReport();
    HashMap<String, ArrayList<String>> months = monthlyReport.readFileContentsOrNull();
    ArrayList<String> years = yearlyReport.readFileContentsOrNull();

    public void reconciliation() {
        if (months.size() < 1 || years.size() < 1) {
            System.out.println("Отчеты не были считаны!");
            System.out.println(years);
            System.out.println(months);
        } else {
            for (int i = 2; i < years.size(); i += 3) {
                if (years.get(i).equals("false")) {
                    if (!(years.get(i - 1).equals(monthlyReport.profitPerMonth(months.get(years.get(i - 2)))))) {
                        System.out.println("Месяц в котором обнаружено несоответствие - " + years.get(i - 2));
                        return;
                    }

                } else if (years.get(i).equals("true")) {
                    if (!(years.get(i - 1).equals(monthlyReport.spendingPerMonth(months.get(years.get(i - 2)))))) {
                        System.out.println("Месяц в котором обнаружено несоответствие - " + years.get(i - 2));
                        return;
                    }

                }
            }
            System.out.println("Операция завершена успешно!");
        }
    }

}
