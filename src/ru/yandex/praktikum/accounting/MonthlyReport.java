package ru.yandex.praktikum.accounting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {
    private final HashMap<Integer, ArrayList<MonthRecord>> months;

    public MonthlyReport() {
        months = new HashMap<>();
    }

    public HashMap<Integer, ArrayList<MonthRecord>> getMonths() {
        return months;
    }

    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            return null;
        }
    }

    public void saveDataFromMonthlyReports() {
        for (int i = 1; i < 13; i++) {
            ArrayList<MonthRecord> monthRecords = new ArrayList<>();
            MonthRecord monthRecord;
            String pathData = readFileContentsOrNull("resources/m.20210" + i + ".csv");
            if (pathData == null) {
                continue;
            }
            String[] lines = pathData.split(System.lineSeparator());
            for (int j = 1; j < lines.length; j++) {
                String[] lineContents = lines[j].split(",");
                monthRecord = new MonthRecord(lineContents[0],
                        Boolean.parseBoolean(lineContents[1]),
                        Integer.parseInt(lineContents[2]),
                        Integer.parseInt(lineContents[3]));
                monthRecords.add(monthRecord);
                months.put(i, monthRecords);
            }
        }
    }

    public int spendingPerMonth(ArrayList<MonthRecord> monthRecords) {
        int sumOfSpending = 0;
        for (MonthRecord monthRecord : monthRecords) {
            if (monthRecord.getIsExpense()) {
                sumOfSpending += monthRecord.getQuantity() * monthRecord.getSumOfOne();
            }
        }
        return sumOfSpending;
    }

    public int profitPerMonth(ArrayList<MonthRecord> monthRecords) {
        int sumOfProfit = 0;
        for (MonthRecord monthRecord : monthRecords) {
            if (!monthRecord.getIsExpense()) {
                sumOfProfit += monthRecord.getQuantity() * monthRecord.getSumOfOne();
            }
        }
        return sumOfProfit;
    }

    public String infoOfMonths(int month) {
        int maxProfit = 0;
        int maxSpending = 0;
        int profit;
        int spending;
        String nameOfMaxProfitItem = "";
        String nameOfMaxSpendingItem = "";
        for (int i = 0; i < months.get(month).size(); i++) {
            for (MonthRecord monthRecord : months.get(month)) {
                if (!monthRecord.getIsExpense()) {
                    profit = monthRecord.getQuantity() * monthRecord.getSumOfOne();
                    if (profit > maxProfit) {
                        maxProfit = profit;
                        nameOfMaxProfitItem = monthRecord.getItemName();
                    }
                } else {
                    spending = monthRecord.getQuantity() * monthRecord.getSumOfOne();
                    if (spending > maxSpending) {
                        maxSpending = spending;
                        nameOfMaxSpendingItem = monthRecord.getItemName();
                    }
                }
            }
        }
        return "Название месяца - " + month + "\n"
                + "Самый прибыльный товар - " + nameOfMaxProfitItem + ". На сумму - " + maxProfit + "\n"
                + "Самая большая трата - " + nameOfMaxSpendingItem + ". На сумму - " + maxSpending;
    }
}
