package ru.yandex.praktikum.accounting;

import java.util.Scanner;

public class Main {

    private static final int READ_ALL_MONTHLY_REPORTS = 1;
    private static final int READ_YEARLY_REPORT = 2;
    private static final int VERIFY_REPORTS = 3;
    private static final int DISPLAY_INFO_ABOUT_MONTHS = 4;
    private static final int DISPLAY_INFO_ABOUT_YEAR = 5;
    private static final int EXIT_THE_PROGRAM = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();
        Reconciliation reconciliation = new Reconciliation(yearlyReport, monthlyReport);

        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == READ_ALL_MONTHLY_REPORTS) {
                monthlyReport.saveDataFromMonthlyReports();
                System.out.println("Прочитано отчетов: " + monthlyReport.getMonths().size());
            } else if (command == READ_YEARLY_REPORT) {
                if (yearlyReport.saveDataFromYearlyReport() == null) {
                    System.out.println("Отчет не был найден!");
                } else {
                    System.out.println("Годовой отчет считан!");
                }
            } else if (command == VERIFY_REPORTS) {
                if (monthlyReport.getMonths().isEmpty() || yearlyReport.getYears().isEmpty()) {
                    System.out.println("Отчеты не были считаны!");
                    continue;
                }
                System.out.println(reconciliation.checkReports());
            } else if (command == DISPLAY_INFO_ABOUT_MONTHS) {
                if (monthlyReport.getMonths().isEmpty()) {
                    System.out.println("Информация о месячных отчетах не была считана!");
                    continue;
                }
                for (int month : monthlyReport.getMonths().keySet()) {
                    System.out.println(monthlyReport.infoOfMonths(month));
                }
            } else if (command == DISPLAY_INFO_ABOUT_YEAR) {
                if (yearlyReport.getYears().isEmpty()) {
                    System.out.println("Информация о годовом отчете не была считана!");
                    continue;
                }
                System.out.println("Рассматриваемый год - " + yearlyReport.getNumberOfYear());
                for (int i = 0; i < yearlyReport.getYears().size(); i += 2) {
                    System.out.println("Месяц - " + yearlyReport.getYears().get(i).getMonth() + ". Прибыль - " + yearlyReport.getProfitPerMonth(i));
                }
                System.out.println("Средний расход за все месяцы в году - " + yearlyReport.getAverageExpenseOfMonths());
                System.out.println("Средний доход за все месяцы в году - " + yearlyReport.getAverageProfitOfMonths());
            } else if (command == EXIT_THE_PROGRAM) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выход");
    }
}
