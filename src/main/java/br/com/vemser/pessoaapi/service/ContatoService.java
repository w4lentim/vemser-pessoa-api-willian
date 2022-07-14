package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.ContatoCreateDTO;
import br.com.vemser.pessoaapi.dto.ContatoDTO;
import br.com.vemser.pessoaapi.entity.Contato;
import br.com.vemser.pessoaapi.entity.Pessoa;
import br.com.vemser.pessoaapi.repository.ContatoRepository;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private ObjectMapper objectMapper;

    public List<ContatoDTO> list() {
        return contatoRepository.list().stream()
                .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                .collect(Collectors.toList());
    }

    public ContatoDTO create(Integer idPessoa, ContatoCreateDTO contato) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.verifyByIdPessoa(idPessoa);
        log.info("Criando contato...");
        contato.setIdPessoa(idPessoa);
        // --- ENTRADA ---
        Contato contatoEntity = objectMapper.convertValue(contato, Contato.class);
        Contato contatoCriado = contatoRepository.create(contatoEntity);
        // --- RETORNO ---
        ContatoDTO contatoDTO = objectMapper.convertValue(contatoCriado, ContatoDTO.class);
        log.info("Contato criado com sucesso!");
        return contatoDTO;
    }

    public ContatoDTO update(Integer idContato, ContatoCreateDTO contatoAtualizar) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.verifyByIdPessoa(contatoAtualizar.getIdPessoa());
        log.info("Atualizando os dados de contato " + pessoa.getNome());
        // --- ENTRADA ---
        Contato contatoEntity = objectMapper.convertValue(contatoAtualizar, Contato.class);
        Contato contatoAtualizado = contatoRepository.update(verifyByIdContato(idContato), contatoEntity);
        // --- RETORNO ---
        ContatoDTO contatoDTO = objectMapper.convertValue(contatoAtualizado, ContatoDTO.class);
        log.info("Dados do contato atualizados");
        return contatoDTO;
    }

    public void delete(Integer idContato) throws RegraDeNegocioException {
        log.info("Deletando contato...");
        contatoRepository.delete(verifyByIdContato(idContato));
        log.info("Contato deletado com sucesso!");
    }

    public Contato verifyByIdContato(Integer idContato) throws RegraDeNegocioException {
        return contatoRepository.list().stream()
                .filter(contato -> contato.getIdContato().equals(idContato))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Não foi possível encontrar nenhum contato associado ao ID."));
    }

    public List<ContatoDTO> listContatoByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        return list().stream()
                .filter(contato -> contato.getIdPessoa().equals(idPessoa)).toList();
    }
}
