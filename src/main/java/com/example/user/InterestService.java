package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestService {
    @Autowired
    private InterestRepository interestRepository;

    public Interest getInterestById(int id) {
        return interestRepository.findById(id).orElse(null);
    }
}
