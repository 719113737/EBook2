package com.example.demo.ratelimit;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


public class RateLimitInterceptor implements HandlerInterceptor {

    private final Bucket bucket;

    private final int tokenNum;

    public RateLimitInterceptor(Bucket bucket, int tokenNum) {
        this.bucket = bucket;
        this.tokenNum = tokenNum;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ConsumptionProbe consumptionPro = this.bucket.tryConsumeAndReturnRemaining(this.tokenNum);
        if(consumptionPro.isConsumed()){
            response.addHeader("X-Rate-Limit-Remaining",Long.toString(consumptionPro.getRemainingTokens()));
            return true;
        }

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.addHeader("X-Rate-Limit-Retry-After-Milliseconds",Long.toString(TimeUnit.NANOSECONDS.toMillis(consumptionPro.getNanosToWaitForRefill())));

        return false;
    }
}
