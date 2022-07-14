package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.dto.PessoaDadosPessoaisCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDadosPessoaisDTO;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.PessoaDadosPessoaisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoa-dados-pessoais")
@Validated
public class PessoaDadosPessoaisController {

    @Autowired
    PessoaDadosPessoaisService pessoaDadosPessoaisService;

    @Operation(summary = "Listar dados pessoais", description = "Realizará a listagem de todos os dados pessoais das pessoas do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Os dados pessoais foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<PessoaDadosPessoaisDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(pessoaDadosPessoaisService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Adicionar nova pessoa e seus dados pessoais", description = "Adicionará uma nova pessoa ao banco e seus dados pessoais")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A adição da pessoa e seus dados pessoais no banco foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<PessoaDadosPessoaisDTO> create(@Valid @RequestBody PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(pessoaDadosPessoaisService.create(pessoaDadosPessoaisCreateDTO), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Atualizar pessoa e seus dados pessoais", description = "Atualizará uma pessoa associada ao ID e todos os seus dados pessoais")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A pessoa associada ao ID foi encontrada e todos os seus dados foram atualizados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaDadosPessoaisDTO> update(@PathVariable("idPessoa") Integer idPessoa, @Valid @RequestBody PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(pessoaDadosPessoaisService.update(idPessoa, pessoaDadosPessoaisCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deletar pessoa e seus dados pessoais", description = "Removerá do banco de dados a pessoa associada ao ID e todos os seus dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A pessoa associada ao ID foi encontrada e todos os seus dados foram removidos com sucesso do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPessoa}")
    public ResponseEntity<Void> delete(@PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        pessoaDadosPessoaisService.delete(idPessoa);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
