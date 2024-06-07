package school.sptech.cursos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
@Entity
@Getter
@Setter
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @NotBlank
    private String nome;
    @NotNull
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @Past
    @NotNull
    private LocalDate dataNascimento;
    @NotNull
    private LocalDate dataInscricao;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Transient
    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    @PrePersist
    @PreUpdate
    public void preUpdate() {
        checkAge();
    }

    public void checkAge() {
        if (Period.between(dataNascimento, dataInscricao).getYears() < 18) {
            throw new IllegalArgumentException("O inscrito deve ter no mínimo 18 anos completos na data da inscrição");
        }
    }


}
