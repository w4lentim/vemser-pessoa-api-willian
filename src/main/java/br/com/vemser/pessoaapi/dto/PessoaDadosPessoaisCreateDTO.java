package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.entity.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDadosPessoaisCreateDTO {

    @Schema(description = "Nome da Pessoa")
    @NotBlank(message = "O nome não pode ser nulo/vazio/conter somente espaços.")
    private String nome;

    @Schema(description = "Data de nascimento da Pessoa")
    @Past
    @NotNull
    private LocalDate dataNascimento;

    @Schema(description = "CPF da Pessoa")
    @NotBlank
    @Size(min = 11, max = 11, message = "O CPF deve conter 11 números.")
    private String cpf;

    @Schema(description = "E-mail da Pessoa")
    @NotBlank
    private String email;

    @Schema(description = "CNH do usuário")
    @NotBlank
    private String cnh;

    @Schema(description = "Nome da mãe do usuário")
    @NotBlank
    private String nomeMae;

    @Schema(description = "Nome do pai do usuário")
    @NotBlank
    private String nomePai;

    @Schema(description = "Registro (RG) do usuário")
    @NotBlank
    private String rg;

    @Schema(description = "Sexo do usuário")
    @NotNull
    private Sexo sexo;

    @Schema(description = "Título de eleitor do usuário")
    @NotBlank
    private String tituloEleitor;
}
