package school.sptech.cursos.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import school.sptech.cursos.util.ErrorMessageHelper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Teste de integração CursoController")
class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("\uD83D\uDCBE Teste de salvar curso")
    public class CursoSaveTeste {

        @Test
        @DirtiesContext
        @DisplayName("1 - Deve salvar um curso com sucesso")
        void testSaveCurso() throws Exception {

            var json = """
                        {
                            "nome": "Curso de Java",
                            "descricao": "Curso de Java para iniciantes",
                            "cargaHoraria": 40
                        }
                    """;

            mockMvc.perform(post("/cursos")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.nome").value("Curso de Java"))
                    .andExpect(jsonPath("$.descricao").value("Curso de Java para iniciantes"))
                    .andExpect(jsonPath("$.cargaHoraria").value(40));
        }

        @Test
        @DirtiesContext
        @DisplayName("2 - Deve retornar erro ao tentar salvar um curso sem nome")
        void testSaveCursoSemNome() throws Exception {

            var erros = List.of("must not be blank");

            var json = """
                        {
                            "descricao": "Curso de Java para iniciantes",
                            "cargaHoraria": 40
                        }
                    """;

            MvcResult mvcResult = mockMvc.perform(post("/cursos")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            ErrorMessageHelper.assertContainsErrorMessages(mvcResult, erros);
        }

        @Test
        @DirtiesContext
        @DisplayName("3 - Deve retornar erro ao tentar salvar um curso sem descrição")
        void testSaveCursoSemDescricao() throws Exception {

            var erros = List.of("must not be blank");

            var json = """
                        {
                            "nome": "Curso de Java",
                            "cargaHoraria": 40
                        }
                    """;

            MvcResult mvcResult = mockMvc.perform(post("/cursos")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            ErrorMessageHelper.assertContainsErrorMessages(mvcResult, erros);
        }

        @Test
        @DirtiesContext
        @DisplayName("4 - Deve retornar erro ao tentar salvar um curso sem carga horária")
        void testSaveCursoSemCargaHoraria() throws Exception {

            var erros = List.of("must not be null");

            var json = """
                        {
                            "nome": "Curso de Java",
                            "descricao": "Curso de Java para iniciantes"
                        }
                    """;

            MvcResult mvcResult = mockMvc.perform(post("/cursos")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            ErrorMessageHelper.assertContainsErrorMessages(mvcResult, erros);
        }

        @Test
        @DirtiesContext
        @DisplayName("5 - Deve retornar erro ao tentar salvar um curso com carga horária menor que 1")
        void testSaveCursoCargaHorariaMenorQue1() throws Exception {

            var erros = List.of("must be greater than 0");

            var json = """
                        {
                            "nome": "Curso de Java",
                            "descricao": "Curso de Java para iniciantes",
                            "cargaHoraria": 0
                        }
                    """;

            MvcResult mvcResult = mockMvc.perform(post("/cursos")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            ErrorMessageHelper.assertContainsErrorMessages(mvcResult, erros);
        }

        @Test
        @DirtiesContext
        @DisplayName("6 - Deve retornar erro ao tentar salvar um curso com carga horária negativa")
        void testSaveCursoCargaHorariaNegativa() throws Exception {

            var erros = List.of("must be greater than 0");

            var json = """
                        {
                            "nome": "Curso de Java",
                            "descricao": "Curso de Java para iniciantes",
                            "cargaHoraria": -1
                        }
                    """;

            MvcResult mvcResult = mockMvc.perform(post("/cursos")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            ErrorMessageHelper.assertContainsErrorMessages(mvcResult, erros);
        }

        @Test
        @DirtiesContext
        @DisplayName("7 - Deve retornar erro ao tentar salvar um curso com nome já utilizado")
        void testSaveCursoNomeJaUtilizado() throws Exception {

            var json = """
                        {
                            "nome": "desenvolvimento DE software",
                            "descricao": "Curso sobre princípios de desenvolvimento de software, incluindo design, teste e manutenção de software.",
                            "cargaHoraria": 40
                        }
                    """;

            mockMvc.perform(post("/cursos")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("\uD83D\uDCCB Teste de Listar cursos")
    public class CursoFindAllTeste {

        @Test
        @DirtiesContext
        @DisplayName("1 - Deve listar todos os cursos")
        void testFindAllCursos() throws
                Exception {

            mockMvc.perform(get("/cursos"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$[0].id").exists())
                    .andExpect(jsonPath("$[0].nome").value("Desenvolvimento de Software"))
                    .andExpect(jsonPath("$[0].descricao").value("Curso sobre princípios de desenvolvimento de software, incluindo design, teste e manutenção de software."));
        }

        @Test
        @DirtiesContext
        @Sql(scripts = "classpath:sql/truncate-curso.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @DisplayName("2 - Deve retornar 204 quando não houver cursos")
        void testFindAllCursosVazio() throws Exception {
            mockMvc.perform(get("/cursos"))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("\uD83D\uDD0D Teste de buscar curso por id")
    public class CursoFindByIdTeste {

        @Test
        @DirtiesContext
        @DisplayName("1 - Deve buscar um curso por id")
        void testFindCursoById() throws Exception {
            mockMvc.perform(get("/cursos/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.nome").value("Desenvolvimento de Software"))
                    .andExpect(jsonPath("$.descricao").value("Curso sobre princípios de desenvolvimento de software, incluindo design, teste e manutenção de software."));
        }

        @Test
        @DirtiesContext
        @DisplayName("2 - Deve retornar 404 ao buscar um curso inexistente")
        void testFindCursoByIdInexistente() throws Exception {
            mockMvc.perform(get("/cursos/42"))
                    .andExpect(status().isNotFound());
        }
    }
}
