package edu.uiowa.slis.FAST.GeoCoordinates;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class GeoCoordinatesSubjectURI extends edu.uiowa.slis.FAST.TagLibSupport {
	static GeoCoordinatesSubjectURI currentInstance = null;
	private static final Log log = LogFactory.getLog(GeoCoordinatesSubjectURI.class);

	// functional property

	public int doStartTag() throws JspException {
		try {
			GeoCoordinates theGeoCoordinates = (GeoCoordinates)findAncestorWithClass(this, GeoCoordinates.class);
			if (!theGeoCoordinates.commitNeeded) {
				pageContext.getOut().print(theGeoCoordinates.getSubjectURI());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GeoCoordinates for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing GeoCoordinates for subjectURI tag ");
		}
		return SKIP_BODY;
	}

	public String getSubjectURI() throws JspTagException {
		try {
			GeoCoordinates theGeoCoordinates = (GeoCoordinates)findAncestorWithClass(this, GeoCoordinates.class);
			return theGeoCoordinates.getSubjectURI();
		} catch (Exception e) {
			log.error(" Can't find enclosing GeoCoordinates for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing GeoCoordinates for subjectURI tag ");
		}
	}

	public void setSubjectURI(String subjectURI) throws JspTagException {
		try {
			GeoCoordinates theGeoCoordinates = (GeoCoordinates)findAncestorWithClass(this, GeoCoordinates.class);
			theGeoCoordinates.setSubjectURI(subjectURI);
		} catch (Exception e) {
			log.error("Can't find enclosing GeoCoordinates for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing GeoCoordinates for subjectURI tag ");
		}
	}
}

