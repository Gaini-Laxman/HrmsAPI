package com.javafullstackguru.repository;


import com.javafullstackguru.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
    // You can add custom query methods if needed
}

