package br.com.vemser.pessoaapi.enums;

import java.util.Arrays;

public enum TipoDeContato {

    COMERCIAL(1),
    RESIDENCIAL(2);

    private Integer tipo;

    TipoDeContato(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public static TipoDeContato ofTipo(Integer tipo) {
        return Arrays.stream(TipoDeContato.values())
                .filter(tdc -> tdc.getTipo().equals(tipo))
                .findFirst()
                .get();
    }
}
