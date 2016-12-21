package net.sh.spring.demo.service;

import net.sh.spring.demo.entity.Account;
import net.sh.spring.demo.utils.CacheConstants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Deven on 2016-12-19.
 */
@Service("accountService")
//@CacheConfig(cacheNames = "accountService:accountCache")
public class AccountService {

    private static Map<String, Account> map = new ConcurrentHashMap<String, Account>();

    static {
        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            Account acc = new Account("account-" + random.nextInt(10));
            map.put(acc.getId(), acc);
        }
    }

    //@Cacheable(value = "accountCache", key = "#userName") //如果使用了key属性，则KeyGenerator不会生效
    @Cacheable(cacheNames = "accountService:accountCache")
//    @Cacheable
    public Account getAccountById(String userId) {
        System.out.println("AccountService: real query account: " + userId);
        return getFromDB(userId);
    }


    @CachePut(cacheNames = "accountService:accountCache", key = "#account.id")
//    @CachePut(key = "#account.id")
    public Account saveAccount(Account account) {
        System.out.println("AccountService: real update account: " + account.getId());
        return saveToDB(account); //返回值必须为Account，否则被缓存的就是其他类型了
    }


    @CacheEvict(cacheNames="accountService:accountCache", allEntries=true)
//    @CacheEvict(allEntries=true)
    public void refreshAccounts(List<Account> accounts) {
        System.out.println("AccountService: real refresh all accounts... " );
        for(Account acc : accounts) {
            getFromDB(acc.getId());
        }
    }

    // -------------------------------------------------------------------------

    private Account getFromDB(String acctId) {
        System.out.println("Real querying db: " + acctId);
        return map.get(acctId);
    }

    private Account saveToDB(Account account) {
        if(map.containsKey(account.getId())) {
            System.out.println("Real updating db: " + account.getId() + " exists, update record");
        } else {
            System.out.println("Real updating db: " + account.getId() + " not exists, save record");
        }
        map.put(account.getId(), account);
        return account;
    }
}
