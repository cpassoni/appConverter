package ca.app.wsController;

import ca.app.user.service.ISVService;
import ca.app.user.vo.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller responsible for provide users information
 */
@RestController
public class UserController {

    @Autowired
    private ISVService isvService;

    @RequestMapping(value="users", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<UserBean> getUsers() {
        return isvService.readUsers();
    }
}

