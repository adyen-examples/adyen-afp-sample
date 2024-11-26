package com.adyen.controller;

import com.adyen.model.User;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BaseController {

    private final Logger log = LoggerFactory.getLogger(BaseController.class);

    private final static String LOGGED_USER_ID_ATTRIBUTE_NAME = "LoggedUserId";
    private final static String LOGGED_USER_ATTRIBUTE_NAME = "LoggedUser";

    @Autowired
    protected HttpSession session;

    /**
     * Store userId on session
     * @param userId
     */
    public void setUserIdOnSession(String userId) {
        session.setAttribute(LOGGED_USER_ID_ATTRIBUTE_NAME, userId);
    }

    /**
     * Store user on session
     * @param user
     */
    public void setUserOnSession(User user) {
        session.setAttribute(LOGGED_USER_ATTRIBUTE_NAME, user);
    }

    /**
     * Get userId from session
     * @return
     */
    public String getUserIdOnSession() {
        return (String) session.getAttribute(LOGGED_USER_ID_ATTRIBUTE_NAME);
    }

    /**
     * Remove userId from session
     */
    public void removeUserIdFromSession() {
        session.removeAttribute(LOGGED_USER_ID_ATTRIBUTE_NAME);
    }


    /**
     * Get user from session
     * @return
     */
    public User getUserOnSession() {
        return (User) session.getAttribute(LOGGED_USER_ATTRIBUTE_NAME);
    }

    /**
     * Remove user from session
     */
    public void removeUserFromSession() {
        session.removeAttribute(LOGGED_USER_ATTRIBUTE_NAME);
    }


    // Add an attribute to the session
    protected void addAttribute(String attributeName, Object attributeValue) {
        session.setAttribute(attributeName, attributeValue);
    }

    // Remove an attribute from the session
    protected void removeAttribute(String attributeName) {
        session.removeAttribute(attributeName);
    }

    // Get an attribute from the session
    protected Object getAttribute(String attributeName) {
        return session.getAttribute(attributeName);
    }

}
