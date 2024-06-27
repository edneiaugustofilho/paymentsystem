package com.edneiaugusto.paymentsystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class PaymentsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentsystemApplication.class, args);
    }

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.profiles.version}")
    private String versao;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @EventListener(ApplicationReadyEvent.class)
    public void printStartedBanner() {
        String labelName = ConsoleLogUtil.formatar("Nome", ConsoleLogUtil.Cor.AMARELO, ConsoleLogUtil.Estilo.NEGRITO);
        String formatarName = ConsoleLogUtil.formatar("%s", ConsoleLogUtil.Cor.AZUL);
        String labelVersao = ConsoleLogUtil.formatar("Versão", ConsoleLogUtil.Cor.AMARELO, ConsoleLogUtil.Estilo.NEGRITO);
        String formatarVersao = ConsoleLogUtil.formatar("%s", ConsoleLogUtil.Cor.ROXO);
        String labelProfile = ConsoleLogUtil.formatar("Profile", ConsoleLogUtil.Cor.AMARELO, ConsoleLogUtil.Estilo.NEGRITO);
        String formatarProfile = ConsoleLogUtil.formatar("%s", ConsoleLogUtil.Cor.VERMELHO);
        String poweredBy = ConsoleLogUtil.formatar("Powered by ednei.fiho", ConsoleLogUtil.Cor.CIANO);

        String banner =
                        ":: " + labelName + " " + formatarName + " ::\n" +
                        ":: " + labelVersao + " " + formatarVersao + " ::\n" +
                        ":: " + labelProfile + " " + formatarProfile + " ::\n" +
                        ":: " + poweredBy + " ::\n";

        System.out.printf((banner) + "%n", applicationName, versao, activeProfile);
    }
}
