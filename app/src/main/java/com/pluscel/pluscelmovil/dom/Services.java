package com.pluscel.pluscelmovil.dom;

import java.util.List;

/**
 * Created by Pablo Pincheira on 11/10/15.
 */
public class Services {

    List<IsisService> value;
    Object extensions;
    List<RestLink> links;

    public List<IsisService> getValue() {
        return value;
    }

    public void setValue(List<IsisService> value) {
        this.value = value;
    }

    public Object getExtensions() {
        return extensions;
    }

    public void setExtensions(Object extensions) {
        this.extensions = extensions;
    }

    public List<RestLink> getLinks() {
        return links;
    }

    public void setLinks(List<RestLink> links) {
        this.links = links;
    }
}
