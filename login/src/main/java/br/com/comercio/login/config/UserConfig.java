package br.com.comercio.login.config;

import br.com.comercio.login.repository.UserRepository;
import br.com.comercio.login.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserConfig {
    private final UserRepository userRepository;

    @Bean
    public void setUser(){
        if(userRepository.findAll().isEmpty()){
            userRepository.save(new User("admin","admin", "72827b5b-5c7f-4ec7-af45"));
        }
    }
}
