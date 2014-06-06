package org.vaadin.aceeditor.client;

import java.io.Serializable;

import org.vaadin.aceeditor.client.TransportDoc.TransportMarker;



/**
 * 
 * Ace marker.
 * 
 * The cssClass must be defined in some css file. Example:
 * 
 * .ace_marker-layer .mymarker1 {
 *		background: red;
 *  	border-bottom: 2px solid black;
 *  	position: absolute;
 *  }
 *
 */
public class AceMarker implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Ace Marker type.
	 *
	 */
	public enum Type {
		line,
		text
	}
	
	/**
	 * What to do with the marker when the text changes.
	 * 
	 * By default, Ace just keeps the marker in its place (DEFAULT).
	 * Alternatively, you can set the marker to ADJUST to text insertion/deletion.
	 * Or, you can set the marker to be REMOVE'd on text change.
	 *
	 */
	public enum OnTextChange {
		/**
		 * Keep the marker in its place.
		 */
		DEFAULT,
		/**
		 * Adjust the marker based on text insertion/deletion.
		 */
		ADJUST,
		/**
		 * Remove the marker when text changes.
		 */
		REMOVE
	}
	
	private final String markerId;
	private final AceRange range;
	private final OnTextChange onChange;
	private final String cssClass;
	private final Type type;
	private final boolean inFront;
	
	
	
	
	
	public AceMarker(String markerId, AceRange range, String cssClass, Type type, boolean inFront, OnTextChange onChange) {
		this.markerId = markerId;
		this.range = range.isBackwards() ? range.reversed() : range;
		this.cssClass = cssClass;
		this.type = type;
		this.inFront = inFront;
		this.onChange = onChange;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof AceMarker) {
			AceMarker om = (AceMarker)o;
			return markerId.equals(om.markerId) && range.equals(om.range) && onChange.equals(om.onChange) &&
					cssClass.equals(om.cssClass) && type.equals(om.type) && inFront==om.inFront;
		}
		return false;
	}
	@Override
	public int hashCode() {
		return range.hashCode(); // ?
	}

	@Override
	public String toString() {
		return "("+range+";"+cssClass+";"+type+";"+inFront+")";
	}



//	@Override
//	public int compareTo(AceMarker other) {
//		if (range.row1 < other.range.row1) {
//			return -1;
//		}
//		if (range.row1 > other.range.row1) {
//			return 1;
//		}
//		if (range.col1 < other.range.col1) {
//			return -1;
//		}
//		if (range.col1 > other.range.col1) {
//			return 1;
//		}
//		return 0;
//	}
	
	public TransportMarker asTransport() {
		TransportMarker tm = new TransportMarker();
		tm.markerId = markerId;
		tm.range = range.asTransport();
		tm.cssClass = cssClass;
		tm.type = type.toString();
		tm.inFront = inFront;
		tm.onChange = onChange.toString();
		return tm;
	}



	public String getMarkerId() {
		return markerId;
	}

	public static AceMarker fromTransport(TransportMarker im) {
		return new AceMarker(im.markerId, AceRange.fromTransport(im.range),
				im.cssClass, Type.valueOf(im.type), im.inFront, OnTextChange.valueOf(im.onChange));
	}

	public AceRange getRange() {
		return range;
	}
	
	public OnTextChange getOnChange() {
		return onChange;
	}

	public String getCssClass() {
		return cssClass;
	}

	public Type getType() {
		return type;
	}

	public boolean isInFront() {
		return inFront;
	}

	public AceMarker withNewPosition(AceRange newRange) {
		return new AceMarker(markerId, newRange, cssClass, type, inFront, onChange);
	}
}
