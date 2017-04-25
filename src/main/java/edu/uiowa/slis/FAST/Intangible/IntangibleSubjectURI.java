package edu.uiowa.slis.FAST.Intangible;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class IntangibleSubjectURI extends edu.uiowa.slis.FAST.TagLibSupport {
	static IntangibleSubjectURI currentInstance = null;
	private static final Log log = LogFactory.getLog(IntangibleSubjectURI.class);

	// functional property

	public int doStartTag() throws JspException {
		try {
			Intangible theIntangible = (Intangible)findAncestorWithClass(this, Intangible.class);
			if (!theIntangible.commitNeeded) {
				pageContext.getOut().print(theIntangible.getSubjectURI());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Intangible for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing Intangible for subjectURI tag ");
		}
		return SKIP_BODY;
	}

	public String getSubjectURI() throws JspTagException {
		try {
			Intangible theIntangible = (Intangible)findAncestorWithClass(this, Intangible.class);
			return theIntangible.getSubjectURI();
		} catch (Exception e) {
			log.error(" Can't find enclosing Intangible for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing Intangible for subjectURI tag ");
		}
	}

	public void setSubjectURI(String subjectURI) throws JspTagException {
		try {
			Intangible theIntangible = (Intangible)findAncestorWithClass(this, Intangible.class);
			theIntangible.setSubjectURI(subjectURI);
		} catch (Exception e) {
			log.error("Can't find enclosing Intangible for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing Intangible for subjectURI tag ");
		}
	}
}

