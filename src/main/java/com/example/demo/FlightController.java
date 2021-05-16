package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {

    @PostMapping("/flights/tickets/total")
    public TicketTotal calculateTicketTotal(@RequestBody TicketRequest ticketRequest){
        final TicketTotal ticketTotal = new TicketTotal();
        System.out.println("******** "+ticketRequest);
        if(ticketRequest!=null && !ticketRequest.getTickets().isEmpty()){
            ticketTotal.setResult(
                    ticketRequest.getTickets().stream()
                            .mapToInt(t -> t.getPrice())
                            .sum()
            );
        }
        return ticketTotal;
    }
}
