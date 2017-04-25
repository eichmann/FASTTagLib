package edu.uiowa.slis.FAST.GeoCoordinates;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class GeoCoordinatesGeoInverse extends edu.uiowa.slis.FAST.TagLibSupport {
	static GeoCoordinatesGeoInverse currentInstance = null;
	private static final Log log = LogFactory.getLog(GeoCoordinatesGeoInverse.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			GeoCoordinatesGeoInverseIterator theGeoCoordinatesGeoInverseIterator = (GeoCoordinatesGeoInverseIterator)findAncestorWithClass(this, GeoCoordinatesGeoInverseIterator.class);
			pageContext.getOut().print(theGeoCoordinatesGeoInverseIterator.getGeoInverse());
		} catch (Exception e) {
			log.error("Can't find enclosing GeoCoordinates for geo tag ", e);
			throw new JspTagException("Error: Can't find enclosing GeoCoordinates for geo tag ");
		}
		return SKIP_BODY;
	}
}

