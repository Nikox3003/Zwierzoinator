package me.nikox.zwierzoinator.objects;

public class Variable {

    private String variable, replacement;

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public Variable(String variable, String replacement) {
        this.variable = variable;
        this.replacement = replacement;
    }
}
