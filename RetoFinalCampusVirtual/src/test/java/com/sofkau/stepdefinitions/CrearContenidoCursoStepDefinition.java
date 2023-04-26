package com.sofkau.stepdefinitions;

import com.sofkau.models.contenido.ContenidoCurso;
import com.sofkau.models.rutasdeaprendizaje.ResponseCrearRutaExitosa;
import com.sofkau.setup.ApiSetUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;

import static com.sofkau.questions.rutasdeaprendizaje.ReturnCrearRutaExitosaResponse.returnCrearRutaExitosaResponse;
import static com.sofkau.tasks.DoPost.doPost;
import static com.sofkau.utils.CampusVirtual.CAMPUS_VIRTUAL_BASE_URL;
import static com.sofkau.utils.CampusVirtual.CONTENT_COURSE_RESOURCE;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.core.IsNull.notNullValue;

public class CrearContenidoCursoStepDefinition extends ApiSetUp {

    public static Logger LOGGER=Logger.getLogger(CrearContenidoCursoStepDefinition.class);
    private ContenidoCurso contenidoCurso = new ContenidoCurso();

    @Given("que el administrador se encuentra en la pagina de contenidos")
    public void queElAdministradorSeEncuentraEnLaPaginaDeContenidos() {
        try {
            setUp(CAMPUS_VIRTUAL_BASE_URL.getValue());
            LOGGER.info("Se inicio la automatizacion en la URL: " + CAMPUS_VIRTUAL_BASE_URL.getValue());
        } catch (Exception e) {
            LOGGER.error("Error al iniciar la automatizacion : Detalles: " + e.getMessage());
            actor.should(
                    seeThatResponse("El servidor no está disponible",
                            response -> response.statusCode(HttpStatus.SC_OK))
            );
        }
    }

    @When("el adminitrador ingresa la informacion del id del curso {string}, titulo {string}, descripcion {string}, tipo {int} y duracion {double}")
    public void elAdminitradorIngresaLaInformacionDelIdDelCursoTituloDescripcionTipoYDuracion(String courseID, String title, String description, Integer type, Double duration) {
        try {
            contenidoCurso.setCourseID(courseID);
            contenidoCurso.setTitle(title);
            contenidoCurso.setDescription(description);
            contenidoCurso.setType(type);
            contenidoCurso.setDuration(duration);

            actor.attemptsTo(
                    doPost()
                            .withTheResource(CONTENT_COURSE_RESOURCE.getValue())
                            .andTheRequestBody(contenidoCurso)
            );
            LOGGER.info(SerenityRest.lastResponse().body().asString());
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al enviar la solicitud POST: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("el administrador debera recibir un codigo de estado {int}")
    public void elAdministradorDeberaRecibirUnCodigoDeEstado(Integer statusCode) {
        try {
            if(statusCode.equals(500)){
                actor.should(
                        seeThatResponse("El codigo de respuesta es -->: " + HttpStatus.SC_INTERNAL_SERVER_ERROR,
                                response -> response.statusCode(statusCode))
                );
            } else if (statusCode.equals(400)){
                actor.should(
                        seeThatResponse("El codigo de respuesta es -->: " + HttpStatus.SC_BAD_REQUEST,
                                response -> response.statusCode(statusCode))
                );
            } else if (statusCode.equals(200)){
                actor.should(
                        seeThatResponse("El codigo de respuesta es -->: " + HttpStatus.SC_OK,
                                response -> response.statusCode(statusCode))
                );
            } else {
                LOGGER.warn("El código de estado ingresado no es válido.");
            }
        } catch (Exception e){
            LOGGER.warn(e.getMessage());
        }

    }

}
