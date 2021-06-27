package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    private static final Pattern pathParam = Pattern.compile("\\/(\\d*)$");
    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter wr = resp.getWriter();

        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {
            List<Product> products = productRepository.findAll();

            wr.println("<table>");
            wr.println("<tr>");
            wr.println("<th>Id</th>");
            wr.println("<th>Name</th>");
            wr.println("<th>Cost</th>");
            wr.println("</tr>");

            for (Product pr : products) {
                wr.println("<tr>");
                wr.println("<td><a href='" + getServletContext().getContextPath() + "/product/" + pr.getId() + "'>" + pr.getId() + "</a></td>");
                wr.println("<td>" + pr.getName() + "</a>" + "</td>");
                wr.println("<td>" + pr.getCost() + "</td>");

                wr.println("</tr>");
            }
            resp.getWriter().println("</table>");
        } else {
            Matcher matcher = pathParam.matcher(req.getPathInfo());
            if (matcher.matches()) {
                long id;
                try {
                    id = Long.parseLong(matcher.group(1));
                } catch (NumberFormatException ex) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                Product product = productRepository.findById(id);
                resp.getWriter().println("<p>Product info</p>");
                resp.getWriter().println("<p>Id: " + product.getId() + "</p>");
                resp.getWriter().println("<p>Name: " + product.getName() + "</p>");
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

