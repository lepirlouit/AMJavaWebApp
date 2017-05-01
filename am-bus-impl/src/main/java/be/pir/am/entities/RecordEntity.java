package be.pir.am.entities;

import be.pir.am.jpaconverter.BooleanToStringConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "records")
public class RecordEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "athlete")
    private AthleteEntity athlete;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventtype")
    private EventTypeEntity eventtype;
    private Date date;
    @Column(precision = 8, scale = 3)
    private BigDecimal value;
    @Convert(converter = BooleanToStringConverter.class)
    private Boolean seasonflag;
    @Convert(converter = BooleanToStringConverter.class)
    private Boolean alltimeflag;

    public AthleteEntity getAthlete() {
        return athlete;
    }

    public void setAthlete(AthleteEntity athlete) {
        this.athlete = athlete;
    }

    public EventTypeEntity getEventtype() {
        return eventtype;
    }

    public void setEventtype(EventTypeEntity eventtype) {
        this.eventtype = eventtype;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Boolean getSeasonflag() {
        return seasonflag;
    }

    public void setSeasonflag(Boolean seasonflag) {
        this.seasonflag = seasonflag;
    }

    public Boolean getAlltimeflag() {
        return alltimeflag;
    }

    public void setAlltimeflag(Boolean alltimeflag) {
        this.alltimeflag = alltimeflag;
    }

}
