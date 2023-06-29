package it.polito.tdp.nyc.model;

import java.util.Objects;

public class Event implements Comparable<Event>{

	public enum EventType{
		SHARE,
		STOP
	}
	
	private EventType type;
	private int time;
	private NTA nta;
	private int duration;
	
	
	public Event(EventType type, int time, NTA nta, int duration) {
		super();
		this.type = type;
		this.time = time;
		this.nta = nta;
		this.duration = duration;
	}


	public EventType getType() {
		return type;
	}


	public int getTime() {
		return time;
	}


	public NTA getNta() {
		return nta;
	}


	public int getDuration() {
		return duration;
	}


	@Override
	public int hashCode() {
		return Objects.hash(duration, nta, time, type);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return duration == other.duration && Objects.equals(nta, other.nta) && time == other.time && type == other.type;
	}


	@Override
	public int compareTo(Event o) {
		return this.time-o.time;
	}


	@Override
	public String toString() {
		return "Event [type=" + type + ", time=" + time + ", nta=" + nta + ", duration=" + duration + "]";
	}
	
	
	
	
	
}
