package be.gold.agency.backendbill.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class InvoiceCalculator {

    public static BigDecimal calculateTaxesIncludedAmount(BigDecimal montantHT, BigDecimal tauxTVA) {
        BigDecimal calculatedAmount = BigDecimal.ZERO;
        calculatedAmount = calculatedAmount.add(montantHT.multiply(tauxTVA.add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100)));
        calculatedAmount = calculatedAmount.setScale(2, RoundingMode.HALF_UP);
        return calculatedAmount;
    }

    public static BigDecimal calculateTotalHT(BigDecimal montantHT, BigDecimal quantity) {
        BigDecimal calculatedAmount = BigDecimal.ZERO;
        calculatedAmount = calculatedAmount.add(montantHT.multiply(quantity));
        calculatedAmount = calculatedAmount.setScale(2, RoundingMode.HALF_UP);
        return calculatedAmount;
    }

    public static BigDecimal calculateTotalHTFromMultipleElement(List<BigDecimal> lineTotals) {
        BigDecimal calculatedAmount = BigDecimal.ZERO;
        for (BigDecimal lineTotal : lineTotals) {
            calculatedAmount = calculatedAmount.add(lineTotal);
        }
        calculatedAmount = calculatedAmount.setScale(2, RoundingMode.HALF_UP);
        return calculatedAmount;
    }

    public static BigDecimal calculateVatAmount(BigDecimal amountHT, BigDecimal vatRate) {
        BigDecimal vatAmount = amountHT.multiply(vatRate).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        return vatAmount;
    }


}
