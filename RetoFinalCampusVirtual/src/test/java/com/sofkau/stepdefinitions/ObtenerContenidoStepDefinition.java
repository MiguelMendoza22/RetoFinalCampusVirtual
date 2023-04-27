package com.sofkau.stepdefinitions;

import com.sofkau.setup.ApiSetUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;

import static com.sofkau.tasks.DoGet.doGet;
import static com.sofkau.utils.CampusVirtual.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObtenerContenidoStepDefinition extends ApiSetUp {
    public static Logger LOGGER = Logger.getLogger(ObtenerContenidoStepDefinition.class);
    @Given("que el usuario se encuentra en la pagina de obtener contenido")
    public void queElUsuarioSeEncuentraEnLaPaginaDeObtenerContenido() {
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

    @When("el usuario envia una peticion para obtener todos los contenidos")
    public void elUsuarioEnviaUnaPeticionParaObtenerTodosLosContenidos() {
        try {
            actor.attemptsTo(
                    doGet()
                            .withTheResource(CONTENT_COURSE_RESOURCE.getValue())
            );
            LOGGER.info(SerenityRest.lastResponse().body().asString());
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al enviar la solicitud GET: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("el usuario debera recibir un body de respuesta y un codigo de estado {int}")
    public void elUsuarioDeberaRecibirUnBodyDeRespuestaYUnCodigoDeEstado(Integer statusCode) {
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

    @When("el usuario envia una peticion para obtener el contenido del curso por {string}")
    public void elUsuarioEnviaUnaPeticionParaObtenerElContenidoDelCursoPor(String id) {
        try {
            actor.attemptsTo(
                    doGet()
                            .withTheResource(CONTENT_COURSE_RESOURCE.getValue() + id)
            );
            LOGGER.info(SerenityRest.lastResponse().body().asString());
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al enviar la solicitud GET: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("el usuario debera ver un body de respuesta con el {string} del contenido y un codigo de estado {int}")
    public void elUsuarioDeberaVerUnBodyDeRespuestaConElDelContenidoYUnCodigoDeEstado(String title, Integer statusCode) {

        try {
            actor.should(
                    seeThatResponse("El codigo de respuesta es: " + HttpStatus.SC_OK,
                            response -> response.statusCode(statusCode))
            );
            String responseBody = SerenityRest.lastResponse().body().asString();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject = (JSONObject) parser.parse(responseBody);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String titulo = (String) jsonObject.get("title");
            assertEquals(title, titulo);


        } catch (Exception e) {
            LOGGER.info("Error al realizar la comparacion");
            LOGGER.warn(e.getMessage());
            Assertions.fail();
        }
    }

}
