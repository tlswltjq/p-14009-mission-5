package com.back.wiseSaying;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String command;
    private Map<String, String> params = new HashMap<>();

    public Request(String inputCommand) {
        parseCommand(inputCommand);
    }

    public String getCommand() {
        return command;
    }

    public Map<String, String> getParams() {
        return params;
    }

    void parseCommand(String inputCommand) {
        String[] parts = inputCommand.split("\\?", 2);
        this.command = parts[0];

        if (parts.length > 1) {
            String[] paramPairs = parts[1].split("&");
            for (String pair : paramPairs) {
                String[] keyValue = pair.split("=", 2);
                String key = keyValue[0];
                String value = keyValue.length > 1 ? keyValue[1] : "";
                this.params.put(key, value);
            }
        }
    }
    public Integer getIntParam(String name, int defaultValue) {
        try {
            return Integer.parseInt(params.getOrDefault(name, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
