package ru.yandex.praktikum.accounting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    ArrayList<YearRecord> years;

    public ArrayList<YearRecord> getYears() {
        return years;
    }

    YearlyReport() {
        years = new ArrayList<>();
    }

    public ArrayList<YearRecord> saveContentFromFile() {
        String pathData = null;
        YearRecord yearRecord;
        try {
            pathData = Files.readString(Path.of("resources/y.2021.csv"));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
        }
        String[] lines = pathData.split(System.lineSeparator());
        for (int j = 1; j < lines.length; j++) {
            String[] lineContents = lines[j].split(",");
            for (int k = 0; k < lineContents.length; k += 3) {
                yearRecord = new YearRecord(lineContents[k], lineContents[k + 1], lineContents[k + 2]);
                years.add(yearRecord);
            }
        }
        return years;
    }

    public void infoOfYear() {
        if (years.isEmpty()) {
            System.out.println("Информация о годовом отчете не была считана!");
            return;
        }
        Path path = Paths.get("resources/y.2021.csv");
        String fileName = path.getFileName().toString();
        String year = fileName.replaceAll("[^0-9]", "");
        System.out.println("Рассматриваемый год - " + year);

        MonthlyReport monthlyReport = new MonthlyReport();
        HashMap<String, ArrayList<MonthRecord>> months = monthlyReport.saveContentFromFile();
        for (String month : months.keySet()) {
            int profitPerMonth = Math.abs(Integer.parseInt(monthlyReport.spendingPerMonth(monthlyReport.getMonths().get(month)))
                    - Integer.parseInt(monthlyReport.profitPerMonth(monthlyReport.getMonths().get(month))));
            System.out.println("Месяц - " + month + ". Прибыль - " + profitPerMonth);
        }

        int sumOfProfit = 0;
        int sumOfSpending = 0;
        for (YearRecord yearRecord : years) {
            int profitInMonth;
            int spendingInMonth;
            if (yearRecord.getIsExpense().equals("false")) {
                profitInMonth = Integer.parseInt(yearRecord.getAmount());
                sumOfProfit += profitInMonth;
            } else if (yearRecord.getIsExpense().equals("true")) {
                spendingInMonth = Integer.parseInt(yearRecord.getAmount());
                sumOfSpending += spendingInMonth;
            }
        }
        System.out.println("Средний расход за все месяцы в году - " + sumOfSpending / 3);
        System.out.println("Средний доход за все месяцы в году - " + sumOfProfit / 3);

    }
}
