import java.math.BigDecimal;
import java.util.Objects;

public class BankAccount {

    private final String owner;
    private final long withdrawalLimit;
    private Balance balance;
    private boolean blocked = false;
    
    public BankAccount(String owner, Balance balance, long withdrawalLimit) {
        Objects.requireNonNull(owner, "Нужно указать владельца аккаунта!");
        if (owner.isEmpty()) {
            throw new IllegalArgumentException("Имя владельца не должно быть пустым!");
        }
        Objects.requireNonNull(balance, "Нужно ввести начальный баланс аккаунта!");
        if (withdrawalLimit < 1) {
            throw new IllegalArgumentException("Лимит снятия должен быть больше нуля!");
        }

        this.owner = owner;
        this.balance = balance;
        this.withdrawalLimit = withdrawalLimit;
    }

    public String getOwner() {
        return owner;
    }

    public long GetWithdrawalLimit() {
        return withdrawalLimit;
    }

    public Balance getBalance() {
        return balance;
    }

    public boolean isBlocked()
    {
        return blocked;
    }
    
    public void block() {
        blocked = true;
    }

    public void unblock() {
        blocked = false;
    }

    public void deposite(Balance amount)
    {
        statusCheck(amount);
        balance = balance.add(amount);
    }

    public void withdraw(Balance amount) {
        statusCheck(amount);
        if (amount.getCountOfUnits() > withdrawalLimit) {
            throw new IllegalArgumentException("Превышен лимит списания!");
        }
        balance = balance.substract(amount); 
    }

    public void transferTo(BankAccount recipient, Balance amount) {
        statusCheck(amount);
        Objects.requireNonNull(recipient, "Нужно ввести получателя!");
        if (recipient == this) {
            throw new IllegalArgumentException("Нельзя перевести на тот же аккаунт!");
        }
        if (amount.getCountOfUnits() > withdrawalLimit) {
            throw new IllegalArgumentException("Превышен лимит списания!");
        }

        Balance senderResult = balance.substract(amount);
        Balance recipientResult = recipient.balance.add(amount);
        balance = senderResult;
        recipient.balance = recipientResult;
    }

    private void statusCheck(Balance amount) {
        Objects.requireNonNull(amount, "Нужно ввести данные для операции!");
        if (blocked) {
            throw new IllegalStateException("Аккаунт заблокирован!");
        }
    }

    @Override 
    public String toString() {
        return "Bank Account: " + owner + " - " + balance + "; Withdrawal limit = " + BigDecimal.valueOf(withdrawalLimit, 2).toPlainString() + "; Block status = " + blocked;
    }
}