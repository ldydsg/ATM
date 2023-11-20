package aom.itheima;

public class Account {
    private String cardId;
    private String username;
    private char sex;
    private String password;
    private  double money;
    private double limit;//限额

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public String getCardId() {
        return cardId;
    }

    public String getUsername() {
        return username+(sex=='男'?"先生":"女士");
    }

    public char getSex() {
        return sex;
    }

    public String getPassword() {
        return password;
    }

    public double getMoney() {
        return money;
    }

    public double getLimit() {
        return limit;
    }
}
