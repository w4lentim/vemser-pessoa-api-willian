package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.EnderecoCreateDTO;
import br.com.vemser.pessoaapi.dto.EnderecoDTO;
import br.com.vemser.pessoaapi.entity.EnderecoEntity;
import br.com.vemser.pessoaapi.entity.PessoaEntity;
import br.com.vemser.pessoaapi.enums.TipoDeMensagem;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.EnderecoRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EnderecoService {

    private EnderecoRepository enderecoRepository;
    private PessoaService pessoaService;
    private EmailService emailService;
    private ObjectMapper objectMapper;

    public List<EnderecoDTO> list() {
        log.info("Listando todos os endereços...");
        return enderecoRepository.findAll().stream()
                .map(this::enderecoToEnderecoDTO)
                .toList();
    }

    public EnderecoDTO listEnderecoByIdEndereco(Integer idEndereco) throws RegraDeNegocioException {
        log.info("Listando endereço pelo idEndereço...");
        return enderecoToEnderecoDTO(enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RegraDeNegocioException("O endereço não está cadastrado em nosso banco de dados.")));
    }

    public EnderecoDTO create(Integer idPessoa, EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        log.info("Adicionando endereço...");
        PessoaEntity pessoaRecuperada = pessoaService.listByIdPessoa(idPessoa);

        EnderecoDTO enderecoDTO = enderecoToEnderecoDTO(enderecoRepository.save(enderecoCreateDTOToEndereco(enderecoCreateDTO)));

        String tipo = TipoDeMensagem.CREATE.getTipo();
        emailService.sendEmailEndereco(pessoaService.convertPessoaToDTO(pessoaRecuperada), enderecoDTO, tipo);

        log.info("Endereço adicionado com sucesso!");
        return enderecoDTO;
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando endereço...");

        EnderecoDTO enderecoRecuperado = listEnderecoByIdEndereco(idEndereco);

        EnderecoDTO enderecoDTO = enderecoToEnderecoDTO(enderecoRepository.save(enderecoCreateDTOToEndereco(enderecoAtualizar)));

        log.info("Endereço atualizado com sucesso!");

        String tipo = TipoDeMensagem.UPDATE.getTipo();
        return enderecoDTO;
    }

    public void delete(Integer idEndereco) throws RegraDeNegocioException {
        log.info("Deletando endereço...");

        enderecoRepository.delete(enderecoDTOToEnderecoEntity(listEnderecoByIdEndereco(idEndereco)));

        log.info("Endereço deletado com sucesso!");
    }

    public EnderecoEntity enderecoDTOToEnderecoEntity(EnderecoDTO enderecoDTO) {
        return objectMapper.convertValue(enderecoDTO, EnderecoEntity.class);
    }

    public EnderecoEntity enderecoCreateDTOToEndereco (EnderecoCreateDTO enderecoCreateDTO){
        return objectMapper.convertValue(enderecoCreateDTO, EnderecoEntity.class);
    }

    public EnderecoDTO enderecoToEnderecoDTO(EnderecoEntity enderecoEntity) {
        return objectMapper.convertValue(enderecoEntity, EnderecoDTO.class);
    }
}
