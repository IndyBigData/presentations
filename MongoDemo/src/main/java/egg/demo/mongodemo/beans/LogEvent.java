package egg.demo.mongodemo.beans;

import java.util.Date;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="logEvent")
public class LogEvent {
    @Id
    protected String id;
    protected String source;
    protected String message;
    protected Date createdAt;

    public LogEvent() { 
        this.id = UUID.randomUUID().toString(); 
        this.createdAt = new Date();
    }
    
    public LogEvent(String id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getSource() { return source; }
    public void setSource(Class source) { this.source = source.getCanonicalName(); }
    public void setSource(String source) { this.source = source; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogEvent other = (LogEvent) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "LogEvent{" + "id=" + id + ", source=" + source + ", message=" + message + ", createdAt=" + createdAt + '}';
    }
}
