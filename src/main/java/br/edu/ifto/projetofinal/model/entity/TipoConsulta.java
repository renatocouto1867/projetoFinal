package br.edu.ifto.projetofinal.model.entity;

public enum TipoConsulta {
    CONSULTA("Consulta"),
    RETORNO("Retorno"),
    ENCAIXE("Encaixe");

    private final String descricao;

    TipoConsulta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
