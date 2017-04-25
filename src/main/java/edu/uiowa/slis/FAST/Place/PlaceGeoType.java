package edu.uiowa.slis.FAST.Place;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PlaceGeoType extends edu.uiowa.slis.FAST.TagLibSupport {
	static PlaceGeoType currentInstance = null;
	private static final Log log = LogFactory.getLog(PlaceGeoType.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			PlaceGeoIterator thePlaceGeoIterator = (PlaceGeoIterator)findAncestorWithClass(this, PlaceGeoIterator.class);
			pageContext.getOut().print(thePlaceGeoIterator.getType());
		} catch (Exception e) {
			log.error("Can't find enclosing Place for geo tag ", e);
			throw new JspTagException("Error: Can't find enclosing Place for geo tag ");
		}
		return SKIP_BODY;
	}
}

