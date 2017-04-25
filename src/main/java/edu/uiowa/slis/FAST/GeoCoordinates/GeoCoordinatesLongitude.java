package edu.uiowa.slis.FAST.GeoCoordinates;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class GeoCoordinatesLongitude extends edu.uiowa.slis.FAST.TagLibSupport {
	static GeoCoordinatesLongitude currentInstance = null;
	private static final Log log = LogFactory.getLog(GeoCoordinatesLongitude.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			GeoCoordinatesLongitudeIterator theGeoCoordinates = (GeoCoordinatesLongitudeIterator)findAncestorWithClass(this, GeoCoordinatesLongitudeIterator.class);
			pageContext.getOut().print(theGeoCoordinates.getLongitude());
		} catch (Exception e) {
			log.error("Can't find enclosing GeoCoordinates for longitude tag ", e);
			throw new JspTagException("Error: Can't find enclosing GeoCoordinates for longitude tag ");
		}
		return SKIP_BODY;
	}
}

