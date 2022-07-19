package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.EnderecoDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.entity.EnderecoEntity;
import br.com.vemser.pessoaapi.entity.PessoaEntity;
import br.com.vemser.pessoaapi.enums.TipoDeMensagem;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fnConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    public void sendEmailPessoa(PessoaDTO pessoaDTO, String tipo) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(pessoaDTO.getEmail());
            if (tipo.equals(TipoDeMensagem.CREATE.getTipo())) {
                mimeMessageHelper.setSubject("Seja bem vindo(a) ao nosso aplicativo!");
            } else if (tipo.equals(TipoDeMensagem.UPDATE.getTipo())) {
                mimeMessageHelper.setSubject("Seus dados foram atualizados com sucesso!");
            } else if (tipo.equals(TipoDeMensagem.DELETE.getTipo())) {
                mimeMessageHelper.setSubject("Seus dados foram apagados do nosso sistema!");
            }
            mimeMessageHelper.setText(getContentFromTemplatePessoa(pessoaDTO, tipo), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String getContentFromTemplatePessoa(PessoaDTO pessoaDTO, String tipo) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", pessoaDTO.getNome());
        dados.put("id", pessoaDTO.getIdPessoa());
        dados.put("email", from);

        Template template;
        if (tipo.equals(TipoDeMensagem.CREATE.getTipo())) {
            template = fnConfiguration.getTemplate("emailCreatePessoa-template.ftl");
        } else if (tipo.equals(TipoDeMensagem.UPDATE.getTipo())) {
            template = fnConfiguration.getTemplate("emailUpdatePessoa-template.ftl");
        } else {
            template = fnConfiguration.getTemplate("emailDeletePessoa-template.ftl");
        }
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public void sendEmailEndereco(PessoaDTO pessoaDTO, EnderecoDTO enderecoDTO, String tipo) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(pessoaDTO.getEmail());
            if (tipo.equals(TipoDeMensagem.CREATE.getTipo())) {
                mimeMessageHelper.setSubject("Seu novo endereço foi adicionado!");
            } else if (tipo.equals(TipoDeMensagem.UPDATE.getTipo())) {
                mimeMessageHelper.setSubject("Seus dados de endereço foram atualizados!");
            } else if (tipo.equals(TipoDeMensagem.DELETE.getTipo())) {
                mimeMessageHelper.setSubject("Seus dados de endereço foram apagados de nosso sistema!");
            }
            mimeMessageHelper.setText(getContentFromTemplateEndereco(pessoaDTO, enderecoDTO, tipo), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String getContentFromTemplateEndereco(PessoaDTO pessoaDTO, EnderecoDTO enderecoDTO, String tipo) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", pessoaDTO.getNome());
        dados.put("id", pessoaDTO.getIdPessoa());
        dados.put("idEndereco", enderecoDTO.getIdEndereco());
        dados.put("email", from);

        Template template;
        if (tipo.equals(TipoDeMensagem.CREATE.getTipo())) {
            template = fnConfiguration.getTemplate("emailCreateEndereco-template.ftl");
        } else if (tipo.equals(TipoDeMensagem.UPDATE.getTipo())) {
            template = fnConfiguration.getTemplate("emailUpdateEndereco-template.ftl");
        } else {
            template = fnConfiguration.getTemplate("emailDeleteEndereco-template.ftl");
        }
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
}
