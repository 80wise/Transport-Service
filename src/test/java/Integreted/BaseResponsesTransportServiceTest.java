package Integreted;

import DAL.Enums.StatusCode;
import DAL.Interfaces.ITransportRepository;
import DAL.Models.Transport;
import DAL.Repositories.mongoDB.TransportRepositoryMdb;
import Helper.Implementation.BaseResponse;
import Services.Implementations.TransportService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BaseResponsesTransportServiceTest {

    @Test
    public void tryToGetTransportsMeanWithAnAbsentNameEqualsEmptyResource(){
        try{
            //Arrange
            String transportName = "Ship";
            ITransportRepository transportRepository = mock(TransportRepositoryMdb.class);
            when(transportRepository.getByName(transportName)).thenReturn(Optional.empty());
            TransportService transportService = new TransportService(transportRepository);

            //Act
            BaseResponse<Optional<List<Transport>>> transports = transportService.getByName(transportName);

            //Assert
            assertEquals(transports.getStatusCode(), StatusCode.emptyResource);

        }catch (Exception ex){
            fail();
        }
    }
    @Test
    public void tryToGetAllTransportsMeanEqualsSuccess(){
        try{

            ITransportRepository transportRepository = mock(TransportRepositoryMdb.class);
            when(transportRepository.getAll()).thenReturn(getAllTransports());
            TransportService transportService = new TransportService(transportRepository);

            BaseResponse<Optional<List<Transport>>> transports = transportService.getAll();

            //Assert
            assertEquals(transports.getStatusCode(), StatusCode.success);

        }catch (Exception ex){
            fail();
        }
    }

    public Optional<List<Transport>> getAllTransports(){
        return Optional.of(asList(
                new Transport(1,"plane"),
                new Transport(2, "Cargo"),
                new Transport(3,"Boat")
        ));
    }
}
