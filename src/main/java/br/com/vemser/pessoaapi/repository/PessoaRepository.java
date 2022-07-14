package br.com.vemser.pessoaapi.repository;

import br.com.vemser.pessoaapi.entity.Pessoa;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class PessoaRepository {

    private static List<Pessoa> listaPessoas = new ArrayList<>();
    private AtomicInteger COUNTER = new AtomicInteger();
    
    public PessoaRepository() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        listaPessoas.add(new Pessoa(COUNTER.incrementAndGet() /*1*/, "Maicon Gerardi", LocalDate.parse("10/10/1990", formatter), "11122233344", "aaa@gmail.com"));
        listaPessoas.add(new Pessoa(COUNTER.incrementAndGet() /*2*/, "Charles Pereira", LocalDate.parse("08/05/1985", formatter), "11122233355", "bbb@gmail.com"));
        listaPessoas.add(new Pessoa(COUNTER.incrementAndGet() /*3*/, "Marina Oliveira", LocalDate.parse("30/03/1970", formatter), "11122233344", "ccc@gmail.com"));
        listaPessoas.add(new Pessoa(COUNTER.incrementAndGet() /*4*/, "Rafa", LocalDate.parse("01/07/1990", formatter), "11122233344", "ddd@gmail.com"));
        listaPessoas.add(new Pessoa(COUNTER.incrementAndGet() /*5*/, "Ana Clara", LocalDate.parse("10/07/1990", formatter), "11122233344", "eee@gmail.com"));
    }

    public List<Pessoa> list() { return listaPessoas; }

    public List<Pessoa> listByName(String nome) {
        return listaPessoas.stream()
                .filter(pessoa -> pessoa.getNome().toUpperCase().contains(nome.toUpperCase()))
                .collect(Collectors.toList());
    }

    public Pessoa create(Pessoa pessoa) {
        pessoa.setIdPessoa(COUNTER.incrementAndGet());
        listaPessoas.add(pessoa);
        return pessoa;
    }

    public Pessoa update(Pessoa pessoaRecuperada, Pessoa pessoaAtualizar) throws RegraDeNegocioException {
        pessoaRecuperada.setCpf(pessoaAtualizar.getCpf());
        pessoaRecuperada.setNome(pessoaAtualizar.getNome());;
        pessoaRecuperada.setDataNascimento(pessoaAtualizar.getDataNascimento());
        pessoaRecuperada.setEmail(pessoaAtualizar.getEmail());
        return pessoaRecuperada;
    }

    public void delete(Pessoa pessoa) throws RegraDeNegocioException {
        listaPessoas.remove(pessoa);
    }
}
