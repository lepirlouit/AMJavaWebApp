package be.pir.am.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "licenses")
public class LicenseEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "athlete")
    private AthleteEntity athlete;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team")
    @NotFound(action = NotFoundAction.IGNORE)
    private TeamEntity team;
    private String licensenumber;
    private String bib;
    @Column(name = "ipcclass_track", length = 16)
    private String ipcclass_track;
    @Column(name = "ipcclass_field", length = 16)
    private String ipcclass_field;
    @Column(name = "id_for_federation", length = 64)
    private String id_for_federation;

    public LicenseEntity() {
    }

    public LicenseEntity(Integer id) {
        super(id);
    }

    public AthleteEntity getAthlete() {
        return athlete;
    }

    public void setAthlete(AthleteEntity athlete) {
        this.athlete = athlete;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public String getLicensenumber() {
        return licensenumber;
    }

    public void setLicensenumber(String licensenumber) {
        this.licensenumber = licensenumber;
    }

    public String getBib() {
        return bib;
    }

    public void setBib(String bib) {
        this.bib = bib;
    }

    public String getIpcclass_track() {
        return ipcclass_track;
    }

    public void setIpcclass_track(String ipcclass_track) {
        this.ipcclass_track = ipcclass_track;
    }

    public String getIpcclass_field() {
        return ipcclass_field;
    }

    public void setIpcclass_field(String ipcclass_field) {
        this.ipcclass_field = ipcclass_field;
    }

    public String getId_for_federation() {
        return id_for_federation;
    }

    public void setId_for_federation(String id_for_federation) {
        this.id_for_federation = id_for_federation;
    }

}
