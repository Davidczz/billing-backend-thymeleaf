package be.gold.agency.backendbill.tools;

import java.math.BigDecimal;

public class VATCalculator {

    public static BigDecimal calculateTaxesIncludedAmount(BigDecimal montantHT, BigDecimal tauxTVA) {
        return montantHT.multiply(tauxTVA.add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100));
    }
}
