package com.abc.customer.onboarding;

import com.abc.customer.onboarding.database.CopyOfIdDao;
import com.abc.customer.onboarding.database.CopyOfIdRepository;
import com.abc.customer.onboarding.database.Gender;
import com.abc.customer.onboarding.database.OnboardingDao;
import com.abc.customer.onboarding.database.OnboardingRepository;
import com.abc.customer.onboarding.email.EmailSenderSevice;
import com.abc.customer.onboarding.web.Onboarding;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static com.abc.customer.onboarding.OnboardingMapperTest.createDummyImageFile;
import static com.abc.customer.onboarding.OnboardingMapperTest.createOnboardingStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OnboardingServiceTest {
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
        Onboarding onboarding = createOnboardingStub();

        OnboardingDao onboardingDaoStub = createOnboardingDaoStub();
        when(onboardingRepository.findByMailAddress(anyString()))
                .thenReturn(Optional.empty());
        when(onboardingMapper.mapOnboardingtoOnboardingDao(onboarding))
                .thenReturn(onboardingDaoStub);
        assertEquals(HttpStatus.CREATED, onboardingService.createOnboarding(onboarding).getStatus());

    }

    @Test
    void testSaveOnboardingAllreadyExistst() {
        Onboarding onboarding = createOnboardingStub();
        OnboardingDao onboardingDaoStub = createOnboardingDaoStub();

        when(onboardingRepository.findByMailAddress(anyString()))
                .thenReturn(Optional.of(onboardingDaoStub));
        assertThrows(IllegalArgumentException.class, () -> onboardingService.createOnboarding(onboarding));
    }

    public static OnboardingDao createOnboardingDaoStub() {
        OnboardingDao onboardingDao = new OnboardingDao();
        LocalDate localDate = LocalDate.of(1990, 1, 1);
        onboardingDao.setBirth(localDate);
        onboardingDao.setGender(Gender.MALE.toString());
        onboardingDao.setPhone("1234567890");
        onboardingDao.setCopyOfId(createCopyOfidDaoStub());
        onboardingDao.setFirstName("testFirstName");
        onboardingDao.setLastName("TestLastname");
        onboardingDao.setMobileNumber("06123456789");
        onboardingDao.setNationality("Netherlands");
        onboardingDao.setMailAddress("test@test.com");
        onboardingDao.setResidentialAddress("testStreet 12");
        onboardingDao.setSocialSecurityNumber("123456789");
        return onboardingDao;
    }

    private static CopyOfIdDao createCopyOfidDaoStub() {
        CopyOfIdDao copyOfIdDao = new CopyOfIdDao();
        copyOfIdDao.setIdNumber("12345676");
        try {
            copyOfIdDao.setPhoto(createDummyImageFile().getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return copyOfIdDao;
    }

}