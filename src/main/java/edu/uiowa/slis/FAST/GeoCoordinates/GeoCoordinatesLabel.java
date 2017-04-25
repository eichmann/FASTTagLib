package edu.uiowa.slis.FAST.GeoCoordinates;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class GeoCoordinatesLabel extends edu.uiowa.slis.FAST.TagLibSupport {
	static GeoCoordinatesLabel currentInstance = null;
	private static final Log log = LogFactory.getLog(GeoCoordinatesLabel.class);

	// functional property

	public int doStartTag() throws JspException {
		try {
			GeoCoordinates theGeoCoordinates = (GeoCoordinates)findAncestorWithClass(this, GeoCoordinates.class);
			if (!theGeoCoordinates.commitNeeded) {
				pageContext.getOut().print(theGeoCoordinates.getLabel());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GeoCoordinates for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing GeoCoordinates for label tag ");
		}
		return SKIP_BODY;
	}

	public String getLabel() throws JspTagException {
		try {
			GeoCoordinates theGeoCoordinates = (GeoCoordinates)findAncestorWithClass(this, GeoCoordinates.class);
			return theGeoCoordinates.getLabel();
		} catch (Exception e) {
			log.error(" Can't find enclosing GeoCoordinates for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing GeoCoordinates for label tag ");
		}
	}

	public void setLabel(String label) throws JspTagException {
		try {
			GeoCoordinates theGeoCoordinates = (GeoCoordinates)findAncestorWithClass(this, GeoCoordinates.class);
			theGeoCoordinates.setLabel(label);
		} catch (Exception e) {
			log.error("Can't find enclosing GeoCoordinates for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing GeoCoordinates for label tag ");
		}
	}
}

