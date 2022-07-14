package br.com.vemser.pessoaapi.entity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contato {

    private Integer idContato;
    private Integer idPessoa;
    private TipoDeContato tipo;
    private String numero;
    private String descricao;

}
