package com.sofkau.stepdefinitions;

import com.sofkau.setup.ApiSetUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;

import static com.sofkau.tasks.DoDelete.doDelete;
import static com.sofkau.utils.CampusVirtual.*;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class EliminarContenidoStepDefinition extends ApiSetUp {
    public static Logger LOGGER = Logger.getLogger(EliminarContenidoStepDefinition.class);

    @Given("que el administrador esta en la pagina para eliminar contenidos")
    public void queElAdministradorEstaEnLaPaginaParaEliminarContenidos() {
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

    @When("el adminitrador envia una peticion on el {string} del contenido")
    public void elAdminitradorEnviaUnaPeticionOnElDelContenido(String id) {
        try {
            actor.attemptsTo(
                    doDelete()
                            .withTheResource(CONTENT_COURSE_RESOURCE.getValue() + id)
            );
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al enviar la solicitud DELETE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("el adminitrador debera recibir un codigo  {int}")
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
}
