package school.sptech.cursos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.cursos.entity.Curso;
import school.sptech.cursos.repository.CursoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    public Curso findById(Integer id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        return curso.orElse(null);
    }

    public Curso save(Curso curso) {
        if (curso.getCargaHoraria() == null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "must not be null");
        }
        Optional<Curso> cursoExistente = cursoRepository.findByNomeIgnoreCase(curso.getNome());
        if (cursoExistente.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Nome do curso já existe");
        }
        return cursoRepository.save(curso);
    }
}
