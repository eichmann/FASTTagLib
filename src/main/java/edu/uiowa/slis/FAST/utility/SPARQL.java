package edu.uiowa.slis.FAST.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

@SuppressWarnings("serial")
public class SPARQL extends edu.uiowa.slis.FAST.TagLibSupport {
	static SPARQL currentInstance = null;
	private static final Log log = LogFactory.getLog(SPARQL.class);

	String subject = null;
	String predicate = null;
	String object = null;
	ResultSet rs = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			rs = getResultSet(prefix+
				" SELECT ?p ?o where { "+
				"<" + subject + "> ?p ?o . "+
				"} ORDER BY ?p ?o");
			if(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				predicate = sol.get("?p").toString();
				object = sol.get("?o").toString();
				return EVAL_BODY_INCLUDE;
			}
		} catch (Exception e) {
			log.error("Exception raised in VideoIterator doStartTag", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in VideoIterator doStartTag");
		}

		return SKIP_BODY;
	}

	public int doAfterBody() throws JspException {
		try {
			if(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				predicate = sol.get("?p").toString();
				object = sol.get("?o").toString();
				return EVAL_BODY_AGAIN;
			}
		} catch (Exception e) {
			log.error("Exception raised in SPARQL doAfterBody", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in SPARQL doAfterBody");
		}

		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			// do processing
		} catch (Exception e) {
			log.error("Exception raised in SPARQL doEndTag", e);
			throw new JspTagException("Exception raised in SPARQL doEndTag");
		} finally {
			clearServiceState();
			freeConnection();
		}

		return super.doEndTag();
	}

	private void clearServiceState() {
		subject = null;
		predicate = null;
		object = null;
	}

	public  void setSubject(String theSubject) {
		subject = theSubject;
	}

	public  String getSubject() {
		return subject;
	}

	public  void setPredicate(String thePredicate) {
		predicate = thePredicate;
	}

	public  String getPredicate() {
		return predicate;
	}

	public  void setObject(String theObject) {
		object = theObject;
	}

	public  String getObject() {
		return object;
	}
}
