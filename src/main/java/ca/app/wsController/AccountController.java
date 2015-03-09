package ca.app.wsController;

import ca.app.user.service.ISVService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    
    @Autowired
    private ISVService isvService;

    @RequestMapping(value="accounts/{uuid}", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String getAccount(@RequestParam(value="uuid") String uuid) {
        Gson g = new Gson();
        return g.toJson(isvService.readAccountByUUID(uuid));
    }

    @RequestMapping(value="accounts", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String getAllAccount() {
        Gson g = new Gson();
        return g.toJson(isvService.readAccounts());
    }


}

