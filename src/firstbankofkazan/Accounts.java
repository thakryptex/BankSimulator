package firstbankofkazan;



import firstbankofkazan.enums.CardType;
import firstbankofkazan.enums.TransactionType;
import firstbankofkazan.exceptions.BalanceException;
import firstbankofkazan.exceptions.TooManyTransactionsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Accounts {
    // минимальные суммы для вкладов при создании счетов разных типов
    private static final int CHEQUING_MIN = 1000;
    private static final int SAVINGS_MIN = 50000;
    private static final int BUSINESS_MIN = 5000000;

    private int accountId;
    private People people;
    private List<Cards> cards = new ArrayList<>(); // список карт, принадлежащих аккаунту

    public Accounts(People people) {
        this.people = people;
        accountId = (int) (Math.random() * 1000000); // рандомный id для аккаунта
    }

    // метод создания карты с проверкой на минимальный вклад
    public void createCard(double money, CardType type) {
        try {
            switch (type) {
                case CHEQUING:
                    if (money < CHEQUING_MIN) throw new BalanceException("Недостаточно средств для открытия счёта");
                    break;
                case SAVINGS:
                    if (money < SAVINGS_MIN) throw new BalanceException("Недостаточно средств для открытия счёта");
                    break;
                case BUSINESS:
                    if (money < BUSINESS_MIN) throw new BalanceException("Недостаточно средств для открытия счёта");
                    break;
            }
        } catch (BalanceException e) {
            System.out.println(e.getMessage());
            return;
        }
        Cards card = new Cards(people.getAccount(), money, type);
        cards.add(card);
        System.out.println(card);
    }

    public void closeCard() {
        for (int i = 0; i < cards.size(); i++) {
            System.out.println(cards.get(i));
        }
        System.out.println("Введите id карты, которую Вы хотите закрыть:");
        Scanner scanner = new Scanner(System.in);
        int cardId = scanner.nextInt();

        cards.remove(cardId);
    }

    // метод для создания транзакций типа deposit, withdrawal
    public void makeTransaction(TransactionType type, Cards card, double money) {
        try {
            if (card.getTransactionLog().size() >= 100) {
                throw new TooManyTransactionsException("Вы израсходовали лимит транзакций за этот месяц. Пожалуйста, дождитесь следующего месяца.");
            }
            switch (type) {
                case DEPOSIT:
                    Transactions.deposit(money, card, card.getTransactionLog().size()+1);
                    break;
                case WITHDRAWAL:
                    Transactions.withdrawal(money, card, card.getTransactionLog().size()+1);
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // метод для создания транзакций типа transfer
    public void makeTransaction(TransactionType type, Cards card1, Cards card2, double money) {
        try {
            if (card1.getTransactionLog().size() >= 100) throw new TooManyTransactionsException("Вы израсходовали лимит транзакций за этот месяц. Пожалуйста, дождитесь следующего месяца.");
            Transactions.transfer(money, card1, card2, card1.getTransactionLog().size()+1, card2.getTransactionLog().size()+1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Cards> getCards() {
        return cards;
    }

    public int getAccountId() {
        return accountId;
    }

    public People getOwner() {
        return people;
    }

    @Override
    public String toString() {
        return "Аккаунт № " + accountId;
    }
}