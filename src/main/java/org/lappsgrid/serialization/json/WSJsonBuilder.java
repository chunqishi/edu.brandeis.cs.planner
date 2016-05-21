package org.lappsgrid.serialization.json;

import org.lappsgrid.discriminator.Discriminators;

/**
 * Created by lapps on 10/22/2014.
 * REF:  http://lapps.github.io/interchange/index.html
 */
public class WSJsonBuilder {
    String context = "http://vocab.lappsgrid.org/context-1.0.0.jsonld";

    String input;
    String output;
    JsonObj error = null;
    JsonObj json = null;
    JsonArr pipelines = null;
    String discriminator = null;
    int n = 1;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public WSJsonBuilder() {
        json = new JsonObj();
        error = new JsonObj();
        pipelines = new JsonArr();
        n = 1;
    }

    public WSJsonBuilder(String textjson) {
        this();
        json = new JsonObj(textjson);
        input = json.getString("input");
        output = json.getString("output");
        if (json.has("n")) {
            n = json.getInt("n");
        }
    }


    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public JsonArr newPipeline() {
        JsonObj pipeline = new JsonObj();
        pipeline.put("@type", "PipeLine");
        JsonArr components = new JsonArr();
        pipeline.put("components", components);
        pipelines.put(pipeline);
        return components;
    }

    public JsonObj addComponent(JsonArr components, String id) {
        JsonObj component = new JsonObj();
        component.put("@type", "Component");
        component.put("id", id);
        components.put(component);
        return component;
    }

    public String toString() {
        json.put("discriminator", discriminator);
        if (discriminator.equals(Discriminators.Uri.JSON_LD)) {
            json.put("pipelines", pipelines);

        } else if (discriminator.equals(Discriminators.Uri.ERROR)) {
            json.put("payload", error);
        }
        return json.toString();
    }

    public void setError(String msg, String stacktrace) {
        this.setDiscriminator(Discriminators.Uri.ERROR);
        JsonObj val = new JsonObj();
        val.put("@value", msg);
        val.put("stacktrace", stacktrace);
        error.put("text", val);
    }
}