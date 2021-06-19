package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/product")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Product> products = productRepository.findAll();

            PrintWriter wr = resp.getWriter();

            wr.println("<table>");
            wr.println("<tr>");
            wr.println("<th>Id</th>");
            wr.println("<th>Name</th>");
            wr.println("<th>Cost</th>");
            wr.println("</tr>");

            for (Product pr : products) {
                wr.println("<tr>");
                wr.println("<td>" + pr.getId() + "</td>");
                wr.println("<td>" + pr.getName() + "</td>");
                wr.println("<td>" + pr.getCost() + "</td>");
                wr.println("</tr>");
            }
            resp.getWriter().println("</table>");
        } catch (NullPointerException e) {
            resp.getWriter().println("</tr>");
        }
    }
}
