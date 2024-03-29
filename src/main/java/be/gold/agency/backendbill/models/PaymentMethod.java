package be.gold.agency.backendbill.models;

public enum PaymentMethod {
    BANK_TRANSFER("Virement bancaire"),
    CASH("Cash"),
    CREDIT_CARD("Carte de crédit"),
    STRIPE("Stripe");

    private final String displayName;
    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}