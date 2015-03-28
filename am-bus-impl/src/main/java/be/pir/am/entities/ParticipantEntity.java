package be.pir.am.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "participants")
public class ParticipantEntity  extends BaseEntity {
	@Id
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitor")
	private CompetitorEntity competitor;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "participation")
	private ParticipationEntity participation;
	private int seqno;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CompetitorEntity getCompetitor() {
		return competitor;
	}
	public void setCompetitor(CompetitorEntity competitor) {
		this.competitor = competitor;
	}
	public ParticipationEntity getParticipation() {
		return participation;
	}
	public void setParticipation(ParticipationEntity participation) {
		this.participation = participation;
	}
	public int getSeqno() {
		return seqno;
	}
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

}
