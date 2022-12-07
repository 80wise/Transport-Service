package Connections;

import DAL.Enums.StatusCode;
import DAL.Interfaces.IOrderRepository;
import DAL.Models.Order;
import DAL.Repositories.MsSQL.OrderRepositoryMs;
import DAL.Repositories.mongoDB.OrderRepositoryMdb;
import Helper.Implementation.BaseResponse;
import Services.Implementations.OrderService;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataBaseConnectionsTest {
    @Test
    public void testMsServerWithCorrectPath_true() {
        String connectionUrl = "jdbc:sqlserver://LENOVO-PC2\\SQLEXPRESS:49170;" +
                "database=ServiceOfTransport;" +
                "user=sa;" +
                "password=08240;" +
                "encrypt=true;trustServerCertificate=true";
        int idOrder = 1;
        IOrderRepository orderRepository = new OrderRepositoryMs(connectionUrl);
        OrderService orderService = new OrderService(orderRepository);

        BaseResponse<Optional<Order>> order = orderService.getById(idOrder);
        assertEquals(order.getStatusCode(), StatusCode.success);

    }

    @Test
    public void testMongoDBWithCorrectPath_true() {
        IOrderRepository orderRepository = new OrderRepositoryMdb("mongodb://localhost:27017",
                "TransportService", "Orders", null, null);
        OrderService orderService = new OrderService(orderRepository);
        int idOrder = 1;
        BaseResponse<Optional<Order>> order = orderService.getById(idOrder);

        assertEquals(order.getStatusCode(), StatusCode.success);
    }

    @Test
    public void testMsServerWithIncorrectPath_true() {
        String connectionUrl = "path";
        int idOrder = 1;
        IOrderRepository orderRepository = new OrderRepositoryMs(connectionUrl);
        OrderService orderService = new OrderService(orderRepository);

        BaseResponse<Optional<Order>> order = orderService.getById(idOrder);
        assertEquals(order.getStatusCode(), StatusCode.errorServer);

    }

    @Test
    public void testMongoDBWithIncorrectPath_true() {
        IOrderRepository orderRepository = new OrderRepositoryMdb("path",
                "TransportService", "Orders", null, null);
        OrderService orderService = new OrderService(orderRepository);
        int idOrder = 1;
        BaseResponse<Optional<Order>> order = orderService.getById(idOrder);

        assertEquals(order.getStatusCode(), StatusCode.errorServer);
    }
}
