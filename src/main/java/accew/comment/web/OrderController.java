package accew.comment.web;

import accew.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by CrazyFive on 2017/3/26.
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    @RequestMapping("orderForm")
    public String orderForm(HttpServletRequest request){

        return "order/order";
    }
}
