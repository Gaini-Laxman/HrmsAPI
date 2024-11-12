package com.javafullstackguru.service;

import com.javafullstackguru.entity.PerformanceReview;

import java.util.List;
import java.util.Optional;

public interface PerformanceReviewService {
    PerformanceReview createPerformanceReview(PerformanceReview performanceReview);
    Optional<PerformanceReview> getPerformanceReviewById(Integer id);
    List<PerformanceReview> getAllPerformanceReviews();
    List<PerformanceReview> getPerformanceReviewsByEmployeeId(Integer employeeId);
    PerformanceReview updatePerformanceReview(Integer id, PerformanceReview updatedPerformanceReview);
    void deletePerformanceReviewById(Integer id);
}
