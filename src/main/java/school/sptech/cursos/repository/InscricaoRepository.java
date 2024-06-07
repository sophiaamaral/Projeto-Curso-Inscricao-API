package school.sptech.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.cursos.entity.Inscricao;

public interface InscricaoRepository extends JpaRepository<Inscricao, Integer> {

}
