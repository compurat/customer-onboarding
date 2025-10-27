package com.abc.customer.onboarding;

import com.abc.customer.onboarding.database.CopyOfIdEntity;
import com.abc.customer.onboarding.database.CopyOfIdRepository;
import com.abc.customer.onboarding.database.OnboardingEntity;
import com.abc.customer.onboarding.database.OnboardingRepository;
import com.abc.customer.onboarding.email.EmailSenderSevice;
import com.abc.customer.onboarding.web.OnBoardingResult;
import com.abc.customer.onboarding.web.Onboarding;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * this service checks, creates an onboarding for the customer
 * and mails the instructions to him.
 */
@Service
public class OnboardingService {
    private final OnboardingRepository onboardingRepository;
    private final OnboardingMapper onboardingMapper;
    private final CopyOfIdRepository copyOfIdRepository;
    private final EmailSenderSevice emailSenderSevice;

    OnboardingService(OnboardingRepository onboardingRepository,
                      CopyOfIdRepository copyOfIdRepository,
                      OnboardingMapper onboardingMapper,
                      EmailSenderSevice emailSenderSevice) {
        this.onboardingRepository = onboardingRepository;
        this.onboardingMapper = onboardingMapper;
        this.copyOfIdRepository = copyOfIdRepository;
        this.emailSenderSevice = emailSenderSevice;
    }

    /**
     *
     * @param onboarding contains all the information that is needed for the onboarding.
     * @return
     */
    OnBoardingResult createOnboarding(Onboarding onboarding) {
        Optional<OnboardingEntity> existing = onboardingRepository.findByMailAddress(
                onboarding.getMailAddress()
        );
        if (existing.isPresent()) {
            throw new InvalidParameterException("Mail address already exists");
        }

        OnboardingEntity onboardingEntityDao = onboardingMapper.mapOnboardingtoOnboardingDao(onboarding);
        String password = createPassword();
        CopyOfIdEntity copyOfIdEntity = onboardingEntityDao.getCopyOfId();
        copyOfIdRepository.save(copyOfIdEntity);
        onboardingEntityDao.setCustomerId(UUID.randomUUID().toString());
        onboardingEntityDao.setPassword(password);
        onboardingRepository.save(onboardingEntityDao);
        emailSenderSevice.sendSimpleEmail(onboarding.getMailAddress(), onboarding.getLastName(), password);
        var onboardingResult = new OnBoardingResult();

        onboardingResult.setStatus(HttpStatus.CREATED);
        onboardingResult.setMessage("OnboardingEntity was successful, you will receive an email with instructions howto access our customer portal");
        return onboardingResult;
    }

    private String createPassword() {
        String allChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+[]{}\\|;:'\",.<>/?`~ ";
        Random random = new Random();
        StringBuilder password = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(allChars.length());
            password.append(allChars.charAt(randomIndex));
        }

        return password.toString();
    }

}
