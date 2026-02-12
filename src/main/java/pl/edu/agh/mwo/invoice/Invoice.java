package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
//    private Collection<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        this.addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Produkt nie może być nullem");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Ilość musi być większa od zera");
        }
        this.products.put(product, quantity);
    }
    private Map<Product, Integer> products = new HashMap<>();

    public BigDecimal getNetValue() {
        BigDecimal value = BigDecimal.ZERO;
        for (Product product : this.products.keySet()) {
            Integer quantity = this.products.get(product);
            BigDecimal price = product.getPrice();
            price = price.multiply(BigDecimal.valueOf(quantity));
            value = value.add(price);
        }
        return value;
    }

    public BigDecimal getTax() {
        return getGrossValue().subtract(getNetValue());
    }

    public BigDecimal getGrossValue() {
        BigDecimal totalGross = BigDecimal.ZERO;

        for (Product product : this.products.keySet()) {
            Integer quantity = this.products.get(product);
            BigDecimal priceWithTax = product.getPriceWithTax();
            BigDecimal lineTotal = priceWithTax.multiply(BigDecimal.valueOf(quantity));
            totalGross = totalGross.add(lineTotal);
        }
        return totalGross;
    }
}
