package com.example.poshell.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Cart {

    private List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        for (Item ii : items) {
            if (Objects.equals(ii.getProduct().getId(), item.getProduct().getId())) {
                item.setAmount(item.getAmount() + ii.getAmount());
            }
        }
        items.removeIf(i -> Objects.equals(i.getProduct().getId(), item.getProduct().getId()));
        return items.add(item);
    }

    public boolean modifyItem(Item item) {
        items.removeIf(i -> Objects.equals(i.getProduct().getId(), item.getProduct().getId()));
        if (item.getAmount() != 0) return this.addItem(item);
        else return true;
    }

    @Override
    public String toString() {
        if (items.size() == 0) {
            return "Empty Cart";
        }
        double total = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cart -----------------\n");

        for (int i = 0; i < items.size(); i++) {
            stringBuilder.append(items.get(i).toString()).append("\n");
            total += items.get(i).getAmount() * items.get(i).getProduct().getPrice();
        }
        stringBuilder.append("----------------------\n");

        stringBuilder.append("Total...\t\t\t" + total);

        return stringBuilder.toString();
    }

    public double total() {
        double total = 0;
        for (Item item : items) {
            total += item.getAmount() * item.getProduct().getPrice();
        }
        return total;
    }
}
