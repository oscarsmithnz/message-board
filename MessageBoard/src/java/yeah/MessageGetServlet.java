/*
 * This class is a servlet for getting messages from the database to show on the web page. 
 * Forwards the request to either AllThreads.jsp or MessageThread.jsp
 */
package yeah;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Oscar
 */
@WebServlet(name = "MessageGetServlet", urlPatterns
        = {
            "/lookup"
        })
public class MessageGetServlet extends HttpServlet {

    private Logger logger;
    @PersistenceContext(unitName = "MessageBoardPU")
    private EntityManager entityManager; //field

    public MessageGetServlet() {
        logger = Logger.getLogger(getClass().getName());
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        //check if new user logging in, by checking if there is a user parameter in the request.
        String user = request.getParameter("user");
        if (user != null) {
            //new user, session bean
            String beanName = "user";
            HttpSession session = request.getSession();
            session.setAttribute(beanName, user);
        }

        //find the id of requested post to query database
        long requestedPostID;
        try {
            //postid request bean
            requestedPostID = Long.parseLong(request.getParameter("postid"));
        } catch (NumberFormatException e) {
            //for getting all parent posts.
            requestedPostID = 0;
        }
        List<Post> postList = null;
        //query database for posts with id = requested id & parent id = requested id for child posts.
        String jpqlCommand = "SELECT p FROM Post p WHERE (p.postID = :postid OR p.parentPost = :postid)";
        Query query = entityManager.createQuery(jpqlCommand);
        query.setParameter("postid", requestedPostID);
        postList = query.getResultList();
        logger.log(Level.INFO, "Successfully executed jpql query for postID {0}", requestedPostID);

        if (postList.size() > 0) {
            //sort the posts by id, to give chronological view.
            Collections.sort(postList);
            //reverse order when showing all new threads, to show most recent first.
            //session bean that is changed throughout the session
            String beanName = "parentPost";
            HttpSession session = request.getSession();
            session.setAttribute(beanName, requestedPostID);
            if (requestedPostID == 0) {
                //not in a thread
                Collections.reverse(postList);
                request.setAttribute("PostList", postList);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AllThreads.jsp");
                dispatcher.forward(request, response);
            } else {
                //in a thread
                request.setAttribute("PostList", postList);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/MessageThread.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
