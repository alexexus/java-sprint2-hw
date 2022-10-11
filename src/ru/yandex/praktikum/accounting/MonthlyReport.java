package ru.yandex.praktikum.accounting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {

    private final HashMap<Integer, ArrayList<MonthRecord>> months;

    public HashMap<Integer, ArrayList<MonthRecord>> getMonths() {
        return months;
    }

    public MonthlyReport() {
        months = new HashMap<>();
    }

    // Не могу придумать как вывести блок try - catch и проверку pathData на null в main класс =(
    public HashMap<Integer, ArrayList<MonthRecord>> saveDataFromMonthlyReports() {
        for (int i = 1; i < 4; i++) {
            ArrayList<MonthRecord> monthRecords = new ArrayList<>();
            MonthRecord monthRecord;
            String pathData = null;
            try {
                pathData = Files.readString(Path.of("resources/m.20210" + i + ".csv"));
            } catch (IOException e) {
                System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            }
            if (pathData == null) {
                System.out.println("Файл №" + i + " не был считан!");
            } else {
                String[] lines = pathData.split(System.lineSeparator());
                for (int j = 1; j < lines.length; j++) {
                    String[] lineContents = lines[j].split(",");
                    monthRecord = new MonthRecord(lineContents[0], Boolean.parseBoolean(lineContents[1]), Integer.parseInt(lineContents[2]), Integer.parseInt(lineContents[3]));
                    monthRecords.add(monthRecord);
                    months.put(i, monthRecords);
                }
            }
        }
        return months;
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

    public String[] infoOfMonths(int month) {
        String[] arr = new String[5];
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
        arr[0] = String.valueOf(month);
        arr[1] = nameOfMaxProfitItem;
        arr[2] = String.valueOf(maxProfit);
        arr[3] = nameOfMaxSpendingItem;
        arr[4] = String.valueOf(maxSpending);
        return arr;
    }
}
