package ru.yandex.praktikum.accounting;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();
        Reconciliation reconciliation = new Reconciliation();

        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                monthlyReport.readFileContentsOrNull();
                System.out.println("Все месячные отчеты считаны!");
            } else if (command == 2) {
                yearlyReport.readFileContentsOrNull();
                System.out.println("Годовой отчет считан!");
            } else if (command == 3) {
                reconciliation.reconciliation();
            } else if (command == 4) {
                monthlyReport.infoOfMonths();
            } else if (command == 5) {
                yearlyReport.infoOfYear();
            } else if (command == 0) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }

    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выход");
    }
}

