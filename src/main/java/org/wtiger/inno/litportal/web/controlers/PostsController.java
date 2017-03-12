package org.wtiger.inno.litportal.web.controlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wtiger.inno.litportal.models.rows.CommentsEntity;
import org.wtiger.inno.litportal.models.rows.GroupsEntity;
import org.wtiger.inno.litportal.models.rows.PostsEntity;
import org.wtiger.inno.litportal.services.ServiceComments;
import org.wtiger.inno.litportal.services.ServiceGroups;
import org.wtiger.inno.litportal.services.ServicePosts;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

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
            GroupsEntity group = serviceGroups.getObjectById(group_uuid);
            List<GroupsEntity> groups = serviceGroups.getListOfGroupsByParentID(group_uuid);
            List<PostsEntity> posts = servicePosts.getPostsByGroupID(group_uuid);
            model.addAttribute("group", group);
            model.addAttribute("groups", groups);
            model.addAttribute("posts", posts);
            result = "posts";
        } catch (ServiceException e) {
            logger.error("Сервиса при работе с главной страницей", e);
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
                PostsEntity post = servicePosts.getPostByID(postUuid);
                model.addAttribute("post", post);
                List<CommentsEntity> comments = serviceComments.getCommentsByID(post.getPostUuid());
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
}
