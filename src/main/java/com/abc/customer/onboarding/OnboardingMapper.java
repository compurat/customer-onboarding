package com.abc.customer.onboarding;

import com.abc.customer.onboarding.database.CopyOfIdEntity;
import com.abc.customer.onboarding.database.OnboardingEntity;
import com.abc.customer.onboarding.web.CopyOfId;
import com.abc.customer.onboarding.web.Onboarding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OnboardingMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnboardingMapper.class);

    OnboardingEntity mapOnboardingtoOnboardingDao(Onboarding onboarding) {
        OnboardingEntity onboardingEntityDao = new OnboardingEntity();
        onboardingEntityDao.setBirth(onboarding.getBirth());
        onboardingEntityDao.setMailAddress(onboarding.getMailAddress());
        onboardingEntityDao.setFirstName(onboarding.getFirstName());
        onboardingEntityDao.setGender(onboarding.getGender().toString());
        onboardingEntityDao.setLastName(onboarding.getLastName());
        onboardingEntityDao.setCopyOfId(mapCopyOfIdToCopyOfIdDao(onboarding.getCopyOfId()));
        onboardingEntityDao.setMobileNumber(onboarding.getMobileNumber());
        onboardingEntityDao.setNationality(onboarding.getNationality());
        onboardingEntityDao.setResidentialAddress(onboarding.getResidentialAddress());
        onboardingEntityDao.setSocialSecurityNumber(onboarding.getSocialSecurityNumber());

        return onboardingEntityDao;
    }

    CopyOfIdEntity mapCopyOfIdToCopyOfIdDao(CopyOfId copyOfId) {
        CopyOfIdEntity copyOfIdEntityDao = new CopyOfIdEntity();
        copyOfIdEntityDao.setIdNumber(copyOfId.getIdNumber());
        try {
            // Direct byte[] gebruiken - geen conversie naar BigInteger!
            byte[] photoBytes = copyOfId.getPhoto().getInputStream().readAllBytes();
            copyOfIdEntityDao.setPhoto(photoBytes);  // Dit is byte[], niet BigInteger
        } catch (IOException e) {
            LOGGER.error("Failed to read photo bytes for CopyOfIdEntity: {}", copyOfId.getIdNumber(), e);
            throw new IllegalArgumentException("Failed to process photo", e);
        }
        return copyOfIdEntityDao;
    }
}
