package com.brennaswitzer.foodinger.config;

import com.brennaswitzer.foodinger.security.GitHubOAuth2User;
import com.brennaswitzer.foodinger.security.GoogleOAuth2User;
import com.brennaswitzer.foodinger.security.LocalUserOAuth2UserService;
import com.brennaswitzer.foodinger.security.OAuth2UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${foodinger.public-url}")
    String publicUrl;

    @Autowired(required = false)
    ClientRegistrationRepository oauthClientRepo;

    @Autowired
    LocalUserOAuth2UserService oAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests(a -> a
                .antMatchers(
                        "/",
                        "/login-providers",
                        "/webjars/**",
                        "/static/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .csrf(c -> c
                .csrfTokenRepository(csrfTokenRepository())
            )
            .formLogin().disable()
            .httpBasic().disable()
            .exceptionHandling(e -> e
                .authenticationEntryPoint(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .logout(l -> l
                .logoutSuccessUrl(publicUrl)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
        if (oauthClientRepo != null) {
            http
                .oauth2Login(o -> o
                    .userInfoEndpoint(e -> e
                        .userService(oAuth2UserService)));
        }
        // @formatter:on
    }

    @Bean
    protected OAuth2UserService<OAuth2UserRequest, OAuth2UserInfo> oAuth2CustomTypesService() {
        return new OAuth2UserService<>() {
            private final CustomUserTypesOAuth2UserService delegate = new CustomUserTypesOAuth2UserService(Map.of(
                    "google", GoogleOAuth2User.class,
                    "github", GitHubOAuth2User.class
            ));

            @Override
            public OAuth2UserInfo loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
                return (OAuth2UserInfo) delegate.loadUser(userRequest);
            }
        };
    }

    @Bean
    protected HttpSessionCsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }

}
