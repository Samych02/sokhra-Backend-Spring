//package org.example.sokhrabackendspring.shipment.service;
//
//import org.example.sokhrabackendspring.shipment.entity.Request;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//public class RequestStatusService {
//  @Autowired
//  private RequestRepository requestRepository;
//
//  @Autowired
//  private TravelListingRepository travelListingRepository;
//
//  @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
//  public void updateRequestStatus() {
//    LocalDate today = LocalDate.now();
//    List<Request> requests = requestRepository.findAll();
//
//    for (Request request : requests) {
//      TravelListing travelListing = request.getTravelListing();
//      if (travelListing.getTravelDate().isBefore(today) && request.getStatus() != RequestStatus.DELIVERED) {
//        request.setStatus(RequestStatus.CANCELLED);
//        requestRepository.save(request);
//      }
//    }
//  }
//}
