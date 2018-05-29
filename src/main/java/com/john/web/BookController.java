package com.john.web;

import com.john.dto.AppointExecution;
import com.john.dto.Result;
import com.john.enums.AppointmentStateEnum;
import com.john.exception.NoNumberException;
import com.john.exception.RepeatAppointException;
import com.john.pojo.Appointment;
import com.john.pojo.Book;
import com.john.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Project miaosha
 * @Created with IntelliJ IDEA.
 * @User: zhanghuan
 * @Author: 张桓
 * @Email: yz30.com@aliyun.com
 * @QQ: 248404941
 * @Date: 2018/5/22
 * @Time: 17:17
 * 　　　　　　　　　　　　　　　 _ooOoo_
 * 　　　　　　　　　　　　　　　o8888888o
 * 　　　　　　　　　　　　　　　88" . "88
 * 　　　　　　　　　　　　　　　(| -_- |)
 * 　　　　　　　　　　　　　　　 O\ = /O
 * 　　　　　　　　　　　　　 ____/`---'\____
 * 　　　　　　　　　　　　 .   ' \\| |// `.
 * 　　　　　　　　　　　　 / \\||| : |||// \
 * 　　　　　　　　　　　　/ _||||| -:- |||||- \
 * 　　　　　　　　　　　     | | \\\ - /// | |
 * 　　　　　　　　　　　　　| \_| ''\---/'' | |
 * 　　　　　　　　　　　　 \ .-\__ `-` ___/-. /
 * 　　　　　　　　　　 ___`. .' /--.--\ `. . __
 * 　　　　　　　　 ."" '< `.___\_<|>_/___.' >'"".
 * 　　　　　　　　| | : `- \`.;`\ _ /`;.`/ - ` : | |
 * 　　　　　　　　   \ \ `-. \_ __\ /__ _/ .-` / /
 * 　　　　　======`-.____`-.___\_____/___.-`____.-'======
 * 　　　　　　　　　　　　　　　 `=---='
 * 　　　　　 .............................................
 * 　　　　　　　　　　佛祖保佑             永无BUG
 * 　　　　　佛曰:
 * 　　　　　　　　　　写字楼里写字间，写字间里程序员；
 * 　　　　　　　　　　程序人员写程序，又拿程序换酒钱。
 * 　　　　　　　　　　酒醒只在网上坐，酒醉还来网下眠；
 * 　　　　　　　　　　酒醉酒醒日复日，网上网下年复年。
 * 　　　　　　　　　　但愿老死电脑间，不愿鞠躬老板前；
 * 　　　　　　　　　　奔驰宝马贵者趣，公交自行程序员。
 * 　　　　　　　　　　别人笑我忒疯癫，我笑自己命太贱；
 * 　　　　　　　　　　不见满街漂亮妹，哪个归得程序员？
 */
@Controller
@RequestMapping("/book")
// url ： /模块/资源/{id}/细分/seckill/list
public class BookController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Book> list = bookService.getList();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping(value = "/{bookId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("bookId") Long bookId, Model model){
        if (bookId == null){
            return "redirect:/list";
        }
        Book book = bookService.getById(bookId);
        if (book == null){
            return "forward:/list";
        }
        model.addAttribute("book", book);
        return "detail";
    }

    @RequestMapping(value = "/{bookId}/appoint", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Result<AppointExecution> appoint(@PathVariable("bookId") Long bookId, @RequestParam("studentId") Long studentId){
        if (studentId == null || studentId.equals("")){
            return new Result<AppointExecution>(false, "学号不能为空");
        }
        AppointExecution execution = null;
        try {
            execution = bookService.appoint(bookId, studentId);
        }catch (NoNumberException e1){
            execution = new AppointExecution(bookId, AppointmentStateEnum.NO_NUMBER);
        } catch (RepeatAppointException e2){
            execution = new AppointExecution(bookId, AppointmentStateEnum.REPEAT_APPOINT);
        } catch (Exception e){
            execution = new AppointExecution(bookId, AppointmentStateEnum.INNER_ERROR);
        }
        return new Result<AppointExecution>(true, execution);
    }


}
