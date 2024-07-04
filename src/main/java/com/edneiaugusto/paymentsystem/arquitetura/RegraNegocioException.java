package com.edneiaugusto.paymentsystem.arquitetura;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@JsonIgnoreProperties(value = {"message", "suppressed", "localizedMessage", "cause", "stackTrace"}, ignoreUnknown = true)
@Getter
@ToString(of = "mensagens")
public class RegraNegocioException extends RuntimeException {

    private HttpStatus status;

    private final List<String> mensagens = new ArrayList<>();

    @JsonIgnore
    private final Map<String, Object> informacoesComplementares = new HashMap<>();

    public static RegraNegocioException build(){
        return new RegraNegocioException();
    }

    public static RegraNegocioException build(String mensagem){
        return build().addMensagem(mensagem);
    }

    public static RegraNegocioException build(Collection<String> mensagens){
        return build().addMensagens(mensagens);
    }

    public RegraNegocioException(){
        super("Regra Negocio Exception");
        status = HttpStatus.BAD_REQUEST;
    }

    public RegraNegocioException(String mensagem){
        this();
        addMensagem(mensagem);
    }

    public RegraNegocioException addMensagem(String mensagem){
        mensagens.add(mensagem);
        return this;
    }

    public RegraNegocioException addMensagens(Collection<String> mensagens){
        this.mensagens.addAll(mensagens);
        return this;
    }

    public RegraNegocioException status(HttpStatus status){
        if(Objects.nonNull(status)){
            this.status = status;
        }
        return this;
    }

    public void addInformacaoComplementar(String key, Object value){
        informacoesComplementares.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getInformacaoComplementar(String key){
        return informacoesComplementares.containsKey(key) ? (T) informacoesComplementares.get(key) : null;
    }

    @JsonIgnore
    public String getMensagensSeparadosPorLinhas(){
        return String.join("\n", mensagens);
    }

    public void lancar() {
        if(!mensagens.isEmpty()){
            throw this;
        }
    }

    public int getStatusCode(){
        return getStatus().value();
    }

    @Override
    public String getMessage() {
        return getMensagensSeparadosPorLinhas();
    }
}
