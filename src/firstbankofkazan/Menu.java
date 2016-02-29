package firstbankofkazan;


import firstbankofkazan.enums.CardType;
import firstbankofkazan.enums.Sex;
import firstbankofkazan.enums.TransactionType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private static Scanner scanner = new Scanner(System.in);
    private static String t;
    private static int i, j;

    // меню создания человека и аккаунта
    public static People menuCreatePeople() {
        People people = new People();
        System.out.println();

        System.out.println("Введите свои фамилию имя отчество");
        people.setSecondName(scanner.next());
        people.setFirstName(scanner.next());
        people.setPatronym(scanner.nextLine().replace(" ", ""));
        System.out.println();

        System.out.println("Пожалуйста, укажите свой пол:");
        do {
            t = scanner.nextLine().toUpperCase();
            if (t.equals("MALE") || t.equals("M") || t.equals("М") || t.equals("МУЖСКОЙ")) {
                people.setSex(Sex.MALE);
                break;
            }
            else if (t.equals("FEMALE") || t.equals("F") || t.equals("Ж") || t.equals("ЖЕНСКИЙ")) {
                people.setSex(Sex.FEMALE);
                break;
            }
            else {
                System.out.println("Укажите пол корректно");
            }
        }
        while (true);

        System.out.println();

        System.out.println("Укажите дату рождения (формат: 01.03.1980):");
        while (true) {
            try {
                people.setDateOfBirth(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Вы ввели дату в неправильном формате. Введите, пожалуйста, в этом формате: 01.03.1980, где 01 - это день, 03 - это месяц, а 1980 - год рождения");
            }
        }
        System.out.println();

        System.out.println("Последний пункт - укажите номер паспорта:");
        while (true) {
            try {
                people.setPassportNum(scanner.nextLong());
                break;
            } catch (Exception e) {
                System.out.println("Вы ввели неверный номер паспорта (слишком много цифр), попробуйте ещё раз...");
            }
        }
        System.out.println();

        people.setAccount(new Accounts(people));

        return people;
    }

    // меню работы с картами
    public static void menuCards(Accounts account) {
        System.out.println();
        if (account.getCards().size() == 0) {
            System.out.println("Выберите одну из опций:");
            System.out.println("1 - Узнать больше о типах счетов и условиях");
            System.out.println("2 - Создать счёт");
            while (true) {
                i = scanner.nextInt();
                if (i == 1) {
                    menuInfoCardTypes(account);
                    break;
                } else if (i == 2) {
                    menuCreateCard(account);
                    break;
                } else System.out.println("Опций под введённым Вами номером нет. Выберите 1 или 2.");
            }
        } else {
            menuShowCardsList(account);
            System.out.println();
            System.out.println("Введите порядковый № карты, с которой хотите работать:");
            while (true) {
                i = scanner.nextInt();
                if (i < 1 || i > account.getCards().size()) {
                    System.out.println("Введён неправильный номер. Попробуйте ещё раз.");
                } else break;
            }
            System.out.println();
            System.out.println(account.getCards().get(i - 1));
            System.out.println();
            while (true) {
                System.out.println("Выберите опцию:");
                System.out.println("1 - совершить транзакцию");
                System.out.println("2 - вывести список транзакций");
                j = scanner.nextInt();
                if (j == 1) {
                    menuChooseTransaction(account.getCards().get(i - 1));
                    break;
                } else if (j == 2) {
                    menuPrintCardTransactions(account.getCards().get(i - 1));
                    break;
                } else
                    System.out.println("Опций под введённым Вами номером нет. Выберите 1 или 2.");
            }
        }
    }

    // меню выбора транзакций
    private static void menuChooseTransaction(Cards card) {
        System.out.println();
        while (true) {
            System.out.println("Какого типа транзакцию Вы хотите совершить:");
            System.out.println("1 - Положить деньги на счёт");
            System.out.println("2 - Снять деньги со счёта");
            System.out.println("3 - Перевести деньги");
            i = scanner.nextInt();
            if (i == 1) {
                menuMakeTransaction(card, TransactionType.DEPOSIT);
                break;
            } else if (i == 2) {
                menuMakeTransaction(card, TransactionType.WITHDRAWAL);
                break;
            } else if (i == 3) {
                menuMakeTransaction(card, TransactionType.TRANSFER);
                break;
            } else
                System.out.println("Опций под введённым Вами номером нет. Выберите 1, 2 или 3.");
        }
    }

    // меню создания транзакции
    private static void menuMakeTransaction(Cards card, TransactionType type) {
        System.out.println();
        while (true) {
            System.out.println("Введите сумму транзакции:");
            i = scanner.nextInt();
            if (i < 0) System.out.println("Сумма не может быть меньше нуля. Введите другую сумму.");
            else break;
        }
        switch (type) {
            case DEPOSIT:
                card.getAccount().makeTransaction(type, card, i);
                break;
            case WITHDRAWAL:
                card.getAccount().makeTransaction(type, card, i);
                break;
            case TRANSFER:
                while (true) {
                    System.out.println("Напишите порядковый № карты, на которую Вы хотите перевести средства:");
                    menuShowCardsList(card.getAccount());
                    j = scanner.nextInt();
                    if (j < 1 || j > card.getAccount().getCards().size()) {
                        System.out.println("Введен неправильный номер. Попробуйте ещё раз.");
                    }
                    else break;
                }
                card.getAccount().makeTransaction(type, card, card.getAccount().getCards().get(j - 1), i);
                break;
        }
    }

    // вывод транзакций на экран
    private static void menuPrintCardTransactions(Cards cards) {
        System.out.println();
        System.out.println("Транзакции карты " + cards.getCardId() + ":");
        for (int k = 0; k < cards.getTransactionLog().size(); k++) {
            cards.getTransactionLog().get(i);
        }
    }

    // вывод списка карт на экран
    private static void menuShowCardsList(Accounts account) {
        System.out.println();
        for (int k = 0; k < account.getCards().size(); k++) {
            System.out.println(account.getCards().get(k));
        }
    }

    // меню информации о счетах
    private static void menuInfoCardTypes(Accounts account) {
        System.out.println();
        System.out.println("Наш Банк предлагает Вам 3 типа счетов:");
        System.out.println("1) Chequing: минимальный стартовый вклад - 1000 р., начисляемая сумма - 1000 р./мес.");
        System.out.println("2) Savings: минимальный стартовый вклад - 50000 р., начисляемая сумма - 5% в мес.");
        System.out.println("3) Business: минимальный стартовый вклад - 5000000 р., начисляемая сумма - 1% в мес.");
        System.out.println();
        System.out.println("Хотите открыть счёт или хотите выйти?");
        System.out.println("1 - Открыть счёт");
        System.out.println("2 - Завершить обслуживание");
        while (true) {
            i = scanner.nextInt();
            if (i == 1) {
                menuCreateCard(account);
                break;
            } else if (i == 2) {
                System.exit(0);
            } else System.out.println("Опций под введённым Вами номером нет. Выберите 1 или 2.");
        }
    }

    // меню создания карты
    private static void menuCreateCard(Accounts account) {
        System.out.println();
        System.out.println("Для создания счёта необходимо выбрать один из трёх типов счетов и положить минимальную сумму на счёт.");
        System.out.println();
        while (true) {
            System.out.println("Выберите тип счёта:");
            System.out.println("1 - Chequing (мин. 1000 р.)");
            System.out.println("2 - Savings (мин. 50000 р.)");
            System.out.println("3 - Business (мин. 5000000 р.)");
            i = scanner.nextInt();
            if (!(i == 1 || i == 2 || i == 3)) {
                System.out.println("Опций под введённым Вами номером нет. Выберите 1, 2 или 3.");
                System.out.println();
                continue;
            }
            System.out.println();
            System.out.println("Введите сумму, которую хотите положить на счёт:");
            j = scanner.nextInt();
            if ((i == 1 && j < 1000) || (i == 2 && j < 50000) || (i == 3 && j < 5000000)) {
                System.out.println("Указанная сумма слишком мала для выбранного Вами типа. Попробуйте ещё раз.");
                System.out.println();
                continue;
            }
            Cards card;
            switch (i) {
                case 1:
                    account.createCard(j, CardType.CHEQUING);
                    break;
                case 2:
                    account.createCard(j, CardType.SAVINGS);
                    break;
                case 3:
                    account.createCard(j, CardType.BUSINESS);
                    break;
            }
            break;
        }
        System.out.println("Операция проведена успешно.");
    }

    // меню главного меню АТМ
    public static void menuAccountMain(Accounts account) {
        System.out.println();
        while (true) {
            System.out.println("Главное меню АТМ Первого Банка Казани.");
            System.out.println("Выберите одну из опций:");
            System.out.println("1 - Создать/закрыть счёт");
            System.out.println("2 - Операции со счетами");
            System.out.println("3 - Посмотреть полный баланс аккаунта");
            System.out.println("4 - Получить полный список транзакций по всем счетам аккаунта");
            System.out.println("5 - Выйти");
            i = scanner.nextInt();
            if (i == 1) { //TODO сделать все эти операции
                System.out.println("1 - Создать/закрыть счёт");
                break;
            } else if (i == 2) {
                System.out.println("2 - Операции со счетами");
                break;
            } else if (i == 3) {
                System.out.println("3 - Посмотреть полный баланс аккаунта");
                break;
            } else if (i == 4) {
                System.out.println("4 - Получить полный список транзакций по всем счетам аккаунта");
                break;
            } else if (i == 5) {
                System.exit(0);
            } else
                System.out.println("Опций под введённым Вами номером нет. Выберите 1, 2, 3 или 4.");
        }
    }

    // меню создания txt-файла со списком транзакций
    public static void menuSaveTransactionLogsIntoTxt(Cards card) {
        System.out.println();
        while (true) {
            System.out.println("Вы хотите сохранить все операции по карте в файл?");
            System.out.println("1 - Да");
            System.out.println("2 - Нет");
            i = scanner.nextInt();
            if (i == 1) break;
            else if (i == 2) System.exit(0);
            else System.out.println("Опций под введённым Вами номером нет. Выберите 1 или 2.");
        }
        System.out.println();
        System.out.print("Сохраняю...");
        System.out.print(".");
        System.out.print(".");
        System.out.print(".");
        System.out.println();
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("transactions.txt");
            fileWriter.write(card.getAccount().getOwner().toString() + "\n \n" +
                    card.getAccount().toString() + ": \n \n" +
                    "Логи всех транзакций по карте №" + String.format("%06d", card.getCardId()) + ": \n");
            for (int j = 0; j < card.getTransactionLog().size(); j++) {
                fileWriter.append((j+1) + ") " + card.getTransactionLog().get(j).toString()+"\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Сохранено.");
    }
}
