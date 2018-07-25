package br.com.viasoft.kubernetes;

public interface KubernetesService {

    String execute(String msg);

    void resetContext();

}
