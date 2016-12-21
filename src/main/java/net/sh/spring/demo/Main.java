package net.sh.spring.demo;

import net.sh.spring.demo.entity.Account;
import net.sh.spring.demo.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deven on 2016-12-19.
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext-cache.xml");// 加载 spring 配置文件

        AccountService s = (AccountService) context.getBean("accountService");

        System.out.println("------------------------------");

        // 第一次查询，应该走数据库
        System.out.println("[1] query 1st time: ");
        Account acc1 = s.getAccountById("1");
        System.out.println(acc1);

        System.out.println("[1] update: ");
        acc1.setName("account-1");
        s.saveAccount(acc1);
        System.out.println(acc1);

        // 第二次查询，应该不查数据库，直接返回缓存的值
        System.out.println("[1] query 2nd time: ");
        acc1 = s.getAccountById("1");
        System.out.println(acc1);

        System.out.println("------------------------------");

        System.out.println("[2] query 1st time: ");
        Account acc2 = s.getAccountById("2");
        System.out.println(acc2);

        System.out.println("[2] update: ");
        acc2.setName("account-2");
        s.saveAccount(acc2);
        System.out.println(acc2);

        System.out.println("[2] query 2nd time: ");
        acc2 = s.getAccountById("2");
        System.out.println(acc2);

        System.out.println("------------------------------");

//        {
//            List<Account> accounts = new ArrayList<Account>();
//            accounts.add(acc1);
//            accounts.add(acc2);
//
//            System.out.println("[x] refresh accounts: ");
//            s.refreshAccounts(accounts);
//        }
    }

}
