package com.javafullstackguru.restcontroller;

import com.javafullstackguru.entity.PerformanceReview;
import com.javafullstackguru.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance-reviews")
public class PerformanceReviewController {

    @Autowired
    private PerformanceReviewService performanceReviewService;

    @PostMapping
    public ResponseEntity<PerformanceReview> createPerformanceReview(@RequestBody PerformanceReview performanceReview) {
        PerformanceReview createdReview = performanceReviewService.createPerformanceReview(performanceReview);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceReview> getPerformanceReviewById(@PathVariable Integer id) {
        return performanceReviewService.getPerformanceReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<PerformanceReview> getAllPerformanceReviews() {
        return performanceReviewService.getAllPerformanceReviews();
    }

    @GetMapping("/employee/{employeeId}")
    public List<PerformanceReview> getPerformanceReviewsByEmployeeId(@PathVariable Integer employeeId) {
        return performanceReviewService.getPerformanceReviewsByEmployeeId(employeeId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerformanceReview> updatePerformanceReview(@PathVariable Integer id, @RequestBody PerformanceReview updatedReview) {
        PerformanceReview updated = performanceReviewService.updatePerformanceReview(id, updatedReview);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformanceReviewById(@PathVariable Integer id) {
        performanceReviewService.deletePerformanceReviewById(id);
        return ResponseEntity.noContent().build();
    }
}
