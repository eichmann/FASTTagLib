package edu.uiowa.slis.FAST.Organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class OrganizationSubjectURI extends edu.uiowa.slis.FAST.TagLibSupport {
	static OrganizationSubjectURI currentInstance = null;
	private static final Log log = LogFactory.getLog(OrganizationSubjectURI.class);

	// functional property

	public int doStartTag() throws JspException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			if (!theOrganization.commitNeeded) {
				pageContext.getOut().print(theOrganization.getSubjectURI());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for subjectURI tag ");
		}
		return SKIP_BODY;
	}

	public String getSubjectURI() throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			return theOrganization.getSubjectURI();
		} catch (Exception e) {
			log.error(" Can't find enclosing Organization for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for subjectURI tag ");
		}
	}

	public void setSubjectURI(String subjectURI) throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			theOrganization.setSubjectURI(subjectURI);
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for subjectURI tag ");
		}
	}
}

