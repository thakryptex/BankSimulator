package firstbankofkazan;


import firstbankofkazan.enums.Sex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class People {
    private String firstName;
    private String secondName;
    private String patronym;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private String dateOfBirth;
    private Sex sex;
    private long passportNum;
    private Accounts account;

    public People() {
    }

    public People(String firstName, String secondName, String patronym, String dateOfBirth, Sex sex, long passportNum) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronym = patronym;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.passportNum = passportNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronym() {
        return patronym;
    }

    public void setPatronym(String patronym) {
        this.patronym = patronym;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) throws ParseException {
        Date date = dateFormat.parse(dateOfBirth);
        this.dateOfBirth = dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public long getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(long passportNum) {
        this.passportNum = passportNum;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    @Override
    public String toString() {
        if (this.account == null)
            return secondName + " " + firstName + " " + patronym +
                    ", пол: " + sex +
                    ", дата рождения: " + dateOfBirth +
                    ", паспорт №: " + passportNum;
            return secondName + " " + firstName + " " + patronym +
                    ", пол: " + sex +
                    ", дата рождения: " + dateOfBirth +
                    ", паспорт №: " + passportNum +
                    ", " + account.toString().toLowerCase();
    }
}
