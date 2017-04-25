package edu.uiowa.slis.FAST.CreativeWork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class CreativeWorkLabel extends edu.uiowa.slis.FAST.TagLibSupport {
	static CreativeWorkLabel currentInstance = null;
	private static final Log log = LogFactory.getLog(CreativeWorkLabel.class);

	// functional property

	public int doStartTag() throws JspException {
		try {
			CreativeWork theCreativeWork = (CreativeWork)findAncestorWithClass(this, CreativeWork.class);
			if (!theCreativeWork.commitNeeded) {
				pageContext.getOut().print(theCreativeWork.getLabel());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for label tag ");
		}
		return SKIP_BODY;
	}

	public String getLabel() throws JspTagException {
		try {
			CreativeWork theCreativeWork = (CreativeWork)findAncestorWithClass(this, CreativeWork.class);
			return theCreativeWork.getLabel();
		} catch (Exception e) {
			log.error(" Can't find enclosing CreativeWork for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for label tag ");
		}
	}

	public void setLabel(String label) throws JspTagException {
		try {
			CreativeWork theCreativeWork = (CreativeWork)findAncestorWithClass(this, CreativeWork.class);
			theCreativeWork.setLabel(label);
		} catch (Exception e) {
			log.error("Can't find enclosing CreativeWork for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing CreativeWork for label tag ");
		}
	}
}

