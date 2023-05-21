package com.infogen.loyalty.repository;

import com.infogen.loyalty.entity.Customer;
import com.infogen.loyalty.entity.RewardPoints;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RewardPointsRepository extends JpaRepository<RewardPoints, Long> {

    Optional<RewardPoints> findOneByCustomerAndMonth(Customer customer, Date month);

    @Query(value = "select * from reward_points where month_date between :monthStart and :monthEnd",
            countQuery = "select count(*) from reward_points where month between :monthStart and :monthEnd", nativeQuery = true)
    List<RewardPoints> findAllByMonthBetweenAndGroupByCustomer(@Param("monthStart") Date monthDateStart,
                                                               @Param("monthEnd") Date monthDateEnd, Pageable pageable);
}