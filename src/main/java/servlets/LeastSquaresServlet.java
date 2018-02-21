package servlets;

import services.leastsquaresolver.SolverService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/least-squares")
public class LeastSquaresServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("/WEB-INF/views/least-squares.jsp").forward(httpServletRequest,httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String x = httpServletRequest.getParameter("x");
        String y = httpServletRequest.getParameter("y");
        String f = httpServletRequest.getParameter("f");
        String m = httpServletRequest.getParameter("method");
        String p = httpServletRequest.getParameter("pivot");

        try {
            SolverService solverService = SolverService.createSolverService(x, y, f, m, p);
            httpServletRequest.setAttribute("solution",solverService.getSolution());
            httpServletRequest.setAttribute("error",solverService.getError());
            httpServletRequest.setAttribute("finalFunction",solverService.getCompleteFunction());
            httpServletRequest.setAttribute("logResult", "Resuelto.");
        } catch (Exception e){
            System.out.println(e.getMessage());
            httpServletRequest.setAttribute("logResult", "Error: " + e.getMessage());
        }

        httpServletRequest.setAttribute("x",x);
        httpServletRequest.setAttribute("y",y);
        httpServletRequest.setAttribute("f",f);


        httpServletRequest.getRequestDispatcher("/WEB-INF/views/least-squares.jsp").forward(httpServletRequest,httpServletResponse);

    }


}
