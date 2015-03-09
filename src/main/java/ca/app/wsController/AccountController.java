package ca.app.wsController;

import ca.app.integration.service.EventService;
import ca.app.integration.vo.APIResult;
import ca.app.user.service.ISVService;
import ca.app.user.vo.AccountBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

