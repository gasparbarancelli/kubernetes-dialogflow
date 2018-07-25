package br.com.viasoft.kubernetes.commands;

import com.google.cloud.dialogflow.v2.QueryResult;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class Versao implements ExecuteCommand {

    @Override
    public String execute(String service, QueryResult queryResult) throws Exception {
        String commandVersion = "kubectl get deployments "+service+"-deployment -o jsonpath='{.spec.template.spec.containers[0].image}'";
        Process process = Runtime.getRuntime().exec(commandVersion);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String versao = stdInput.readLine();
        versao = versao.substring(versao.lastIndexOf(":") + 1, versao.length() - 1);
        if (versao.equals("latest")) {
            return "O serviço " + service + " está rodando na última versão";
        } else {
            return "O serviço " + service + " está rodando na versão " + versao;
        }
    }

    @Override
    public Boolean isIntent(String intentName) {
        return intentName.equals("Versao") || intentName.equals("Versao servico");
    }
}
