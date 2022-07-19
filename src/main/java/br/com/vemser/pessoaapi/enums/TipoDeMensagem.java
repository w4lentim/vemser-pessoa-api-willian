package br.com.vemser.pessoaapi.enums;

import java.util.Arrays;

public enum TipoDeMensagem {

    CREATE("create"),
    UPDATE("update"),
    DELETE("delete");

    private String tipo;

    TipoDeMensagem(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public static TipoDeMensagem ofTipo(String tipo) {
        return Arrays.stream(TipoDeMensagem.values())
                .filter(tdm -> tdm.getTipo().equals(tipo))
                .findFirst()
                .get();
    }
}
