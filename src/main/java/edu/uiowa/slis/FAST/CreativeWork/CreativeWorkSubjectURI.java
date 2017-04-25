package edu.uiowa.slis.FAST.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkSubjectURI extends edu.uiowa.slis.FAST.TagLibSupport {
	static CreativeWorkSubjectURI currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkSubjectURI.class);

	// functional property

	public int doStartTag() throws JspException {
		try {
			CreativeWork theCreativeWork = (CreativeWork)findAncestorWithClass(this, CreativeWork.class);
			if (!theCreativeWork.commitNeeded) {
				pageContext.getOut().print(theCreativeWork.getSubjectURI());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for subjectURI tag ");
		}
		return SKIP_BODY;
	}

	public String getSubjectURI() throws JspTagException {
		try {
			CreativeWork theCreativeWork = (CreativeWork)findAncestorWithClass(this, CreativeWork.class);
			return theCreativeWork.getSubjectURI();
		} catch (Exception e) {
			log.error(" Can't find enclosing CreativeWork for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for subjectURI tag ");
		}
	}

	public void setSubjectURI(String subjectURI) throws JspTagException {
		try {
			CreativeWork theCreativeWork = (CreativeWork)findAncestorWithClass(this, CreativeWork.class);
			theCreativeWork.setSubjectURI(subjectURI);
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for subjectURI tag ");
		}
	}
}

