package com.edneiaugusto.paymentsystem.util;

/**
 * Created by julio.bueno on 07/05/2019.
 */
public interface Constants {

    int _1_SEGUNDO = 1000;
    int _5_SEGUNDOS_MS = 5000;
    int _10_SEGUNDOS_MS = 10000;
    int _30_SEGUNDOS_MS = 30000;
    int _1_MINUTO_MS = 60000;
    int _2_MINUTO_MS = 120000;
    int _5_MINUTOS_MS = 300000;
    int _10_MINUTOS_MS = 600000;
    int _15_MINUTOS_MS = 900000;
    int _30_MINUTOS_MS = 1800000;
    int _60_MINUTOS_MS = 3600000;
    int _120_MINUTOS_MS = 7200000;

    int HORARIO_COMERCIAL_INICIAL = 8;
    int HORARIO_COMERCIAL_FINAL = 18;

    String SPRING_PROFILE_DEV = "dev";
    String SPRING_PROFILE_PRD = "prd";
    String SPRING_PROFILE_TESTE = "teste";

    String userVerifyApiUrl = "/api/v1/user/verify";

}
