package br.edu.ifto.projetofinal.model.entity;

import br.edu.ifto.projetofinal.model.validation.groups.Insert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "pessoaId")
public class Paciente extends Pessoa implements Serializable {
    @NotBlank(groups = Insert.class, message = "O campo Convênio Médico não pode ser vazio, caso o paciente não possua plano, digite SUS")
    protected String convenioMedico;

    public String getConvenioMedico() {
        return convenioMedico;
    }

    public void setConvenioMedico(String convenioMedico) {
        this.convenioMedico = convenioMedico;
    }

    @OneToMany(mappedBy = "paciente")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Consulta> consultaList;
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
