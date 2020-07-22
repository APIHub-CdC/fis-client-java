package io.fis.mx.client.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;


public class DatosGenerales {
  @SerializedName("nombres")
  private String nombres = null;
  @SerializedName("apellidoPaterno")
  private String apellidoPaterno = null;
  @SerializedName("apellidoMaterno")
  private String apellidoMaterno = null;
  @SerializedName("fechaNacimiento")
  private String fechaNacimiento = null;
  @SerializedName("RFC")
  private String RFC = null;
  @SerializedName("CURP")
  private String CURP = null;
  public DatosGenerales nombres(String nombres) {
    this.nombres = nombres;
    return this;
  }
   
  @ApiModelProperty(example = "JUAN", value = "Nombre de la persona")
  public String getNombres() {
    return nombres;
  }
  public void setNombres(String nombres) {
    this.nombres = nombres;
  }
  public DatosGenerales apellidoPaterno(String apellidoPaterno) {
    this.apellidoPaterno = apellidoPaterno;
    return this;
  }
   
  @ApiModelProperty(example = "LOPEZ", value = "Apelido paterno de la persona")
  public String getApellidoPaterno() {
    return apellidoPaterno;
  }
  public void setApellidoPaterno(String apellidoPaterno) {
    this.apellidoPaterno = apellidoPaterno;
  }
  public DatosGenerales apellidoMaterno(String apellidoMaterno) {
    this.apellidoMaterno = apellidoMaterno;
    return this;
  }
   
  @ApiModelProperty(example = "FUENTES", value = "Apelido materno de la persona")
  public String getApellidoMaterno() {
    return apellidoMaterno;
  }
  public void setApellidoMaterno(String apellidoMaterno) {
    this.apellidoMaterno = apellidoMaterno;
  }
  public DatosGenerales fechaNacimiento(String fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
    return this;
  }
   
  @ApiModelProperty(example = "23-03-1978", value = "Fecha de nacimiento de la persona, en el formato especificado (por defecto dd-MM-yyyy)")
  public String getFechaNacimiento() {
    return fechaNacimiento;
  }
  public void setFechaNacimiento(String fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }
  public DatosGenerales RFC(String RFC) {
    this.RFC = RFC;
    return this;
  }
   
  @ApiModelProperty(example = "RFCC870909765", value = "RFC de la persona")
  public String getRFC() {
    return RFC;
  }
  public void setRFC(String RFC) {
    this.RFC = RFC;
  }
  public DatosGenerales CURP(String CURP) {
    this.CURP = CURP;
    return this;
  }
   
  @ApiModelProperty(example = "RFCC870909MMCNRC02", value = "CURP de la persona")
  public String getCURP() {
    return CURP;
  }
  public void setCURP(String CURP) {
    this.CURP = CURP;
  }
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatosGenerales datosGenerales = (DatosGenerales) o;
    return Objects.equals(this.nombres, datosGenerales.nombres) &&
        Objects.equals(this.apellidoPaterno, datosGenerales.apellidoPaterno) &&
        Objects.equals(this.apellidoMaterno, datosGenerales.apellidoMaterno) &&
        Objects.equals(this.fechaNacimiento, datosGenerales.fechaNacimiento) &&
        Objects.equals(this.RFC, datosGenerales.RFC) &&
        Objects.equals(this.CURP, datosGenerales.CURP);
  }
  @Override
  public int hashCode() {
    return Objects.hash(nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, RFC, CURP);
  }
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatosGenerales {\n");
    
    sb.append("    nombres: ").append(toIndentedString(nombres)).append("\n");
    sb.append("    apellidoPaterno: ").append(toIndentedString(apellidoPaterno)).append("\n");
    sb.append("    apellidoMaterno: ").append(toIndentedString(apellidoMaterno)).append("\n");
    sb.append("    fechaNacimiento: ").append(toIndentedString(fechaNacimiento)).append("\n");
    sb.append("    RFC: ").append(toIndentedString(RFC)).append("\n");
    sb.append("    CURP: ").append(toIndentedString(CURP)).append("\n");
    sb.append("}");
    return sb.toString();
  }
  
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
