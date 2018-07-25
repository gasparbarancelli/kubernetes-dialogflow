package br.com.viasoft.kubernetes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kubernetes")
public class KubernetesController {

    private final KubernetesService kubernetesService;

    @Autowired
    public KubernetesController(KubernetesService kubernetesService) {
        this.kubernetesService = kubernetesService;
    }

    @GetMapping("execute")
    public String execute(@RequestParam("msg") String msg) {
        return kubernetesService.execute(msg);
    }

    @GetMapping("resetContext")
    public void resetContext() {
        kubernetesService.resetContext();
    }

}
