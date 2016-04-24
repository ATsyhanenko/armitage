package org.armitage.inc.AAInfo.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@XmlRootElement
@Component
public class DataRoot {
    private List<LocationXML> locations = new ArrayList<LocationXML>();

    @XmlElementWrapper(name="locations")
    @XmlElement(name="location")
    public List<LocationXML> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationXML> locations) {
        this.locations = locations;
    }
}
