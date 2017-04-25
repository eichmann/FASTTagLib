package edu.uiowa.slis.FAST.Place;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PlaceSubjectURI extends edu.uiowa.slis.FAST.TagLibSupport {
	static PlaceSubjectURI currentInstance = null;
	private static final Log log = LogFactory.getLog(PlaceSubjectURI.class);

	// functional property

	public int doStartTag() throws JspException {
		try {
			Place thePlace = (Place)findAncestorWithClass(this, Place.class);
			if (!thePlace.commitNeeded) {
				pageContext.getOut().print(thePlace.getSubjectURI());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Place for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing Place for subjectURI tag ");
		}
		return SKIP_BODY;
	}

	public String getSubjectURI() throws JspTagException {
		try {
			Place thePlace = (Place)findAncestorWithClass(this, Place.class);
			return thePlace.getSubjectURI();
		} catch (Exception e) {
			log.error(" Can't find enclosing Place for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing Place for subjectURI tag ");
		}
	}

	public void setSubjectURI(String subjectURI) throws JspTagException {
		try {
			Place thePlace = (Place)findAncestorWithClass(this, Place.class);
			thePlace.setSubjectURI(subjectURI);
		} catch (Exception e) {
			log.error("Can't find enclosing Place for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing Place for subjectURI tag ");
		}
	}
}

