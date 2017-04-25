package edu.uiowa.slis.FAST.Organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class OrganizationLabel extends edu.uiowa.slis.FAST.TagLibSupport {
	static OrganizationLabel currentInstance = null;
	private static final Log log = LogFactory.getLog(OrganizationLabel.class);

	// functional property

	public int doStartTag() throws JspException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			if (!theOrganization.commitNeeded) {
				pageContext.getOut().print(theOrganization.getLabel());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for label tag ");
		}
		return SKIP_BODY;
	}

	public String getLabel() throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			return theOrganization.getLabel();
		} catch (Exception e) {
			log.error(" Can't find enclosing Organization for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for label tag ");
		}
	}

	public void setLabel(String label) throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			theOrganization.setLabel(label);
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for label tag ");
		}
	}
}

