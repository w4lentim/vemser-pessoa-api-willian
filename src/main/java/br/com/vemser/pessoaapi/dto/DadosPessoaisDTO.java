package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosPessoaisDTO {

    @Schema(description = "CNH do usuário")
    private String cnh;
    @Schema(description = "CPF do usuário")
    private String cpf;
    @Schema(description = "Nome do usuário")
    private String nome;
    @Schema(description = "Nome da mãe do usuário")
    private String nomeMae;
    @Schema(description = "Nome do pai do usuário")
    private String nomePai;
    @Schema(description = "Registro (RG) do usuário")
    private String rg;
    @Schema(description = "Sexo do usuário")
    private Sexo sexo;
    @Schema(description = "Título de eleitor do usuário")
    private String tituloEleitor;
}
