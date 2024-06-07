package school.sptech.cursos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.cursos.entity.Inscricao;
import school.sptech.cursos.repository.InscricaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InscricaoService {
    @Autowired
    private InscricaoRepository inscricaoRepository;

    public List<Inscricao> findAll() {
        return inscricaoRepository.findAll();
    }

    public Inscricao findById(Integer id) {
        Optional<Inscricao> inscricao = inscricaoRepository.findById(id);
        return inscricao.orElse(null);
    }

    public Inscricao save(Inscricao inscricao, Integer idCurso) {
        if (inscricao.getIdade() < 18){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O inscrito deve ter no mínimo 18 anos completos na data da inscrição");
        }
        return inscricaoRepository.save(inscricao);
    }

    public void delete(Integer id) {
        inscricaoRepository.deleteById(id);
    }

}
