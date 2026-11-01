public class Main {

    public static void main(String[] args) {
        BankAccount account1 = new BankAccount("Vasya", new Balance(1000000_00, Currency.RUB), 50000_00);
        BankAccount account2 = new BankAccount("John", new Balance(20000_00, Currency.USD), 1000_00);
        BankAccount account3 = new BankAccount("Petya", new Balance(300000_00, Currency.RUB), 20000_00);

        System.out.println(account1.toString());
        System.out.println(account2.toString());
        System.out.println(account3.toString());

        System.out.println("\nPetya -(1000 RUB)-> Vasya");
        account3.transferTo(account1, new Balance(1000_00, Currency.RUB));
        System.out.println(account1.toString());

        System.out.println("Vasya +50000");
        account1.deposite(new Balance(50000_00, Currency.RUB));
        System.out.println(account1.toString());

        System.out.println("Vasya -100");
        account1.withdraw(new Balance(100_00, Currency.RUB));
        System.out.println(account1.toString());

        try {
            System.out.print("\nПеревод себе: ");
            account1.transferTo(account1, new Balance(123, Currency.RUB));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.print("Превышение лимита: ");
            account1.transferTo(account1, new Balance(50000_01, Currency.RUB));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.print("Недостаточно средств: ");
            account2.withdraw(new Balance(21000_00, Currency.USD));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.print("Аккаунт заблокирован: ");
            account2.block();
            account2.transferTo(account1, new Balance(50000_01, Currency.RUB));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}