package br.com.viasoft.kubernetes.commands;

import com.google.cloud.dialogflow.v2.QueryResult;

public interface ExecuteCommand {

    String execute(String service, QueryResult queryResult) throws Exception;

    Boolean isIntent(String intentName);

}
