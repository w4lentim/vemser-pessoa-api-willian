package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.client.DadosPessoaisClient;
import br.com.vemser.pessoaapi.dto.DadosPessoaisDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DadosPessoaisService {

    @Autowired
    private DadosPessoaisClient client;

    public List<DadosPessoaisDTO> getAll() {
        return client.getAll();
    }

    public DadosPessoaisDTO get(String cpf) {
        return client.get(cpf);
    }

    public DadosPessoaisDTO post(DadosPessoaisDTO dadosPessoaisDTO) {
        return client.post(dadosPessoaisDTO);
    }

    public DadosPessoaisDTO put(String cpf, DadosPessoaisDTO dadosPessoaisDTO) {
        return client.put(cpf, dadosPessoaisDTO);
    }

    public void delete(String cpf) {
        client.delete(cpf);
    }
}
