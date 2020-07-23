package io.fis.mx.client.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;


public class ScoreNoHit {
  @SerializedName("valor")
  private BigDecimal valor = null;
  @SerializedName("codigo")
  private String codigo = null;
  public ScoreNoHit valor(BigDecimal valor) {
    this.valor = valor;
    return this;
  }
   
  @ApiModelProperty(example = "20.0", value = "Score obtenido")
  public BigDecimal getValor() {
    return valor;
  }
  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }
  public ScoreNoHit codigo(String codigo) {
    this.codigo = codigo;
    return this;
  }
   
  @ApiModelProperty(example = "E3", value = "Exclusion Codes: <table><thead><tr><th>Valor</th><th>Descripción</th></tr></thead><tbody><tr><td>E1</td><td>El cliente no poseehistorial crediticio reportado en alguna de las SICs mexicanas</td></tr><tr><td>E2</td><td>El cliente está reportado como fallecido</td></tr><tr><td>E3</td><td>El cliente no tiene la información mínima necesaria para el cálculo del score</td></tr><tr><td>E4</td><td>El código postal no es correcto</td></tr></tbody></table>")
  public String getCodigo() {
    return codigo;
  }
  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScoreNoHit scoreNoHit = (ScoreNoHit) o;
    return Objects.equals(this.valor, scoreNoHit.valor) &&
        Objects.equals(this.codigo, scoreNoHit.codigo);
  }
  @Override
  public int hashCode() {
    return Objects.hash(valor, codigo);
  }
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScoreNoHit {\n");
    
    sb.append("    valor: ").append(toIndentedString(valor)).append("\n");
    sb.append("    codigo: ").append(toIndentedString(codigo)).append("\n");
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
