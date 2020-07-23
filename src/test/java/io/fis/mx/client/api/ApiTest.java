package io.fis.mx.client.api;

import io.fis.mx.client.ApiClient;
import io.fis.mx.client.ApiException;
import io.fis.mx.client.model.CatalogoEstadoCivil;
import io.fis.mx.client.model.CatalogoEstados;
import io.fis.mx.client.model.CatalogoResidencia;
import io.fis.mx.client.model.CatalogoSexo;
import io.fis.mx.client.model.CatalogoTipoAsentamiento;
import io.fis.mx.client.model.CatalogoTipoDomicilio;
import io.fis.mx.client.model.DomicilioPeticion;
import io.fis.mx.client.model.Errores;
import io.fis.mx.client.model.PersonaPeticion;
import io.fis.mx.client.model.Respuesta;
import io.fis.mx.interceptor.SignerInterceptor;
import okhttp3.OkHttpClient;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.junit.Before;

import java.util.concurrent.TimeUnit;

public class ApiTest {
        
    private Logger logger = LoggerFactory.getLogger(ApiTest.class.getName());

    private final FinancialInclusionScoreApi api = new FinancialInclusionScoreApi();
    private final SignerInterceptor interceptor = new SignerInterceptor();
    private ApiClient apiClient = null;

    private String xApiKey = "your_api_key";
    private String username = "your_username";
    private String password = "your_password";  
    
    @Before()
    public void setUp() {
        this.apiClient = api.getApiClient();
        this.apiClient.setBasePath("the_url");
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new SignerInterceptor())
                .build();
        apiClient.setHttpClient(okHttpClient);
    }

    @Test
    public void getScoreNoHitDGTest() throws ApiException {
        
        DomicilioPeticion domicilio = new DomicilioPeticion();
        domicilio.setDireccion(null);
        domicilio.setColoniaPoblacion(null);
        domicilio.setDelegacionMunicipio(null);
        domicilio.setCiudad(null);
        domicilio.setEstado(CatalogoEstados.CDMX);
        domicilio.setCP(null);
        domicilio.setFechaResidencia(null);
        domicilio.setNumeroTelefono(null);
        domicilio.setTipoDomicilio(CatalogoTipoDomicilio.C);
        domicilio.setTipoAsentamiento(CatalogoTipoAsentamiento._1);
        
        PersonaPeticion persona = new PersonaPeticion();
        persona.setPrimerNombre("NOMBRE");
        persona.setSegundoNombre(null);
        persona.setApellidoPaterno("PATERNO");
        persona.setApellidoMaterno("MATERNO");
        persona.setApellidoAdicional(null);
        persona.setFechaNacimiento("27-06-1986");
        persona.setRFC(null);
        persona.setCURP(null);
        persona.setNacionalidad(null);
        persona.setResidencia(CatalogoResidencia.NUMBER_1);
        persona.setEstadoCivil(CatalogoEstadoCivil.D);
        persona.setSexo(CatalogoSexo.M);
        persona.setClaveElectorIFE(null);
        persona.setNumeroDependientes(null);
        persona.setFechaDefuncion(null);
        persona.setDomicilio(domicilio);
        
        try {
            Respuesta response = api.getScoreNoHitDG(xApiKey, username, password, persona);
            logger.info(response.toString());
            Assert.assertTrue(response != null);
        } catch (ApiException e) {
            Errores errores = interceptor.getErrores();
            logger.info(errores.toString());
        }        
    }
    
}