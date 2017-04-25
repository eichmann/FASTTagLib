package edu.uiowa.slis.FAST.Intangible;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class IntangibleLabel extends edu.uiowa.slis.FAST.TagLibSupport {
	static IntangibleLabel currentInstance = null;
	private static final Log log = LogFactory.getLog(IntangibleLabel.class);

	// functional property

	public int doStartTag() throws JspException {
		try {
			Intangible theIntangible = (Intangible)findAncestorWithClass(this, Intangible.class);
			if (!theIntangible.commitNeeded) {
				pageContext.getOut().print(theIntangible.getLabel());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Intangible for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing Intangible for label tag ");
		}
		return SKIP_BODY;
	}

	public String getLabel() throws JspTagException {
		try {
			Intangible theIntangible = (Intangible)findAncestorWithClass(this, Intangible.class);
			return theIntangible.getLabel();
		} catch (Exception e) {
			log.error(" Can't find enclosing Intangible for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing Intangible for label tag ");
		}
	}

	public void setLabel(String label) throws JspTagException {
		try {
			Intangible theIntangible = (Intangible)findAncestorWithClass(this, Intangible.class);
			theIntangible.setLabel(label);
		} catch (Exception e) {
			log.error("Can't find enclosing Intangible for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing Intangible for label tag ");
		}
	}
}

