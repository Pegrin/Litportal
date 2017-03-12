package org.wtiger.inno.litportal.web.controlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wtiger.inno.litportal.models.rows.CommentsEntity;
import org.wtiger.inno.litportal.services.ServiceComments;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.UUID;

/**
 * @author Khayrutdinov Marat
 *         Email: mail@wtiger.org
 *         Created on 12.03.2017.
 */
@Controller
public class CommentsController {
    private Logger logger = Logger.getLogger(CommentsController.class);
    private ServiceComments serviceComments;

    @Autowired
    public void setServiceComments(ServiceComments serviceComments) {
        this.serviceComments = serviceComments;
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.GET)
    public String getCommentEditPage(Model model,
                                     @RequestParam(name = "post_uuid") String postUuidParam) {
        String result = "editComment";
        try {
            UUID postUuid = UUID.fromString(postUuidParam);
            model.addAttribute("comment", serviceComments.getCommentsByID(postUuid));
        } catch (ServiceException e) {
            result = "redirect: ./dbError";
        } catch (IllegalArgumentException e) {
            result = "404page";
        }
        return result;
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(Model model,
                             @ModelAttribute("comment") CommentsEntity comment) {
        String result = null;
        if (comment == null || comment.getPostUuid() == null) {
            result = "redirect:./dbError";
        }
        result = "redirect:./post?post_uuid=" + comment.getPostUuid();
        try {
            serviceComments.CreateNewComment(comment);
        } catch (ServiceException e) {
            result = "redirect:./dbError";
        }
        return result;
    }

    @ModelAttribute("comment")
    public CommentsEntity getPerson() {
        return new CommentsEntity();
    }
}
