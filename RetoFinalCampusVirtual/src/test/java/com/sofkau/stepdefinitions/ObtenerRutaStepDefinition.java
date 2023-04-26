package com.sofkau.stepdefinitions;

import com.sofkau.models.rutasdeaprendizaje.ResponseCrearRutaExitosa;
import com.sofkau.setup.ApiSetUp;
import com.sofkau.tasks.DoGet;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;

import static com.sofkau.questions.rutasdeaprendizaje.ReturnCrearRutaExitosaResponse.returnCrearRutaExitosaResponse;
import static com.sofkau.tasks.DoGet.doGet;
import static com.sofkau.utils.CampusVirtual.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;


public class ObtenerRutaStepDefinition extends ApiSetUp {

    public static Logger LOGGER = Logger.getLogger(ObtenerRutaStepDefinition.class);
    @Given("Que el usuario se encuentra en la pagina de obtener rutas")
    public void queElUsuarioSeEncuentraEnLaPaginaDeObtenerRutas() {

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

    @When("el adminitrador envia una peticion para obtener todos los rutas")
    public void elAdminitradorEnviaUnaPeticionParaObtenerTodosLosRutas() {
        try {
            actor.attemptsTo(
                    doGet()
                            .withTheResource(GET_PATH_RESOURCE.getValue())
            );
            LOGGER.info(SerenityRest.lastResponse().body().asString());
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al enviar la solicitud GET: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("el administrador debera recibir un body de respuesta y un codigo de estado {int}")
    public void elAdministradorDeberaRecibirUnBodyDeRespuestaYUnCodigoDeEstado(Integer statusCode) {
        String responseBody = SerenityRest.lastResponse().getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        try {
            actor.should(
                    seeThatResponse("El codigo de respuesta es: " + HttpStatus.SC_OK,
                            response -> response.statusCode(statusCode)),
                    seeThat("El campo 'title' esta presente", value -> jsonPath.getString("title"), notNullValue())
                            .orComplainWith(AssertionError.class, "El campo 'title' no esta presente o es nulo")
            );

        } catch (Exception e) {
            LOGGER.info("Error al realizar la comparacion");
            LOGGER.warn(e.getMessage());
            Assertions.fail();
        }
    }

    @When("el adminitrador envia una peticion con el {string} de la ruta de aprendizaje")
    public void elAdminitradorEnviaUnaPeticionConElDeLaRutaDeAprendizaje(String id) {
        try {
            actor.attemptsTo(
                    doGet()
                            .withTheResource(GET_PATH_ID_RESOURCE.getValue() + id)
            );
            LOGGER.info(SerenityRest.lastResponse().body().asString());
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al enviar la solicitud GET: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("el administrador debera recibir un body con el titulo {string} y un codigo de estado {int}")
    public void elAdministradorDeberaRecibirUnBodyConElTituloYUnCodigoDeEstado(String title, Integer statusCode) {
        try {
            ResponseCrearRutaExitosa responseCrearRutaExitosa = returnCrearRutaExitosaResponse().answeredBy(actor);
            actor.should(
                    seeThatResponse("El codigo de respuesta es: " + HttpStatus.SC_OK,
                            response -> response.statusCode(statusCode)),
                    seeThat("Retorna titulo",
                            act -> responseCrearRutaExitosa.getTitle(), equalTo(title))
            );
        } catch (Exception e) {
            LOGGER.info("Error al realizar la comparacion");
            LOGGER.warn(e.getMessage());
            Assertions.fail();
        }
    }

}
