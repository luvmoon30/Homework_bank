import java.util.Objects;
import java.math.BigDecimal;

public final class Balance {

    private final long countOfUnits;
    private final Currency currency;

    public Balance(long countOfUnits, Currency currency) {
        this.currency = Objects.requireNonNull(currency, "Нужно указать валюту!");
        if (countOfUnits < 0) {
            throw new IllegalArgumentException("Баланс не должен бфть отрицательным!");
        }
        this.countOfUnits = countOfUnits;
    }

    public long getCountOfUnits() {
        return countOfUnits;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Balance add(Balance amount) {
        Objects.requireNonNull(amount, "Нужно ввести данные для пополнения баланса!");
        if (currency != amount.currency) {
            throw new IllegalArgumentException("Валюта не совпадает!");
        }
        return new Balance(Math.addExact(countOfUnits, amount.countOfUnits), currency);
    }

    public Balance substract(Balance amount) {
        Objects.requireNonNull(amount, "Нужно ввести данные для снятия денег с баланса!");
        if (currency != amount.currency) {
            throw new IllegalArgumentException("Валюта не совпадает!");
        }
        if (amount.countOfUnits > countOfUnits) {
            throw new IllegalArgumentException("Не хватает средств!");
        }
        return new Balance(countOfUnits - amount.countOfUnits, currency);
    }

    public Balance convertTo(Currency targetCurrency) {
        Objects.requireNonNull(targetCurrency, "Нужно указать целевую валюту!");
        long result = BigDecimal.valueOf(countOfUnits)
        .multiply(BigDecimal.valueOf(currency.getPriceInRublesKopecks()))
        .divide(BigDecimal.valueOf(targetCurrency.getPriceInRublesKopecks()), 0)
        .longValueExact();
        return new Balance(result, targetCurrency);
    }

    @Override 
    public String toString() {
        return BigDecimal.valueOf(countOfUnits, 2).toPlainString() + " " + currency.getSymbol();
    }
}