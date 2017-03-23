package be.pir.am.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "competitions")
public class CompetitionEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "federation")
    private FederationEntity federation;
    private String name;
    @Column(columnDefinition = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(columnDefinition = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competition")
    private Set<EventEntity> events;

    public CompetitionEntity() {
    }

    public CompetitionEntity(Integer id) {
        super(id);
    }

    public FederationEntity getFederation() {
        return federation;
    }

    public void setFederation(FederationEntity federation) {
        this.federation = federation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(Set<EventEntity> events) {
        this.events = events;
    }

}
