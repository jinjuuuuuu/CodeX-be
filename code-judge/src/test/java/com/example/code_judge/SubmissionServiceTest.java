package com.example.code_judge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.code_judge.service.SubmissionService;
import com.example.code_judge.dto.SubmissionRequestDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SubmissionServiceTest {

    @Mock
    private SubmissionService submissionService;

    public SubmissionServiceTest() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testSubmitCode() {
        // Mocking the behavior of submitCode
        SubmissionRequestDTO request = new SubmissionRequestDTO();
        request.setUserId(1L);
        request.setLanguage("java");
        request.setCode("System.out.println(\"Hello, World!\");");
        request.setProblemId(1L);

        // Mock the return value of submitCode
        when(submissionService.submitCode(request)).thenReturn("Success");

        // Call the mocked method
        String result = submissionService.submitCode(request);

        // Assert the result
        assertEquals("Success", result);
    }
}
