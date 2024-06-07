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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Teste de integração InscricaoController")
class InscricaoControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("\uD83D\uDCBE Teste de salvar inscrição")
    public class InscricaoSaveTeste {

        @Test
        @DirtiesContext
        @DisplayName("1 - Deve salvar uma inscrição com sucesso")
        void testSaveInscricao() throws Exception {

            var json = """
                        {
                            "nome": "Carlos Andrade",
                            "email": "carlos@gmail.com",
                            "dataNascimento": "1988-02-11",
                            "dataInscricao": "2024-05-27"
                        }
                    """;

            mockMvc.perform(post("/inscricoes/cursos/1")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.nome").value("Carlos Andrade"))
                    .andExpect(jsonPath("$.email").value("carlos@gmail.com"))
                    .andExpect(jsonPath("$.dataNascimento").value("1988-02-11"))
                    .andExpect(jsonPath("$.dataInscricao").value("2024-05-27"));
        }

        @Test
        @DirtiesContext
        @DisplayName("2 - Deve salvar uma inscrição com sucesso caso tenha exatamente 18 anos")
        void testSaveInscricaoCom18Anos() throws Exception {

            var dataInscricao = java.time.LocalDate.now();

            var json = """
                        {
                            "nome": "Jamal",
                            "email": "malik@bol.com",
                            "dataNascimento": "%s",
                            "dataInscricao": "%s"
                        }
                    """.formatted(dataInscricao.minusYears(18), dataInscricao);
        }

        @Test
        @DirtiesContext
        @DisplayName("3 - Deve retornar erro ao tentar salvar uma inscrição sem nome")
        void testSaveInscricaoSemNome() throws Exception {

            var erros = List.of("must not be blank");

            var json = """
                        {
                            "email": "jamal@yahoo.com",
                            "dataNascimento": "1990-01-01",
                            "dataInscricao": "2024-05-27"
                        }
                    """;

            MvcResult mvcResult = mockMvc.perform(post("/inscricoes/cursos/1")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            ErrorMessageHelper.assertContainsErrorMessages(mvcResult, erros);
        }

        @Test
        @DirtiesContext
        @DisplayName("4 - Deve retornar erro ao tentar salvar uma inscrição sem email")
        void testSaveInscricaoSemEmail() throws Exception {

            var erros = List.of("must not be blank");

            var json = """
                        {
                            "nome": "Jamal",
                            "dataNascimento": "1990-01-01",
                            "dataInscricao": "2024-05-27"
                        }
                    """;

            MvcResult mvcResult = mockMvc.perform(post("/inscricoes/cursos/1")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            ErrorMessageHelper.assertContainsErrorMessages(mvcResult, erros);
        }

        @Test
        @DirtiesContext
        @DisplayName("5 - Deve retornar erro ao tentar salvar uma inscrição sem data de nascimento")
        void testSaveInscricaoSemDataNascimento() throws Exception {

            var erros = List.of("must not be null");

            var json = """
                        {
                            "nome": "Jamal",
                            "email": "jamal@bol.com",
                            "dataInscricao": "2024-05-27"
                        }
                    """;

            MvcResult mvcResult = mockMvc.perform(post("/inscricoes/cursos/1")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            ErrorMessageHelper.assertContainsErrorMessages(mvcResult, erros);
        }

        @Test
        @DirtiesContext
        @DisplayName("6 - Deve retornar erro ao tentar salvar uma inscrição sem data de inscrição")
        void testSaveInscricaoSemDataInscricao() throws Exception {

            var erros = List.of("must not be null");

            var json = """
                        {
                            "nome": "Jamal",
                            "email": "jamal@bol.com",
                            "dataNascimento": "1990-05-27"
                        }
                    """;

            MvcResult mvcResult = mockMvc.perform(post("/inscricoes/cursos/1")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            ErrorMessageHelper.assertContainsErrorMessages(mvcResult, erros);
        }

        @Test
        @DirtiesContext
        @DisplayName("7 - Deve retornar erro ao tentar salvar uma inscrição com email inválido")
        void testSaveInscricaoEmailInvalido() throws Exception {

            var erros = List.of("must be a well-formed email address");

            var json = """
                        {
                            "nome": "Jamal",
                            "email": "issonaoeumemail",
                            "dataNascimento": "1990-05-27",
                            "dataInscricao": "2024-05-27"
                        }
                    """;

            MvcResult mvcResult = mockMvc.perform(post("/inscricoes/cursos/1")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            ErrorMessageHelper.assertContainsErrorMessages(mvcResult, erros);
        }

        @Test
        @DirtiesContext
        @DisplayName("8 - Deve retornar erro ao tentar salvar uma inscrição com data de nascimento atual")
        void testSaveInscricaoDataNascimentoFutura() throws Exception {

            var erros = List.of("must be a past date");

            var json = """
                        {
                            "nome": "Jamal",
                            "email": "malik@yahoo.com",
                            "dataNascimento": "%s",
                            "dataInscricao": "2024-05-27"
                            }
                    """.formatted(java.time.LocalDate.now());

            MvcResult mvcResult = mockMvc.perform(post("/inscricoes/cursos/1")
                            .contentType("application/json")
                            .content(json))
                    .andReturn();

            ErrorMessageHelper.assertContainsErrorMessages(mvcResult, erros);
        }

        @Test
        @DirtiesContext
        @DisplayName("9 - Deve retornar erro ao tentar salvar uma inscrição caso tenha 17 anos ou menos")
        void testSaveInscricaoMenorDeIdade() throws Exception {

            var dataInscricao = java.time.LocalDate.now();

            var json = """
                        {
                            "nome": "Paulo",
                            "email": "paulo@ptm.com",
                            "dataNascimento": "%s",
                            "dataInscricao": "%s"
                        }
                    """.formatted(dataInscricao.minusYears(17), dataInscricao);
            mockMvc.perform(post("/inscricoes/cursos/1")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DirtiesContext
        @DisplayName("10 - Deve retornar erro ao tentar salvar com valores vazios")
        void testSaveInscricaoComValoresVazios() throws Exception {

            var json = """
                        {
                            "nome": "",
                            "email": "",
                            "dataNascimento": "",
                            "dataInscricao": ""
                        }
                    """;

            mockMvc.perform(post("/inscricoes/cursos/1")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DirtiesContext
        @DisplayName("11 - Deve retornar erro ao tentar salvar uma inscrição com valores nulos")
        void testSaveInscricaoComValoresNulos() throws Exception {

            var json = """
                        {
                            "nome": null,
                            "email": null,
                            "dataNascimento": null,
                            "dataInscricao": null
                        }
                    """;

            mockMvc.perform(post("/inscricoes/cursos/1")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DirtiesContext
        @DisplayName("12 - Deve retornar erro ao tentar salvar uma inscrição com valores em branco")
        void testSaveInscricaoComValoresEmBranco() throws Exception {

            var json = """
                        {
                            "nome": " ",
                            "email": " ",
                            "dataNascimento": " ",
                            "dataInscricao": " "
                        }
                    """;

            mockMvc.perform(post("/inscricoes/cursos/1")
                            .contentType("application/json")
                            .content(json))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("\uD83D\uDCCB Listar inscrições")
    public class InscricaoListTeste {

        @Test
        @DirtiesContext
        @DisplayName("1 - Deve listar todas as inscrições")
        void testListInscricoes() throws Exception {

            mockMvc.perform(get("/inscricoes"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(5)))
                    .andExpect(jsonPath("$[0].id").exists())
                    .andExpect(jsonPath("$[0].nome").value("João Silva"))
                    .andExpect(jsonPath("$[0].email").value("joao.silva@email.com"))
                    .andExpect(jsonPath("$[0].dataNascimento").value("1990-04-23"))
                    .andExpect(jsonPath("$[0].dataInscricao").value("2024-05-25"))
                    .andExpect(jsonPath("$[0].curso.id").value(1))
                    .andExpect(jsonPath("$[0].curso.nome").value("Desenvolvimento de Software"))
                    .andExpect(jsonPath("$[0].curso.descricao").value("Curso sobre princípios de desenvolvimento de software, incluindo design, teste e manutenção de software."))
                    .andExpect(jsonPath("$[0].curso.cargaHoraria").value(200));
        }

        @Test
        @DirtiesContext
        @Sql(scripts = "classpath:sql/truncate-inscricao.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @DisplayName("2 - Deve retornar 204 quando não houver inscrições")
        void testListInscricoesVazio() throws Exception {
            mockMvc.perform(get("/inscricoes"))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("\uD83D\uDD0D Buscar inscrição por id")
    public class InscricaoFindByIdTeste {

        @Test
        @DirtiesContext
        @DisplayName("1 - Deve buscar uma inscrição por id")
        void testFindInscricaoById() throws Exception {
            mockMvc.perform(get("/inscricoes/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.nome").value("João Silva"))
                    .andExpect(jsonPath("$.email").value("joao.silva@email.com"))
                    .andExpect(jsonPath("$.dataNascimento").value("1990-04-23"))
                    .andExpect(jsonPath("$.dataInscricao").value("2024-05-25"))
                    .andExpect(jsonPath("$.curso.id").value(1))
                    .andExpect(jsonPath("$.curso.nome").value("Desenvolvimento de Software"))
                    .andExpect(jsonPath("$.curso.descricao").value("Curso sobre princípios de desenvolvimento de software, incluindo design, teste e manutenção de software."))
                    .andExpect(jsonPath("$.curso.cargaHoraria").value(200));
        }

        @Test
        @DirtiesContext
        @DisplayName("2 - Deve retornar 404 quando a inscrição não for encontrada")
        void testFindInscricaoByIdNotFound() throws Exception {
            mockMvc.perform(get("/inscricoes/42"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("\uD83D\uDDD1\uFE0F Deletar inscrição")
    public class InscricaoDeleteTeste {

        @Test
        @DirtiesContext
        @DisplayName("1 - Deve deletar uma inscrição por id")
        void testDeleteInscricaoById() throws Exception {
            mockMvc.perform(delete("/inscricoes/1"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DirtiesContext
        @DisplayName("2 - Deve retornar 404 quando a inscrição não for encontrada")
        void testDeleteInscricaoByIdNotFound() throws Exception {
            mockMvc.perform(get("/inscricoes/42"))
                    .andExpect(status().isNotFound());
        }
    }
}