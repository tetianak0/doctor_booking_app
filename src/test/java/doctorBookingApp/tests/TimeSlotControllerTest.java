package doctorBookingApp.tests;

import doctorBookingApp.controller.appointmentsControllers.TimeSlotController;
import doctorBookingApp.dto.TimeSlotDTO;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.service.bookingServices.TimeSlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TimeSlotControllerTest {

    @Mock
    private TimeSlotService timeSlotService;

    @InjectMocks
    private TimeSlotController timeSlotController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTimeSlot() {
        // Arrange
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        TimeSlot expectedTimeSlot = new TimeSlot();
        when(timeSlotService.addTimeSlot(any(TimeSlotDTO.class))).thenReturn(expectedTimeSlot);

        // Act
        TimeSlot actualTimeSlot = timeSlotController.addTimeSlot(timeSlotDTO);

        // Assert
        assertEquals(expectedTimeSlot, actualTimeSlot);
    }

    @Test
    void testGetTimeSlotById() {
        // Arrange
        Long timeSlotId = 1L;
        TimeSlot expectedTimeSlot = new TimeSlot();
        when(timeSlotService.getTimeSlotById(timeSlotId)).thenReturn(Optional.of(expectedTimeSlot));

        // Act
        Optional<TimeSlot> actualTimeSlot = timeSlotController.getTimeSlotById(timeSlotId);

        // Assert
        assertEquals(Optional.of(expectedTimeSlot), actualTimeSlot);
    }

    @Test
    void testGetTimeSlotByIdNotFound() {
        // Arrange
        Long timeSlotId = 1L;
        when(timeSlotService.getTimeSlotById(timeSlotId)).thenReturn(Optional.empty());

        // Act
        Optional<TimeSlot> actualTimeSlot = timeSlotController.getTimeSlotById(timeSlotId);

        // Assert
        assertEquals(Optional.empty(), actualTimeSlot);
    }

}
