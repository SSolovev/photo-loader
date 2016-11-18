package srg.api.app;

import org.springframework.boot.autoconfigure.web.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public String error() {
        return "ERROR!";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
