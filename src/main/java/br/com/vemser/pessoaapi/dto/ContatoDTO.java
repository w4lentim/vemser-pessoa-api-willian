package br.com.vemser.pessoaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContatoDTO extends ContatoCreateDTO {

    @Schema(description = "Identificador (ID) do contato.")
    private Integer idContato;
}
