package br.com.vemser.pessoaapi.dto;
// ------------ Import's Swagger --------------;
import io.swagger.v3.oas.annotations.media.Schema;
// ------------ Import's Lombok ---------------;
import lombok.*;
// ------------ Import's Javax -----------------;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PessoaCreateDTO {

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
}
