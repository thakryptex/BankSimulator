package firstbankofkazan;


import firstbankofkazan.enums.CardType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Cards {

    private int cardId;
    private Accounts account;
    private CardType type;
    private double money;
    private String creationDate;
    private String countingDate;
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private static Calendar calendar = Calendar.getInstance();
    private double[] arrayDailyBalance = new double[30];
    private ArrayList<Transactions> transactionLog = new ArrayList<>();

    // конструктор карты также фиксирует дату создания карты
    public Cards(Accounts account, double money, CardType type) {
        this.account = account;
        this.money = money;
        this.type = type;
        this.cardId = (int) (Math.random() * 1000000);
        creationDate = formatter.format(new Date());
        countingDate = creationDate;
        Date date = Cards.getDate(countingDate);
        this.initializeDailyBalanceArray();
        this.recordDailyBalance(date, money);
    }

    public int getCardId() {
        return cardId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Accounts getAccount() {
        return account;
    }

    // список всех транзакций карты
    public ArrayList<Transactions> getTransactionLog() {
        return transactionLog;
    }

    private void initializeDailyBalanceArray() {
        for (int i = 0; i < arrayDailyBalance.length; i++) {
            arrayDailyBalance[i] = -1;
        }
    }

    public double[] getArrayDailyBalance() {
        return arrayDailyBalance;
    }

    private static Date getDate(String countingDate) {
        Date date = null;
        try {
            date = formatter.parse(countingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // получить день года для расчёта процентов
    private int getDayOfMonth(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    // метод заполняет пустые элементы массива данными о балансе на тот день
    public void recordDailyBalance(Date date, double money) {
        int index = getDayOfMonth(date) - getDayOfMonth(getDate(countingDate)); //TODO сделать фикс для другого года (1 дата - 2015, 2 дата - 2016)
        this.arrayDailyBalance[index] = money;
    }

    // возвращает сумму для оплаты процентов от Банка
    public double calculateInterest() {
        if (this.type.equals(CardType.CHEQUING)) return 1000;
        int day1 = this.getDayOfMonth(getDate(countingDate));
        double payment = 0;
        for (int i = 1; i < 30; i++) {
            int prevDay = 0;
            if (arrayDailyBalance[i] == -1) {
                arrayDailyBalance[i] = arrayDailyBalance[prevDay];
            }
            payment += this.calculatePaymentOnPercents(i);
            prevDay++;
        }
        payment += this.calculatePaymentOnPercents(0);
        return payment;
    }

    // считает конкретную сумму платежа по процентам, основываясь на полном массиве данных о состоянии счёта на каждый день
    private double calculatePaymentOnPercents(int i) {
        double payment = 0;
        switch (this.type) {
            case SAVINGS:
                payment += arrayDailyBalance[i]*(0.05/30);
                break;
            case BUSINESS:
                payment += arrayDailyBalance[i]*(0.01/30);
                break;
        }
        return payment;
    }


    @Override
    public String toString() {
        return "Аккаунт №" + String.format("%06d",account.getAccountId()) +
                ", #" + (account.getCards().indexOf(this)+1) +
                ", Card №" + String.format("%06d",cardId) +
                ", тип карты: " + type +
                ", баланс: " + money +
                ", дата создания: " + countingDate;
    }
}
