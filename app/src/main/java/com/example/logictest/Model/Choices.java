package com.example.logictest.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Choices implements Serializable {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("prompt")
    @Expose
    private String prompt;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("jslogic")
    @Expose
    private String jslogic;
    @SerializedName("choices")
    @Expose
    private List<String> choices = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getJslogic() {
        return jslogic;
    }

    public void setJslogic(String jslogic) {
        this.jslogic = jslogic;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

}
