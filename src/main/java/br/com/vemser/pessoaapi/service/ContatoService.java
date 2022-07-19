package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.ContatoCreateDTO;
import br.com.vemser.pessoaapi.dto.ContatoDTO;
import br.com.vemser.pessoaapi.entity.ContatoEntity;
import br.com.vemser.pessoaapi.entity.PessoaEntity;
import br.com.vemser.pessoaapi.repository.ContatoRepository;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContatoService {

    private ContatoRepository contatoRepository;
    private PessoaService pessoaService;
    private ObjectMapper objectMapper;

    public List<ContatoDTO> list() {
        return contatoRepository.findAll().stream()
                .map(this::contatoToContatoDTO)
                .toList();
    }

    public ContatoDTO create(Integer idPessoa, ContatoCreateDTO contatoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando contato...");

        PessoaEntity pessoaEntity = pessoaService.listByIdPessoa(idPessoa);
        contatoCreateDTO.setIdPessoa(idPessoa);

        log.info("Adicionando um novo contato à " + pessoaEntity.getNome());
        ContatoDTO contatoDTO = contatoToContatoDTO(contatoRepository.save(contatoCreateDTOToContato(contatoCreateDTO)));

        log.info("Contato adicionado com sucesso!");
        return contatoDTO;
    }

    public ContatoDTO update(Integer idContato, ContatoCreateDTO contatoAtualizar) throws RegraDeNegocioException {

        PessoaEntity pessoaEntity = pessoaService.listByIdPessoa(contatoAtualizar.getIdPessoa());

        log.info("Atualizando os dados de contato de " + pessoaEntity.getNome());

        ContatoEntity contatoEntity = findByIdContato(idContato);

        contatoEntity.setIdPessoa(contatoAtualizar.getIdPessoa());
        contatoEntity.setTipo(contatoAtualizar.getTipo());
        contatoEntity.setNumero(contatoAtualizar.getNumero());
        contatoEntity.setDescricao(contatoAtualizar.getDescricao());

        log.info("Dados de contato atualizados para " + pessoaEntity.getNome() + " foram atualizados com sucesso!");
        ContatoDTO contatoDTO = contatoToContatoDTO(contatoRepository.save(contatoEntity));
        return contatoDTO;
    }

    public void delete(Integer idContato) throws RegraDeNegocioException {
        PessoaEntity pessoaEntity = pessoaService.listByIdPessoa(findByIdContato(idContato).getIdPessoa());
        log.info("Removendo contato do banco de dados...");
        contatoRepository.delete(findByIdContato(idContato));
        log.info("Contato removido com sucesso!");
    }

    public ContatoEntity findByIdContato(Integer idContato) throws RegraDeNegocioException {
        return contatoRepository.findById(idContato)
                .orElseThrow(() -> new RegraDeNegocioException("Contato não encontrado no banco de dados."));
    }

    public List<ContatoDTO> listContatoByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        return contatoRepository.findAll().stream()
                .filter(contato -> contato.getIdPessoa().equals(idPessoa))
                .map(this::contatoToContatoDTO)
                .toList();
    }

    public ContatoEntity contatoCreateDTOToContato(ContatoCreateDTO contatoCreateDTO) {
        return objectMapper.convertValue(contatoCreateDTO, ContatoEntity.class);
    }

    public ContatoDTO contatoToContatoDTO(ContatoEntity contato) {
        return objectMapper.convertValue(contato, ContatoDTO.class);
    }
}
