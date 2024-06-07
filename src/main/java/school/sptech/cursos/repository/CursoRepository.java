package school.sptech.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.cursos.entity.Curso;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Integer>{
    Optional<Curso> findByNomeIgnoreCase(String nome);
}
