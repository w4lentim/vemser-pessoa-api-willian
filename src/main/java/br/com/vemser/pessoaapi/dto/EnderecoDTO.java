package br.com.vemser.pessoaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnderecoDTO extends EnderecoCreateDTO {

    @Schema(description = "Identificador (ID) do endereço.")
    private Integer idEndereco;
}
