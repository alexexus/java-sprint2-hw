package ru.yandex.praktikum.accounting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MonthlyReport {
    HashMap<String, ArrayList<String>> months;

    MonthlyReport() {
        months = new HashMap<>();
    }

    public HashMap<String, ArrayList<String>> getMonths() {
        return months;
    }

    public HashMap<String, ArrayList<String>> readFileContentsOrNull() {
        for (int i = 1; i < 4; i++) {
            ArrayList<String> expense = new ArrayList<>();
            String pathData = null;
            try {
                pathData = Files.readString(Path.of("resources/m.20210" + i + ".csv"));
            } catch (IOException e) {
                System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            }
            assert pathData != null;
            String[] lines = pathData.split(System.lineSeparator());
            for (int j = 1; j < lines.length; j++) {
                String[] lineContents = lines[j].split(",");
                expense.addAll(Arrays.asList(lineContents));
                months.put("0" + i, expense);
            }
        }
        return months;
    }

    public String spendingPerMonth(ArrayList<String> spending) {
        int sumOfSpending = 0;
        for (int i = 1; i < spending.size(); i += 4) {
            if (spending.get(i).equals("TRUE")) {
                sumOfSpending += Integer.parseInt(spending.get(i + 1)) * Integer.parseInt(spending.get(i + 2));
            }
        }
        return String.valueOf(sumOfSpending);
    }

    public String profitPerMonth(ArrayList<String> spending) {
        int sumOfProfit = 0;
        for (int i = 1; i < spending.size(); i += 4) {
            if (spending.get(i).equals("FALSE")) {
                sumOfProfit += Integer.parseInt(spending.get(i + 1)) * Integer.parseInt(spending.get(i + 2));
            }
        }
        return String.valueOf(sumOfProfit);
    }

    public void infoOfMonths() {
        if (months.size() < 1) {
            System.out.println("Информация о месячных отчетах не была считана!");
        } else {
            for (String month : months.keySet()) {
                int maxProfit = 0;
                int maxSpending = 0;
                int profit;
                int spending;
                String nameOfMaxProfitItem = "";
                String nameOfMaxSpendingItem = "";
                for (int i = 1; i < months.get(month).size(); i += 4) {
                    if (months.get(month).get(i).equals("FALSE")) {
                        profit = Integer.parseInt(months.get(month).get(i + 1)) * Integer.parseInt(months.get(month).get(i + 2));
                        if (profit > maxProfit) {
                            maxProfit = profit;
                            nameOfMaxProfitItem = months.get(month).get(i - 1);
                        }
                    } else if (months.get(month).get(i).equals("TRUE")) {
                        spending = Integer.parseInt(months.get(month).get(i + 1)) * Integer.parseInt(months.get(month).get(i + 2));
                        if (spending > maxSpending) {
                            maxSpending = spending;
                            nameOfMaxSpendingItem = months.get(month).get(i - 1);
                        }
                    }
                }
                System.out.println("Название месяца - " + month);
                System.out.println("Самый прибыльный товар - " + nameOfMaxProfitItem + ". На сумму - " + maxProfit);
                System.out.println("Самая большая трата - " + nameOfMaxSpendingItem + ". На сумму - " + maxSpending);
            }
        }
    }
}

