
package cellphoneguiapp.njc.utils;

/**
 *
 * @author Neil Jason Canete
 */
public class DataValue<C> extends Object {
  private String strval;
  private Integer intval;
  private Double dblval;
  private Boolean boolval;
  private Object anyval;
  private Class<?> datatype;
  
  public static Class<?> getClassFromString(String data_type) {
    switch (data_type) {
      case "string" -> {
        return String.class;
      }
      case "int" -> {
        return Integer.class;
      }
      case "double" -> {
        return Double.class;
      }
      case "boolean" -> {
        return Boolean.class;
      }
      case "object" -> {
        return Object.class;
      }
    }
    return null;
  }
  
  public static DataValue<?> getDefaultEmptyValue(String data_type) {
    switch (data_type) {
      case "string" -> {
        return new DataValue<>("");
      }
      case "int" -> {
        return new DataValue<>(0);
      }
      case "double" -> {
        return new DataValue<>(0.0);
      }
      case "boolean" -> {
        return new DataValue<>(false);
      }
      case "object" -> {
        return new DataValue<>(null);
      }
    }
    return null;
  }

  public DataValue(C value) {
    datatype = value.getClass();
    if (datatype == String.class)
      strval = String.valueOf(value); 
    else if (datatype == Integer.class)
      intval = Integer.valueOf(value.toString());
    else if (datatype == Double.class)
      dblval = Double.valueOf(value.toString());
    else if (datatype == Boolean.class)
      boolval = Boolean.valueOf(value.toString());
    else
      anyval = value;
  }

  public Class<?> getDataType() {
    return datatype;
  }

  public String getDataTypeAsString() {
    return datatype == String.class ? "string" : (
            datatype == Integer.class ? "int" : (
              datatype == Double.class ? "double" : (
                datatype == Boolean.class ? "boolean" : "object")));
  }

  public String getString() {
    String res;
    try {
      res = datatype == String.class ? strval : (
              datatype == Integer.class ? intval.toString() : (
                datatype == Double.class ? dblval.toString() : (
                  datatype == Boolean.class ? boolval.toString() : anyval.toString())));
    } catch (Exception err) {
      return null;
    }
    return res;
  }

  public Integer getInt() {
    Integer res;
    try {
      res = datatype == String.class ? Integer.valueOf(strval) : (
              datatype == Integer.class ? intval: (
                datatype == Double.class ? dblval.intValue()  : (
                  datatype == Boolean.class ? (boolval ? 1 : 0) : Integer.valueOf(anyval.toString()))));
    } catch (NumberFormatException err) {
      return null;
    }
    return res;
  }

  public Double getDouble() {
    Double res;
    try {
      res = datatype == String.class ? Double.valueOf(strval) : (
              datatype == Integer.class ? intval.doubleValue() : (
                datatype == Double.class ? dblval.doubleValue(): (
                  datatype == Boolean.class ? (boolval ? Double.valueOf(1) : Double.valueOf(0)) : Double.valueOf(anyval.toString()))));
    } catch (NumberFormatException err) {
      return null;
    }
    return res;
  }

  public Boolean getBoolean() {
    Boolean res;
    try {
      res = datatype == String.class ? !strval.isBlank() : (
              datatype == Integer.class ? intval != 0 : (
                datatype == Double.class ? dblval > 0 || dblval < 0 : (
                  datatype == Boolean.class ? boolval : anyval != null)));
    } catch (NumberFormatException err) {
      return null;
    }
    return res;
  }

  public Object getObject() {
    return datatype == String.class ? strval : (
            datatype == Integer.class ? intval : (
              datatype == Double.class ? dblval : (
                datatype == Boolean.class ? boolval : anyval)));
  }

  public C get() {
    return datatype == String.class ? (C) strval : (
            datatype == Integer.class ? (C) intval : (
              datatype == Double.class ? (C) dblval : (
                datatype == Boolean.class ? (C) boolval : (C) anyval)));
  }
}
