package com.nimshub.biobeacon.UserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getProduct() {
        return "This is a product";
    }
}
