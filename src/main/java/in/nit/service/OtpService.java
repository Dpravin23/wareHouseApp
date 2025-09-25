package in.nit.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
@Service
public class OtpService {

    private final Cache<String, String> otpCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    public String generateOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpCache.put(email, otp);
        return otp;
    }

    public boolean verifyOtp(String email, String inputOtp) {
        String cachedOtp = otpCache.getIfPresent(email);
        return inputOtp != null && inputOtp.equals(cachedOtp);
    }
}
