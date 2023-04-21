package com.dev.lmm.api.services;

import com.dev.lmm.api.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public List<User> findAll();
    public Optional<User> findByEmail(String email);
    User findById(Long id);
}
