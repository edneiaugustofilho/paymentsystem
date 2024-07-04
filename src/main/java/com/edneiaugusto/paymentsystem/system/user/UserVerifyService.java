package com.edneiaugusto.paymentsystem.system.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserVerifyService {

    @Autowired
    private UserRepository userRepository;

    public void execute(Input input) {
        User user = userRepository.findByIdAndVerificationCode(input.id, input.code);
        if (Objects.isNull(user)) {
            throw new RuntimeException("Código de verificação inválido");
        }

        user.setVerificationCode(null);
        user.setEnabled(true);

        userRepository.save(user);
    }

    @Getter
    @Setter
    @Builder
    public static final class Input {
        private UUID id;
        private String code;
    }

}
