package com.sofkau.questions.rutasdeaprendizaje;

import com.sofkau.models.rutasdeaprendizaje.ResponseCrearRutaFallida;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ReturnCrearRutaFallidaResponse implements Question<ResponseCrearRutaFallida>{
    @Override
    public ResponseCrearRutaFallida answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(ResponseCrearRutaFallida.class);
    }

    public static ReturnCrearRutaFallidaResponse returnCrearRutaFallidaResponse(){
        return new ReturnCrearRutaFallidaResponse();
    }
}
