package com.app.midtrans.shared.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static long getMinutesUntilExpiry(String expiryTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime expiryTime = LocalDateTime.parse(expiryTimeStr, formatter);
        LocalDateTime now = LocalDateTime.now();

        return Duration.between(now, expiryTime).toMinutes();
    }
}
