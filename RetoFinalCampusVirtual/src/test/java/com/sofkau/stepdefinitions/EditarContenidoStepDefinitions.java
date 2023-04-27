package com.sofkau.stepdefinitions;

import com.sofkau.setup.ApiSetUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;

public class EditarContenidoStepDefinitions extends ApiSetUp {
    public static Logger LOGGER = Logger.getLogger(EditarContenidoStepDefinitions.class);
    @Given("que el administrador se encuentra en la pagina de actualizar contenido")
    public void queElAdministradorSeEncuentraEnLaPaginaDeActualizarContenido() {

    }

    @When("el adminitrador envia una peticion a al contenido {string} con el cuerpo que contiene {string}, {string}, {string}, {int} y {int}")
    public void elAdminitradorEnviaUnaPeticionAAlContenidoConElCuerpoQueContieneY(String string, String string2, String string3, String string4, Integer int1, Integer int2) {

    }

    @Then("el adminitrador debera recibir un codigo {int}")
    public void elAdminitradorDeberaRecibirUnCodigo(Integer int1) {

    }

    @Then("el adminitrador debera recibir un codigo de status {int}")
    public void elAdminitradorDeberaRecibirUnCodigoDeStatus(Integer int1) {

    }
}
