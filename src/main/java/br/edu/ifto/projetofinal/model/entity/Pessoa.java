package br.edu.ifto.projetofinal.model.entity;

import br.edu.ifto.projetofinal.model.validation.groups.Insert;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements Serializable {
    @Size(groups = Insert.class, min = 1, max = 1, message = "O campo sexo não pode ser vazio")
    @Pattern(groups = Insert.class, regexp = "[mfMF]", message = "Sexo invalido")
    protected String sexoBiologico;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Insert.class)
    private Long idPessoa;
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 digitos", groups = Insert.class)
    private String cpf;
    @NotBlank(groups = Insert.class, message = "O campo nome não pode ser vazio")
    private String nome;
    @PastOrPresent(groups = Insert.class, message = "A data de nascimento não pode ser uma data no futuro")
    @NotNull(groups = Insert.class, message = "A data de nascimento não foi informada")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;
    private String telefone;

    public String getSexoBiologico() {
        return sexoBiologico;
    }

    public void setSexoBiologico(String sexoBiologico) {
        this.sexoBiologico = sexoBiologico;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getIdade() {
        LocalDate dataAtual = LocalDate.now();
        int idade = (int) dataNascimento.until(dataAtual, ChronoUnit.YEARS);
        return idade;

    }
}
