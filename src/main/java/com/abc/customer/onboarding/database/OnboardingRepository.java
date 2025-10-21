package com.abc.customer.onboarding.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OnboardingRepository extends CrudRepository<OnboardingEntity, Long> {

    // ✅ VERANDER: findbyMailAddress → findByMailAddress
    Optional<OnboardingEntity> findByMailAddress(String email);
}
