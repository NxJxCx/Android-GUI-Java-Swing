
package cellphoneguiapp.njc.Apps;

import cellphoneguiapp.njc.utils.DataValue;
import cellphoneguiapp.njc.utils.LocalDatabase;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 *
 * @author Neil Jason Canete
 */
public class MessageContentsModel extends javax.swing.AbstractListModel<MessageContent> {

  private final String database_name;
  private int sender_id;
  private int prevDBCount;
  private String state;
  private final java.util.List<java.util.Map<String, DataValue<String>>> selectedMessageData = new java.util.ArrayList<>();
  private final java.util.List<MessageContent> list = new java.util.ArrayList<>();
  
  public MessageContentsModel(String databasename) {
    database_name = databasename;
    state = "normal";
    Timer timer = new javax.swing.Timer(500, (java.awt.event.ActionEvent evt) -> {
      if (prevDBCount != LocalDatabase.DATABASES.get(database_name).getCount()) {
        if (state.equals("normal")) {
          refreshData();
        }
      }
    });
    timer.start();
  }
  
  private void refreshData() {
    state = "loading";
    selectedMessageData.clear();
    LocalDatabase db = LocalDatabase.DATABASES.get(database_name);
    prevDBCount = db.getCount();
    java.util.List<java.util.Map<String, DataValue<String>>> result = new java.util.ArrayList<>();
    java.util.List<java.util.Map<String, DataValue<?>>> filtered;
    filtered = db.selectAllRows().stream().filter(
      (java.util.Map<String, DataValue<?>> val) -> val.get("sender_id").getInt() == sender_id
    ).toList();
    java.util.ListIterator<java.util.Map<String, DataValue<?>>> filtered_iter = filtered.listIterator();
    while (filtered_iter.hasNext()) {
      java.util.Map<String, DataValue<?>> iter_val = filtered_iter.next();
      java.util.Map<String, DataValue<String>> result_val = new java.util.HashMap<>();
      for (String key: iter_val.keySet()) {
        if (!key.equals("_id") && !key.equals("sender_id")) {
          result_val.put(key, (DataValue<String>) iter_val.get(key));
        }
      }
      result.add(result_val);
    }
    setSelectedMessageData(result);
    state = "normal";
  }
  
  public String getDatabaseName() {
    return database_name;
  }
  
  private void setSelectedMessageData(java.util.List<java.util.Map<String, DataValue<String>>> data) {
    selectedMessageData.addAll(data);
    // transfer the messages to list
    removeAllElements();
    for (int i = 0; i < selectedMessageData.size(); i++) {
      java.util.Map<String, DataValue<String>> row = selectedMessageData.get(i);
      String s_msg = row.get("sender_msg").getString();
      String m_msg = row.get("my_msg").getString();
      String date_msg = row.get("date").getString();
      boolean isSender = !s_msg.isEmpty();
      addElement(new MessageContent(isSender ? s_msg : m_msg, date_msg, isSender));
    }
  }
  
  public Integer getSenderID() {
    return sender_id;
  }
  
  public void setSenderID(int _id) {
    sender_id = _id;
    refreshData();
  }
  
  public java.util.List<java.util.Map<String, DataValue<String>>> getSelectedMessages() {
    return selectedMessageData;
  }
  
  public void addAllElements(java.util.List<MessageContent> lists) {
    java.util.ListIterator<MessageContent> iter = lists.listIterator();
    while (iter.hasNext()) {
      addElement(iter.next());
    }
  }
  
  public void addElement(MessageContent element) {
    list.add(element);
    int indx = list.size() - 1;
    fireIntervalAdded(this, indx, indx);
  }
  
  public void removeElementAt(int index) {
    list.remove(index);
    fireIntervalRemoved(this, index, index);
  }
  
  public void removeAllElements() {
    while(getSize() > 0) {
      removeElementAt(0);
    }
  }

  @Override
  public int getSize() {
    return list.size();
  }

  @Override
  public MessageContent getElementAt(int index) {
    return list.get(index);
  }
}
