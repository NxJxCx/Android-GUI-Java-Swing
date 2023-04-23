
package cellphoneguiapp.njc.utils;


/**
 *
 * @author Neil Jason Canete
 */
public class LocalDatabase {
  public static final java.util.Map<String, LocalDatabase> DATABASES = new java.util.HashMap<>();
  private final java.util.Map<String, String> fieldnames = new java.util.HashMap<>();
  private final java.util.List<java.util.Map<String, DataValue<?>>> datarows = new java.util.ArrayList<>();
  private final java.util.List<Integer> ids = new java.util.ArrayList<>();
  private String databasename;
  
  public LocalDatabase(String database_name, java.util.Map<String, String> fields) throws LocalDatabaseError {
    databasename = database_name;
    for (String field_name: fields.keySet()) {
      if (field_name.equals("_id")) {
        continue;
      }
      String dtype = fields.get(field_name).toLowerCase();
      if (!(dtype.equals("string") || dtype.equals("int")
            || dtype.equals("double") || dtype.equals("boolean")
            || dtype.equals("object"))) {
        throw new LocalDatabaseError(databasename, "Datatype of '" + field_name + "' is unsupported. given '" + dtype + "'");
      } else {
        fields.put(field_name, dtype);
      }
    }
    fieldnames.putAll(fields);
    // register to global database
    DATABASES.put(database_name, getInstance());
  }
  
  public LocalDatabase getInstance() {
    return this;
  }
  
  public String getDatabaseName() {
    return databasename;
  }
  
  public String getFieldDataType(String fieldname) {
    return fieldnames.get(fieldname);
  }
  
  public boolean isDataType(String fieldname, String datatype) {
    return fieldnames.get(fieldname).equals(datatype);
  }
  
  public int getLastID() {
    int maxKey = -1;
    for (int _id : ids) {
      if (_id > maxKey) {
        maxKey = _id;
      }
    }
    return maxKey;
  }
  
  public int getNextLatestID() {
    return getLastID() + 1;
  }
  
  public int getCount() {
    return ids.size();
  }
  
  public void insertRow(int _id, java.util.Map<String, DataValue<?>> row_data) throws LocalDatabaseError {
    
    if (ids.contains(_id)) {
      throw new LocalDatabaseError(databasename, "ID " + _id + " already exists.");
    }
    
    java.util.Map<String, DataValue<?>> new_row = new java.util.HashMap<>();
    
    new_row.put("_id", new DataValue<>(_id));
    
    for (String key : fieldnames.keySet()) {
      if (row_data.containsKey(key)) {
        if (row_data.get(key).getDataTypeAsString().equals(fieldnames.get(key))) {
          new_row.put(key, row_data.get(key));
        } else {
          throw new LocalDatabaseError(databasename, "Data type of field '" + key + "' should be a type '" + fieldnames.get(key) + "'. given type: '" + row_data.get(key).getDataTypeAsString() + "'");
        }
      } else {
        // default value
        new_row.put(key, DataValue.getDefaultEmptyValue(fieldnames.get(key)));
      }
    }
    
    datarows.add(new_row);
    ids.add(_id);
  }
  
  public Integer insertRow(java.util.Map<String, DataValue<?>> row_data) throws LocalDatabaseError {
    
    int _id = getNextLatestID();
    
    java.util.Map<String, DataValue<?>> new_row = new java.util.HashMap<>();
    
    new_row.put("_id", new DataValue<>(_id));
    
    for (String key : fieldnames.keySet()) {
      if (row_data.containsKey(key)) {
        if (row_data.get(key).getDataTypeAsString().equals(fieldnames.get(key))) {
          new_row.put(key, row_data.get(key));
        } else {
          throw new LocalDatabaseError(databasename, "Data type of field '" + key + "' should be a type '" + fieldnames.get(key) + "'. given type: '" + row_data.get(key).getDataTypeAsString() + "'");
        }
      } else {
        // default value
        new_row.put(key, DataValue.getDefaultEmptyValue(fieldnames.get(key)));
      }
    }
    
    datarows.add(new_row);
    ids.add(_id);
    return _id;
  }
  
  public java.util.List<java.util.Map<String, DataValue<?>>> selectAllRows() {
    return datarows.stream().toList();
  }
  
  public java.util.Map<String, DataValue<?>> selectRowById(int _id) {
    if (ids.contains(_id)) {
      return datarows.stream().filter(row -> row.get("_id").getInt() == _id).toList().get(0);
    }
    return null;
  }

}
