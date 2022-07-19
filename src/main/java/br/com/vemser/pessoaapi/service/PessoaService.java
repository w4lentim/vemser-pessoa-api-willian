package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.PessoaCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.entity.PessoaEntity;
import br.com.vemser.pessoaapi.enums.TipoDeMensagem;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.PessoaRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PessoaService {

    private PessoaRepository pessoaRepository;
    private EmailService emailService;
    private ObjectMapper objectMapper;

    public List<PessoaDTO> list() {
        return pessoaRepository.findAll()
                .stream()
                .map(this::convertPessoaToDTO).toList();
    }
    public List<PessoaDTO> listByName(String nome) {
        return pessoaRepository.findAll()
                .stream()
                .filter(pessoa -> pessoa.getNome().toLowerCase().contains(nome.toLowerCase()))
                .toList()
                .stream()
                .map(this::convertPessoaToDTO)
                .toList();
    }

    public PessoaEntity listByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        return pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new RegraDeNegocioException("Pessoa não encontrada no Banco de Dados."));
    }

    public PessoaDTO create(PessoaCreateDTO pessoaCreateDTO) throws RegraDeNegocioException {
        log.info("Criando pessoa...");
        PessoaDTO pessoaDTO = convertPessoaToDTO(pessoaRepository.save(convertPessoaToEntity(pessoaCreateDTO)));
        log.info("Pessoa " + pessoaDTO.getNome() + " criada com sucesso!");
        return pessoaDTO;
    }

    public PessoaDTO update(Integer idPessoa, PessoaCreateDTO pessoaAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando pessoa...");
        PessoaEntity pessoaEntity = listByIdPessoa(idPessoa);

        pessoaEntity.setNome(pessoaAtualizar.getNome());
        pessoaEntity.setDataNascimento(pessoaAtualizar.getDataNascimento());
        pessoaEntity.setCpf(pessoaAtualizar.getCpf());
        pessoaEntity.setEmail(pessoaAtualizar.getEmail());

        PessoaDTO pessoaDTO = convertPessoaToDTO(pessoaRepository.save(pessoaEntity));

        String tipo = TipoDeMensagem.UPDATE.getTipo();
        emailService.sendEmailPessoa(pessoaDTO, tipo);
        log.info("Os dados " + pessoaDTO.getNome() + " foram atualizados com sucesso!");
        return pessoaDTO;
    }

    public void delete(Integer idPessoa) throws RegraDeNegocioException {
        log.info("Deletando pessoa...");

        PessoaEntity pessoaVerify = listByIdPessoa(idPessoa);
        pessoaRepository.delete(pessoaVerify);

        log.info("Pessoa " + pessoaVerify.getNome() + " foi removida com sucesso!");

        PessoaDTO pessoaDTO = convertPessoaToDTO(pessoaVerify);
        String tipo = TipoDeMensagem.DELETE.getTipo();
        emailService.sendEmailPessoa(pessoaDTO, tipo);
    }

    public PessoaEntity convertPessoaToEntity(PessoaCreateDTO pessoaCreateDTO) {
        return objectMapper.convertValue(pessoaCreateDTO, PessoaEntity.class);
    }

    public PessoaDTO convertPessoaToDTO(PessoaEntity pessoaEntity) {
        return objectMapper.convertValue(pessoaEntity, PessoaDTO.class);
    }
}
