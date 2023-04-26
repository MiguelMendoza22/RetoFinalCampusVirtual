package com.sofkau.stepdefinitions;
import com.sofkau.models.rutasdeaprendizaje.ResponseCrearRutaFallida;
import com.sofkau.setup.ApiSetUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import static com.sofkau.questions.rutasdeaprendizaje.ReturnCrearRutaFallidaResponse.returnCrearRutaFallidaResponse;
import static com.sofkau.tasks.DoGet.doGet;
import static com.sofkau.utils.CampusVirtual.CAMPUS_VIRTUAL_BASE_URL;
import static com.sofkau.utils.CampusVirtual.GET_COURSES_BY_ID;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.core.IsNull.notNullValue;

public class ObtenerCursoStepDefinition extends ApiSetUp {

    public static Logger LOGGER = Logger.getLogger(ObtenerCursoStepDefinition.class);

    @Given("que el usuario se encuentra en la pagina de cursos")
    public void queElUsuarioSeEncuentraEnLaPaginaDeCursos() {
        try {
            setUp(CAMPUS_VIRTUAL_BASE_URL.getValue());
            LOGGER.info("Se inicio la automatizacion en la URL: " + CAMPUS_VIRTUAL_BASE_URL.getValue());

        } catch (Exception e) {
            LOGGER.error("Error al iniciar la automatizacion : Detalles: " + e.getMessage());
            actor.should(
                    seeThatResponse("El servidor no esta disponible",
                            response -> response.statusCode(HttpStatus.SC_OK))
            );
        }

    }

    @When("el usuario busca un curso por un id {string} en especifico")
    public void elUsuarioBuscaUnCursoPorUnIdEnEspecifico(String id) {
        try{
            actor.attemptsTo(
                    doGet().withTheResource(GET_COURSES_BY_ID.getValue() + id)
            );
            System.out.println(SerenityRest.lastResponse().body().asString());

        } catch (Exception e){
            LOGGER.info("ocurrio un error haciendo la peticion" + e);
        }

    }

    @Then("el usuario debera ver la informacion correspondiente al curso buscado")
    public void elUsuarioDeberaVerLaInformacionCorrespondienteAlCursoBuscado() {

        String responseBody = SerenityRest.lastResponse().getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        try {
            ResponseCrearRutaFallida responseCrearRutaFallida = returnCrearRutaFallidaResponse().answeredBy(actor);
            actor.should(
                    seeThatResponse("Se espera un código de respuesta: " + HttpStatus.SC_OK,
                            response -> response.statusCode(200)),
                    seeThat("El campo 'CourseID' esta presente", value -> jsonPath.getString("courseID"), notNullValue())
                            .orComplainWith(AssertionError.class, "El campo 'CourseID' no esta presente o es nulo"),
                    seeThat("El campo 'pathID' esta presente", value -> jsonPath.getString("pathID"), notNullValue())
                            .orComplainWith(AssertionError.class, "El campo 'pathID' no esta presente o es nulo"),
                    seeThat("El campo 'title' esta presente", value -> jsonPath.getString("title"), notNullValue())
                            .orComplainWith(AssertionError.class, "El campo 'title' no esta presente o es nulo"),
                    seeThat("El campo 'description' esta presente", value -> jsonPath.getString("description"), notNullValue())
                            .orComplainWith(AssertionError.class, "El campo 'description' no esta presente o es nulo"),
                    seeThat("El campo 'duration' esta presente", value -> jsonPath.getString("duration"), notNullValue())
                            .orComplainWith(AssertionError.class, "El campo 'duration' no esta presente o es nulo"),
                    seeThat("El campo 'stateCourse' esta presente", value -> jsonPath.getString("stateCourse"), notNullValue())
                            .orComplainWith(AssertionError.class, "El campo 'stateCourse' no esta presente o es nulo")
            );
            LOGGER.info("Se ha validado correctamente la asercion , SI cumple");

        } catch (Exception e) {
            LOGGER.info("Error al realizar la comparacion");
            LOGGER.warn(e.getMessage());
            Assertions.fail();
        }

    }

    @When("el usuario busca un curso con un {string} que no ha sido creado")
    public void elUsuarioBuscaUnCursoConUnQueNoHaSidoCreado(String id) {
        try{
            actor.attemptsTo(
                    doGet().withTheResource(GET_COURSES_BY_ID.getValue() + id)
            );
            System.out.println(SerenityRest.lastResponse().body().asString());

        } catch (Exception e){
            LOGGER.info("ocurrio un error haciendo la peticion" + e);
        }

    }

    @Then("el usuario debera obtener un codigo de estado {string} y un json de respuesta")
    public void elUsuarioDeberaObtenerUnCodigoDeEstadoYUnJsonDeRespuesta(String statusCode) {
        String responseBody = SerenityRest.lastResponse().getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        try {
            ResponseCrearRutaFallida responseCrearRutaFallida = returnCrearRutaFallidaResponse().answeredBy(actor);
            actor.should(
                    seeThatResponse("El codigo de respuesta es: " + HttpStatus.SC_INTERNAL_SERVER_ERROR,
                            response -> response.statusCode(Integer.parseInt(statusCode))),
                    seeThat("El campo 'Message' esta presente", value -> jsonPath.getString("Message"), notNullValue())
                            .orComplainWith(AssertionError.class, "El campo 'Message' no esta presente o es nulo")
            );

        } catch (Exception e) {
            LOGGER.info("Error al realizar la comparacion");
            LOGGER.warn(e.getMessage());
            Assertions.fail();
        }

    }
}
