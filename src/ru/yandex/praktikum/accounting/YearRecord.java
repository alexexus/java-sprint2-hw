package ru.yandex.praktikum.accounting;

public class YearRecord {
    private final String month;
    private final String amount;
    private final String isExpense;

    public YearRecord(String itemName, String isExpense, String quantity) {
        this.month = itemName;
        this.amount = isExpense;
        this.isExpense = quantity;
    }

    public String getMonth() {
        return month;
    }

    public String getAmount() {
        return amount;
    }

    public String getIsExpense() {
        return isExpense;
    }
}
