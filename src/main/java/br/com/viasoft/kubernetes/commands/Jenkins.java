package br.com.viasoft.kubernetes.commands;

import com.google.cloud.dialogflow.v2.QueryResult;
import org.springframework.stereotype.Service;

@Service
public class Jenkins implements ExecuteCommand {

    @Override
    public String execute(String service, QueryResult queryResult) throws Exception {
        String commandJenkins = "curl -X POST http://192.168.3.195:8080/job/"+service+"-gcloud/build --user admin:32dabab76ce99e7359d986241213807d";
        Runtime.getRuntime().exec(commandJenkins);
        return "Build do serviço " + service + " agendado";
    }

    @Override
    public Boolean isIntent(String intentName) {
        return intentName.equals("Jenkins") || intentName.equals("Jenkins deploy servicos");
    }
}
