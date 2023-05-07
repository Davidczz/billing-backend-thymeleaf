package be.gold.agency.backendbill.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
}