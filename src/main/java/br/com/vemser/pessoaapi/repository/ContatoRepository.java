package br.com.vemser.pessoaapi.repository;

import br.com.vemser.pessoaapi.entity.Contato;
import br.com.vemser.pessoaapi.entity.TipoDeContato;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ContatoRepository {

    private static List<Contato> listaContatos = new ArrayList<>();

    private AtomicInteger COUNTER = new AtomicInteger();

    public ContatoRepository() {
        listaContatos.add(new Contato(COUNTER.incrementAndGet(), 1, TipoDeContato.COMERCIAL, "8533334444", "Telegram"));
        listaContatos.add(new Contato(COUNTER.incrementAndGet(), 2, TipoDeContato.RESIDENCIAL, "85911112222", "Whatsapp"));
        listaContatos.add(new Contato(COUNTER.incrementAndGet(), 3, TipoDeContato.RESIDENCIAL, "85933334444", "Telegram"));
        listaContatos.add(new Contato(COUNTER.incrementAndGet(), 4, TipoDeContato.COMERCIAL, "8533335555", "Whatsapp"));
        listaContatos.add(new Contato(COUNTER.incrementAndGet(), 5, TipoDeContato.COMERCIAL, "8533336666", "Telegram"));
    }

    public List<Contato> list() {
        return listaContatos;
    }

    public Contato create(Contato contato) {
        contato.setIdContato(COUNTER.incrementAndGet());
        listaContatos.add(contato);
        return contato;
    }

    public Contato update(Contato contatoRecuperado, Contato contatoAtualizar) throws RegraDeNegocioException {
        getDadosContato(contatoRecuperado, contatoAtualizar);
        return contatoRecuperado;
    }

    private void getDadosContato(Contato contatoRecuperado, Contato contatoAtualizar) {
        contatoRecuperado.setTipo(contatoAtualizar.getTipo());
        contatoRecuperado.setIdPessoa(contatoAtualizar.getIdPessoa());
        contatoRecuperado.setNumero(contatoAtualizar.getNumero());
        contatoRecuperado.setDescricao(contatoAtualizar.getDescricao());
    }

    public void delete(Contato contato) throws RegraDeNegocioException {
        listaContatos.remove(contato);
    }
}
