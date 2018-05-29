package androidpractice.demo.com.citrixtestproject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Contacts {

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public LinkedHashMap<String, ArrayList<String>> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(LinkedHashMap<String, ArrayList<String>> contactDetails) {
        this.contactDetails = contactDetails;
    }

    private String contactName;
    private LinkedHashMap<String,ArrayList<String>> contactDetails;
}
