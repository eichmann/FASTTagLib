package edu.uiowa.slis.FAST.GeoCoordinates;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class GeoCoordinatesLatitude extends edu.uiowa.slis.FAST.TagLibSupport {
	static GeoCoordinatesLatitude currentInstance = null;
	private static final Log log = LogFactory.getLog(GeoCoordinatesLatitude.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			GeoCoordinatesLatitudeIterator theGeoCoordinates = (GeoCoordinatesLatitudeIterator)findAncestorWithClass(this, GeoCoordinatesLatitudeIterator.class);
			pageContext.getOut().print(theGeoCoordinates.getLatitude());
		} catch (Exception e) {
			log.error("Can't find enclosing GeoCoordinates for latitude tag ", e);
			throw new JspTagException("Error: Can't find enclosing GeoCoordinates for latitude tag ");
		}
		return SKIP_BODY;
	}
}

