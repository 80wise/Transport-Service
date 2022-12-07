package Integreted;

import DAL.Enums.StatusCode;
import DAL.Interfaces.ITrackingRepository;
import DAL.Repositories.mongoDB.TrackingRepositoryMdb;
import Helper.Implementation.BaseResponse;
import Services.Implementations.TrackingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseResponsesTrackingServiceTest {
    private ITrackingRepository tracksRepository;
    private TrackingService trackingService;

    @Test
    public void changeLocationOfATrackSuccessfully() {
        try {
            tracksRepository = mock(TrackingRepositoryMdb.class);
            trackingService = new TrackingService(tracksRepository);

            when(tracksRepository.changeLocation(10, "Kinshasa")).thenReturn(true);
            BaseResponse<Boolean> response = trackingService.changeLocation(10, "Kinshasa");

            assertEquals(response.getDescription(), "Successfully processed The location has been changed");
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void deleteTransportMeanByIdUnsuccessfully() {
        try {
            tracksRepository = mock(TrackingRepositoryMdb.class);
            trackingService = new TrackingService(tracksRepository);
            int id = -1;

            when(tracksRepository.deleteById(id)).thenReturn(false);
            BaseResponse<Boolean> response = trackingService.deleteById(id);

            assertEquals(response.getDescription(), "The transport mean has not been deleted");
            assertNotEquals(response.getStatusCode(), StatusCode.success);
        } catch (Exception ex) {
            fail();
        }
    }
}
