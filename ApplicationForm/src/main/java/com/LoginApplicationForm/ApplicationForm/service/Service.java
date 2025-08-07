package com.LoginApplicationForm.ApplicationForm.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.LoginApplicationForm.ApplicationForm.entity.Application;
import com.LoginApplicationForm.ApplicationForm.repositry.ApplicationRepositry;

@org.springframework.stereotype.Service

public class Service {

    @Autowired
    private ApplicationRepositry apprep;

    @Autowired
    private JavaMailSender mailSender;

    private static class OtpEntry {
        String otp;
        LocalDateTime expiry;

        OtpEntry(String otp, LocalDateTime expiry) {
            this.otp = otp;
            this.expiry = expiry;
        }
    }

    private final Map<String, OtpEntry> otpStore = new HashMap<>();

    public void saveData(Application app) {
        apprep.save(app);
    }

    public String logIn(String usrn, String pass) {
        List<Application> applications = (List<Application>) apprep.findAll();
        boolean match = applications.stream()
            .anyMatch(i -> i.getUserName().equals(usrn) && i.getPassword().equals(pass));
        return match ? "HomePage" : "form";
    }

    public void generateAndSendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(2);
        otpStore.put(email, new OtpEntry(otp, expiry));
        sendOtpEmail(email, otp);
    }

    public boolean validateOtp(String email, String userInputOtp) {
        OtpEntry entry = otpStore.get(email);
        return entry != null && LocalDateTime.now().isBefore(entry.expiry) && entry.otp.equals(userInputOtp);
    }

    private void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + "\nIt is valid for 2 minutes.");
        mailSender.send(message);
    }
    
    
    }

