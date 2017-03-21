package org.wtiger.inno.litportal.web.controlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wtiger.inno.litportal.models.pojo.CommentPojo;
import org.wtiger.inno.litportal.models.pojo.GroupPojo;
import org.wtiger.inno.litportal.models.pojo.PostPojo;
import org.wtiger.inno.litportal.models.pojo.UserPojo;
import org.wtiger.inno.litportal.services.ServiceComments;
import org.wtiger.inno.litportal.services.ServiceGroups;
import org.wtiger.inno.litportal.services.ServicePosts;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 08.03.2017.
 */
@Controller
public class PostsController {
    private static Logger logger = Logger.getLogger(PostsController.class);
    private ServiceGroups serviceGroups;
    private ServicePosts servicePosts;
    private ServiceComments serviceComments;

    @Autowired
    public void setServiceGroups(ServiceGroups serviceGroups) {
        this.serviceGroups = serviceGroups;
    }

    @Autowired
    public void setServicePosts(ServicePosts servicePosts) {
        this.servicePosts = servicePosts;
    }

    @Autowired
    public void setServiceComments(ServiceComments serviceComments) {
        this.serviceComments = serviceComments;
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public String showPostsList(Model model,
                                @RequestParam(name = "group_uuid", value = "", required = false) String groupUuidParameter) {
        String result = "";
        UUID group_uuid = null;
        if (groupUuidParameter != null && !groupUuidParameter.equals("")) {
            group_uuid = UUID.fromString(groupUuidParameter);
        }
        try {
            GroupPojo group = serviceGroups.getObjectById(group_uuid);
            if (group != null) group.setBody(serviceGroups.TextToHTML(group.getBody()));
            List<GroupPojo> groups = serviceGroups.getListOfGroupsByParentID(group_uuid);
            List<PostPojo> posts = servicePosts.getPostsByGroupID(group_uuid);
            model.addAttribute("group", group);
            model.addAttribute("groups", groups);
            model.addAttribute("posts", posts);
            result = "posts";
        } catch (ServiceException e) {
            logger.error("Ошибка сервиса при работе с главной страницей", e);
            result = "dbError";
        }
        return result;
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String showPost(Model model,
                           @RequestParam(name = "post_uuid", defaultValue = "", required = false) String postUuidParameter) {
        String result = "";
        if (postUuidParameter != null && !postUuidParameter.equals("")) {
            try {
                UUID postUuid = UUID.fromString(postUuidParameter);
                PostPojo post = servicePosts.getPostByID(postUuid);
                if (post != null) post.setBody(servicePosts.TextToHTML(post.getBody()));
                model.addAttribute("post", post);
                List<CommentPojo> comments = serviceComments.getCommentsByPostID(post.getPostUuid());
                comments.forEach((el) -> el.setBody(serviceComments.TextToHTML(el.getBody())));
                model.addAttribute("comments", comments);
                result = "post";
            } catch (ServiceException e) {
                logger.error("Ошибка сервиса при получении публикации и списка комментариев.", e);
                result = "dbError";
            } catch (IllegalArgumentException e) {
                result = "postNotFound";
            }
        } else {
            result = "postNotFound";
        }
        return result;
    }

    @RequestMapping(value = "/myPosts", method = RequestMethod.GET)
    public String getMyPostsPage(Model model,
                                 HttpSession session) {
        String result = "myPosts";
        try {
            UserPojo userPojo = (UserPojo) session.getAttribute("user");
            if (userPojo != null) {
                List<PostPojo> posts = servicePosts.getPostsByUser(userPojo.getUserUuid());
                model.addAttribute("posts", posts);
            }
        } catch (ServiceException e) {
            logger.error("Ошибка сервиса при работе с главной страницей", e);
            result = "dbError";
        }
        return result;
    }
}
