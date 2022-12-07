package org.example;

import DAL.Enums.OrderStatus;
import DAL.Enums.PaymentStatus;
import DAL.Interfaces.IPersonRepository;
import DAL.Models.Order;
import DAL.Models.Payment;
import DAL.Repositories.MsSQL.OrderRepositoryMs;
import DAL.Repositories.MsSQL.PaymentRepositoryMs;
import DAL.Repositories.mongoDB.OrderRepositoryMdb;
import Helper.Implementation.BaseResponse;
import Services.Implementations.OrderService;
import Services.Interfaces.Repositories.IOrderService;

import java.util.Optional;

public class App
{
    public static void main( String[] args )
    {
        String connectionUrl = "jdbc:sqlserver://LENOVO-PC2\\SQLEXPRESS:49170;" +
                "database=ServiceOfTransport;" +
                "user=sa;" +
                "password=08240;" +
                "encrypt=true;trustServerCertificate=true";

        IOrderService orderService = new OrderService(new OrderRepositoryMs(connectionUrl));


        BaseResponse<Optional<Order>> res = orderService.getById(1);
        System.out.println(res.getDescription()+" "+res.getStatusCode());
/*
        Order o = new Order(14,1,1,1);
        BaseResponse<Boolean> inserted = orderService.create(o);
        System.out.println(inserted.getDescription()+" "+inserted.getStatusCode());



        BaseResponse<Optional<List<Order>>> res = orderService.getAll();
        System.out.println(res.getDescription()+" "+res.getStatusCode());
        for (Order o: res.getResultQuery().get()) {
            System.out.println(o.toString());
        }


        /*
        BaseResponse<Boolean> deleted = orderService.deleteById(4);
        System.out.println(deleted.getDescription()+" "+deleted.getStatusCode());

         */

        /*BaseResponse<Boolean> updated = orderService.update(new Order(5,100,1,1,1));
        System.out.println(updated.getDescription()+" "+updated.getStatusCode());*/

        try {
/*
            IPersonRepository senderRepository = null;
            IPersonRepository receiverRepository = null;
            OrderRepositoryMdb orderFromMongo = new OrderRepositoryMdb("mongodb://localhost:27017",
                    "TransportService","Orders", null, null);
            boolean updated = orderFromMongo.update(new Order(2,140, OrderStatus.undelivered));
            if(updated)
                System.out.println("the order have been updated");
            else
                System.out.println("not updated order");
/*
            PaymentRepositoryMs paymentRepositoryMs = new PaymentRepositoryMs(connectionUrl);
            if(paymentRepositoryMs.update(new Payment(5, PaymentStatus.unPaid, new java.sql.Date(122,10,29),0))){
                System.out.println("Success");
            }*/

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
