package com.infogen.loyalty.database;

import com.infogen.loyalty.entity.Customer;
import com.infogen.loyalty.entity.RewardPoints;
import com.infogen.loyalty.entity.Transaction;
import com.infogen.loyalty.enums.TransactionStatus;
import com.infogen.loyalty.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Profile("h2")
@Component
public class LoadH2Database {

    private final CustomerRepository customerRepository;

    @Autowired
    public LoadH2Database(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Customer> customerList = createTestData();
        customerRepository.saveAll(customerList);
    }

    private List<Customer> createTestData() {
        List<Customer> customerList = new ArrayList<>();
        List<Transaction> transactionList = new ArrayList<>();
        List<RewardPoints> rewardPointsList = new ArrayList<>();
        Date oneMonthAgo = convertLocalDateToDate(1);
        Date twoMonthsAgo = convertLocalDateToDate(2);
        Date threeMonthsAgo = convertLocalDateToDate(3);

        Customer customer = new Customer();
        customer.setName("Ahmed");
        customer.setUsername("ahmed");
        customer.setCreationDate(threeMonthsAgo);
        customer.setUpdateDate(threeMonthsAgo);

        Transaction transaction = new Transaction();
        transaction.setAmount(120);
        transaction.setUpdateDate(oneMonthAgo);
        transaction.setCreationDate(oneMonthAgo);
        transaction.setLoyaltyPoints(90);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        transaction = new Transaction();
        transaction.setAmount(50);
        transaction.setUpdateDate(twoMonthsAgo);
        transaction.setCreationDate(twoMonthsAgo);
        transaction.setLoyaltyPoints(50);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        transaction = new Transaction();
        transaction.setAmount(200);
        transaction.setUpdateDate(threeMonthsAgo);
        transaction.setCreationDate(threeMonthsAgo);
        transaction.setLoyaltyPoints(350);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        transaction = new Transaction();
        transaction.setAmount(200);
        transaction.setUpdateDate(threeMonthsAgo);
        transaction.setCreationDate(threeMonthsAgo);
        transaction.setLoyaltyPoints(350);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        RewardPoints rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(oneMonthAgo);
        rewardPoints.setPoints(350 * 2);
        rewardPointsList.add(rewardPoints);

        rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(twoMonthsAgo);
        rewardPoints.setPoints(50);
        rewardPointsList.add(rewardPoints);

        rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(threeMonthsAgo);
        rewardPoints.setPoints(90);
        rewardPointsList.add(rewardPoints);

        customer.setTransactions(transactionList);
        customer.setRewardPoints(rewardPointsList);

        customerList.add(customer);

        transactionList = new ArrayList<>();
        rewardPointsList = new ArrayList<>();

        customer = new Customer();
        customer.setName("Khaled");
        customer.setUsername("khaled");
        customer.setCreationDate(threeMonthsAgo);
        customer.setUpdateDate(threeMonthsAgo);

        transaction = new Transaction();
        transaction.setAmount(30);
        transaction.setUpdateDate(oneMonthAgo);
        transaction.setCreationDate(oneMonthAgo);
        transaction.setLoyaltyPoints(0);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        transaction = new Transaction();
        transaction.setAmount(100);
        transaction.setUpdateDate(twoMonthsAgo);
        transaction.setCreationDate(twoMonthsAgo);
        transaction.setLoyaltyPoints(1);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        transaction = new Transaction();
        transaction.setAmount(300);
        transaction.setUpdateDate(threeMonthsAgo);
        transaction.setCreationDate(threeMonthsAgo);
        transaction.setLoyaltyPoints(405);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        transaction = new Transaction();
        transaction.setAmount(300);
        transaction.setUpdateDate(threeMonthsAgo);
        transaction.setCreationDate(threeMonthsAgo);
        transaction.setLoyaltyPoints(405);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(oneMonthAgo);
        rewardPoints.setPoints(405 * 2);
        rewardPointsList.add(rewardPoints);

        rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(twoMonthsAgo);
        rewardPoints.setPoints(1);
        rewardPointsList.add(rewardPoints);

        rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(threeMonthsAgo);
        rewardPoints.setPoints(0);
        rewardPointsList.add(rewardPoints);

        customer.setTransactions(transactionList);
        customer.setRewardPoints(rewardPointsList);
        customerList.add(customer);

        transactionList = new ArrayList<>();
        rewardPointsList = new ArrayList<>();

        customer = new Customer();
        customer.setName("Fouad");
        customer.setUsername("fouad");
        customer.setCreationDate(threeMonthsAgo);
        customer.setUpdateDate(threeMonthsAgo);

        transaction = new Transaction();
        transaction.setAmount(40);
        transaction.setUpdateDate(oneMonthAgo);
        transaction.setCreationDate(oneMonthAgo);
        transaction.setLoyaltyPoints(0);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        transaction = new Transaction();
        transaction.setAmount(10);
        transaction.setUpdateDate(twoMonthsAgo);
        transaction.setCreationDate(twoMonthsAgo);
        transaction.setLoyaltyPoints(0);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        transaction = new Transaction();
        transaction.setAmount(60);
        transaction.setUpdateDate(threeMonthsAgo);
        transaction.setCreationDate(threeMonthsAgo);
        transaction.setLoyaltyPoints(1);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        transaction = new Transaction();
        transaction.setAmount(60);
        transaction.setUpdateDate(threeMonthsAgo);
        transaction.setCreationDate(threeMonthsAgo);
        transaction.setLoyaltyPoints(1);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(oneMonthAgo);
        rewardPoints.setPoints(2);
        rewardPointsList.add(rewardPoints);

        rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(twoMonthsAgo);
        rewardPoints.setPoints(0);
        rewardPointsList.add(rewardPoints);

        rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(threeMonthsAgo);
        rewardPoints.setPoints(0);
        rewardPointsList.add(rewardPoints);

        customer.setTransactions(transactionList);
        customer.setRewardPoints(rewardPointsList);
        customerList.add(customer);

        return customerList;
    }

    private Date convertLocalDateToDate(int monthToMinus) {
        LocalDate calendar = LocalDate.now().minusMonths(monthToMinus);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = calendar.atStartOfDay(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }

}