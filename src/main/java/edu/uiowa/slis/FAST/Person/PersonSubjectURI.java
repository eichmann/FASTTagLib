package edu.uiowa.slis.FAST.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonSubjectURI extends edu.uiowa.slis.FAST.TagLibSupport {
	static PersonSubjectURI currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonSubjectURI.class);

	// functional property

	public int doStartTag() throws JspException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (!thePerson.commitNeeded) {
				pageContext.getOut().print(thePerson.getSubjectURI());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Person for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for subjectURI tag ");
		}
		return SKIP_BODY;
	}

	public String getSubjectURI() throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			return thePerson.getSubjectURI();
		} catch (Exception e) {
			log.error(" Can't find enclosing Person for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for subjectURI tag ");
		}
	}

	public void setSubjectURI(String subjectURI) throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			thePerson.setSubjectURI(subjectURI);
		} catch (Exception e) {
			log.error("Can't find enclosing Person for subjectURI tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for subjectURI tag ");
		}
	}
}

