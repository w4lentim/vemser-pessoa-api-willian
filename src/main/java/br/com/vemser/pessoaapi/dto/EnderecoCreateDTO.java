package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.TipoDeEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnderecoCreateDTO {

    @Schema(description = "Identificador (ID) da pessoa que o endereço está associado.")
    private Integer idPessoa;

    @NotNull(message = "O tipo de endereço deve ser RESIDENCIAL/COMERCIAL.")
    @Schema(description = "Tipo de endereço")
    private TipoDeEndereco tipo;

    @NotEmpty
    @Size(max = 250, message = "Informe um logradouro válido, contendo até 250 caracteres.")
    @Schema(description = "Logradouro da residência")
    private String logradouro;

    @NotNull(message = "O número da residência não pode ser nulo.")
    @Schema(description = "Número da residência")
    private Integer numero;

    @Schema(description = "Complemento da residência")
    private String complemento;

    @NotEmpty(message = "O CEP não pode ser vazio/nulo.")
    @Size(min = 8, max = 8, message = "O CEP deve conter 8 números.")
    @Schema(description = "CEP da residência")
    private String cep;

    @NotEmpty(message = "A cidade não pode ser nula/vazia.")
    @Size(max = 250, message = "Informe uma cidade válida, contendo até 250 caracteres.")
    @Schema(description = "Cidade em que está localizado o endereço")
    private String cidade;

    @NotNull(message = "O estado não pode ser nulo.")
    @Schema(description = "Estado em que está localizado a residência")
    private String estado;

    @NotNull(message = "O país não pode ser nulo.")
    @Schema(description = "País em que está localizado a residência")
    private String pais;
}
