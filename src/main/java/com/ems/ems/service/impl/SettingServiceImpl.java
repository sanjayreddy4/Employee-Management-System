package com.ems.ems.service.impl;

import com.ems.ems.entity.User;
import com.ems.ems.repository.UserRepository;
import com.ems.ems.service.SettingService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public SettingServiceImpl(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }


    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {

        User user = repo.findByUsername(username).orElse(null);
        if (user == null) {
            return false;
        }

        // ✅ Check old password
        if (!encoder.matches(oldPassword, user.getPassword())) {
            return false;
        }

        // ✅ Save encoded password into DB
        user.setPassword(encoder.encode(newPassword));
        repo.save(user);

        return true;
    }
}
