package com.slotmind.config;

import com.slotmind.ai.LLMGateway;
import com.slotmind.ai.impl.MockLLMGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AIConfig {
    
    @Bean
    @Profile("default")
    public LLMGateway mockLLMGateway() {
        return new MockLLMGateway();
    }
    
    // In a production environment, you would have a different profile
    // with a real implementation of LLMGateway
}