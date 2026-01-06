package com.TheSoulStudio.printing_backend.repository;

import com.TheSoulStudio.printing_backend.entity.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnquiryRepository extends JpaRepository<Enquiry,Long> {
}
