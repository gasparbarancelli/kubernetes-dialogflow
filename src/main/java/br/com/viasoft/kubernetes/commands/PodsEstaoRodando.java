package br.com.viasoft.kubernetes.commands;

import com.google.cloud.dialogflow.v2.QueryResult;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class PodsEstaoRodando implements ExecuteCommand {

    @Override
    public String execute(String service, QueryResult queryResult) throws Exception {
        String commandGetPods = "kubectl get deployment "+service+"-deployment -o jsonpath='{.spec.replicas}'";

        Process process = Runtime.getRuntime().exec(commandGetPods);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int quantidade = Integer.parseInt(stdInput.readLine().replaceAll("\\D+", ""));
        if (quantidade == 0) {
            return "Nenhum serviço está rodando";
        } else if (quantidade == 1) {
            return "Um uníco serviço está rodando";
        } else  {
            return quantidade + " serviços estão rodando";
        }
    }

    @Override
    public Boolean isIntent(String intentName) {
        return intentName.equals("Quantos pods estão rodando");
    }

}
