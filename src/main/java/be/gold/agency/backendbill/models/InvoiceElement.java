package be.gold.agency.backendbill.models;

import be.gold.agency.backendbill.tools.InvoiceCalculator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvoiceElement {
    private Integer number;
    private String label;
    private String description;
    private BigDecimal quantity;
    private BigDecimal priceExclVat;
    private BigDecimal vatRate;
    private ElementType elementType;

    public BigDecimal getTotal() {
        return InvoiceCalculator.calculateTaxesIncludedAmount(priceExclVat, vatRate);
    }

    public BigDecimal getTotalExclVat() {
        return InvoiceCalculator.calculateTotalHT(priceExclVat, quantity);
    }
}
