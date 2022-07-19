package br.com.vemser.pessoaapi.enums;

import java.util.Arrays;

public enum TipoDeEndereco {

    COMERCIAL(1),
    RESIDENCIAL(2);

    private Integer tipo;

    TipoDeEndereco(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public static TipoDeEndereco ofTipo(Integer tipo) {
        return Arrays.stream(TipoDeEndereco.values())
                .filter(tdp -> tdp.getTipo().equals(tipo))
                .findFirst()
                .get();
    }
}
