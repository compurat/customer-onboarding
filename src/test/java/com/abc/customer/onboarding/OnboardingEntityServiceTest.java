package com.abc.customer.onboarding;

import com.abc.customer.onboarding.database.CopyOfIdEntity;
import com.abc.customer.onboarding.database.CopyOfIdRepository;
import com.abc.customer.onboarding.database.Gender;
import com.abc.customer.onboarding.database.OnboardingEntity;
import com.abc.customer.onboarding.database.OnboardingRepository;
import com.abc.customer.onboarding.email.EmailSenderSevice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static com.abc.customer.onboarding.OnboardingEntityMapperTest.createDummyImageFile;
import static com.abc.customer.onboarding.OnboardingEntityMapperTest.createOnboardingStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OnboardingEntityServiceTest {
    @Mock
    private OnboardingRepository onboardingRepository;
    @Mock
    private OnboardingMapper onboardingMapper;
    @Mock
    private CopyOfIdRepository copyOfIdRepository;
    @Mock
    private EmailSenderSevice emailSenderSevice;
    @InjectMocks
    private OnboardingService onboardingService;


    @Test
    void testSaveOnboardingHappyFlow() {
        com.abc.customer.onboarding.web.Onboarding onboarding = createOnboardingStub();

        OnboardingEntity onboardingEntityDaoStub = createOnboardingDaoStub();
        when(onboardingRepository.findByMailAddress(anyString()))
                .thenReturn(Optional.empty());
        when(onboardingMapper.mapOnboardingtoOnboardingDao(onboarding))
                .thenReturn(onboardingEntityDaoStub);
        assertEquals(HttpStatus.CREATED, onboardingService.createOnboarding(onboarding).getStatus());

    }

    @Test
    void testSaveOnboardingAllreadyExistst() {
        com.abc.customer.onboarding.web.Onboarding onboarding = createOnboardingStub();
        OnboardingEntity onboardingEntityDaoStub = createOnboardingDaoStub();

        when(onboardingRepository.findByMailAddress(anyString()))
                .thenReturn(Optional.of(onboardingEntityDaoStub));
        assertThrows(IllegalArgumentException.class, () -> onboardingService.createOnboarding(onboarding));
    }

    public static OnboardingEntity createOnboardingDaoStub() {
        OnboardingEntity onboardingEntityDao = new OnboardingEntity();
        LocalDate localDate = LocalDate.of(1990, 1, 1);
        onboardingEntityDao.setBirth(localDate);
        onboardingEntityDao.setGender(Gender.MALE.toString());
        onboardingEntityDao.setPhone("1234567890");
        onboardingEntityDao.setCopyOfId(createCopyOfidDaoStub());
        onboardingEntityDao.setFirstName("testFirstName");
        onboardingEntityDao.setLastName("TestLastname");
        onboardingEntityDao.setMobileNumber("06123456789");
        onboardingEntityDao.setNationality("Netherlands");
        onboardingEntityDao.setMailAddress("test@test.com");
        onboardingEntityDao.setResidentialAddress("testStreet 12");
        onboardingEntityDao.setSocialSecurityNumber("123456789");
        return onboardingEntityDao;
    }

    private static CopyOfIdEntity createCopyOfidDaoStub() {
        CopyOfIdEntity copyOfIdEntityDao = new CopyOfIdEntity();
        copyOfIdEntityDao.setIdNumber("12345676");
        try {
            copyOfIdEntityDao.setPhoto(createDummyImageFile().getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return copyOfIdEntityDao;
    }

}