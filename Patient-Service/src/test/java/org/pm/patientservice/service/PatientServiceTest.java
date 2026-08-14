package org.pm.patientservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pm.patientservice.dto.PatientRequestDTO;
import org.pm.patientservice.dto.PatientResponseDto;
import org.pm.patientservice.exception.EmailAlreadyExistsException;
import org.pm.patientservice.exception.PatientNotFoundException;
import org.pm.patientservice.grpc.BillingServiceGrpcClient;
import org.pm.patientservice.kafka.KafkaProducer;
import org.pm.patientservice.model.Patient;
import org.pm.patientservice.repository.PatientRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private BillingServiceGrpcClient billingServiceGrpcClient;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private PatientService patientService;

    private Patient samplePatient;
    private PatientRequestDTO sampleRequest;

    @BeforeEach
    void setUp() {
        samplePatient = new Patient();
        samplePatient.setId(UUID.randomUUID());
        samplePatient.setName("John Doe");
        samplePatient.setEmail("john.doe@example.com");
        samplePatient.setAddress("123 Main St");
        samplePatient.setDateOfBirth(LocalDate.of(1990, 1, 15));
        samplePatient.setRegisteredDate(LocalDate.now());

        sampleRequest = new PatientRequestDTO();
        sampleRequest.setName("John Doe");
        sampleRequest.setEmail("john.doe@example.com");
        sampleRequest.setAddress("123 Main St");
        sampleRequest.setDateOfBirth("1990-01-15");
        sampleRequest.setRegisteredDate(LocalDate.now().toString());
    }

    @Test
    void getPatients_shouldReturnAllPatients() {
        when(patientRepository.findAll()).thenReturn(List.of(samplePatient));

        List<PatientResponseDto> result = patientService.getPatients();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void createPatient_shouldSavePatientAndCallBillingAndKafka() {
        when(patientRepository.existsByEmail(sampleRequest.getEmail())).thenReturn(false);
        when(patientRepository.save(any(Patient.class))).thenReturn(samplePatient);

        PatientResponseDto result = patientService.createPatient(sampleRequest);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(patientRepository).save(any(Patient.class));
        verify(billingServiceGrpcClient).createBillingcAccount(anyString(), eq("John Doe"), eq("john.doe@example.com"));
        verify(kafkaProducer).sendEvent(samplePatient);
    }

    @Test
    void createPatient_shouldThrowWhenEmailExists() {
        when(patientRepository.existsByEmail(sampleRequest.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> patientService.createPatient(sampleRequest));
        verify(patientRepository, never()).save(any());
    }

    @Test
    void updatePatient_shouldThrowWhenPatientNotFound() {
        UUID id = UUID.randomUUID();
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientService.updatePateint(id, sampleRequest));
    }

    @Test
    void deletePatient_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        doNothing().when(patientRepository).deleteById(id);

        patientService.deletePatient(id);

        verify(patientRepository, times(1)).deleteById(id);
    }
}
