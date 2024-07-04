package com.edneiaugusto.paymentsystem.system.user;

import com.edneiaugusto.paymentsystem.arquitetura.RegraNegocioException;
import com.edneiaugusto.paymentsystem.util.RandString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSendEmailVerificationService userSendEmailVerificationService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User registerUser(User user) {
        if (Objects.nonNull(findByEmail(user.getEmail()))) {
            throw new RegraNegocioException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationCode(RandString.generateRandomString(6));
        user.setEnabled(false);

        User savedUser = userRepository.save(user);
        userSendEmailVerificationService.execute(savedUser);

        return savedUser;
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }
}
