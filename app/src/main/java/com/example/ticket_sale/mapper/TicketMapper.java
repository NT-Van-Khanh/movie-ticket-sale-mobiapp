package com.example.ticket_sale.mapper;

import com.example.ticket_sale.data.dto.TicketDTO;
import com.example.ticket_sale.model.Ticket;

public class TicketMapper {

    public static Ticket toTicket(TicketDTO ticketDTO){
        Ticket t = new Ticket();
        t.setId(ticketDTO.getId());
        t.setName(ticketDTO.getName());
        t.setConditionUse(ticketDTO.getConditionUse());
        t.setPrice(ticketDTO.getPrice());
        t.setTypeTicket(ticketDTO.getTypeTicket());
        t.setSlot(ticketDTO.getSlot());
        return t;
    }
}
