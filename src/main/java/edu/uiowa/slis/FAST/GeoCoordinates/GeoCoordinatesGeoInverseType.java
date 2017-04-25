package edu.uiowa.slis.FAST.GeoCoordinates;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class GeoCoordinatesGeoInverseType extends edu.uiowa.slis.FAST.TagLibSupport {
	static GeoCoordinatesGeoInverseType currentInstance = null;
	private static final Log log = LogFactory.getLog(GeoCoordinatesGeoInverseType.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			GeoCoordinatesGeoInverseIterator theGeoCoordinatesGeoInverseIterator = (GeoCoordinatesGeoInverseIterator)findAncestorWithClass(this, GeoCoordinatesGeoInverseIterator.class);
			pageContext.getOut().print(theGeoCoordinatesGeoInverseIterator.getType());
		} catch (Exception e) {
			log.error("Can't find enclosing GeoCoordinates for geo tag ", e);
			throw new JspTagException("Error: Can't find enclosing GeoCoordinates for geo tag ");
		}
		return SKIP_BODY;
	}
}

