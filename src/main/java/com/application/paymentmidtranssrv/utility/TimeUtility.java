package com.application.paymentmidtranssrv.utility;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtility {

    public static long getMinutesUntilExpiry(String expiryTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime expiryTime = LocalDateTime.parse(expiryTimeStr, formatter);
        LocalDateTime now = LocalDateTime.now();

        return Duration.between(now, expiryTime).toMinutes();
    }
}
