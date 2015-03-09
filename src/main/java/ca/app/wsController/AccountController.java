package ca.app.wsController;

import ca.app.user.service.ISVService;
import ca.app.user.vo.AccountBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller responsible for provide accounts information
 */
@RestController
public class AccountController {
    
    @Autowired
    private ISVService isvService;

    @RequestMapping(value="accounts/{uuid}", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public AccountBean getAccount(@RequestParam(value="uuid") String uuid) {
        return isvService.readAccountByUUID(uuid);
    }

    @RequestMapping(value="accounts", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<AccountBean> getAllAccount() {
        return isvService.readAccounts();
    }


}

