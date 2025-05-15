package com.example.ticket_sale.mapper;

import com.example.ticket_sale.data.dto.UserDTO;
import com.example.ticket_sale.model.User;

public class UserMapper {
    public static User toUser(UserDTO userDTO){
        if (userDTO == null) return null;
        User u = new User();
        u.setId(userDTO.getId());
        u.setName(userDTO.getName());
        u.setPhone(userDTO.getPhoneNumber());
        u.setEmail(userDTO.getEmail());
        if (userDTO.getAccount() != null) {
            u.setUsername(userDTO.getAccount().getUserName());
        }
        u.setPaymentMethods(null);
        return u;
    }
}
