package ru.yandex.praktikum.accounting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {

    private final HashMap<String, ArrayList<MonthRecord>> months;

    public HashMap<String, ArrayList<MonthRecord>> getMonths() {
        return months;
    }

    public MonthlyReport() {
        months = new HashMap<>();
    }

    public HashMap<String, ArrayList<MonthRecord>> saveContentFromFile() {
        for (int i = 1; i < 4; i++) {
            ArrayList<MonthRecord> monthRecords = new ArrayList<>();
            MonthRecord monthRecord;
            String pathData = null;
            try {
                pathData = Files.readString(Path.of("resources/m.20210" + i + ".csv"));
            } catch (IOException e) {
                System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            }
            String[] lines = pathData.split(System.lineSeparator());
            for (int j = 1; j < lines.length; j++) {
                String[] lineContents = lines[j].split(",");
                for (int k = 0; k < lineContents.length; k += 4) {
                    monthRecord = new MonthRecord(lineContents[k], lineContents[k + 1], lineContents[k + 2], lineContents[k + 3]);
                    monthRecords.add(monthRecord);
                }
                months.put("0" + i, monthRecords);
            }
        }
        return months;
    }

    public String spendingPerMonth(ArrayList<MonthRecord> monthRecords) {
        int sumOfSpending = 0;
        for (MonthRecord monthRecord : monthRecords) {
            if (monthRecord.getIsExpense().equals("TRUE")) {
                sumOfSpending += Integer.parseInt(monthRecord.getQuantity()) * Integer.parseInt(monthRecord.getSumOfOne());
            }
        }
        return String.valueOf(sumOfSpending);
    }

    public String profitPerMonth(ArrayList<MonthRecord> monthRecords) {
        int sumOfProfit = 0;
        for (MonthRecord monthRecord : monthRecords) {
            if (monthRecord.getIsExpense().equals("FALSE")) {
                sumOfProfit += Integer.parseInt(monthRecord.getQuantity()) * Integer.parseInt(monthRecord.getSumOfOne());
            }
        }
        return String.valueOf(sumOfProfit);
    }

    public void infoOfMonths() {
        if (months.isEmpty()) {
            System.out.println("Информация о месячных отчетах не была считана!");
            return;
        }
        for (String month : months.keySet()) {
            int maxProfit = 0;
            int maxSpending = 0;
            int profit;
            int spending;
            String nameOfMaxProfitItem = "";
            String nameOfMaxSpendingItem = "";
            for (int i = 0; i < months.get(month).size(); i++) {
                for (MonthRecord monthRecord : months.get(month)) {
                    if (monthRecord.getIsExpense().equals("FALSE")) {
                        profit = Integer.parseInt(monthRecord.getQuantity()) * Integer.parseInt(monthRecord.getSumOfOne());
                        if (profit > maxProfit) {
                            maxProfit = profit;
                            nameOfMaxProfitItem = monthRecord.getItemName();
                        }
                    } else if (monthRecord.getIsExpense().equals("TRUE")) {
                        spending = Integer.parseInt(monthRecord.getQuantity()) * Integer.parseInt(monthRecord.getSumOfOne());
                        if (spending > maxSpending) {
                            maxSpending = spending;
                            nameOfMaxSpendingItem = monthRecord.getItemName();
                        }
                    }
                }
            }
            System.out.println("Название месяца - " + month);
            System.out.println("Самый прибыльный товар - " + nameOfMaxProfitItem + ". На сумму - " + maxProfit);
            System.out.println("Самая большая трата - " + nameOfMaxSpendingItem + ". На сумму - " + maxSpending);
        }
    }
}

