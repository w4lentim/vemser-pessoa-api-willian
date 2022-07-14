package br.com.vemser.pessoaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PessoaDTO extends PessoaCreateDTO {

    @Schema(description = "Identificador (ID) da pessoa.")
    private Integer idPessoa;

}
