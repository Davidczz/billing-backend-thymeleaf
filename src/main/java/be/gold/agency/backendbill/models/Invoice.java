package be.gold.agency.backendbill.models;

import be.gold.agency.backendbill.tools.InvoiceCalculator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Invoice {

    private Company companyFrom;
    private Company companyTo;
    private PrivateCustomer privateCustomerTo;
    private List<InvoiceElement> elements;
    private PaymentMethod paymentMethod;
    private String accountNumber;
    private String additionalText;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private BigDecimal vatRate;

    public BigDecimal getTotalHT() {
        return InvoiceCalculator.calculateTotalHTFromMultipleElement(elements.stream().map(InvoiceElement::getTotalExclVat).collect(Collectors.toList()));
    }

    public BigDecimal getTotalInclVat() {
        return InvoiceCalculator.calculateTaxesIncludedAmount(getTotalHT(), vatRate);
    }

    public BigDecimal getTotalVat() {
        return InvoiceCalculator.calculateVatAmount(getTotalHT(), vatRate);
    }
}