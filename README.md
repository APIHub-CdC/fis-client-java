# fis-client-java

FIS Desarrollado por Círculo de Crédito, es un Score que evalúa a personas sin historial crediticio que califica el nivel de cumplimiento de pago de un individuo considerando variables demográficas y factores de riesgo de crédito territorial.

## Requisitos

1. Java >= 1.7
2. Maven >= 3.3
## Instalación

Para la instalación de las dependencias se deberá ejecutar el siguiente comando:
```shell
mvn install -Dmaven.test.skip=true
```
## Guía de inicio

### Paso 1. Generar llave y certificado

Antes de lanzar la prueba se deberá tener un keystore para la llave privada y el certificado asociado a ésta.
Para generar el keystore se ejecutan las instrucciones que se encuentran en ***src/main/security/createKeystore.sh*** o con los siguientes comandos:

**Opcional**: Si desea cifrar su contenedor, coloque una contraseña en una variable de ambiente.

```shell
export KEY_PASSWORD=your_super_secure_password
```

**Opcional**: Si desea cifrar su keystore, coloque una contraseña en una variable de ambiente.

```shell
export KEYSTORE_PASSWORD=your_super_secure_keystore_password
```

- Definición de los nombres de archivos y alias.

```shell
export PRIVATE_KEY_FILE=pri_key.pem
export CERTIFICATE_FILE=certificate.pem
export SUBJECT=/C=MX/ST=MX/L=MX/O=CDC/CN=CDC
export PKCS12_FILE=keypair.p12
export KEYSTORE_FILE=keystore.jks
export ALIAS=cdc
```
- Generar llave y certificado.

```shell
# Genera la llave privada.
openssl ecparam -name secp384r1 -genkey -out ${PRIVATE_KEY_FILE}

# Genera el certificado público
openssl req -new -x509 -days 365 \
  -key ${PRIVATE_KEY_FILE} \
  -out ${CERTIFICATE_FILE} \
  -subj "${SUBJECT}"

```

- Generar contenedor PKCS12 a partir de la llave privada y el certificado

```shell
# Genera el archivo pkcs12 a partir de la llave privada y el certificado.
# Deberá empaquetar su llave privada y el certificado.

openssl pkcs12 -name ${ALIAS} \
  -export -out ${PKCS12_FILE} \
  -inkey ${PRIVATE_KEY_FILE} \
  -in ${CERTIFICATE_FILE} \
  -password pass:${KEY_PASSWORD}

```

- Generar un keystore dummy y eliminar su contenido.

```sh
#Genera un Keystore con un par de llaves dummy.
keytool -genkey -alias dummy -keyalg RSA \
    -keysize 2048 -keystore ${KEYSTORE_FILE} \
    -dname "CN=dummy, OU=, O=, L=, S=, C=" \
    -storepass ${KEYSTORE_PASSWORD} -keypass ${KEY_PASSWORD}
#Elimina el par de llaves dummy.
keytool -delete -alias dummy \
    -keystore ${KEYSTORE_FILE} \
    -storepass ${KEYSTORE_PASSWORD}
```

- Importar el contenedor PKCS12 al keystore

```sh
#Importamos el contenedor PKCS12
keytool -importkeystore -srckeystore ${PKCS12_FILE} \
  -srcstoretype PKCS12 \
  -srcstorepass ${KEY_PASSWORD} \
  -destkeystore ${KEYSTORE_FILE} \
  -deststoretype JKS -storepass ${KEYSTORE_PASSWORD} \
  -alias ${ALIAS}
#Lista el contenido del Kesystore para verificar que
keytool -list -keystore ${KEYSTORE_FILE} \
  -storepass ${KEYSTORE_PASSWORD}
```

### Paso 2. Carga del certificado dentro del portal de desarrolladores

 1. Iniciar sesión.
 2. Dar clic en la sección "**Mis aplicaciones**".
 3. Seleccionar la aplicación.
 4. Ir a la pestaña de "**Certificados para @tuApp**".
    <p align="center">
      <img src="https://github.com/APIHub-CdC/imagenes-cdc/blob/master/applications.png">
    </p>
 5. Al abrirse la ventana, seleccionar el certificado previamente creado y dar clic en el botón "**Cargar**":
    <p align="center">
      <img src="https://github.com/APIHub-CdC/imagenes-cdc/blob/master/upload_cert.png">
    </p>

### Paso 3. Descarga del certificado de Círculo de Crédito dentro del portal de desarrolladores

 1. Iniciar sesión.
 2. Dar clic en la sección "**Mis aplicaciones**".
 3. Seleccionar la aplicación.
 4. Ir a la pestaña de "**Certificados para @tuApp**".
    <p align="center">
        <img src="https://github.com/APIHub-CdC/imagenes-cdc/blob/master/applications.png">
    </p>
 5. Al abrirse la ventana, dar clic al botón "**Descargar**":
    <p align="center">
        <img src="https://github.com/APIHub-CdC/imagenes-cdc/blob/master/download_cert.png">
    </p>

### Paso 4. Modificar archivo de configuraciones

Para hacer uso del certificado que se descargó y el keystore que se creó se deberán modificar las rutas que se encuentran en ***src/main/resources/config.properties***
```properties
keystore_file=your_path_for_your_keystore/keystore.jks
cdc_cert_file=your_path_for_certificate_of_cdc/cdc_cert.pem
keystore_password=your_super_secure_keystore_password
key_alias=cdc
key_password=your_super_secure_password
```
### Paso 5. Modificar URL y datos de petición

En el archivo ApiTest.java, que se encuentra en ***src/test/java/io/fis/mx/client/api/***. Se deberán modificar los datos de la petición y de la URL para el consumo de la API en setBasePath("the_url"), como se muestra en el siguiente fragmento de código con los datos correspondientes:

> **NOTA:** Los datos de la siguiente petición son solo representativos.

```java
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
    persona.setFechaNacimiento("27-06-1986");
    persona.setResidencia(CatalogoResidencia.NUMBER_1);
    persona.setEstadoCivil(CatalogoEstadoCivil.D);
    persona.setSexo(CatalogoSexo.M);
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

```
### Paso 6. Ejecutar la prueba unitaria

Teniendo los pasos anteriores ya solo falta ejecutar la prueba unitaria, con el siguiente comando:
```shell
mvn test -Dmaven.install.skip=true
```
