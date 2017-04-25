package edu.uiowa.slis.FAST.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class SPARQLObject extends edu.uiowa.slis.FAST.TagLibSupport {
	static SPARQLObject currentInstance = null;
	private static final Log log = LogFactory.getLog(SPARQLObject.class);

	public int doStartTag() throws JspException {
		try {
			SPARQL theSPARQL = (SPARQL)findAncestorWithClass(this, SPARQL.class);
			pageContext.getOut().print(theSPARQL.getObject());
		} catch (Exception e) {
			log.error("Can't find enclosing SPARQL for object tag ", e);
			throw new JspTagException("Error: Can't find enclosing SPARQL for object tag ");
		}
		return SKIP_BODY;
	}
}
