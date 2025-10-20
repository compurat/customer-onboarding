package com.abc.customer.onboarding;

import com.abc.customer.onboarding.database.CopyOfIdDao;
import com.abc.customer.onboarding.database.OnboardingDao;
import com.abc.customer.onboarding.web.CopyOfId;
import com.abc.customer.onboarding.web.Onboarding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OnboardingMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnboardingMapper.class);

    OnboardingDao mapOnboardingtoOnboardingDao(Onboarding onboarding) {
        OnboardingDao onboardingDao = new OnboardingDao();
        onboardingDao.setBirth(onboarding.getBirth());
        onboardingDao.setMailAddress(onboarding.getMailAddress());
        onboardingDao.setFirstName(onboarding.getFirstName());
        onboardingDao.setGender(onboarding.getGender().toString());
        onboardingDao.setLastName(onboarding.getLastName());
        onboardingDao.setCopyOfId(mapCopyOfIdToCopyOfIdDao(onboarding.getCopyOfId()));
        onboardingDao.setMobileNumber(onboarding.getMobileNumber());
        onboardingDao.setNationality(onboarding.getNationality());
        onboardingDao.setResidentialAddress(onboarding.getResidentialAddress());
        onboardingDao.setSocialSecurityNumber(onboarding.getSocialSecurityNumber());

        return onboardingDao;
    }

    CopyOfIdDao mapCopyOfIdToCopyOfIdDao(CopyOfId copyOfId) {
        CopyOfIdDao copyOfIdDao = new CopyOfIdDao();
        copyOfIdDao.setIdNumber(copyOfId.getIdNumber());
        try {
            // Direct byte[] gebruiken - geen conversie naar BigInteger!
            byte[] photoBytes = copyOfId.getPhoto().getInputStream().readAllBytes();
            copyOfIdDao.setPhoto(photoBytes);  // Dit is byte[], niet BigInteger
        } catch (IOException e) {
            LOGGER.error("Failed to read photo bytes for CopyOfId: {}", copyOfId.getIdNumber(), e);
            throw new IllegalArgumentException("Failed to process photo", e);
        }
        return copyOfIdDao;
    }
}
