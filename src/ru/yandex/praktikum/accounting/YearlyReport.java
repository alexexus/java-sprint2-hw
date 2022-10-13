package ru.yandex.praktikum.accounting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class YearlyReport {
    private final ArrayList<YearRecord> years;

    public YearlyReport() {
        years = new ArrayList<>();
    }

    public ArrayList<YearRecord> getYears() {
        return years;
    }

    private String readFileContentsOrNull() {
        try {
            return Files.readString(Path.of("resources/y.2021.csv"));
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<YearRecord> saveDataFromYearlyReport() {
        YearRecord yearRecord;
        String pathData = readFileContentsOrNull();
        if (pathData == null) {
            return null;
        }
        String[] lines = pathData.split(System.lineSeparator());
        for (int j = 1; j < lines.length; j++) {
            String[] lineContents = lines[j].split(",");
            yearRecord = new YearRecord(Integer.parseInt(lineContents[0]),
                    Integer.parseInt(lineContents[1]),
                    Boolean.parseBoolean(lineContents[2]));
            years.add(yearRecord);
        }
        return years;
    }

    public String getNumberOfYear() {
        Path path = Paths.get("resources/y.2021.csv");
        String fileName = path.getFileName().toString();
        return fileName.replaceAll("[^0-9]", "");
    }

    public int getProfitPerMonth(int month) {
        int expense;
        int profit;
        if (years.get(month).getIsExpense()) {
            expense = years.get(month).getAmount();
            profit = years.get(month + 1).getAmount();
        } else {
            profit = years.get(month).getAmount();
            expense = years.get(month + 1).getAmount();
        }
        return profit - expense;
    }

    public int getAverageExpenseOfMonths() {
        int sumOfSpending = 0;
        for (YearRecord yearRecord : years) {
            int spendingInMonth;
            if (yearRecord.getIsExpense()) {
                spendingInMonth = yearRecord.getAmount();
                sumOfSpending += spendingInMonth;
            }
        }
        return sumOfSpending / 3;
    }

    public int getAverageProfitOfMonths() {
        int sumOfProfit = 0;
        for (YearRecord yearRecord : years) {
            int profitInMonth;
            if (!yearRecord.getIsExpense()) {
                profitInMonth = yearRecord.getAmount();
                sumOfProfit += profitInMonth;
            }
        }
        return sumOfProfit / 3;
    }
}
