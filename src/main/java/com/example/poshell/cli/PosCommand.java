package com.example.poshell.cli;

import com.example.poshell.biz.PosService;
import com.example.poshell.model.Cart;
import com.example.poshell.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.validation.constraints.DecimalMin;

@ShellComponent
public class PosCommand {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @ShellMethod(value = "List Products", key = "p")
    public String products() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Product product : posService.products()) {
            stringBuilder.append("\t").append(++i).append("\t").append(product).append("\n");
        }
        return stringBuilder.toString();
    }

    @ShellMethod(value = "New Cart", key = "n")
    public String newCart() {
        return posService.newCart() + " OK";
    }

    @ShellMethod(value = "Add a Product to Cart", key = "a")
    public String addToCart(String productId, @DecimalMin(value = "1") int amount) {
        if (posService.add(productId, amount)) {
            return posService.getCart().toString();
        }
        return "ERROR";
    }

    @ShellMethod(value = "Print Cart", key = "pc")
    public String printCart() {
        Cart cart = posService.getCart();
        if (cart != null)
            return cart.toString();
        else return "ERROR: NO CART!";
    }

    @ShellMethod(value = "Check Out", key = "c")
    public String checkOut() {
        Cart cart = posService.getCart();
        if (cart != null) {
            double res = posService.checkout(posService.getCart());
            this.newCart();
            return String.format("Checking out...\nTotal: %.2f\nCheck out successfully.\nCart is empty now.", res);
        } else return "ERROR: NO CART!";
    }

    @ShellMethod(value = "Modify Item Amount in Cart", key = "m")
    public String modifyItemAmountInCart(String productId, @DecimalMin(value = "0") int amount) {
        if (posService.modify(productId, amount)) {
            return posService.getCart().toString();
        }
        return "ERROR";
    }
}
