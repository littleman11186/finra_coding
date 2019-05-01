package com.finra.exercise.finra_coding.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for pulling and putting file metadata objects
 * 
 * @author Dan Little
 *
 */

@Repository
public interface FinraCodingFileDAO extends JpaRepository<FinraCodingFileMetadata, Long> {
 
    public List<FinraCodingFileMetadata> findByOwnerContainingIgnoreCase(String owner);
 
}