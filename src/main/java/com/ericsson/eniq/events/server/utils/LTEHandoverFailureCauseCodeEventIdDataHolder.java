package com.ericsson.eniq.events.server.utils;

/**
 * @author eeiyspn
 * 
 */
public class LTEHandoverFailureCauseCodeEventIdDataHolder {

	private String causeCode;

	private String eventId;

	public LTEHandoverFailureCauseCodeEventIdDataHolder(final String eventId,
			final String causeCode) {
		this.causeCode = causeCode;
		this.eventId = eventId;
	}

	public String getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(final String causeCode) {
		this.causeCode = causeCode;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(final String eventId) {
		this.eventId = eventId;
	}

}
