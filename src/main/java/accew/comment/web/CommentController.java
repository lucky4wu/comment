package accew.comment.web;

import accew.comment.model.Comment;
import accew.comment.service.CommentService;
import accew.common.BaseController;
import accew.common.MessageResult;
import accew.common.file.PropertyUtil;
import accew.modules.pagger.PageListDTO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by CrazyFive on 2017/2/28.
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController{

    private static Logger logger = accew.modules.logger.Logger.getLog(CommentController.class);

    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public String listPage(HttpServletRequest request, Model model, Comment comment,
                           @RequestParam("page") long page, @RequestParam("pageSize") long pageSize){
        Map<String, Object> map = getParamMap(request);

        PageListDTO<Comment> pageListDTO = commentService.queryCheckedPage(map, page, pageSize);

        return JSON.toJSONString(pageListDTO);
    }


    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(Comment c, HttpServletRequest request, HttpServletResponse response, Model model){
        String userNo = getUserNo(request, response);
        c.setCreateUser(userNo);

        saveUploadFile(request, c);

        commentService.addComment(c);

        model.addAttribute("userNo", userNo);

        return "/comment/list";
    }

    private void saveUploadFile(HttpServletRequest request, Comment c){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)){
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            while(iter.hasNext()){
                MultipartFile file = multiRequest.getFile(iter.next().toString());

                if (file != null){
                    if (StringUtils.isEmpty(file.getOriginalFilename())){
                        continue;
                    }
                    String newFileName = UUID.randomUUID().toString();
                    int start = file.getOriginalFilename().indexOf(".");
                    if (start != -1){
                        newFileName = newFileName.concat(file.getOriginalFilename().substring(start, file.getOriginalFilename().length()));
                    }else {
                        newFileName = newFileName.concat(".jpg");
                    }
                    String path = PropertyUtil.getProperty("file.properties", "uploadFilePath") + newFileName;
                    c.setImageUrl("/upload/" + newFileName);
                    try {
                        file.transferTo(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @RequestMapping(value = "/replyContent/{id}", method = RequestMethod.POST)
    public String replyContent(@PathVariable Long id, Comment comment, HttpServletRequest request, HttpServletResponse response, Model model){
        String userNo = getUserNo(request, response);
        comment.setId(id);

        saveUploadFile(request, comment);

        MessageResult mr;
        try {
            commentService.replyContent(comment, userNo);
            response.sendRedirect("/comment/contentList/"+id);
            mr = getSuccessMsg();
        }catch (Exception e){
            e.printStackTrace();
            mr = getCodeAndMsg(999, e.getMessage());
        }
        model.addAttribute("id", id);

        return "/comment/contentList";
    }

    @ResponseBody
    @RequestMapping(value = "/getParentTitleAndContent/{id}", method = RequestMethod.GET)
    public String getParentTitleAndContent(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response){
        List<Comment> commentList = commentService.queryParentTitleAndContent(id);

        return JSONObject.valueToString(commentList);
    }

    @RequestMapping("/contentList/{id}")
    public String redirectToContentList(@PathVariable Long id, HttpServletRequest request, Model model){
        model.addAttribute("id", id);

        return "/comment/contentList";
    }

    @RequestMapping("/remind/{userNo}")
    public String remind(@PathVariable String userNo, HttpServletRequest request){

        return null;
    }

}
