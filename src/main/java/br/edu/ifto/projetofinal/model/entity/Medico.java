package br.edu.ifto.projetofinal.model.entity;

import br.edu.ifto.projetofinal.model.validation.groups.Insert;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "pessoaId")
public class Medico extends Pessoa implements Serializable {
    @NotBlank(groups = Insert.class, message = "o compo CRM é obrigatorio")
    private String crm;

    @OneToMany(mappedBy = "medico")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Consulta> consultaList;

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public List<Consulta> getConsultaList() {
        return consultaList;
    }

    public void setConsultaList(List<Consulta> consultaList) {
        this.consultaList = consultaList;
    }

    public double valorTotalConsulta() {
        Double valor = consultaList.stream().mapToDouble(Consulta::getValor).sum();
        if (valor.isNaN()) return 0.0;
        return valor;
    }
}
