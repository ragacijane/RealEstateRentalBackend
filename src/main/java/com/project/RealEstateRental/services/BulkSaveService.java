package com.project.RealEstateRental.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BulkSaveService {

    @Transactional
    public <T> void saveAll(JpaRepository<T, ?> repo, List<String> data, Function<String, T> entityCreator, Function<T, String> nameExtractor) {
        Set<String> existingNames = repo.findAll().stream().map(nameExtractor).collect(Collectors.toSet());
        List<T> entitiesToInsert = data.stream()
                .filter(name -> !existingNames.contains(name))
                .map(entityCreator)
                .collect(Collectors.toList());
        if (!entitiesToInsert.isEmpty()) {
            repo.saveAll(entitiesToInsert);
        }
    }
}
