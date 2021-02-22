package it.polito.tdp.PremierLeague.model;

public class Partita implements Comparable<Partita> {
	
	@Override
	public String toString() {
		return "Partita [partitaId=" + partitaId + ", teamHomeId=" + teamHomeId + ", teamAwayId=" + teamAwayId
				+ ", teamHomeName=" + teamHomeName + ", teamAwayName=" + teamAwayName + "]";
	}

	private Integer partitaId;
	private Integer teamHomeId;
	private Integer teamAwayId;
	private String teamHomeName;
	private String teamAwayName;
	
	public Partita(Integer partitaId, Integer teamHomeId, Integer teamAwayId, String teamHomeName,
			String teamAwayName) {
		super();
		this.partitaId = partitaId;
		this.teamHomeId = teamHomeId;
		this.teamAwayId = teamAwayId;
		this.teamHomeName = teamHomeName;
		this.teamAwayName = teamAwayName;
	}

	public Integer getPartitaId() {
		return partitaId;
	}

	public void setPartitaId(Integer partitaId) {
		this.partitaId = partitaId;
	}

	public Integer getTeamHomeId() {
		return teamHomeId;
	}

	public void setTeamHomeId(Integer teamHomeId) {
		this.teamHomeId = teamHomeId;
	}

	public Integer getTeamAwayId() {
		return teamAwayId;
	}

	public void setTeamAwayId(Integer teamAwayId) {
		this.teamAwayId = teamAwayId;
	}

	public String getTeamHomeName() {
		return teamHomeName;
	}

	public void setTeamHomeName(String teamHomeName) {
		this.teamHomeName = teamHomeName;
	}

	public String getTeamAwayName() {
		return teamAwayName;
	}

	public void setTeamAwayName(String teamAwayName) {
		this.teamAwayName = teamAwayName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partitaId == null) ? 0 : partitaId.hashCode());
		result = prime * result + ((teamAwayId == null) ? 0 : teamAwayId.hashCode());
		result = prime * result + ((teamAwayName == null) ? 0 : teamAwayName.hashCode());
		result = prime * result + ((teamHomeId == null) ? 0 : teamHomeId.hashCode());
		result = prime * result + ((teamHomeName == null) ? 0 : teamHomeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partita other = (Partita) obj;
		if (partitaId == null) {
			if (other.partitaId != null)
				return false;
		} else if (!partitaId.equals(other.partitaId))
			return false;
		if (teamAwayId == null) {
			if (other.teamAwayId != null)
				return false;
		} else if (!teamAwayId.equals(other.teamAwayId))
			return false;
		if (teamAwayName == null) {
			if (other.teamAwayName != null)
				return false;
		} else if (!teamAwayName.equals(other.teamAwayName))
			return false;
		if (teamHomeId == null) {
			if (other.teamHomeId != null)
				return false;
		} else if (!teamHomeId.equals(other.teamHomeId))
			return false;
		if (teamHomeName == null) {
			if (other.teamHomeName != null)
				return false;
		} else if (!teamHomeName.equals(other.teamHomeName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Partita other) {
		return this.partitaId.compareTo(other.getPartitaId());
	}
	
}