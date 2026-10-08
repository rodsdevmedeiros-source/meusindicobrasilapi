package com.api.meusindicobrasil.security;


import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets =
            new ConcurrentHashMap<>();

    private Bucket criarBucket() {

        return Bucket.builder()
                .addLimit(limit -> limit
                        .capacity(100)
                        .refillGreedy(100, Duration.ofMinutes(1))
                )
                .build();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String ip = request.getRemoteAddr();

        Bucket bucket = buckets.computeIfAbsent(
                ip,
                key -> criarBucket()
        );

        ConsumptionProbe probe =
                bucket.tryConsumeAndReturnRemaining(1);

        response.setHeader(
                "X-Rate-Limit-Remaining",
                String.valueOf(probe.getRemainingTokens())
        );

        if (!probe.isConsumed()) {

            long segundos = TimeUnit.NANOSECONDS.toSeconds(
                    probe.getNanosToWaitForRefill()
            );

            response.setStatus(429);
            response.setHeader(
                    "Retry-After",
                    String.valueOf(segundos)
            );

            response.setContentType("application/json");

            response.getWriter().write("""
                    {
                        "status": 429,
                        "message": "Limite de requisições excedido."
                    }
                    """);

            return;
        }

        filterChain.doFilter(request, response);
    }
}
