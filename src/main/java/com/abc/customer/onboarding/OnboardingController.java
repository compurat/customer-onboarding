package com.abc.customer.onboarding;

import com.abc.customer.onboarding.web.OnBoardingResult;
import com.abc.customer.onboarding.web.Onboarding;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/customer/onboarding")
@Tag(name = "Customer Onboarding", description = "Customer onboarding API")
public class OnboardingController {

    private final OnboardingService onboardingService;

    OnboardingController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Onboarding created successfull",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OnBoardingResult.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "duplicate input",
                    content = @Content
            )
    })
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    OnBoardingResult createNewOnboarding(
            @RequestPart("onboarding") @Valid Onboarding onboarding,
            @RequestPart("photo") MultipartFile photo) {
        onboarding.getCopyOfId().setPhoto(photo);
        return onboardingService.createOnboarding(onboarding);
    }
}
