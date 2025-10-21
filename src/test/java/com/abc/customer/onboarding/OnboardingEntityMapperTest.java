package com.abc.customer.onboarding;

import com.abc.customer.onboarding.database.Gender;
import com.abc.customer.onboarding.database.OnboardingEntity;
import com.abc.customer.onboarding.stubs.MultiPartFileStub;
import com.abc.customer.onboarding.web.CopyOfId;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OnboardingEntityMapperTest {

    @Test
    void testMapOnboardingtoOnboardingDao() {
        OnboardingMapper onboardingMapper = new OnboardingMapper();
        com.abc.customer.onboarding.web.Onboarding onboarding = createOnboardingStub();
        OnboardingEntity onboardingEntityDao = onboardingMapper.mapOnboardingtoOnboardingDao(onboarding);
        assertTrue(equals(onboardingEntityDao, onboarding));
    }

    public static com.abc.customer.onboarding.web.Onboarding createOnboardingStub() {
        com.abc.customer.onboarding.web.Onboarding onboarding = new com.abc.customer.onboarding.web.Onboarding();
        LocalDate localDate = LocalDate.of(1990, 1, 1);
        onboarding.setBirth(localDate);
        onboarding.setGender(Gender.MALE);
        onboarding.setPhone("0123456789");
        onboarding.setCopyOfId(createCopyOfidStub());
        onboarding.setFirstName("testFirstName");
        onboarding.setLastName("TestLastname");
        onboarding.setMobileNumber("0612345678");
        onboarding.setNationality("Netherlands");
        onboarding.setMailAddress("test@test.com");
        onboarding.setResidentialAddress("testStreet 12");
        onboarding.setSocialSecurityNumber("123456789");
        return onboarding;
    }

    private static CopyOfId createCopyOfidStub() {
        CopyOfId copyOfId = new CopyOfId();
        copyOfId.setIdNumber("12345676");
        copyOfId.setPhoto(createDummyImageFile());
        return copyOfId;
    }

    public static MultipartFile createDummyImageFile() {
        // Simpele 1x1 pixel PNG (kleinste mogelijke PNG)
        byte[] imageData = new byte[]{
                (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A,
                0x00, 0x00, 0x00, 0x0D, 0x49, 0x48, 0x44, 0x52,
                0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01,
                0x08, 0x06, 0x00, 0x00, 0x00, 0x1F, 0x15, (byte) 0xC4,
                (byte) 0x89, 0x00, 0x00, 0x00, 0x0A, 0x49, 0x44, 0x41, 0x54,
                0x78, (byte) 0x9C, 0x63, 0x00, 0x01, 0x00, 0x00, 0x05,
                0x00, 0x01, 0x0D, 0x0A, 0x2D, (byte) 0xB4, 0x00, 0x00,
                0x00, 0x00, 0x49, 0x45, 0x4E, 0x44, (byte) 0xAE, 0x42,
                0x60, (byte) 0x82
        };

        return new MultiPartFileStub(
                "photo",
                "test-image.png",
                "image/png",
                imageData
        );
    }

    private boolean equals(OnboardingEntity onboardingEntityDao, com.abc.customer.onboarding.web.Onboarding onboarding) {
        byte[] photo = null;
        try {

            photo = onboarding.getCopyOfId().getPhoto().getInputStream().readAllBytes();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        return onboardingEntityDao.getBirth().equals(onboarding.getBirth()) &&
                onboardingEntityDao.getFirstName().equals(onboarding.getFirstName()) &&
                onboardingEntityDao.getGender() == onboarding.getGender().toString() &&
                onboardingEntityDao.getLastName().equals(onboarding.getLastName()) &&
                onboardingEntityDao.getCopyOfId().getIdNumber().equals(onboarding.getCopyOfId().getIdNumber()) &&
                Arrays.equals(onboardingEntityDao.getCopyOfId().getPhoto(), photo) &&
                onboardingEntityDao.getResidentialAddress().equals(onboarding.getResidentialAddress()) &&
                onboardingEntityDao.getSocialSecurityNumber().equals(onboarding.getSocialSecurityNumber()) &&
                onboardingEntityDao.getMobileNumber().equals(onboarding.getMobileNumber()) &&
                onboardingEntityDao.getNationality().equals(onboarding.getNationality()) &&
                onboardingEntityDao.getMailAddress().equals(onboarding.getMailAddress());
    }
}
