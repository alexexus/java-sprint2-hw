package ru.yandex.praktikum.accounting;

public class MonthRecord {

    private final String itemName;
    private final String isExpense;
    private final String quantity;
    private final String sumOfOne;

    public MonthRecord(String itemName, String isExpense, String quantity, String sumOfOne) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
    }

    public String getItemName() {
        return itemName;
    }

    public String getIsExpense() {
        return isExpense;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSumOfOne() {
        return sumOfOne;
    }
}
