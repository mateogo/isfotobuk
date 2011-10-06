package ar.kennedy.is2011.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.kennedy.is2011.models.ImageProxyModel;
import ar.kennedy.is2011.models.ModelItf;
import ar.kennedy.is2011.session.Session;

/**
 * @author mlabarinas
 */
public class ImageProxyController extends AbstractController {

	private static final long serialVersionUID = -397532218428910357L;

	public void action(HttpServletRequest request, HttpServletResponse response, Session session) throws Exception {
		ModelItf model = new ImageProxyModel(request, response);
		
		((ImageProxyModel) model).getImage();
	}

	public boolean validateLogin() {
		return false;
	}
	
	protected void setHttpHeaders(HttpServletResponse response) {
		response.setHeader("Cache-Control", (new StringBuilder()).append("max-age=").append("2592000").append(", public").toString());
	}

}