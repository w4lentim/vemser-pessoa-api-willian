package br.com.vemser.pessoaapi.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private Integer idEndereco;
    private Integer idPessoa;
    private TipoDeEndereco tipo;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String cep;
    private String cidade;
    private String estado;
    private String pais;
}
