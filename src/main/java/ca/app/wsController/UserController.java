package ca.app.wsController;

import ca.app.user.service.ISVService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private ISVService isvService;

    @RequestMapping(value="users", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String getUsers() {
        Gson g = new Gson();
        return g.toJson(isvService.readUsers());
    }


}

