package kz.sagrad.halykoperator;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MenuElement {
    public String id = "";
    public String parent = "";
    public String text = "";
    public boolean folder = false;

    @JsonIgnore
    public boolean available = false;

    public MenuElement () {
        //for Firebase
    }

    public MenuElement(String parent, String id, String text, Boolean folder) {
        this.parent = parent;
        this.id = id;
        this.text = text;
        this.folder = folder;
    }

    public String getId() {
        return id;
    }

    public String getParent() {
        return parent;
    }

    public String getText() {
        return text;
    }

    public boolean isFolder() {
        return folder;
    }
}
