package org.wtiger.inno.litportal.web.controlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wtiger.inno.litportal.models.pojo.CommentPojo;
import org.wtiger.inno.litportal.models.pojo.UserPojo;
import org.wtiger.inno.litportal.services.ServiceComments;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import javax.servlet.http.HttpSession;
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


    @RequestMapping(value = "/editComment", method = RequestMethod.GET)
    public String getCommentEditPage(Model model,
                                     @RequestParam(name = "comment_uuid") String commentUuidParam) {
        String result = "editComment";
        try {
            UUID commentUuid = UUID.fromString(commentUuidParam);
            CommentPojo pojo = serviceComments.getCommentByID(commentUuid);
            model.addAttribute("commentUuid", pojo.getCommentUuid());
            model.addAttribute("body", pojo.getBody());
        } catch (ServiceException e) {
            result = "redirect: ./dbError";
        } catch (IllegalArgumentException e) {
            result = "404page";
        }
        return result;
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.GET)
    public String getCommentAddPage(Model model,
                                    @RequestParam(name = "post_uuid") String postUuidParam) {
        String result = "addComment";
        try {
            UUID postUuid = UUID.fromString(postUuidParam);
            model.addAttribute("postUuid", postUuid);
        } catch (IllegalArgumentException e) {
            result = "404page";
        }
        return result;
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    public String deleteComment(CommentPojo comment) {
        String result = "redirect:./post?post_uuid=" + comment.getPostUuid();
        try {
            serviceComments.deleteComment(comment);
        } catch (ServiceException e) {
            result = "dbError";
        }
        return result;
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.GET)
    public String getCommentDeletePage(Model model,
                                       @RequestParam(name = "comment_uuid") String commentUuidParam) {
        String result = "deleteComment";
        try {
            UUID commentUuid = UUID.fromString(commentUuidParam);
            CommentPojo commentPojo = serviceComments.getCommentByID(commentUuid);
            model.addAttribute("comment", commentPojo);
        } catch (IllegalArgumentException e) {
            result = "404page";
        } catch (ServiceException e) {
            result = "404page";
        }
        return result;
    }

    @RequestMapping(value = "/editComment", method = RequestMethod.POST)
    public String editComment(Model model,
                              @RequestParam("commentUuid") String commentUuidParam,
                              @RequestParam("body") String body) {
        String result = null;
        if (commentUuidParam.equals("")) {
            result = "redirect:./dbError";
        }
        UUID commentUuid = UUID.fromString(commentUuidParam);
        CommentPojo comment = null;
        try {
            comment = serviceComments.getCommentByID(commentUuid);
        } catch (ServiceException e) {
            result = "dbError";
        }
        comment.setBody(body);
        result = "redirect:./post?post_uuid=" + comment.getPostUuid();
        try {
            serviceComments.updateComment(comment);
        } catch (ServiceException e) {
            result = "dbError";
        }
        return result;
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(Model model,
                             @RequestParam("postUuid") String postUuidParam,
                             @RequestParam("body") String body,
                             HttpSession session) {
        String result = null;
        if (postUuidParam.equals("")) {
            result = "dbError";
        }
        UUID postUuid = UUID.fromString(postUuidParam);
        UserPojo usersPojo = (UserPojo) session.getAttribute("user");
        try {
            serviceComments.createNewComment(postUuid, body, usersPojo.getUserUuid());
            result = "redirect:./post?post_uuid=" + postUuidParam;
        } catch (ServiceException e) {
            result = "dbError";
        }
        return result;
    }
}
