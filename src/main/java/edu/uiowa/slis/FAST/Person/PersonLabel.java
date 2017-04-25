package edu.uiowa.slis.FAST.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonLabel extends edu.uiowa.slis.FAST.TagLibSupport {
	static PersonLabel currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonLabel.class);

	// functional property

	public int doStartTag() throws JspException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (!thePerson.commitNeeded) {
				pageContext.getOut().print(thePerson.getLabel());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Person for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for label tag ");
		}
		return SKIP_BODY;
	}

	public String getLabel() throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			return thePerson.getLabel();
		} catch (Exception e) {
			log.error(" Can't find enclosing Person for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for label tag ");
		}
	}

	public void setLabel(String label) throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			thePerson.setLabel(label);
		} catch (Exception e) {
			log.error("Can't find enclosing Person for label tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for label tag ");
		}
	}
}

