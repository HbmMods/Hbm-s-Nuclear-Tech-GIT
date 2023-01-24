package com.hbm.util;

import com.hbm.util.Tuple.Triplet;

public class NoteBuilder {
	
	private String beat = "";

	public static NoteBuilder start() {
		return new NoteBuilder();
	}
	
	public NoteBuilder add(Instrument instrument, Note note, Octave octave) {
		if(!beat.isEmpty())
			beat += "-";
		
		String result = instrument.ordinal() + ":" + note.ordinal() + ":" + octave.ordinal();
		
		beat += result;
		
		return this;
	}
	
	public String end() {
		return beat;
	}
	
	public static Triplet<Instrument, Note, Octave>[] translate(String beat) {
		String[] hits = beat.split("-");
		Triplet<Instrument, Note, Octave>[] notes = new Triplet[hits.length];
		
		try {
			for(int i = 0; i < hits.length; i++) {
				String[] components = hits[i].split(":");
				Instrument instrument = Instrument.values()[Integer.parseInt(components[0])];
				Note note = Note.values()[Integer.parseInt(components[1])];
				Octave octave = Octave.values()[Integer.parseInt(components[2])];
				
				notes[i] = new Triplet(instrument, note, octave);
			}
			
			return notes;
		} catch(Exception ex) {
			return new Triplet[0];
		}
	}
	
	public static enum Instrument {
		PIANO,
		BASSDRUM,
		SNARE,
		CLICKS,
		BASSGUITAR;
	}
	
	public static enum Note {
		F_SHARP,
		G,
		G_SHARP,
		A,
		A_SHARP,
		B,
		C,
		C_SHARP,
		D,
		D_SHARP,
		E,
		F;
	}
	
	public static enum Octave {
		LOW, MID, HIGH;
	}
}
