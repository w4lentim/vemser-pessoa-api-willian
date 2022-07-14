package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.*;
import br.com.vemser.pessoaapi.entity.Pessoa;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaDadosPessoaisService {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private DadosPessoaisService dadosPessoaisService;

    @Autowired
    private ObjectMapper objectMapper;

    public List<PessoaDadosPessoaisDTO> list() {
        List<PessoaDadosPessoaisDTO> pessoaDadosPessoaisDTO = new ArrayList<>();
        List<PessoaDTO> pessoaDTO = pessoaService.list();

        for (int i = 0; i < pessoaDTO.size(); i++) {
            pessoaDadosPessoaisDTO.add(new PessoaDadosPessoaisDTO());
            pessoaDadosPessoaisDTO.get(i).setIdPessoa(pessoaDTO.get(i).getIdPessoa());
            pessoaDadosPessoaisDTO.get(i).setNome(pessoaDTO.get(i).getNome());
            pessoaDadosPessoaisDTO.get(i).setCpf(pessoaDTO.get(i).getCpf());
            pessoaDadosPessoaisDTO.get(i).setDataNascimento(pessoaDTO.get(i).getDataNascimento());
            pessoaDadosPessoaisDTO.get(i).setEmail(pessoaDTO.get(i).getEmail());
        }

        for (PessoaDadosPessoaisDTO pessoaDPDTO : pessoaDadosPessoaisDTO) {
            Optional<DadosPessoaisDTO> dadosPessoaisDTO = dadosPessoaisService.getAll()
                    .stream()
                    .filter(dadosPessoais -> dadosPessoais.getCpf().equals(pessoaDPDTO.getCpf()))
                    .findAny();
            if (dadosPessoaisDTO.isPresent()) {
                pessoaDPDTO.setCnh(dadosPessoaisDTO.get().getCnh());
                pessoaDPDTO.setNomeMae(dadosPessoaisDTO.get().getNomeMae());
                pessoaDPDTO.setNomePai(dadosPessoaisDTO.get().getNomePai());
                pessoaDPDTO.setRg(dadosPessoaisDTO.get().getRg());
                pessoaDPDTO.setSexo(dadosPessoaisDTO.get().getSexo());
                pessoaDPDTO.setTituloEleitor(dadosPessoaisDTO.get().getTituloEleitor());
            }
        }
        return pessoaDadosPessoaisDTO;
    }

    public PessoaDadosPessoaisDTO create(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) throws RegraDeNegocioException {
        PessoaDTO pessoaDTO = pessoaService.create(convertToPessoaDTO(pessoaDadosPessoaisCreateDTO));
        dadosPessoaisService.post(convertToDadosPessoaisDTO(pessoaDadosPessoaisCreateDTO));

        PessoaDadosPessoaisDTO pessoaDadosPessoaisDTO = CreateDTOToPessoaDadosPessoaisDTO(pessoaDadosPessoaisCreateDTO);
        pessoaDadosPessoaisDTO.setIdPessoa(pessoaDTO.getIdPessoa());

        return pessoaDadosPessoaisDTO;
    }

    public PessoaDadosPessoaisDTO update(Integer idPessoa, PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) throws RegraDeNegocioException {
        PessoaCreateDTO pessoaCreateDTO = convertToPessoaDTO(pessoaDadosPessoaisCreateDTO);
        pessoaService.update(idPessoa, pessoaCreateDTO);

        DadosPessoaisDTO dadosPessoaisDTO = convertToDadosPessoaisDTO(pessoaDadosPessoaisCreateDTO);
        dadosPessoaisService.put(pessoaDadosPessoaisCreateDTO.getCpf(), dadosPessoaisDTO);

        PessoaDadosPessoaisDTO pessoaDadosPessoaisDTO = CreateDTOToPessoaDadosPessoaisDTO(pessoaDadosPessoaisCreateDTO);
        pessoaDadosPessoaisDTO.setIdPessoa(idPessoa);
        return pessoaDadosPessoaisDTO;
    }

    public void delete(Integer idPessoa) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.verifyByIdPessoa(idPessoa);
        pessoaService.delete(idPessoa);
        if (dadosPessoaisService.getAll().stream()
                .anyMatch(dadosPessoaisDTO -> dadosPessoaisDTO.getCpf().equals(pessoa.getCpf()))) {
            dadosPessoaisService.delete(pessoa.getCpf());
        }
    }

    public PessoaCreateDTO convertToPessoaDTO(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) {
        PessoaCreateDTO pessoaCreateDTO = new PessoaCreateDTO();

        getConvertPessoaDTO(pessoaDadosPessoaisCreateDTO, pessoaCreateDTO);

        return pessoaCreateDTO;
    }

    public DadosPessoaisDTO convertToDadosPessoaisDTO(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) {
        DadosPessoaisDTO dadosPessoaisDTO = new DadosPessoaisDTO();

        getConvertDadosPessoaisDTO(pessoaDadosPessoaisCreateDTO, dadosPessoaisDTO);

        return dadosPessoaisDTO;
    }

    private void getConvertPessoaDTO(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO, PessoaCreateDTO pessoaCreateDTO) {
        pessoaCreateDTO.setNome(pessoaDadosPessoaisCreateDTO.getNome());
        pessoaCreateDTO.setCpf(pessoaDadosPessoaisCreateDTO.getCpf());
        pessoaCreateDTO.setDataNascimento(pessoaDadosPessoaisCreateDTO.getDataNascimento());
        pessoaCreateDTO.setEmail(pessoaDadosPessoaisCreateDTO.getEmail());
    }

    private void getConvertDadosPessoaisDTO(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO, DadosPessoaisDTO dadosPessoaisDTO) {
        dadosPessoaisDTO.setNome(pessoaDadosPessoaisCreateDTO.getNome());
        dadosPessoaisDTO.setCpf(pessoaDadosPessoaisCreateDTO.getCpf());
        dadosPessoaisDTO.setCnh(pessoaDadosPessoaisCreateDTO.getCnh());
        dadosPessoaisDTO.setNomeMae(pessoaDadosPessoaisCreateDTO.getNomeMae());
        dadosPessoaisDTO.setNomePai(pessoaDadosPessoaisCreateDTO.getNomePai());
        dadosPessoaisDTO.setRg(pessoaDadosPessoaisCreateDTO.getRg());
        dadosPessoaisDTO.setSexo(pessoaDadosPessoaisCreateDTO.getSexo());
        dadosPessoaisDTO.setTituloEleitor(pessoaDadosPessoaisCreateDTO.getTituloEleitor());
    }

    public PessoaDadosPessoaisDTO CreateDTOToPessoaDadosPessoaisDTO(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) {
        return objectMapper.convertValue(pessoaDadosPessoaisCreateDTO, PessoaDadosPessoaisDTO.class);
    }
}
