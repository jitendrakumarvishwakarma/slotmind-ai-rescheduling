package com.slotmind.repository;

import com.slotmind.model.Provider;
import com.slotmind.model.ProviderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, String> {
    List<Provider> findByStatus(ProviderStatus status);
    List<Provider> findBySpecialtyAndStatus(String specialty, ProviderStatus status);
}