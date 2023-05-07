package be.gold.agency.backendbill.models;

import be.gold.agency.backendbill.tools.VATCalculator;
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
    private Integer quantity;
    private BigDecimal priceExclVat;
    private BigDecimal vatRate;

    public BigDecimal getTotal() {
        return VATCalculator.calculateTaxesIncludedAmount(priceExclVat, vatRate);
    }
}
