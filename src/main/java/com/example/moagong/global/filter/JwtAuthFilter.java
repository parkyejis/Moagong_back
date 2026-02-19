package com.example.moagong.global.filter;

import com.example.moagong.domain.auth.token.JwtProvider;
import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws SecurityException, IOException {
        String auth = request.getHeader("Authorization");

        if(auth != null && auth.startsWith("Bearer")) {
            String token = auth.substring(7);

            try {
                Long user_id = jwtProvider.getUserId(token);

                // 여기서 단순히 userId만 Principal로 넣는 예
                Authentication authentication = new UsernamePasswordAuthenticationToken(user_id, null, List.of());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                //토큰이 개졌거나 만료된 경우: 인증 없 음 상태로 진행 or 401처리
                SecurityContextHolder.clearContext();
            }
        }
        try {
            chain.doFilter(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
