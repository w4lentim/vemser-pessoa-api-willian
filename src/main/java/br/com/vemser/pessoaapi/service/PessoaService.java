package br.com.vemser.pessoaapi.service;
// ---------- Import's Classes -----------;
import br.com.vemser.pessoaapi.dto.PessoaCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.entity.Pessoa;
import br.com.vemser.pessoaapi.entity.TipoDeMensagem;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.PessoaRepository;
// ---------- Import's Fasterxml ---------;
import com.fasterxml.jackson.databind.ObjectMapper;
// ---------- Import's Lombok ------------;
import lombok.extern.slf4j.Slf4j;
// ---------- Import's SpringBoot --------;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// ---------- Import's Java --------------;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ObjectMapper objectMapper;

    public List<PessoaDTO> list() {
        return pessoaRepository.list().stream()
                .map(pessoa -> objectMapper.convertValue(pessoa, PessoaDTO.class))
                .collect(Collectors.toList());
    }
    public List<PessoaDTO> listByName(String nome) {
        List<PessoaDTO> pessoasDTO = new ArrayList<>();
        List<Pessoa> pessoasEntity = pessoaRepository.list().stream()
                .filter(pessoa -> pessoa.getNome().equals(nome)).toList();
        for (Pessoa pessoa : pessoasEntity) {
            pessoasDTO.add(objectMapper.convertValue(pessoa, PessoaDTO.class));
        }
        return pessoasDTO;
    }
    public PessoaDTO create(PessoaCreateDTO pessoa) throws RegraDeNegocioException {
        log.info("Criando pessoa...");
        // --- ENTRADA ---
        Pessoa pessoaEntity = objectMapper.convertValue(pessoa, Pessoa.class);
        Pessoa pessoaCriada = pessoaRepository.create(pessoaEntity);
        // --- RETORNO ---
        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaCriada, PessoaDTO.class);
        String tipo = TipoDeMensagem.CREATE.getTipo();
        emailService.sendEmailPessoa(pessoaDTO, tipo);
        log.info("Pessoa " + pessoaDTO.getNome() + " criada com sucesso!");
        return pessoaDTO;
    }

    public PessoaDTO update(Integer idPessoa, PessoaCreateDTO pessoaAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando pessoa...");
        Pessoa pessoaEntity = convertPessoaToEntity(pessoaAtualizar);
        Pessoa pessoaAtualizada = pessoaRepository.update(verifyByIdPessoa(idPessoa), pessoaEntity);
        PessoaDTO pessoaDTO = convertPessoaToDTO(pessoaAtualizada);
        String tipo = TipoDeMensagem.UPDATE.getTipo();
        emailService.sendEmailPessoa(pessoaDTO, tipo);
        log.info("Dados atualizados da pessoa: " + pessoaAtualizada);
        return pessoaDTO;
    }

    public void delete(Integer idPessoa) throws RegraDeNegocioException {
        log.info("Deletando pessoa...");
        Pessoa pessoaVerify = verifyByIdPessoa(idPessoa);
        pessoaRepository.list().remove(pessoaVerify);
        log.info("Pessoa " + pessoaVerify.getNome() + " foi removida com sucesso!");
        PessoaDTO pessoaDTO = convertPessoaToDTO(pessoaVerify);
        String tipo = TipoDeMensagem.DELETE.getTipo();
        emailService.sendEmailPessoa(pessoaDTO, tipo);
    }

    public Pessoa verifyByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        return pessoaRepository.list().stream()
                .filter(pessoa -> pessoa.getIdPessoa().equals(idPessoa))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Não foi encontrado nenhuma pessoa associada ao ID."));
    }

    public Pessoa convertPessoaToEntity(PessoaCreateDTO pessoaDTO) {
        return objectMapper.convertValue(pessoaDTO, Pessoa.class);
    }

    public PessoaDTO convertPessoaToDTO(Pessoa pessoa) {
        return objectMapper.convertValue(pessoa, PessoaDTO.class);
    }
}
