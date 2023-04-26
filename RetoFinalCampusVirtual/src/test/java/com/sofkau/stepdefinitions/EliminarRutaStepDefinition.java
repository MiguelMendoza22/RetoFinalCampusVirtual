package com.sofkau.stepdefinitions;

import com.sofkau.setup.ApiSetUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;

import static com.sofkau.tasks.DoDelete.doDelete;
import static com.sofkau.utils.CampusVirtual.CAMPUS_VIRTUAL_BASE_URL;
import static com.sofkau.utils.CampusVirtual.DELETE_PATH_ID;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class EliminarRutaStepDefinition extends ApiSetUp {
    public static Logger LOGGER = Logger.getLogger(EliminarRutaStepDefinition.class);
    @Given("que el administrador esta en la pagina para eliminar rutas de aprendizaje")
    public void queElAdministradorEstaEnLaPaginaParaEliminarRutasDeAprendizaje() {
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

    @When("el adminitrador envia una peticion al ruta con el {string}")
    public void elAdminitradorEnviaUnaPeticionAlRutaConEl(String id) {
        try {
            actor.attemptsTo(
                    doDelete()
                            .withTheResource(DELETE_PATH_ID.getValue() + id)
            );
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al enviar la solicitud DELETE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("el adminitrador debera recibir un codigo de estado {int}")
    public void elAdminitradorDeberaRecibirUnCodigoDeEstado(Integer statusCode) {
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
