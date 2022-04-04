package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Jogos;
import persistence.GenericDao;
import persistence.JogosDao;

@WebServlet("/rodadas")
public class RodadasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RodadasServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Jogos j = new Jogos();

		String codigoTimeA = request.getParameter("codigoTimeA");
		String codigoTimeB = request.getParameter("codigoTimeB");
		String golsTimeA = request.getParameter("golsTimeA");
		String golsTimeB = request.getParameter("golsTimeB");
		String data = request.getParameter("data");
		String botao = request.getParameter("botao");

		GenericDao gDao = new GenericDao();
		JogosDao jDao = new JogosDao(gDao);
		List<Jogos> listaJogos = new ArrayList<Jogos>();
		String saida = "";
		String erro = "";

		try {
			if (botao.equals("Jogos")) {
				listaJogos = jDao.geraRodada();
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();

		} finally {
			RequestDispatcher rd = request.getRequestDispatcher("jogos.jsp");
			request.setAttribute("listaJogos", listaJogos);
			//request.setAttribute("jogos", j);
			request.setAttribute("codigoTimeA", codigoTimeA);
			request.setAttribute("codigoTimeB", codigoTimeB);
			request.setAttribute("golsTimeA", golsTimeA);
			request.setAttribute("golsTimeB", golsTimeB);
			request.setAttribute("data", data);
			request.setAttribute("erro", erro);
			request.setAttribute("saida", saida);
			rd.forward(request, response);
		}
	}
}
