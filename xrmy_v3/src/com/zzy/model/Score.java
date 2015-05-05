package com.zzy.model;

import com.zzy.model.supermodel.ModelObject;

public class Score extends ModelObject<Long> {

	private Long eventId;
	private Long personAccId;
	private int score;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getPersonAccId() {
		return personAccId;
	}

	public void setPersonAccId(Long personAccId) {
		this.personAccId = personAccId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
