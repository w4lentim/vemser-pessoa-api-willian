package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.dto.DadosPessoaisDTO;
import br.com.vemser.pessoaapi.service.DadosPessoaisService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dados-pessoais")
public class DadosPessoaisController {

    @Autowired
    private DadosPessoaisService dadosPessoaisService;

    @GetMapping
    public ResponseEntity<List<DadosPessoaisDTO>> getAll() {
        return new ResponseEntity<>(dadosPessoaisService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<DadosPessoaisDTO> get(@PathVariable("cpf") String cpf) {
        return new ResponseEntity<>(dadosPessoaisService.get(cpf), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DadosPessoaisDTO> post(@RequestBody DadosPessoaisDTO dadosPessoaisDTO) {
        return new ResponseEntity<>(dadosPessoaisService.post(dadosPessoaisDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<DadosPessoaisDTO> put(@PathVariable("cpf") String cpf, @RequestBody DadosPessoaisDTO dadosPessoaisDTO) {
        return new ResponseEntity<>(dadosPessoaisService.put(cpf, dadosPessoaisDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable("cpf") String cpf) {
        dadosPessoaisService.delete(cpf);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
