/*
 * This class is a servlet for uploading messages to the database. It redirects after uploading to the message get servlet.
 */
package yeah;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Oscar
 */
@WebServlet(name = "MessageUploadServlet", urlPatterns
        = {
            "/PostMessage"
        })
public class MessageUploadServlet extends HttpServlet {

    private Logger logger;
    //entity manager factory for the one method get persistence unit util get identifier.
    @PersistenceUnit(unitName="MessageBoardPU")
    private EntityManagerFactory entityManagerFactory; //field
    @PersistenceContext(unitName="MessageBoardPU")
    private EntityManager entityManager; //field
    @Resource
    private UserTransaction userTransaction; //field

    public MessageUploadServlet() {
        logger = Logger.getLogger(getClass().getName());
    }
    
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String content = request.getParameter("content");
        //create new post object for saving to the database
        Post tempPost = new Post();
        tempPost.setContent(content);
        //access session bean for parent post
        String beanName = "parentPost";
        HttpSession session = request.getSession();
        long parentPostID = (long)session.getAttribute(beanName);
        tempPost.setParentPost(parentPostID);
        //access session bean for user
        beanName = "user";
        String user = (String)session.getAttribute(beanName);
        tempPost.setUser(user);
        //try and persist entity to database.
        try {
            userTransaction.begin();
            entityManager.persist(tempPost);
            userTransaction.commit();
            System.out.println("message saved");
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(MessageUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (parentPostID == 0){
        //getting id of post just saved to database
            long newPostID = (long) entityManagerFactory.getPersistenceUnitUtil().getIdentifier(tempPost);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/lookup?postid=" + newPostID);
            dispatcher.forward(request, response);
        }else{
            //have to do it with parent post id to show whole thread instead of just new post
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/lookup?postid=" + parentPostID);
            dispatcher.forward(request, response);
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
