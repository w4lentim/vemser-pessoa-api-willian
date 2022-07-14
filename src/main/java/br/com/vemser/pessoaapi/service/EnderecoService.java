package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.EnderecoCreateDTO;
import br.com.vemser.pessoaapi.dto.EnderecoDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.entity.Endereco;
import br.com.vemser.pessoaapi.entity.Pessoa;
import br.com.vemser.pessoaapi.entity.TipoDeMensagem;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ObjectMapper objectMapper;

    public List<EnderecoDTO> list() {
        log.info("Listado todos os endereços");
        return enderecoRepository.list().stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public EnderecoDTO listEnderecoByIdEndereco(Integer idEndereco) throws RegraDeNegocioException {
        log.info("Listado endereço pelo idEndereço");
        Endereco enderecoEntity = verifyEnderecoById(idEndereco);
        return objectMapper.convertValue(enderecoEntity, EnderecoDTO.class);
    }

    public EnderecoDTO create(Integer idPessoa, EnderecoCreateDTO endereco) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.verifyByIdPessoa(idPessoa);
        log.info("Criando um endereço para a pessoa: " + pessoa.getNome());
        Endereco enderecoEntity = convertEnderecoToEntity(endereco);
        enderecoEntity.setIdPessoa(idPessoa);
        enderecoRepository.create(enderecoEntity);
        EnderecoDTO enderecoDTO = convertEnderecoToDTO(enderecoEntity);
        String tipo = TipoDeMensagem.CREATE.getTipo();
        emailService.sendEmailEndereco(pessoaService.convertPessoaToDTO(pessoa), enderecoDTO, tipo);
        log.info("Endereço adicionado com sucesso!");
        return enderecoDTO;
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando endereço...");
        Endereco enderecoEntity = convertEnderecoToEntity(enderecoAtualizar);
        Endereco enderecoRecuperado = verifyEnderecoById(idEndereco);
        Pessoa pessoaRecuperada = pessoaService.verifyByIdPessoa(enderecoEntity.getIdPessoa());
        getDadosEndereco(enderecoRecuperado, enderecoEntity);
        log.info("Endereco atualizado com sucesso!");
        EnderecoDTO enderecoDTO = convertEnderecoToDTO(enderecoRecuperado);
        String tipo = TipoDeMensagem.UPDATE.getTipo();
        emailService.sendEmailEndereco(pessoaService.convertPessoaToDTO(pessoaRecuperada), enderecoDTO, tipo);
        return enderecoDTO;
    }

    public void delete(Integer idEndereco) throws RegraDeNegocioException {
        log.info("Deletando endereço...");
        Endereco enderecoRecuperado = verifyEnderecoById(idEndereco);
        Pessoa pessoaRecuperada = pessoaService.verifyByIdPessoa(enderecoRecuperado.getIdPessoa());
        enderecoRepository.list().remove(enderecoRecuperado);
        log.info("Endereço deletado com sucesso!");
        String tipo = TipoDeMensagem.DELETE.getTipo();
        emailService.sendEmailEndereco(pessoaService.convertPessoaToDTO(pessoaRecuperada), convertEnderecoToDTO(enderecoRecuperado), tipo);
    }

    public Endereco verifyEnderecoById(Integer idEndereco) throws RegraDeNegocioException {
        return enderecoRepository.list().stream()
                .filter(endereco -> endereco.getIdEndereco().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Não foi possível encontrar nenhum endereço associado ao ID."));
    }

    public List<EnderecoDTO> listEnderecoByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        return enderecoRepository.list().stream()
                .filter(endereco -> endereco.getIdPessoa().equals(idPessoa))
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public Endereco convertEnderecoToEntity(EnderecoCreateDTO enderecoDTO) {
        return objectMapper.convertValue(enderecoDTO, Endereco.class);
    }

    public EnderecoDTO convertEnderecoToDTO(Endereco endereco) {
        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }

    private void getDadosEndereco(Endereco enderecoRecuperado, Endereco enderecoEntity) {
        enderecoRecuperado.setTipo(enderecoEntity.getTipo());
        enderecoRecuperado.setLogradouro(enderecoEntity.getLogradouro());
        enderecoRecuperado.setNumero(enderecoEntity.getNumero());
        enderecoRecuperado.setComplemento(enderecoEntity.getComplemento());
        enderecoRecuperado.setCep(enderecoEntity.getCep());
        enderecoRecuperado.setCidade(enderecoEntity.getCidade());
        enderecoRecuperado.setEstado(enderecoEntity.getEstado());
        enderecoRecuperado.setPais(enderecoEntity.getPais());
    }
}
