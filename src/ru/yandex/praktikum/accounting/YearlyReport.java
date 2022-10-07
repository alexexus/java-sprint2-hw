package ru.yandex.praktikum.accounting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class YearlyReport {
    ArrayList<String> years;

    public ArrayList<String> getYears() {
        return years;
    }

    YearlyReport() {
        years = new ArrayList<>();
    }

    public ArrayList<String> readFileContentsOrNull() {
        String pathData = null;
        try {
            pathData = Files.readString(Path.of("resources/y.2021.csv"));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
        }
        assert pathData != null;
        String[] lines = pathData.split(System.lineSeparator());
        for (int j = 1; j < lines.length; j++) {
            String[] lineContents = lines[j].split(",");
            years.addAll(Arrays.asList(lineContents));
        }
        return years;
    }

    public void infoOfYear() {
        if (years.size() < 1) {
            System.out.println("Информация о годовом отчете не была считана!");
        } else {
            Path path = Paths.get("resources/y.2021.csv");
            String fileName = path.getFileName().toString();
            String numberOnly = fileName.replaceAll("[^0-9]", "");
            System.out.println("Рассматриваемый год - " + numberOnly);

            for (int j = 1; j < years.size(); j += 6) {
                int profit = Math.abs(Integer.parseInt(years.get(j)) - Integer.parseInt(years.get(j + 3)));
                System.out.println("Месяц - " + years.get(j - 1) + ". Прибыль - " + profit);
            }

            int sumOfProfit = 0;
            int sumOfSpending = 0;
            for (int i = 2; i < years.size(); i += 3) {
                int profitInMonth = 0;
                int spendingInMonth = 0;
                if (years.get(i).equals("false")) {
                    profitInMonth = Integer.parseInt(years.get(i - 1));
                    sumOfProfit += profitInMonth;
                } else if (years.get(i).equals("true")) {
                    spendingInMonth = Integer.parseInt(years.get(i - 1));
                    sumOfSpending += spendingInMonth;
                }
            }
            System.out.println("Средний расход за все месяцы в году - " + sumOfSpending / 3);
            System.out.println("Средний доход за все месяцы в году - " + sumOfProfit / 3);
        }
    }
}
