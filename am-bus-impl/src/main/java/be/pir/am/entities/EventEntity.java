package be.pir.am.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class EventEntity extends BaseEntity {
	@Id
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competition", nullable = false)
	private CompetitionEntity competition;
	@Column(length = 64)
	private String name;
	@ManyToMany
	@JoinTable(name = "eventcategories", joinColumns = { @JoinColumn(name = "event", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "category", referencedColumnName = "ID") })
	private Set<CategoryEntity> categories;
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "event")
	private Set<RoundEntity> rounds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CompetitionEntity getCompetition() {
		return competition;
	}

	public void setCompetition(CompetitionEntity competition) {
		this.competition = competition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<CategoryEntity> getCategories() {
		return categories;
	}

	public void setCategories(Set<CategoryEntity> categories) {
		this.categories = categories;
	}

	public Set<RoundEntity> getRounds() {
		return rounds;
	}

	public void setRounds(Set<RoundEntity> rounds) {
		this.rounds = rounds;
	}

}
