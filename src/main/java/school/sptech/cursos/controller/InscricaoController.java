package school.sptech.cursos.controller;

import jakarta.persistence.Access;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.cursos.entity.Inscricao;
import school.sptech.cursos.service.InscricaoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inscricoes")
public class InscricaoController {
    @Autowired
    private InscricaoService inscricaoService;

    @GetMapping
    public ResponseEntity<List<Inscricao>> findAll() {
        List<Inscricao> inscricoes = inscricaoService.findAll();
        if (inscricoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else {
        return ResponseEntity.ok(inscricoes);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscricao> findById(@PathVariable Integer id) {
        Inscricao inscricao = inscricaoService.findById(id);
        if (inscricao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inscricao);
    }

    @PostMapping("/cursos/{idCurso}")
    public ResponseEntity<Inscricao> save(
            @PathVariable Integer idCurso,
            @RequestBody @Valid Inscricao inscricao
    ) {
        Inscricao inscricaoSalva = inscricaoService.save(inscricao, idCurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricaoSalva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        inscricaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
