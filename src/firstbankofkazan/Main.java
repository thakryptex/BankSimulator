package firstbankofkazan;


import firstbankofkazan.enums.TransactionType;

import java.util.Scanner;

public class Main {
    /*
    Программа-АТМ Первого Банка Казани.
    У одного человека может быть только один аккаунт внутри Банка (Банк работает не больше, чем со 100 аккаунтами).
    Внутри аккаунта человек может открывать несколько счетов (не больше 100).
    Каждый счёт работает через аккаунт.
    Есть три типа счетов: chequing, savings, business.
    Каждый счёт имеет минимальный вклад при создании счёта (делается проверка) и свои условия работы.
    Внутри счёта человек может осуществлять три типа транзакций: положить, снять, перевести деньги.
    Банк платит проценты за хранение денег на счету.
    Операция производится раз в месяц (30 дней), а расчёт процентов производится ежедневно в конце дня.
    Каждая успешная транзакция привязана ко времени и фиксируется в массив транзакций (не больше 100 для одного счёта).
    Список транзакций можно сохранить в txt-файл с удобным видом для чтения.
     */

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Загрузка ATM...");
        System.out.println();
        System.out.println("Добро пожаловать в Первый Банк Казани!");
        System.out.println();
        System.out.println("Для работы с АТМ Вам необходимо открыть свой аккаунт в нашем Банке.");
        System.out.println("Для этого Вам потребуется ввести следующие данные:");
        System.out.println("Фамилия Имя Отчество, Дата рождения, Пол, Номер паспорта.");
        System.out.println();
        System.out.println("Вы хотите продолжить?");
        System.out.println("1 - Да");
        System.out.println("2 - Нет");

        int i;
        // проверка ввода
        while (true) {
            i = scanner.nextInt();
            if (i == 1 || i == 2) break;
            else {
                System.out.println("Вам необходимо ввести либо 1 = Да, либо 2 = Нет. Попробуйте ещё раз:");
            }
        }
        // выход из АТМ
        if (i == 2) {
            System.out.println("Без личных данных мы не можем открыть Вам аккаунт в нашем Банке. Всего Вам доброго!");
            System.exit(0);
        }

        // создание человека и его аккаунта в Банке
        People people = Menu.menuCreatePeople();

        System.out.println("Ваши данные:");
        System.out.println(people);
        System.out.println();

        System.out.println("Вы успешно прошли регистрацию аккаунта в Первом Банке Казани.");
        System.out.println("Теперь Вы можете открывать счета в нашем Банке, хранить на них деньги и получать за это проценты.");

        // меню управления счетами/картами
        Menu.menuCards(people.getAccount());

//        Menu.menuAccountMain(people.getAccount());
        System.out.println();

        // если карта создана, то делаем некоторую работу с ней
        if (people.getAccount().getCards().size() > 0) {
            Cards card = people.getAccount().getCards().get(0);
            System.out.println("Введём 4 транзакции с картой...");
            people.getAccount().makeTransaction(TransactionType.DEPOSIT, card, 666);
            Thread.sleep(500);
            people.getAccount().makeTransaction(TransactionType.WITHDRAWAL, card, 333);
            Thread.sleep(500);
            people.getAccount().makeTransaction(TransactionType.DEPOSIT, card, 999);
            Thread.sleep(500);
            people.getAccount().makeTransaction(TransactionType.WITHDRAWAL, card, 777);

            System.out.println();
            System.out.println("Пусть Банк выплатит Вам проценты за месяц...");
            Bank.payInterest(card);

            // сохранение списка транзакций в txt-файл
            Menu.menuSaveTransactionLogsIntoTxt(card);

            System.out.println();
            System.out.println("Спасибо за использование услуг Первого Банка Казани. Всего доброго!");
        }


    }
}
