package com.sofkau.stepdefinitions;

import com.sofkau.models.contenido.ContenidoCurso;
import com.sofkau.models.contenido.CuerpoEditarContenido;
import com.sofkau.setup.ApiSetUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;

import static com.sofkau.tasks.DoPut.doPut;
import static com.sofkau.utils.CampusVirtual.CAMPUS_VIRTUAL_BASE_URL;
import static com.sofkau.utils.CampusVirtual.EDIT_CONTENT_RESOURCE;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class EditarContenidoStepDefinitions extends ApiSetUp {
    public static Logger LOGGER = Logger.getLogger(EditarContenidoStepDefinitions.class);
    CuerpoEditarContenido cuerpoEditarContenido = new CuerpoEditarContenido();
    @Given("que el administrador se encuentra en la pagina de actualizar contenido")
    public void queElAdministradorSeEncuentraEnLaPaginaDeActualizarContenido() {
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

    @When("el adminitrador envia una peticion a al contenido {string} con el cuerpo que contiene {string}, {string}, {string}, {int} y {int}")
    public void elAdminitradorEnviaUnaPeticionAAlContenidoConElCuerpoQueContieneY(String id, String courseID, String title, String description, Integer type, Integer duration) {
        cuerpoEditarContenido.setCourseID(courseID);
        cuerpoEditarContenido.setTitle(title);
        cuerpoEditarContenido.setDescription(description);
        cuerpoEditarContenido.setType(type);
        cuerpoEditarContenido.setDuration(duration);
        cuerpoEditarContenido.setStateContent(1);
        actor.attemptsTo(
                doPut().withResource(EDIT_CONTENT_RESOURCE.getValue() + id)
                        .andTheRequestBody(cuerpoEditarContenido)
        );
    }

    @Then("el adminitrador debera recibir un codigo {int}")
    public void elAdminitradorDeberaRecibirUnCodigo(Integer statusCode) {
        try {
            actor.should(
                    seeThatResponse("El codigo de respuesta es: " + HttpStatus.SC_OK,
                            response -> response.statusCode(statusCode))
            );
        } catch (Exception e) {
            LOGGER.info("Error al realizar la comparacion");
            LOGGER.warn(e.getMessage());
            Assertions.fail();
        }
    }

    @Then("el adminitrador debera recibir un codigo de status {int}")
    public void elAdminitradorDeberaRecibirUnCodigoDeStatus(Integer statusCode) {
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
            } else {
                LOGGER.warn("El código de estado ingresado no es válido.");
            }
        } catch (Exception e) {
            LOGGER.info("Error al realizar la comparacion");
            LOGGER.warn(e.getMessage());
            Assertions.fail();
        }
    }
}
