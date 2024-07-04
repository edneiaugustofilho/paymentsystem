package com.edneiaugusto.paymentsystem.system.user;

import com.edneiaugusto.paymentsystem.arquitetura.RegraNegocioException;
import com.edneiaugusto.paymentsystem.system.thread.ThreadPool;
import com.edneiaugusto.paymentsystem.util.Constants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
public class UserSendEmailVerificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${senderemail}")
    private String senderEmail;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    ThreadPool threadPool;

    public void execute(User user) {
        threadPool.executarTarefa(() -> {
            final String email = user.getEmail();
            final String senderName = "Payment System";
            final String subject = "Verificação de e-mail";

            String content = UserSendEmailVerificationHelper.getVerificationEmailContent();

            content = content.replace("[[NAME]]", user.getName());

            String verifyURL = "http://localhost:" + serverPort + Constants.userVerifyApiUrl + "/" + user.getId() + "/" + user.getVerificationCode();
            content = content.replace("[[URL]]", verifyURL);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            try {
                helper.setFrom(senderEmail, senderName);
                helper.setTo(email);
                helper.setSubject(subject);
                helper.setText(content, true);
            } catch (MessagingException | UnsupportedEncodingException e) {
                log.error("Erro ao enviar e-mail de verificação", e);
                throw new RegraNegocioException("Erro ao enviar e-mail de verificação");
            }

            javaMailSender.send(mimeMessage);
        });
    }

}
