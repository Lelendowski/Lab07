package it.polito.tdp.poweroutages.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class PowerOutages {
	int PowerOutagesId;
	int customers_affected;
	LocalDateTime date_event_began;
	LocalDateTime date_event_finished;
	
	public PowerOutages(int powerOutagesId, int customers_affected,
			LocalDateTime date_event_began, LocalDateTime date_event_finished) {
		PowerOutagesId = powerOutagesId;
		this.customers_affected = customers_affected;
		this.date_event_began = date_event_began;
		this.date_event_finished = date_event_finished;
	}

	public int getPowerOutagesId() {
		return PowerOutagesId;
	}

	public int getCustomers_affected() {
		return customers_affected;
	}

	public LocalDateTime getDate_event_began() {
		return date_event_began;
	}

	public LocalDateTime getDate_event_finished() {
		return date_event_finished;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date_event_began);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutages other = (PowerOutages) obj;
		return Objects.equals(date_event_began, other.date_event_began);
	}
	
	
}
