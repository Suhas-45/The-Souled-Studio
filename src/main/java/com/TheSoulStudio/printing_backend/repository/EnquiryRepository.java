package com.TheSoulStudio.printing_backend.repository;

import com.TheSoulStudio.printing_backend.entity.Enquiry;
import com.TheSoulStudio.printing_backend.entity.EnquiryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnquiryRepository extends JpaRepository<Enquiry,Long> {
    long countByStatus(EnquiryStatus status);
}
