package school.sptech.cursos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @NotBlank
    @Column(unique = true)
    private String nome;
    @NotNull
    @NotBlank
    private String descricao;
    @Positive
    @NotNull(message = "must not be null")
    private Integer cargaHoraria;
}
