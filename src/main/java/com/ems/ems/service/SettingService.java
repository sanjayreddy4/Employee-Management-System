package com.ems.ems.service;

public interface SettingService {
    boolean changePassword(String oldPassword, String newPassword, String confirmPassword);
}
