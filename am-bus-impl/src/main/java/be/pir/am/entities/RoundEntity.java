package be.pir.am.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rounds")
public class RoundEntity extends BaseEntity {
    @Column(columnDefinition = "TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeScheduled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event", nullable = false)
    private EventEntity event;

    private String name;

    public Date getTimeScheduled() {
        return timeScheduled;
    }

    public void setTimeScheduled(Date timeScheduled) {
        this.timeScheduled = timeScheduled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

}
