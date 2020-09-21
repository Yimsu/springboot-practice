package app.messages;

import java.util.Date;
import java.util.Objects;

public class Message {

  // id값이 Integer인 이유: 새로운 메시지객체에 대해 생성된 id가 없고, 값이 null이 되기 때문에
  private Integer id;
  private String text;
  private Date createdDate;

  // 하나의 매개변수 text를 받는 생성자를 추가
  public Message(String text) {
    this.text = text;
    this.createdDate = new Date();
  }

  public Message(int id, String text, Date createdDate) {
    this.id = id;
    this.text = text;
    this.createdDate = createdDate;
  }

  public Integer getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Message message = (Message) o;
    return Objects.equals(id, message.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}