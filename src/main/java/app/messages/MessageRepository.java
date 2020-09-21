package app.messages;

import ch.qos.logback.classic.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;


@Component
public class MessageRepository {

  private final static Log log = LogFactory.getLog(MessageRepository.class);

  private DataSource dataSource;

  public MessageRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public Message saveMessage(Message message) {
    // 데이터베이스랑 연결 DataSourceUtils
    Connection c = DataSourceUtils.getConnection(dataSource);
    Logger logger = null;
    try {
      String insertSql = "INSERT INTO messages (`id`, `text`, `created_date`) VALUE (null, ?, ?)";
      PreparedStatement ps = c.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
      // Prepare the parameters for the SQL
      ps.setString(1, message.getText());
      ps.setTimestamp(2, new Timestamp(message.getCreatedDate().getTime()));
      //데이터베이스 sql실행
      int rowsAffected = ps.executeUpdate();
      if (rowsAffected > 0) {
        // 새로 저장된 메시지 id로 가져오기
        ResultSet result = ps.getGeneratedKeys();
        if (result.next()) {
          int id = result.getInt(1);
          return new Message(id, message.getText(), message.getCreatedDate());
        } else {
          logger.error("Failed to retrieve id. No row in result set");
          return null;
        }
      } else {
        // Insert 실패
        return null;
      }
    } catch (SQLException ex) {
       logger.error("Failed to save message", ex);
      try {
        c.close();
      } catch (SQLException e) {
        logger.error("Failed to close connection", e);
      }
    } finally {
      DataSourceUtils.releaseConnection(c, dataSource);
    }
    return null;
  }
}