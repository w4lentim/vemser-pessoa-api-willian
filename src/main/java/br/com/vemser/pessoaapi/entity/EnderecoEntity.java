package br.com.vemser.pessoaapi.entity;

import br.com.vemser.pessoaapi.enums.TipoDeEndereco;
import lombok.*;

import javax.persistence.*;

@Entity(name = "ENDERECO_PESSOA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoEntity {

    @Id
    @SequenceGenerator(name = "ENDERECO_SEQ", sequenceName = "SEQ_ENDERECO_CONTATO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENDERECO_SEQ")
    @Column(name = "ID_ENDERECO")
    private Integer idEndereco;

//    private Integer idPessoa;

    @Column(name = "TIPO")
    private TipoDeEndereco tipo;

    @Column(name = "LOGRADOURO")
    private String logradouro;

    @Column(name = "NUMERO")
    private Integer numero;

    @Column(name = "COMPLEMENTO")
    private String complemento;

    @Column(name = "CEP")
    private String cep;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "PAIS")
    private String pais;
}
