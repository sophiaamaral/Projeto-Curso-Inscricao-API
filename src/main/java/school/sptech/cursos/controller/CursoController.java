package school.sptech.cursos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.cursos.entity.Curso;
import school.sptech.cursos.service.CursoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> findAll() {
        List<Curso> cursos = cursoService.findAll();
        if (cursos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
        return ResponseEntity.ok(cursos);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Integer id) {
        Curso curso = cursoService.findById(id);
        if (curso == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(curso);
    }

    @PostMapping
    public ResponseEntity<Curso> save(@RequestBody @Valid Curso curso) {
        Curso cursoSalvo = cursoService.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoSalvo);
    }
}
