package br.com.vemser.pessoaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDadosPessoaisDTO extends PessoaDadosPessoaisCreateDTO {

    @Schema(description = "Identificador (ID) exclusivo da pessoa")
    private Integer idPessoa;
}
