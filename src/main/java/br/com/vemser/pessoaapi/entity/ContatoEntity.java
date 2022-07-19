package br.com.vemser.pessoaapi.entity;
import br.com.vemser.pessoaapi.enums.TipoDeContato;
import lombok.*;

import javax.persistence.*;

@Entity(name = "CONTATO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContatoEntity {

    @Id
    @SequenceGenerator(name = "SEQ_CONTATO", sequenceName = "SEQ_CONTATO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTATO")
    private Integer idContato;

    @Column(name = "ID_PESSOA")
    private Integer idPessoa;

    @Column(name = "TIPO")
    private TipoDeContato tipo;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "DESCRICAO")
    private String descricao;

}
