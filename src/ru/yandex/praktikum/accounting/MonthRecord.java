package ru.yandex.praktikum.accounting;

public class MonthRecord {

    private final String itemName;
    private final boolean isExpense;
    private final int quantity;
    private final int sumOfOne;

    public MonthRecord(String itemName, boolean isExpense, int quantity, int sumOfOne) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
    }

    public String getItemName() {
        return itemName;
    }

    public boolean getIsExpense() {
        return isExpense;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSumOfOne() {
        return sumOfOne;
    }
}
