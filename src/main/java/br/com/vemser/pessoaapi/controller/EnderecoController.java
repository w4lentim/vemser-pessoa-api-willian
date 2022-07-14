package br.com.vemser.pessoaapi.controller;
// ------------ Import's Classes -------------------;
import br.com.vemser.pessoaapi.dto.EnderecoCreateDTO;
import br.com.vemser.pessoaapi.dto.EnderecoDTO;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.EnderecoService;
// ------------ Import's Swagger -------------------;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
// ------------ Import's SpringBoot ----------------;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
// ------------ Import's Java ----------------------;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/endereco")
@Validated
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Operation(summary = "Listar endereços", description = "Listará todos os endereços do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Todos endereços foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> list() {
        return new ResponseEntity<>(enderecoService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar endereço pelo ID", description = "Listará um endereço do banco de acordo com o ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! O endereço referente ao ID foi listado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissao negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> listEnderecoByIdEndereco(@PathVariable("idEndereco") Integer idEndereco) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.listEnderecoByIdEndereco(idEndereco), HttpStatus.OK);
    }

    @Operation(summary = "Listar endereços da pessoa", description = "Listará os endereços associados a pessoa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Os endereços associados a pessoa informada foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissao negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execuçao, foi gerada uma exceção")
            }
    )
    @GetMapping("/{idPessoa}/pessoa")
    public ResponseEntity<List<EnderecoDTO>> listByIdPessoa(@PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.listEnderecoByIdPessoa(idPessoa), HttpStatus.OK);
    }

    @Operation(summary = "Adicionar um endereço a pessoa", description = "Adicionará um novo endereço a uma pessoa do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! O endereço foi adicionado a pessoa com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissao negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execuçao, foi gerada uma exceção")
            }
    )
    @PostMapping("/{idPessoa}")
    public ResponseEntity<EnderecoDTO> create(@PathVariable("idPessoa") Integer idPessoa, @RequestBody @Valid EnderecoCreateDTO endereco) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.create(idPessoa, endereco), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar endereço", description = "Atualizará todos os dados de um endereço de acordo com ID informado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Os dados do endereço foram atualizados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissao negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execuçao, foi gerada uma exceção")
            }
    )
    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> update(@PathVariable("idEndereco") Integer idEndereco, @RequestBody @Valid EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.update(idEndereco, enderecoAtualizar), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Deletar endereço", description = "Deletará um endereço do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! O endereço foi removido com sucesso do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Permissao negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> delete(@PathVariable("idEndereco") Integer idEndereco) throws RegraDeNegocioException {
        enderecoService.delete(idEndereco);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
