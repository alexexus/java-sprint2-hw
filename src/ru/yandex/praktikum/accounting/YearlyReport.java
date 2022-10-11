package ru.yandex.praktikum.accounting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class YearlyReport {
    private final ArrayList<YearRecord> years;

    public ArrayList<YearRecord> getYears() {
        return years;
    }

    public YearlyReport() {
        years = new ArrayList<>();
    }

    // Не могу придумать как вывести блок try - catch и проверку pathData на null в main класс =(
    public ArrayList<YearRecord> saveDataFromYearlyReport() {
        String pathData = null;
        YearRecord yearRecord;
        try {
            pathData = Files.readString(Path.of("resources/y.2021.csv"));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
        }
        if (pathData == null) {
            System.out.println("Файл не был считан!");
        } else {
            String[] lines = pathData.split(System.lineSeparator());
            for (int j = 1; j < lines.length; j++) {
                String[] lineContents = lines[j].split(",");
                yearRecord = new YearRecord(Integer.parseInt(lineContents[0]), Integer.parseInt(lineContents[1]), Boolean.parseBoolean(lineContents[2]));
                years.add(yearRecord);
            }
        }
        return years;
    }

    public String getNumberOfYear() {
        Path path = Paths.get("resources/y.2021.csv");
        String fileName = path.getFileName().toString();
        return fileName.replaceAll("[^0-9]", "");
    }

    public int getProfitPerMonth(int month) {
        int expense = years.get(month).getAmount();
        int profit = years.get(month + 1).getAmount();
        return Math.abs(profit - expense);
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
