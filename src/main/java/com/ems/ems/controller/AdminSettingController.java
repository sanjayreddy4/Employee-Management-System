package com.ems.ems.controller;

import com.ems.ems.service.SettingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminSettingController {

    private final SettingService settingService;

    public AdminSettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("/setting")
    public String settingPage() {
        return "admin/setting";
    }

    @PostMapping("/setting/change-password")
    public String changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Principal principal,
            RedirectAttributes ra
    ) {
        // new & confirm match check
        if (!newPassword.equals(confirmPassword)) {
            ra.addFlashAttribute("error", "New Password and Confirm Password not matched!");
            return "redirect:/admin/setting";
        }

        boolean changed = settingService.changePassword(principal.getName(), oldPassword, newPassword);

        if (!changed) {
            ra.addFlashAttribute("error", "Old Password is incorrect!");
        } else {
            ra.addFlashAttribute("success", "Password Changed Successfully");
        }

        return "redirect:/admin/setting";
    }

    private boolean isStrongPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }


}
