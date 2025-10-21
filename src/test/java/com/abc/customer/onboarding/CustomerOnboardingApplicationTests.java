package com.abc.customer.onboarding;

import com.abc.customer.onboarding.email.EmailSenderSevice;
import com.abc.customer.onboarding.web.Onboarding;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.StandardCharsets;

import static com.abc.customer.onboarding.OnboardingMapperTest.createOnboardingStub;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest
class CustomerOnboardingApplicationTests {

    private MockMvc mockMvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @MockitoBean
    private EmailSenderSevice emailSenderSevice;
    @Autowired
    private OnboardingController onboardingController;
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("CUSTOMER_ONBOARDING")
            .withUsername("test_user")
            .withPassword("test_password");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    public void init() {
        MockMvcBuilder mockMvcBuilder = MockMvcBuilders.standaloneSetup(onboardingController);
        this.mockMvc = mockMvcBuilder.build();
        doNothing().when(emailSenderSevice).sendSimpleEmail(anyString(), anyString());
    }

    @Test
    void contextLoads() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();

    }

    @Test
    void testCreateOnboardingHappyFlow() throws Exception {
        // Maak een eenvoudige foto part
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

        MockMultipartFile photoPart = new MockMultipartFile(
                "photo",
                "test-id.png",
                MediaType.IMAGE_PNG_VALUE,
                imageData
        );

        // Maak JSON content
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        Onboarding onboardingStub = createOnboardingStub();
        onboardingStub.getCopyOfId().setPhoto(null);
        String jsonContent = objectMapper.writeValueAsString(onboardingStub);

        System.out.println("=== JSON Content ===");
        System.out.println(jsonContent);
        System.out.println("===================");

        MockMultipartFile jsonPart = new MockMultipartFile(
                "onboarding",
                "onboarding.json",  // Geef een filename op
                MediaType.APPLICATION_JSON_VALUE,
                jsonContent.getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/customer/onboarding/create")
                        .file(jsonPart)
                        .file(photoPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
