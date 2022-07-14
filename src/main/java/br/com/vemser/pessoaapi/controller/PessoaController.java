package br.com.vemser.pessoaapi.controller;
// ----------- Import's Classes ------------;
import br.com.vemser.pessoaapi.dto.PessoaCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.EmailService;
import br.com.vemser.pessoaapi.service.PessoaService;
// ----------- Import's Swagger ------------;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
// ----------- Import's SpringFramework ----;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
// ----------- Import's Java ---------------;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
@Validated
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private EmailService emailService;

    public PessoaController() {}

    @Operation(summary = "Enviar e-mail", description = "Enviar um e-mail para a pessoa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "E-mail enviado para a pessoa com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não possui permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/email")
    public String email() {
        emailService.sendEmail();
        return "Enviando email...";
    }

    @Operation(summary = "Listar pessoas", description = "Realizará a listagem de todas as pessoas cadastradas no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! As pessoas foram listadas com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping
    public List<PessoaDTO> list() {
        return pessoaService.list();
    }

    @Operation(summary = "Buscar pessoa pelo nome", description = "Realizará uma busca no banco de dados pela pessoa que corresponde ao nome informado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A pessoa foi encontrada banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping("/byname")
    public List<PessoaDTO> listByName(@RequestParam("nome") String nome) {
        return pessoaService.listByName(nome);
    }

    @Operation(summary = "Adicionar pessoa", description = "Adicionará uma nova pessoa ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A pessoa foi adicionada com sucesso ao banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<PessoaDTO> create(@RequestBody @Valid PessoaCreateDTO pessoa) throws RegraDeNegocioException {
        return new ResponseEntity<>(pessoaService.create(pessoa), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar pessoa", description = "Atualizará os dados de uma pessoa do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Os dados da pessoa informada foram atualizados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaDTO> update(@PathVariable("idPessoa") Integer id, @RequestBody @Valid PessoaCreateDTO pessoaAtualizar) throws RegraDeNegocioException {
        return new ResponseEntity<>(pessoaService.update(id, pessoaAtualizar), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Deletar pessoa", description = "Deletará a pessoa e todos os seus dados do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A pessoa e todos os seus dados foram removidos com sucesso do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Permissao negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execuçao, foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPessoa}")
    public ResponseEntity<Void> delete(@PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        pessoaService.delete(idPessoa);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}