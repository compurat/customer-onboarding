package com.abc.customer.onboarding;

import com.abc.customer.onboarding.database.CopyOfIdDao;
import com.abc.customer.onboarding.database.CopyOfIdRepository;
import com.abc.customer.onboarding.database.OnboardingDao;
import com.abc.customer.onboarding.database.OnboardingRepository;
import com.abc.customer.onboarding.email.EmailSenderSevice;
import com.abc.customer.onboarding.web.OnBoardingResult;
import com.abc.customer.onboarding.web.Onboarding;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;
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
        Optional<OnboardingDao> existing = onboardingRepository.findByMailAddress(
                onboarding.getMailAddress()
        );
        if (existing.isPresent()) {
            throw new InvalidParameterException("Mail address already exists");
        }

        OnboardingDao onboardingDao = onboardingMapper.mapOnboardingtoOnboardingDao(onboarding);
        CopyOfIdDao copyOfId = onboardingDao.getCopyOfId();
        copyOfIdRepository.save(copyOfId);
        onboardingDao.setCustomerId(UUID.randomUUID().toString());
        onboardingRepository.save(onboardingDao);
        emailSenderSevice.sendSimpleEmail(onboarding.getMailAddress(), onboarding.getLastName());
        var onboardingResult = new OnBoardingResult();

        onboardingResult.setStatus(HttpStatus.CREATED);
        onboardingResult.setMessage("Onboarding was successful, you will receive an email with instructions howto access our customer portal");
        return onboardingResult;
    }


}
