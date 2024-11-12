package com.javafullstackguru.service;

import com.javafullstackguru.entity.PerformanceReview;
import com.javafullstackguru.repository.PerformanceReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;

    @Override
    public PerformanceReview createPerformanceReview(PerformanceReview performanceReview) {
        return performanceReviewRepository.save(performanceReview);
    }

    @Override
    public Optional<PerformanceReview> getPerformanceReviewById(Integer id) {
        return performanceReviewRepository.findById(id);
    }

    @Override
    public List<PerformanceReview> getAllPerformanceReviews() {
        return performanceReviewRepository.findAll();
    }

    @Override
    public List<PerformanceReview> getPerformanceReviewsByEmployeeId(Integer employeeId) {
        return performanceReviewRepository.findByEmployeeEmployeeId(employeeId);
    }

    @Override
    public PerformanceReview updatePerformanceReview(Integer id, PerformanceReview updatedPerformanceReview) {
        return performanceReviewRepository.findById(id).map(review -> {
            review.setReviewPeriod(updatedPerformanceReview.getReviewPeriod());
            review.setScore(updatedPerformanceReview.getScore());
            review.setComments(updatedPerformanceReview.getComments());
            return performanceReviewRepository.save(review);
        }).orElse(null);
    }

    @Override
    public void deletePerformanceReviewById(Integer id) {
        performanceReviewRepository.deleteById(id);
    }
}
