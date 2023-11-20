package aom.itheima;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    private ArrayList<Account>accounts=new ArrayList<>();
    private Scanner sc=new Scanner(System.in);
    private Account loginAcc;//记住登录后的用户账户
    //启动ATM系统，展示欢迎界面
    public void start(){
        while (true) {
            System.out.println("===欢迎您进入到了ATM系统===");
            System.out.println("1.用户登录");
            System.out.println("2.用户开户");
            System.out.println("请选择：");
            int command=sc.nextInt();
            switch (command){
                case 1:
                    //用户登陆
                    login();
                    break;
                case 2:
                    //用户操作
                    creatAccount();
                    break;
                default:
                    System.out.println("没有操作");
            }
        }
    }
    //完成用户的登陆操作
    private void login(){
        System.out.println("==系统登陆");
        if(accounts.size()==0){
            System.out.println("系统中无任何账户，请先开户再来~~  ");
            return;
        }
        //2.系统中存在账户对象，可以操作
        while (true) {
            System.out.println("请您输入您的登陆卡号：");
            String cardId=sc.next();
            //3.判断卡号是否存在
            Account acc=getAccountByCardId(cardId);
            if(acc==null){
                System.out.println("您输入的登录卡号不存在，请确认~~");
            }else{
                while (true) {
                    System.out.println("请您输入登陆密码");
                    String password=sc.next();
                    //4.判断密码是否正确
                    if(acc.getPassword().equals(password)){
                        loginAcc=acc;
                        //密码正确
                        System.out.println("恭喜您，"+acc.getUsername()+"恭喜您成功登录系统，您的卡号是："+acc.getCardId());
                        //展示登陆后操作页面
                        showUserCommand();
                        return;//结束当前登录方法
                    }else{
                        System.out.println("您输入的密码不正确，请确认~~");
                    }
                }
            }
        }
    }
    private void showUserCommand(){
        while (true) {
            System.out.println(loginAcc.getUsername()+"您可以选择如下功能进行账户的处理====");
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.密码修改");
            System.out.println("6.退出");
            System.out.println("7.注销当前账户");
            System.out.println("请选择：");
            int command=sc.nextInt();
            switch (command) {
                case 1:
                    //查询账户
                    showLoginAccount();
                    break;
                case 2:
                    //存款
                    depositMoney();
                    break;
                case 3:
                    //取款
                    drawmoney();
                    break;
                case 4:
                    //转账
                    transferMoney();
                    break;
                case 5:
                    //密码修改
                    updatePassword();
                    return;//结束当前方法
                case 6:
                    //退出
                    System.out.println(loginAcc.getUsername() + "您退出系统成功");

                    return;
                case 7:
                    //注销
                    if (deleteAccount()) {
                        return;
                    }

                    break;
                default:
                    System.out.println("您当前的操作有误，请确认~~");
            }
        }
    }
//修改密码
    private void updatePassword() {
        System.out.println("==账户密码修改操作==");
        while (true) {
            System.out.println("请您输入当前账户密码：");
            String password=sc.next();
            if (loginAcc.getPassword().equals(password)){
                //认证通过
                while (true) {
                    System.out.println("请您输入新密码");
                    String newPassword=sc.next();
                    System.out.println("请您再次输入密码");
                    String okPassword=sc.next();
                    if (okPassword.equals(newPassword)){
                        //可以真正修改密码
                        loginAcc.setPassword(okPassword);
                        System.out.println("恭喜您您输入的密码修改成功");
                        return;
                    }else{
                        System.out.println("您输入的两次密码不一致");
                    }
                }

            }else{
                System.out.println("您当前输入的密码不正确");
            }
        }
    }

    //销户
    private boolean deleteAccount() {
        System.out.println("==正在进行销户操作==");
        System.out.println("请问您确认销户吗？y/n");
        String command=sc.next();
        switch (command){
            case "y":
                //销户
                //判断账户是否有钱
                if (loginAcc.getMoney()==0){
                    accounts.remove(loginAcc);
                    System.out.println("您好，您的的账户已经成功销户");
                    return true;
                }else{
                    System.out.println("对不起您的账户中存在金额，不允许销户~~");
                }
            default:
                System.out.println("好的，您的帐户保留");
                return false;
        }
    }

    //转账
    private void transferMoney() {
        System.out.println("==用户转账==");
        //判断是否有其他账户
        if (accounts.size()<2){
            System.out.println("当前账户只有一个账户，无法为其它账户转账");
            return;
        }
        if(loginAcc.getMoney()==0){
            System.out.println("您自己都没钱，就别转了~~");
            return;
        }
        //3.真正开始转帐
        while (true) {
            System.out.println("请您输入对方的卡号");
            String cardId=sc.next();
            //4。判断卡号是否正确
            Account acc= getAccountByCardId(cardId);
            if (acc==null){
                System.out.println("您输入的卡号不存在");
            }else {
                //对方账户存在，继续让用户认证姓氏。
                String name="*"+acc.getUsername().substring(1);
                System.out.println("请您输入【"+name+"】姓氏");
                String preName=sc.next();
                //5.判断姓氏是否正确
                if (acc.getUsername().startsWith(preName)){
                    while (true) {
                        //认证通过了
                        System.out.println("请您输入转账金额：");
                        double money=sc.nextDouble();
                        //6.判断金额是否有没有超过自己的余额
                        if (loginAcc.getMoney()>=money){
                            //没超过余额
                            //更新自己的账户余额
                            loginAcc.setMoney(loginAcc.getMoney()-money);
                            //更新对方的
                            acc.setMoney(acc.getMoney()+money);
                            System.out.println("转账成功");
                            return;//直接跳出转账方法
                        }else{
                            System.out.println("您余额不足，无法转这么多钱，最多可转："+loginAcc.getMoney());
                        }
                    }
                }else{
                    System.out.println("对不起，您认证的姓氏有问题");
                }

            }
        }
    }

    //取钱
    private void drawmoney() {
        System.out.println("==取钱操作==");
        //判断账户余额是否达到了100元，如果不够则不允许取钱
        if (loginAcc.getMoney()<100){
            System.out.println("您的账户余额不足100元，不允许取钱");
            return;
        }
        //2.用户输入取款金额
        while (true) {
            System.out.println("请您输入取款金额");
            double money=sc.nextDouble();
            //3.判断账户余额是否足够
            if (loginAcc.getMoney()>=money){
                //余额充足
                //4.判断当前取款金额是否超过限额
                if (money>loginAcc.getLimit()) {
                    System.out.println("您当前取款金额超过了每次限额，您每次最多可取："+loginAcc.getLimit());
                }else {
                loginAcc.setMoney(loginAcc.getMoney()-money);
                    System.out.println("您取款："+money+"成功，取款后余额为："+loginAcc.getMoney());
                    break;
                }
            }else{
                System.out.println("余额不足，您账户的余额是："+loginAcc.getMoney());
            }
        }
    }

    //存钱
    private void depositMoney() {
        System.out.println("==存钱操作==");
        System.out.println("请您输入存款金额");
        double money=sc.nextDouble();
        //更新账户余额
        loginAcc.setMoney(loginAcc.getMoney()+money);
        System.out.println("恭喜您，您存钱："+money+"成功，存钱后余额是："+loginAcc.getMoney());
    }

    //展示登陆账户信息
    private  void showLoginAccount(){
        System.out.println("==当前您的信息如下==");
        System.out.println("卡号："+loginAcc.getCardId());
        System.out.println("户主："+loginAcc.getUsername());
        System.out.println("性别："+loginAcc.getSex());
        System.out.println("余额："+loginAcc.getMoney());
        System.out.println("每次取现额度："+loginAcc.getLimit());
    }
    private  void  creatAccount(){
        System.out.println("==系统开户操作==");
        Account acc=new Account();
        while (true) {
            System.out.println("请您输入您的账户名称：");
            String name=sc.next();
            acc.setUsername(name);
            System.out.println("请输入您的性别：");
            char sex= sc.next().charAt(0);
            if(sex=='男'||sex=='女'){
                acc.setSex(sex);
                break;
            }else{
                System.out.println("您输入的性别有误，只能为男或女");
            }
        }
        while (true) {
            System.out.println("请您输入您的账号密码");
            String password=sc.next();
            System.out.println("请您输入您的确认密码：");
            String okPassword= sc.next();
            if(okPassword.equals(password)){
                acc.setPassword(password);
                break;
            }else{
                System.out.println("两次输入密码不一致，请重新输入");
            }
        }
        System.out.println("请您输入您的取现额度：");
        double limit=sc.nextDouble();
        acc.setLimit(limit);
        //重点：为账户生成一个卡号（由系统自动生成，88位数字表示，不能与其他账户卡号重复）
        String newCardId=creatCardId();
        acc.setCardId(newCardId);
    //3.把账户对象存到集合中去
        accounts.add(acc);
        System.out.println("恭喜您，"+acc.getUsername()+"开户成功，您的卡号是："+acc.getCardId());
    }
    private String creatCardId(){
        while (true) {
            String cardId="";
            //2.使用循环，循环8次，每次产生一个随机数
            Random r=new Random();
            for (int i = 0; i < 8; i++) {
                int data=r.nextInt(10);//0-9
                cardId+=data;
            }
            //3.判断cardId中记住的卡号，是否与其他账户的卡号重复了，没有重复，才可以作为一个新卡号返回。
            Account acc=getAccountByCardId(cardId);
            if(acc==null){
                //说明cardid没有找到账户对象，因此没有与其他重复，可以返回
                return cardId;
            }
        }
    }
    //根据卡号查询账户对象返回
    private Account getAccountByCardId(String cardId){
        for (int i = 0; i < accounts.size(); i++) {
            Account acc =accounts.get(i);
            //判断这个账户acc中卡号是否是我们要找的
            if(acc.getCardId().equals(cardId)){
                return acc;
            }
        }
        return null;
    }
}
