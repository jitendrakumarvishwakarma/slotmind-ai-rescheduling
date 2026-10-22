package com.slotmind.service;

import com.slotmind.model.Provider;
import com.slotmind.model.ProviderStatus;
import java.util.List;
import java.util.Optional;

public interface ProviderService {
    List<Provider> getAllProviders();
    Optional<Provider> getProviderById(String id);
    Provider createProvider(Provider provider);
    Provider updateProvider(String id, Provider providerDetails);
    void deleteProvider(String id);
    List<Provider> getProvidersByStatus(ProviderStatus status);
}